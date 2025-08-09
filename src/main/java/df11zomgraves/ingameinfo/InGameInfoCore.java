package df11zomgraves.ingameinfo;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import df11zomgraves.ingameinfo.gui.overlay.Info;
import df11zomgraves.ingameinfo.gui.overlay.InfoText;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.parser.IParser;
import df11zomgraves.ingameinfo.parser.json.JsonParser;
import df11zomgraves.ingameinfo.parser.text.TextParser;
import df11zomgraves.ingameinfo.parser.xml.XmlParser;
import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import df11zomgraves.ingameinfo.value.Value;
import df11zomgraves.ingameinfo.value.ValueComplex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InGameInfoCore {
	private static final Pattern PATTERN = Pattern.compile("\\{ICON\\|( *)\\}", Pattern.CASE_INSENSITIVE);
	private static final Matcher MATCHER = PATTERN.matcher("");
	public static final InGameInfoCore INSTANCE = new InGameInfoCore();

	private IParser parser;

	private final Minecraft minecraft = Minecraft.getInstance();
	private File configDirectory = null;
	private File configFile = null;
	private final Map<Alignment, List<List<Value>>> format = new HashMap<Alignment, List<List<Value>>>();
	private final List<Info> info = new ArrayList<Info>();
	private final List<Info> infoItemQueue = new ArrayList<Info>();

	private InGameInfoCore() {
		Tag.setInfo(this.infoItemQueue);
		Value.setInfo(this.infoItemQueue);
	}

	public boolean setConfigDirectory(final File directory) {
		this.configDirectory = directory;
		return true;
	}

	public File getConfigDirectory() {
		return this.configDirectory;
	}

	public boolean setConfigFile(final String filename) {
		final File file = new File(this.configDirectory, filename);
		if (file.exists()) {
			if (filename.endsWith(Names.Files.EXT_XML)) {
				this.configFile = file;
				this.parser = new XmlParser();
				return true;
			} else if (filename.endsWith(Names.Files.EXT_JSON)) {
				this.configFile = file;
				this.parser = new JsonParser();
				return true;
			} else if (filename.endsWith(Names.Files.EXT_TXT)) {
				this.configFile = file;
				this.parser = new TextParser();
				return true;
			}
		}

		InGameInfoXML.logger.warn("The config '{}' does not exist", filename);
		this.configFile = null;
		this.parser = new XmlParser();
		return filename.equalsIgnoreCase("default");
	}

	public void onTickClient() {
		final ClientLevel world = this.minecraft.level;
		if (world == null)
			return;
		Tag.setWorld(world);

		final LocalPlayer player = this.minecraft.player;
		if (player == null)
			return;
		Tag.setPlayer(player);
		
		Window window = this.minecraft.getWindow();
		final int scaledWidth = (int) (window.getGuiScaledWidth() / ConfigurationHandler.scale);
		final int scaledHeight = (int) (window.getGuiScaledHeight() / ConfigurationHandler.scale);
		this.info.clear();
		int x, y;
		for (final Alignment alignment : Alignment.values()) {
			final List<List<Value>> lines = this.format.get(alignment);

			if (lines == null)
				continue;

			final Font fontRenderer = this.minecraft.font;
			final List<Info> queue = new ArrayList<Info>();

			for (final List<Value> line : lines) {
				StringBuilder str = new StringBuilder();

				this.infoItemQueue.clear();
				
				String s;
				for (final Value value : line) {
					s = getValue(value);
					str.append(s);
				}
				
				if (str.length() > 0) {
					final String processed = str.toString().replaceAll("\\{ICON\\|( *)\\}", "$1");

					x = alignment.getX(scaledWidth, fontRenderer.width(processed));
					final InfoText text = new InfoText(fontRenderer, processed, x, 0);
					
					if (this.infoItemQueue.size() > 0) {
						MATCHER.reset(str.toString());

						for (int i = 0; i < this.infoItemQueue.size() && MATCHER.find(); i++) {
							final Info item = this.infoItemQueue.get(i);
							item.x = fontRenderer.width(str.substring(0, MATCHER.start()));
							text.children.add(item);

							str = new StringBuilder(
									str.toString().replaceFirst(Pattern.quote(MATCHER.group(0)), MATCHER.group(1)));
							MATCHER.reset(str.toString());
						}
					}
					queue.add(text);
				}
			}

			y = alignment.getY(scaledHeight, queue.size() * (fontRenderer.lineHeight + 1));
			for (final Info item : queue) {
				item.y = y;
				this.info.add(item);
				y += fontRenderer.lineHeight + 1;
			}

			this.info.addAll(queue);
		}

		Tag.releaseResources();
		ValueComplex.ValueFile.tick();
	}

	public void onTickRender() {
		PoseStack poseStack = new PoseStack();
		poseStack.pushPose();
//		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

		for (final Info info : this.info) {
			info.draw(poseStack);
		}

//		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		poseStack.popPose();
	}

	public boolean loadConfig(final String filename) {
		return setConfigFile(filename) && reloadConfig();
	}

	public boolean reloadConfig() {
		this.info.clear();
		this.infoItemQueue.clear();
		this.format.clear();

		if (this.parser == null) {
			return false;
		}

		final InputStream inputStream = getInputStream();
		if (inputStream == null) {
			return false;
		}

		try {
			if (this.parser.load(inputStream) && this.parser.parse(this.format))
				return true;
		} catch (final Exception e) {
			minecraft.gui.getChat().addMessage(new TranslatableComponent(e.getMessage()));
            return false;
        }

		this.format.clear();
		return false;
	}

	private InputStream getInputStream() {
		InputStream inputStream = null;

		try {
			if (this.configFile != null && this.configFile.exists()) {
				InGameInfoXML.logger.debug("Loading file config...");
				inputStream = new FileInputStream(this.configFile);
			} else {
				InGameInfoXML.logger.debug("Loading default config...");
				final ResourceLocation resourceLocation = new ResourceLocation("ingameinfo",
						Names.Files.FILE_XML.toLowerCase(Locale.ENGLISH));
				final Resource resource = this.minecraft.getResourceManager().getResource(resourceLocation);
				inputStream = resource.getInputStream();
			}
		} catch (final Exception e) {
			InGameInfoXML.logger.error(e.getMessage(), e);
		}

		return inputStream;
	}

	private String getValue(final Value value) {
		try {
			if (value.isValidSize()) {
				return value.getReplacedValue();
			}
		} catch (final Exception e) {
			InGameInfoXML.logger.debug("Failed to get value!", e);
			return "null";
		}

		return "";
	}
}
