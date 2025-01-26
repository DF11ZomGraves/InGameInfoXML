package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class Ticker {
	public static final Ticker INSTANCE = new Ticker();

	public static boolean enabled = true;

	private final Minecraft client = Minecraft.getInstance();
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;
	private static boolean showVersionMessage = true;

	private Ticker() {
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRenderGameOverlayEventPre(final RenderGameOverlayEvent.Pre event) {
		Options options = this.client.options;
		if (enabled) {
			if (options.hideGui)
				event.setCanceled(true);
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

	private void onTick(final TickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END) {
			if (isRunning())
				if (event.type == TickEvent.Type.CLIENT)
					this.core.onTickClient();
				else if (event.type == TickEvent.Type.RENDER)
					this.core.onTickRender();

			if ((!enabled || this.client.options == null) && event.type == TickEvent.Type.CLIENT)
				Tag.releaseResources();
		}
	}
	
	private void showVerionInfo() {
		String keyToggle = KeyInputHandler.KEY_TOGGLE.getKey().getName();
		String keyReload = KeyInputHandler.KEY_RELOAD.getKey().getName();
		String keyToggle1 = new TranslatableComponent(keyToggle).getString();
		String keyReload1 = new TranslatableComponent(keyReload).getString();
		client.gui.getChat().addMessage(new TranslatableComponent(Names.VERSION_CHAT, Names.VERSION, keyToggle1, keyReload1));
	}
}
