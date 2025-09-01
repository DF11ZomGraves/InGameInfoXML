package com.github.lunatrius.ingameinfo.handler;

import com.github.lunatrius.ingameinfo.InGameInfoCore;
import com.github.lunatrius.ingameinfo.reference.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyInputHandler {
	public static final KeyInputHandler INSTANCE = new KeyInputHandler();
	public static final KeyBinding KEY_BINDING_TOGGLE = new KeyBinding(Names.Keys.TOGGLE, Keyboard.KEY_F10,
			Names.Keys.CATEGORY);
	public static final KeyBinding KEY_BINDING_RELOAD = new KeyBinding(Names.Keys.RELOAD, Keyboard.KEY_F12,
			Names.Keys.CATEGORY);
	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_TOGGLE, KEY_BINDING_RELOAD };
	private final Minecraft minecraft = Minecraft.getMinecraft();

	private KeyInputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		final Minecraft mc = this.minecraft;
		if (mc.currentScreen == null) {
			if (KEY_BINDING_TOGGLE.isPressed())
				Ticker.enabled = !Ticker.enabled;
			else if (KEY_BINDING_RELOAD.isPressed()) {
				ConfigurationHandler.reload();
				mc.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentTranslation(Names.Command.Message.RELOAD));
				final boolean success = InGameInfoCore.INSTANCE.reloadConfig();
				mc.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentTranslation(
						success ? Names.Command.Message.SUCCESS : Names.Command.Message.FAILURE));
			}

		}
	}
}
