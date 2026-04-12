package com.github.lunatrius.ingameinfo.handler;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.InGameInfoXML;
import com.github.lunatrius.ingameinfo.reference.Names;
import com.github.lunatrius.ingameinfo.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigurationHandler {
	public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();

	public static Configuration configuration;

	public static final String CONFIG_NAME_DEFAULT = Names.Files.FILE_XML;
	public static final boolean REPLACE_DEBUG_DEFAULT = false;
	public static final boolean SHOW_IN_CHAT_DEFAULT = true;
	public static final boolean SHOW_ON_PLAYER_LIST_DEFAULT = true;
	public static final double SCALE_DEFAULT = 1.0;
	public static final int FILE_INTERVAL_DEFAULT = 5;
	public static final boolean SHOW_OVERLAY_POTIONS_DEFAULT = true;
	public static final boolean SHOW_OVERLAY_ITEM_ICONS_DEFAULT = true;
	public static final boolean NUMERIC_AMPLIFIER_DEFAULT = false;
	public static final boolean SEND_SEED_TO_CHAT_DEFAULT = false;
	public static final String SERVER_SEED_DEFAULT = "0";
	public static final String ALIGNMENT_MIDDLECENTER_DEFAULT = "MIDDLECENTER";
	public static final int HEALTH_DECIMAL_PLACE_DEFAULT = 2;
	public static final int HUNGER_DECIMAL_PLACE_DEFAULT = 2;
	public static final boolean DISPLAY_INVISIBLE_PLAYER_DEFAULT = false;

	public static String configName = CONFIG_NAME_DEFAULT;
	public static boolean replaceDebug = REPLACE_DEBUG_DEFAULT;
	public static boolean showInChat = SHOW_IN_CHAT_DEFAULT;
	public static boolean showOnPlayerList = SHOW_ON_PLAYER_LIST_DEFAULT;
	public static float scale = (float) SCALE_DEFAULT;
	public static int fileInterval = FILE_INTERVAL_DEFAULT;
	public static boolean showOverlayPotions = SHOW_OVERLAY_POTIONS_DEFAULT;
	public static boolean showOverlayItemIcons = SHOW_OVERLAY_ITEM_ICONS_DEFAULT;
	public static boolean numericAmplifier = NUMERIC_AMPLIFIER_DEFAULT;
	public static boolean sendSeedToChat = SEND_SEED_TO_CHAT_DEFAULT;
	public static long serverSeed = 0;
	public static String alignmentMiddleCenter = ALIGNMENT_MIDDLECENTER_DEFAULT;
	public static int healthDecimalPlace = HEALTH_DECIMAL_PLACE_DEFAULT;
	public static int hungerDecimalPlace = HUNGER_DECIMAL_PLACE_DEFAULT;
	public static boolean displayInvisiblePlayer = DISPLAY_INVISIBLE_PLAYER_DEFAULT;

	public static Property propConfigName = null;
	public static Property propReplaceDebug = null;
	public static Property propShowInChat = null;
	public static Property propShowOnPlayerList = null;
	public static Property propScale = null;
	public static Property propFileInterval = null;
	public static Property propShowOverlayPotions = null;
	public static Property propShowOverlayItemIcons = null;
	public static Property propnumericAmplifier = null;
	public static Property propServerSeed = null;
	public static Property propAlignmentMiddleCenter = null;
	public static Property propSendSeedToChat = null;
	public static Property propHealthDecimalPlace = null;
	public static Property propHungerDecimalPlace = null;
	public static Property propDisplayInvisiblePlayer = null;
		
	public static final Map<Alignment, Property> propAlignments = new HashMap<Alignment, Property>();

	private ConfigurationHandler() {
		
	}

	public static void init(final File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	private static void loadConfiguration() {
		String prefix = Names.Config.LANG_PREFIX + ".";
		String categoryGeneral = Names.Config.Category.GENERAL;
		
		propConfigName = configuration.get(categoryGeneral, Names.Config.FILENAME, CONFIG_NAME_DEFAULT,
				Names.Config.FILENAME_DESC);
		propConfigName.setLanguageKey(prefix + Names.Config.FILENAME).setRequiresMcRestart(true);
		configName = propConfigName.getString();

		propReplaceDebug = configuration.get(categoryGeneral, Names.Config.REPLACE_DEBUG,
				REPLACE_DEBUG_DEFAULT, Names.Config.REPLACE_DEBUG_DESC);
		propReplaceDebug.setLanguageKey(prefix + Names.Config.REPLACE_DEBUG);
		replaceDebug = propReplaceDebug.getBoolean(REPLACE_DEBUG_DEFAULT);

		propShowInChat = configuration.get(categoryGeneral, Names.Config.SHOW_IN_CHAT,
				SHOW_IN_CHAT_DEFAULT, Names.Config.SHOW_IN_CHAT_DESC);
		propShowInChat.setLanguageKey(prefix + Names.Config.SHOW_IN_CHAT);
		showInChat = propShowInChat.getBoolean(SHOW_IN_CHAT_DEFAULT);

		propShowOnPlayerList = configuration.get(categoryGeneral, Names.Config.SHOW_ON_PLAYER_LIST,
				SHOW_ON_PLAYER_LIST_DEFAULT, Names.Config.SHOW_ON_PLAYER_LIST_DESC);
		propShowOnPlayerList.setLanguageKey(prefix + Names.Config.SHOW_ON_PLAYER_LIST);
		showOnPlayerList = propShowOnPlayerList.getBoolean(SHOW_ON_PLAYER_LIST_DEFAULT);

		propScale = configuration.get(categoryGeneral, Names.Config.SCALE, String.valueOf(SCALE_DEFAULT),
				Names.Config.SCALE_DESC);
		propScale.setLanguageKey(prefix + Names.Config.SCALE);
		propScale.setValidValues(new String[] { "0.5", "1.0", "1.5", "2.0" });
		scale = (float) propScale.getDouble(SCALE_DEFAULT);

		propFileInterval = configuration.get(categoryGeneral, Names.Config.FILE_INTERVAL,
				FILE_INTERVAL_DEFAULT, Names.Config.FILE_INTERVAL_DESC, 1, 60);
		propFileInterval.setLanguageKey(prefix + Names.Config.FILE_INTERVAL);
		fileInterval = propFileInterval.getInt(FILE_INTERVAL_DEFAULT);

		propShowOverlayPotions = configuration.get(categoryGeneral, Names.Config.SHOW_OVERLAY_POTIONS,
				SHOW_OVERLAY_POTIONS_DEFAULT, Names.Config.SHOW_OVERLAY_POTIONS_DESC);
		propShowOverlayPotions.setLanguageKey(prefix + Names.Config.SHOW_OVERLAY_POTIONS);
		showOverlayPotions = propShowOverlayPotions.getBoolean(SHOW_OVERLAY_POTIONS_DEFAULT);

		propShowOverlayItemIcons = configuration.get(categoryGeneral,
				Names.Config.SHOW_OVERLAY_ITEM_ICONS, SHOW_OVERLAY_ITEM_ICONS_DEFAULT, Names.Config.SHOW_OVERLAY_ITEM_ICONS_DESC);
		propShowOverlayItemIcons.setLanguageKey(prefix + Names.Config.SHOW_OVERLAY_ITEM_ICONS);
		showOverlayItemIcons = propShowOverlayItemIcons.getBoolean(SHOW_OVERLAY_ITEM_ICONS_DEFAULT);
		
		propnumericAmplifier = configuration.get(categoryGeneral,
				Names.Config.NUMERIC_AMPLIFIER, NUMERIC_AMPLIFIER_DEFAULT, Names.Config.NUMERIC_AMPLIFIER_DESC);
		propnumericAmplifier.setLanguageKey(prefix + Names.Config.NUMERIC_AMPLIFIER);
		numericAmplifier = propnumericAmplifier.getBoolean(NUMERIC_AMPLIFIER_DEFAULT);
		
		propServerSeed = configuration.get(categoryGeneral, Names.Config.SERVER_SEED, SERVER_SEED_DEFAULT,
				Names.Config.SERVER_SEED_DESC);
		propServerSeed.setLanguageKey(prefix + Names.Config.SERVER_SEED).setRequiresMcRestart(true);
		try {
			serverSeed = Long.parseLong(propServerSeed.getString());
		} catch (Exception e) {
			serverSeed = 0;
		}
		InGameInfoXML.logger.info("serverSeed=" + serverSeed);
		
		propSendSeedToChat = configuration.get(categoryGeneral, Names.Config.SEND_SEED_TO_CHAT, false,
				Names.Config.SEND_SEED_TO_CHAT_DESC);
		propSendSeedToChat.setLanguageKey(prefix + Names.Config.SEND_SEED_TO_CHAT);
		sendSeedToChat = propSendSeedToChat.getBoolean();
		
		propAlignmentMiddleCenter = configuration.get(categoryGeneral, Names.Config.ALIGNMENT_MIDDLECENTER,
				ALIGNMENT_MIDDLECENTER_DEFAULT, Names.Config.ALIGNMENT_MIDDLECENTER_DESC);
		propAlignmentMiddleCenter.setValidValues(new String[] {
				Names.Command.TOP_LEFT,
				Names.Command.TOP_CENTER,
				Names.Command.TOP_RIGHT,
				Names.Command.MIDDLE_LEFT,
				Names.Command.MIDDLE_CENTER,
				Names.Command.MIDDLE_RIGHT,
				Names.Command.BOTTOM_LEFT,
				Names.Command.BOTTOM_CENTER,
				Names.Command.BOTTOM_RIGHT
		});
		propAlignmentMiddleCenter.setLanguageKey(prefix + Names.Config.ALIGNMENT_MIDDLECENTER);
		alignmentMiddleCenter = propAlignmentMiddleCenter.getString();
		
		propHealthDecimalPlace = configuration.get(categoryGeneral,
				Names.Config.HEALTH_DECIMAL_PLACE, HEALTH_DECIMAL_PLACE_DEFAULT, Names.Config.HEALTH_DECIMAL_PLACE_DESC,
				0, 6);
		propHealthDecimalPlace.setLanguageKey(prefix + Names.Config.HEALTH_DECIMAL_PLACE);
		healthDecimalPlace = propHealthDecimalPlace.getInt(HEALTH_DECIMAL_PLACE_DEFAULT);
		
		propHungerDecimalPlace = configuration.get(categoryGeneral, Names.Config.HUNGER_DECIMAL_PLACE,
				HUNGER_DECIMAL_PLACE_DEFAULT, Names.Config.HUNGER_DECIMAL_PLACE_DESC, 0, 6);
		propHungerDecimalPlace.setLanguageKey(prefix + Names.Config.HUNGER_DECIMAL_PLACE);
		hungerDecimalPlace = propHungerDecimalPlace.getInt(HUNGER_DECIMAL_PLACE_DEFAULT);
		
		propDisplayInvisiblePlayer = configuration.get(categoryGeneral, Names.Config.DISPLAY_INVISIBLE_PLAYER,
				false, Names.Config.DISPLAY_INVISIBLE_PLAYER_DESC);
		propDisplayInvisiblePlayer.setLanguageKey(prefix + Names.Config.DISPLAY_INVISIBLE_PLAYER);
		displayInvisiblePlayer = propDisplayInvisiblePlayer.getBoolean();

		for (final Alignment alignment : Alignment.values()) {
			final String alignmentName = alignment.toString().toLowerCase(Locale.ENGLISH);
			final Property property = configuration.get(Names.Config.Category.ALIGNMENT, alignmentName,
					alignment.getDefaultXY(), String.format(Names.Config.ALIGNMENT_DESC, alignment.toString()));
			property.setLanguageKey(prefix + alignmentName);
			property.setValidationPattern(Pattern.compile("-?\\d+ -?\\d+"));
			propAlignments.put(alignment, property);
			alignment.setXY(property.getString());
		}
		save();
	}

	public static void reload() {
		loadConfiguration();
		save();
	}

	public static void save() {
		if (configuration.hasChanged())
			configuration.save();
	}

	public static void setConfigName(final String name) {
		propConfigName.set(name);
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Reference.MODID))
			loadConfiguration();
	}
}
