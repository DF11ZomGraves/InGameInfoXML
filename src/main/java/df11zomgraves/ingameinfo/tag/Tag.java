package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.gui.overlay.Info;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.util.MBlockPos;
import df11zomgraves.ingameinfo.util.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import java.util.List;

public abstract class Tag {
	protected static final Minecraft minecraft = Minecraft.getInstance();
	protected static final MBlockPos playerPosition = new MBlockPos();
	protected static final Vector3f playerMotion = new Vector3f();
	protected static ClientLevel world;
	protected static LocalPlayer player;
	protected static List<Info> info;
	protected static long seed = 0;

	private String name = null;
	private String[] aliases = new String[0];

	public Tag() {
		setSeed(ConfigurationHandler.seed);
	}

	public Tag setName(final String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public Tag setAliases(final String... aliases) {
		this.aliases = aliases;
		return this;
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public boolean isIndexed() {
		return false;
	}

	public int getMaximumIndex() {
		return -1;
	}

	public String getRawName() {
		return this.name;
	}

	public String getFormattedName() {
		return this.name + (isIndexed() ? String.format("[0..%d]", getMaximumIndex()) : "");
	}

	public abstract String getCategory();
	public abstract String getValue();
	
	public static void setSeed(final long seed) {
		Tag.seed = seed;
	}

	public static void setWorld(final ClientLevel world) {
		Tag.world = world;
	}

	public static void setPlayer(final LocalPlayer player) {
		Tag.player = player;
		if (player != null) {
			playerPosition.set(player);
			playerMotion.set((float) (player.getX() - player.xOld), (float) (player.getY() - player.yOld),
					(float) (player.getZ() - player.zOld));
		}
	}

	public static void setInfo(final List<Info> info) {
		Tag.info = info;
	}

	public static void releaseResources() {
		setWorld(null);
		setPlayer(null);
		TagNearbyPlayer.releaseResources();
		TagPlayerEffect.releaseResources();
	}

	public static String getIconTag(final Info info) {
		String str = "";
		for (int i = 0; i < info.getWidth() && minecraft.font.width(str) < info.getWidth(); i++) {
			str += " ";
		}
		return String.format("{ICON|%s}", str);
	}
}
