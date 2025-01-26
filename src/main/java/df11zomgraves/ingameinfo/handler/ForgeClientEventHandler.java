package df11zomgraves.ingameinfo.handler;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nonnull;

import df11zomgraves.ingameinfo.InGameInfoCore;
import df11zomgraves.ingameinfo.command.InGameInfoCommand;
import df11zomgraves.ingameinfo.network.PacketHandler;
import df11zomgraves.ingameinfo.network.RequestSeedPacket;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.Tag;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = Names.MODID, value = Dist.CLIENT)
public class ForgeClientEventHandler {
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END)
			return;
		while (KeyInputHandler.KEY_RELOAD.consumeClick()) {
			Minecraft mc = Minecraft.getInstance();
			mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.RELOAD));
			InGameInfoCore core = InGameInfoCore.INSTANCE;

			Path configPath = FMLPaths.CONFIGDIR.get();
			Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());

			core.setConfigDirectory(modConfigPath.toFile());
			core.setConfigFile(ConfigurationHandler.configName);
			boolean b = core.reloadConfig();
			if (b)
				mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.SUCCESS));
			else
				mc.gui.getChat().addMessage(Component.translatable(Names.Command.Message.FAILURE));
		}
		while (KeyInputHandler.KEY_TOGGLE.consumeClick()) {
			Ticker.enabled = !Ticker.enabled;
		}
	}

	@SubscribeEvent
	public static void onRegisterClientCommands(@Nonnull RegisterClientCommandsEvent event) {
		InGameInfoCommand.register(event.getDispatcher());
	}

	@SubscribeEvent
	public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.isSingleplayer()) {
			ServerPlayer player = (ServerPlayer) event.getEntity();
			long seed;
			seed = player.serverLevel().getSeed();
			Tag.setSeed(seed);
		} else
			try {
				PacketHandler.INSTANCE.sendToServer(new RequestSeedPacket());
			} catch (Exception e) {
				Tag.setSeed(ConfigurationHandler.seed);
			}
	}

	@SubscribeEvent
	public static void onPlayerLoggout(final PlayerEvent.PlayerLoggedOutEvent event) {
		Tag.setSeed(ConfigurationHandler.seed);
	}
}
