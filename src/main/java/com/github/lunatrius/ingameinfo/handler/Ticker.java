package com.github.lunatrius.ingameinfo.handler;

import com.github.lunatrius.ingameinfo.InGameInfoCore;
import com.github.lunatrius.ingameinfo.reference.Names;
import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class Ticker {
	public static final Ticker INSTANCE = new Ticker();

	public static boolean enabled = true;

	private final Minecraft client = Minecraft.getMinecraft();
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;
	private static boolean showVersionMessage = false;

	private Ticker() {
	}

	@SubscribeEvent
	public void clientJoinedEvent(ClientConnectedToServerEvent event) {
		showVersionMessage = true;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRenderGameOverlayEventPre(final RenderGameOverlayEvent.Pre event) {
		if (canRun()) {
			if (ConfigurationHandler.replaceDebug && event.getType() == RenderGameOverlayEvent.ElementType.DEBUG)
				event.setCanceled(true);
			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
				event.setCanceled(true);
		}

		if (!ConfigurationHandler.showOverlayPotions
				&& event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS)
			event.setCanceled(true);
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

	// TODO: this requires a bit of optimization... it's just boolean checks mostly
	// but still
	private boolean canRun() {
		if (!enabled)
			return false;
		if (this.client.mcProfiler.profilingEnabled)
			return true;
		if (ConfigurationHandler.replaceDebug
				|| ConfigurationHandler.replaceDebug == this.client.gameSettings.showDebugInfo)
			return true;

		return false;
	}

	private boolean isRunning() {
		if (!canRun())
			return false;
		if (this.client.mcProfiler.profilingEnabled)
			return true;
		// a && b || !a && !b --> a == b
		if (this.client.gameSettings != null
				&& ConfigurationHandler.replaceDebug == this.client.gameSettings.showDebugInfo) {
			if (!ConfigurationHandler.showOnPlayerList && this.client.gameSettings.keyBindPlayerList.isKeyDown())
				return false;
			if (this.client.gameSettings.hideGUI)
				return false;
			if (this.client.currentScreen == null)
				return true;
			if (ConfigurationHandler.showInChat && this.client.currentScreen instanceof GuiChat)
				return true;
		}
		return false;
	}

	private void onTick(final TickEvent event) {
		if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.END) {
			this.client.mcProfiler.startSection("ingameinfo");
			if (isRunning()) {
				if (event.type == TickEvent.Type.CLIENT)
					this.core.onTickClient();
				else if (event.type == TickEvent.Type.RENDER)
					this.core.onTickRender();
			}

			if ((!enabled || this.client.gameSettings == null) && event.type == TickEvent.Type.CLIENT)
				Tag.releaseResources();
			this.client.mcProfiler.endSection();
		}
	}

	private void showVerionInfo() {
		String keyToggle = KeyInputHandler.KEY_BINDING_TOGGLE.getDisplayName();
		client.ingameGUI.addChatMessage(ChatType.CHAT,
				new TextComponentTranslation(Names.VERSION_CHAT, Reference.VERSION));
		client.ingameGUI.addChatMessage(ChatType.CHAT,
				new TextComponentTranslation(Names.VERSION_CHAT2, keyToggle));
	}
}
