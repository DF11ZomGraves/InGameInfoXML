package df11zomgraves.ingameinfo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.ModContainer;
import df11zomgraves.ingameinfo.command.arguments.InGameInfoArgumentTypes;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.handler.Ticker;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.TagRegistry;
import df11zomgraves.ingameinfo.value.ValueRegistry;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Names.MODID)
public class InGameInfoXML {
	// Directly reference a slf4j logger
	public static Logger logger = LogUtils.getLogger();
	public static long seed = 0;
	public static double mspt = -1;
	public static double tps = -1;
	public static boolean serverInstalled = false;
	public static boolean existMSPT = false;

	public InGameInfoXML(IEventBus modEventBus, ModContainer modContainer) {
		if(FMLEnvironment.dist == Dist.CLIENT) {
			modContainer.registerConfig(ModConfig.Type.CLIENT, ConfigurationHandler.SPEC);
			modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			InGameInfoCore.INSTANCE = new InGameInfoCore();
			NeoForge.EVENT_BUS.register(Ticker.INSTANCE);
			modEventBus.addListener(this::clientSetup);
			InGameInfoArgumentTypes.COMMAND_ARGUMENT_TYPES.register(modEventBus);
			Path configPath = FMLPaths.CONFIGDIR.get();
			Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());
			InGameInfoCore core = InGameInfoCore.INSTANCE;
			core.setConfigDirectory(modConfigPath.toFile());
//			core.setConfigFile(ConfigurationHandler.configName);
//			core.reloadConfig();
			ValueRegistry.INSTANCE.init();
			TagRegistry.INSTANCE.init();
		}
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(InGameInfoArgumentTypes::registerArgumentTypes);
	}
}
