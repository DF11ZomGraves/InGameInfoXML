package com.github.lunatrius.ingameinfo.proxy;

import com.github.lunatrius.ingameinfo.InGameInfoCore;
import com.github.lunatrius.ingameinfo.command.InGameInfoCommand;
import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.handler.KeyInputHandler;
import com.github.lunatrius.ingameinfo.handler.PlayerHandler;
import com.github.lunatrius.ingameinfo.handler.Ticker;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import com.github.lunatrius.ingameinfo.value.registry.ValueRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	private final InGameInfoCore core = InGameInfoCore.INSTANCE;

	@Override
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);

		ValueRegistry.INSTANCE.init();
		this.core.setConfigDirectory(event.getModConfigurationDirectory());
		this.core.setConfigFile(ConfigurationHandler.configName);
		this.core.reloadConfig();
		ConfigurationHandler.propFileInterval.setConfigEntryClass(GuiConfigEntries.NumberSliderEntry.class);
		for (final KeyBinding keyBinding : KeyInputHandler.KEY_BINDINGS)
			ClientRegistry.registerKeyBinding(keyBinding);
	}

	@Override
	public void init(final FMLInitializationEvent event) {
		super.init(event);

		MinecraftForge.EVENT_BUS.register(Ticker.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ConfigurationHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(KeyInputHandler.INSTANCE);
		ClientCommandHandler.instance.registerCommand(InGameInfoCommand.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new PlayerHandler());
	}

	@Override
	public void postInit(final FMLPostInitializationEvent event) {
		TagRegistry.INSTANCE.init();
	}
}
