package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Ticker {
	public static final Ticker INSTANCE = new Ticker();

	public static boolean enabled = true;

	private final Minecraft client = Minecraft.getInstance();
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;
	private static boolean showVersionMessage = true;

	private Ticker() {
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

			return client.screen == null || ConfigurationHandler.showInChat && client.screen instanceof ChatScreen;
		}
		return false;
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
		onTick(event);
	}

	@SubscribeEvent
	public void onRenderTick(final TickEvent.RenderTickEvent event) {
		if (showVersionMessage && client != null && client.player != null) {
			showVersionMessage = false;
			showVerionInfo();
		}
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
