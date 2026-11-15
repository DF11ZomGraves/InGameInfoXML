package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.network.ClientPacketHandler;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;

public class Ticker {
	public static final Ticker INSTANCE = new Ticker();
	public static boolean enabled = true;
	private final Minecraft client = Minecraft.getInstance();
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;
	private boolean inGame = false;
	private long lastRemoteUpdate = 0;

	private Ticker() {
	}

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(INSTANCE::onClientTick);
		HudRenderCallback.EVENT.register(INSTANCE::onRenderGuiPost);
	}

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

	public void onRenderGuiPost(final GuiGraphics guiGraphics, final float tickDelta) {
		if (isRunning()) {
			this.core.onTickClient();
			this.core.onTickRender(guiGraphics);
		} else {
			Tag.releaseResources();
		}
	}

	public void onClientTick(final Minecraft client) {
		boolean inGameCurrent = (client.level == null ? false : true);
		if (inGame) {
			if (!inGameCurrent) {
				InGameInfoXML.seed = ConfigurationHandler.seed;
				InGameInfoXML.mspt = -1;
				InGameInfoXML.tps = -1;
				InGameInfoXML.serverInstalled = false;
				inGame = false;
			} else if (isRunning() && InGameInfoXML.existMSPT) {
				long delay = (System.currentTimeMillis() - lastRemoteUpdate);
				if (delay > 1500 || delay < 0) {
					IntegratedServer singleServer = client.getSingleplayerServer();
					if (singleServer != null) {
						double worldTickTime = singleServer.getAverageTickTime();
						if (worldTickTime < 0)
							worldTickTime = -1;
						InGameInfoXML.mspt = worldTickTime;
						InGameInfoXML.tps = (worldTickTime == -1) ? -1 : Math.min(1000.0 / worldTickTime, 20);
					} else if (InGameInfoXML.serverInstalled) {
						try {
							InGameInfoXML.serverInstalled = false;
							ClientPacketHandler.sendRequestMSPT();
						} catch (final Exception e) {
							InGameInfoXML.mspt = -1;
							InGameInfoXML.tps = -1;
						}
					}
					lastRemoteUpdate = System.currentTimeMillis();
				}
			}
		} else if (inGameCurrent) {
			try {
				inGame = true;
				InGameInfoXML.serverInstalled = true;
				showVerionInfo();
				ClientPacketHandler.sendRequestSeed();
			} catch (final Exception e) {
				InGameInfoXML.seed = ConfigurationHandler.seed;
			}
		}

		if (!enabled || this.client.options == null)
			Tag.releaseResources();
	}

	private void showVerionInfo() {
		String keyToggle1 = KeyInputHandler.KEY_TOGGLE.getTranslatedKeyMessage().getString();
		String keyReload1 = KeyInputHandler.KEY_RELOAD.getTranslatedKeyMessage().getString();
		if (client.gui != null)
			client.gui.getChat().addMessage(Component.translatable(Names.VERSION_CHAT, Names.VERSION, keyToggle1, keyReload1));
	}
}
