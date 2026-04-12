package com.github.lunatrius.ingameinfo.proxy;

import com.github.lunatrius.ingameinfo.InGameInfoXML;
import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class CommonProxy {
	public void preInit(final FMLPreInitializationEvent event) {
		InGameInfoXML.logger = event.getModLog();
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	public void init(final FMLInitializationEvent event) {
		PacketHandler.init();
	}

	public void postInit(final FMLPostInitializationEvent event) {
	}

	public void serverStarting(final FMLServerStartingEvent event) {
	}

	public void serverStopping(final FMLServerStoppingEvent event) {
	}
}
