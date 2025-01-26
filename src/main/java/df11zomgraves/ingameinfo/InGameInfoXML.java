package df11zomgraves.ingameinfo;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.handler.Ticker;
import df11zomgraves.ingameinfo.network.PacketHandler;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.TagRegistry;
import df11zomgraves.ingameinfo.value.ValueRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Names.MODID)
public class InGameInfoXML {
	public static Logger logger = LogManager.getLogger(Names.MODID);

	public InGameInfoXML() {
		PacketHandler.init();
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigurationHandler.SPEC);
		modEventBus.addListener(this::clientSetup);
//		InGameInfoArgumentTypes.COMMAND_ARGUMENT_TYPES.register(bus);
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		IEventBus bus = MinecraftForge.EVENT_BUS;
		bus.register(InGameInfoCore.INSTANCE);
		bus.register(Ticker.INSTANCE);
		ValueRegistry.INSTANCE.init();
		TagRegistry.INSTANCE.init();
		InGameInfoCore core = InGameInfoCore.INSTANCE;
		Path configPath = FMLPaths.CONFIGDIR.get();
		Path modConfigPath = Paths.get(configPath.toAbsolutePath().toString());
		core.setConfigDirectory(modConfigPath.toFile());
		core.setConfigFile(ConfigurationHandler.configName);
		core.reloadConfig();
//		event.enqueueWork(InGameInfoArgumentTypes::registerArgumentTypes);
	}
}
