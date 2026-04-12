package com.github.lunatrius.ingameinfo.proxy;

import com.github.lunatrius.ingameinfo.InGameInfoXML;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		InGameInfoXML.logger = event.getModLog();
	}
	
	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);
	}
}
