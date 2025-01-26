package com.github.lunatrius.ingameinfo.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ServerProxy extends CommonProxy {
	@Override
	public void init(final FMLInitializationEvent event) {
		//MinecraftForge.EVENT_BUS.register(new PlayerHandler());
	}
}
