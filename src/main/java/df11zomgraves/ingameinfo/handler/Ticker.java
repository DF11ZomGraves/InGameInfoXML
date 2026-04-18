package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.network.PacketHandler;
import df11zomgraves.ingameinfo.network.RequestMSPTPacket;
import df11zomgraves.ingameinfo.network.RequestSeedPacket;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import df11zomgraves.ingameinfo.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Ticker {
	public static final Ticker INSTANCE = new Ticker();

	public static boolean enabled = true;

	private final Minecraft client = Minecraft.getInstance();
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;
	private boolean inGame = false;
	private long lastRemoteUpdate = 0;

	private boolean isRunning() {
		if (!enabled)
			return false;
		// a && b || !a && !b --> a == b
		Options options = this.client.options;
		if (options != null) {
			if (!ConfigurationHandler.showOnPlayerList && options.keyPlayerList.isDown())
				return false;
			if (options.hideGui)
				return false;
			if (options.renderDebug || options.renderDebugCharts)
				return false;
			if (this.client.gui == null)
				return true;
			if (this.client.screen instanceof GameModeSwitcherScreen)
				return true;
			return client.screen == null || ConfigurationHandler.showInChat && client.screen instanceof ChatScreen;
		}
		return false;
	}
	
	@SubscribeEvent
	public void onRenderGuiOverlayEvent(final RenderGuiOverlayEvent.Pre event) {
		NamedGuiOverlay overlay = event.getOverlay();
		boolean isSurvivalHUD = overlay == VanillaGuiOverlay.PLAYER_HEALTH.type()
				|| overlay == VanillaGuiOverlay.FOOD_LEVEL.type() || overlay == VanillaGuiOverlay.ARMOR_LEVEL.type();

		if (isSurvivalHUD && !ConfigurationHandler.showSurvivalHUD)
			event.setCanceled(true);
		else if (overlay == VanillaGuiOverlay.POTION_ICONS.type() && !ConfigurationHandler.showOverlayPotions)
			event.setCanceled(true);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRenderGuiEventPost(final RenderGuiEvent.Post event) {
		if (isRunning()) {
			this.core.onTickClient();
			this.core.onTickRender(event.getGuiGraphics());
		}
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		boolean inGameCurrent = (client.level == null ? false : true);
		if (inGame) {
			if (!inGameCurrent) {
				InGameInfoXML.seed = ConfigurationHandler.seed;
				InGameInfoXML.mspt = -1;
				InGameInfoXML.tps = -1;
				InGameInfoXML.serverInstalled = false;
				inGame = false;
			} else if (isRunning()) {
				IntegratedServer singleServer = client.getSingleplayerServer();
				if (singleServer != null && !client.isPaused()) {
					long[] times = singleServer.getTickTime(client.level.dimension());
					if (times != null) {
						double worldTickTime = MathUtils.mean(times) * 1.0E-6D;
						InGameInfoXML.mspt = worldTickTime;
						InGameInfoXML.tps = (worldTickTime == -1) ? -1 : Math.min(1000.0 / worldTickTime, 20);
					}
				} else if (InGameInfoXML.serverInstalled) {
					InGameInfoXML.serverInstalled = false;
					long delay = (System.currentTimeMillis() - lastRemoteUpdate);
					if (delay > 1500 || delay < 0) 
					try {
						PacketHandler.INSTANCE.sendToServer(new RequestMSPTPacket());
						lastRemoteUpdate = System.currentTimeMillis();
					} catch (Exception e) {
						InGameInfoXML.logger.error("Failed to get mspt.");
					}
				}
			}
		} else if (inGameCurrent)
			try {
				inGame = true;
				InGameInfoXML.serverInstalled = true;
				showVerionInfo();
				PacketHandler.INSTANCE.sendToServer(new RequestSeedPacket());
			} catch (Exception e) {
				InGameInfoXML.seed = ConfigurationHandler.seed;
			}
		onTick(event);
	}

	@SubscribeEvent
	public void onRenderTick(final TickEvent.RenderTickEvent event) {
		onTick(event);
	}

	private void onTick(final TickEvent event) {
		if ((!enabled || this.client.options == null) && event.type == TickEvent.Type.CLIENT)
			Tag.releaseResources();
	}

	private void showVerionInfo() {
		String keyToggle = KeyInputHandler.KEY_TOGGLE.getKey().getName();
		String keyReload = KeyInputHandler.KEY_RELOAD.getKey().getName();
		String keyToggle1 = Component.translatable(keyToggle).getString();
		String keyReload1 = Component.translatable(keyReload).getString();
		client.gui.getChat()
				.addMessage(Component.translatable(Names.VERSION_CHAT, Names.VERSION, keyToggle1, keyReload1));
	}
}
