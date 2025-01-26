package df11zomgraves.ingameinfo.handler;

import org.lwjgl.glfw.GLFW;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyInputHandler {
	public static final KeyMapping KEY_TOGGLE = new KeyMapping(Names.Keys.TOGGLE, GLFW.GLFW_KEY_F10, Names.Keys.CATEGORY);
	public static final KeyMapping KEY_RELOAD = new KeyMapping(Names.Keys.RELOAD, GLFW.GLFW_KEY_F12, Names.Keys.CATEGORY);

	@SubscribeEvent
	public static void registerBindings(RegisterKeyMappingsEvent event) {
		event.register(KEY_TOGGLE);
		event.register(KEY_RELOAD);
	}
}

