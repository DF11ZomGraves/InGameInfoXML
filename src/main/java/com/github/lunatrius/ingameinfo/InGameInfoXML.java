package com.github.lunatrius.ingameinfo;

import com.github.lunatrius.ingameinfo.proxy.CommonProxy;
import com.github.lunatrius.ingameinfo.reference.Names;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Names.MODID, name = Names.NAME, version = Names.VERSION, guiFactory = Names.GUI_FACTORY)
public class InGameInfoXML {
	@Instance(Names.MODID)
	public static InGameInfoXML instance;
	public static Logger logger = LogManager.getLogger(Names.MODID);
	public static long seed = 0;
	public static double mspt = -1;
	public static double tps = -1;
	public static boolean serverInstalled = false;
	public static boolean existMSPT = false;

	@SidedProxy(serverSide = Names.PROXY_SERVER, clientSide = Names.PROXY_CLIENT)
	public static CommonProxy proxy;

	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Mod.EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	@Mod.EventHandler
	public void serverStopping(final FMLServerStoppingEvent event) {
		proxy.serverStopping(event);
	}
}
