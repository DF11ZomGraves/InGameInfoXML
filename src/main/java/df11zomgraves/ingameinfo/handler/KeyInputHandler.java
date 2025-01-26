package df11zomgraves.ingameinfo.handler;

import org.lwjgl.glfw.GLFW;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyInputHandler {
	public static final KeyMapping KEY_TOGGLE = new KeyMapping(Names.Keys.TOGGLE, GLFW.GLFW_KEY_F10, Names.Keys.CATEGORY);
	public static final KeyMapping KEY_RELOAD = new KeyMapping(Names.Keys.RELOAD, GLFW.GLFW_KEY_F12, Names.Keys.CATEGORY);

	public static void init() {
		ClientRegistry.registerKeyBinding(KEY_TOGGLE);
		ClientRegistry.registerKeyBinding(KEY_RELOAD);
	}
}
