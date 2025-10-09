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
import df11zomgraves.ingameinfo.util.StringConvertUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.FMLPaths;

public class ForgeClientEventHandler {
    @SubscribeEvent
    public void keyPressed(final InputEvent.KeyInputEvent e) {
        if (KeyInputHandler.KEY_TOGGLE.consumeClick())
        	Ticker.enabled = !Ticker.enabled;
        else if (KeyInputHandler.KEY_RELOAD.consumeClick()) {
        	Minecraft mc = Minecraft.getInstance();
        	mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.RELOAD));
        	InGameInfoCore core = InGameInfoCore.INSTANCE;
        	
        	Path configPath = FMLPaths.CONFIGDIR.get();
    		Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());
        	
        	core.setConfigDirectory(modConfigPath.toFile());
    		core.setConfigFile(ConfigurationHandler.configName);
    		boolean b = core.reloadConfig();
    		if (b)
    			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.SUCCESS));
    		else
    			mc.gui.getChat().addMessage(new TranslatableComponent(Names.Command.Message.FAILURE));
        }
        	
    }
    
	@SubscribeEvent
    public void onRegisterClientCommands(@Nonnull RegisterClientCommandsEvent event) {
		InGameInfoCommand.register(event.getDispatcher());
	}
	
	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		Tag.setSeed(ConfigurationHandler.seed);
		Minecraft mc = Minecraft.getInstance();
		if (mc.isLocalServer()) {
			ServerPlayer player = (ServerPlayer) event.getEntity();
			long seed;
			seed = player.getLevel().getSeed();
			Tag.setSeed(seed);
			StringConvertUtils.sendSeedToChat(seed);
		} else
			try {
				PacketHandler.INSTANCE.sendToServer(new RequestSeedPacket());
			} catch (Exception e) {

			}
	}

	@SubscribeEvent
	public void onPlayerLoggout(final PlayerEvent.PlayerLoggedOutEvent event) {
		Tag.setSeed(ConfigurationHandler.seed);
	}
}
