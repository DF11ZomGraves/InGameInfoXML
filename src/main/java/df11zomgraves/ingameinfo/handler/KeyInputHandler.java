package df11zomgraves.ingameinfo.handler;

import org.lwjgl.glfw.GLFW;

import df11zomgraves.ingameinfo.reference.Names;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class KeyInputHandler {
	public static final KeyMapping KEY_TOGGLE = new KeyMapping(Names.Keys.TOGGLE, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F10, Names.Keys.CATEGORY);
	public static final KeyMapping KEY_RELOAD = new KeyMapping(Names.Keys.RELOAD, com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F12, Names.Keys.CATEGORY);

	public static void register() {
		KeyBindingHelper.registerKeyBinding(KEY_TOGGLE);
		KeyBindingHelper.registerKeyBinding(KEY_RELOAD);
	}
}
