package df11zomgraves.ingameinfo;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import df11zomgraves.ingameinfo.handler.ClientEventHandler;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.handler.KeyInputHandler;
import df11zomgraves.ingameinfo.handler.Ticker;
import df11zomgraves.ingameinfo.network.ClientPacketHandler;
import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.tag.TagRegistry;
import df11zomgraves.ingameinfo.value.ValueRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Fabric client entrypoint of InGame Info XML.
 */
public class InGameInfoXML implements ClientModInitializer {
	public static Logger logger = LogManager.getLogger(Names.MODID);
	public static long seed = 0;
	public static double mspt = -1;
	public static double tps = -1;
	public static boolean serverInstalled = false;
	public static boolean existMSPT = false;

	private static boolean coreInitialized = false;

	@Override
	public void onInitializeClient() {
		ClientPacketHandler.initClient();

		ValueRegistry.INSTANCE.init();
		TagRegistry.INSTANCE.init();

		// The resource manager is not ready during the client entrypoint, so the
		// configuration file is loaded on the first client tick instead.
		ClientTickEvents.END_CLIENT_TICK.register(client -> initializeCore());

		KeyInputHandler.register();
		Ticker.register();
		ClientEventHandler.register();
	}

	private static void initializeCore() {
		if (coreInitialized)
			return;
		coreInitialized = true;

		InGameInfoCore core = InGameInfoCore.INSTANCE;
		Path configPath = FabricLoader.getInstance().getConfigDir();
		core.setConfigDirectory(configPath.toAbsolutePath().toFile());
		core.setConfigFile(ConfigurationHandler.configName);
		core.reloadConfig();
	}

	/** Re-reads the configuration file (used by the reload key and the 'igi load' command). */
	public static boolean reloadCoreConfig() {
		InGameInfoCore core = InGameInfoCore.INSTANCE;
		Path configPath = FabricLoader.getInstance().getConfigDir();
		core.setConfigDirectory(configPath.toAbsolutePath().toFile());
		core.setConfigFile(ConfigurationHandler.configName);
		return core.reloadConfig();
	}
}
