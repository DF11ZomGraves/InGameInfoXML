package com.github.lunatrius.ingameinfo.client.gui.config;

import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.reference.Names;
import com.github.lunatrius.ingameinfo.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiFactory implements IModGuiFactory {
	@Override
	public void initialize(final Minecraft minecraftInstance) {
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiModConfig(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	public static class GuiModConfig extends GuiConfig {
		public GuiModConfig(final GuiScreen guiScreen) {
			super(guiScreen, getConfigElements(ConfigurationHandler.configuration, Names.Config.LANG_PREFIX),
					Reference.MODID, false, false,
					GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
//super(guiScreen, Reference.MODID, ConfigurationHandler.configuration, Names.Config.LANG_PREFIX);
		}
	}

	private static List<IConfigElement> getConfigElements(final Configuration configuration, final String langPrefix) {
		final List<IConfigElement> elements = new ArrayList<IConfigElement>();
		for (final String name : configuration.getCategoryNames()) {
			final ConfigCategory category = configuration.getCategory(name)
					.setLanguageKey(langPrefix + ".category." + name);
			if (category.parent == null)
				elements.add(new ConfigElement(category));
		}

		return elements;
	}
}
