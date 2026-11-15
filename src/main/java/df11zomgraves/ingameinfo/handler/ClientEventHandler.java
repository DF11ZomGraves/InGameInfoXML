package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.command.InGameInfoCommand;
import df11zomgraves.ingameinfo.reference.Names;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Client side event handlers (replaces the Forge {@code @EventBusSubscriber} of
 * {@code ForgeClientEventHandler}).
 */
public class ClientEventHandler {
	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandler::onClientTick);
		ClientCommandRegistrationCallback.EVENT
				.register((dispatcher, registryAccess) -> InGameInfoCommand.register(dispatcher));
	}

	private static void onClientTick(final Minecraft mc) {
		if (KeyInputHandler.KEY_RELOAD.consumeClick()) {
			if (mc.gui != null)
				mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.RELOAD));

			InGameInfoCore core = InGameInfoCore.INSTANCE;
			core.setConfigDirectory(
					net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().toFile());
			core.setConfigFile(ConfigurationHandler.configName);
			boolean b = core.reloadConfig();
			if (mc.gui != null) {
				if (b)
					mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.SUCCESS));
				else
					mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.FAILURE));
			}
		}
		while (KeyInputHandler.KEY_TOGGLE.consumeClick()) {
			Ticker.enabled = !Ticker.enabled;
		}
	}
}
