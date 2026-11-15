package df11zomgraves.ingameinfo.handler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.reference.Names;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Configuration of InGame Info XML.
 *
 * <p>Replaces the Forge {@code ForgeConfigSpec} of the original mod with a plain
 * JSON file stored in the Fabric config directory.
 */
public class ConfigurationHandler {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final String FILE_NAME = "ingameinfoxml-client.json";

	public static boolean showOverlayItemIcons = true;
	public static boolean showOverlayPotions = true;
	public static int fileInterval = 5;
	public static boolean replaceDebug = false;
	public static boolean showOnPlayerList = true;
	public static Double scale = 1.0;
	public static String configName = "ingameinfo.xml";
	public static boolean showInChat = true;
	public static boolean numericAmplifier = false;
	public static boolean sendSeedToChat = false;
	public static boolean showSurvivalHUD = true;
	public static long seed = 0;
	public static Alignment alignmentMiddleCenter = Alignment.MIDDLECENTER;
	public static int healthDecimalPlace = 2;
	public static int hungerDecimalPlace = 2;
	public static int msptDecimalPlace = 1;
	public static int tpsDecimalPlace = 1;
	public static boolean displayInvisiblePlayer = false;

	static {
		load();
	}

	public static File getConfigFile() {
		return new File(FabricLoader.getInstance().getConfigDir().toFile(), FILE_NAME);
	}

	public static void load() {
		JsonObject config = null;
		final File file = getConfigFile();

		if (file.exists()) {
			try (final Reader reader = new FileReader(file)) {
				final JsonElement element = JsonParser.parseReader(reader);
				if (element != null && element.isJsonObject())
					config = element.getAsJsonObject();
			} catch (final Exception e) {
				if (InGameInfoXML.logger != null)
					InGameInfoXML.logger.warn("Could not read the configuration file!", e);
			}
		}

		if (config == null)
			config = new JsonObject();

		configName = getString(config, Names.Config.FILENAME, "ingameinfo.xml");
		showInChat = getBoolean(config, Names.Config.SHOW_IN_CHAT, true);
		showOnPlayerList = getBoolean(config, Names.Config.SHOW_ON_PLAYER_LIST, true);
		scale = getDouble(config, Names.Config.SCALE, 1.0, 0.5, 2.0);
		fileInterval = getInt(config, Names.Config.FILE_INTERVAL, 5, 1, 60);
		showOverlayPotions = getBoolean(config, Names.Config.SHOW_OVERLAY_POTIONS, true);
		showOverlayItemIcons = getBoolean(config, Names.Config.SHOW_OVERLAY_ITEM_ICONS, true);
		numericAmplifier = getBoolean(config, Names.Config.NUMERIC_AMPLIFIER, false);
		sendSeedToChat = getBoolean(config, Names.Config.SEND_SEED_TO_CHAT, false);
		showSurvivalHUD = getBoolean(config, Names.Config.SHOW_SURVIVAL_HUD, true);
		healthDecimalPlace = getInt(config, Names.Config.HEALTH_DECIMAL_PLACE, 2, 0, 6);
		hungerDecimalPlace = getInt(config, Names.Config.HUNGER_DECIMAL_PLACE, 2, 0, 6);
		msptDecimalPlace = getInt(config, Names.Config.MSPT_DECIMAL_PLACE, 1, 0, 6);
		tpsDecimalPlace = getInt(config, Names.Config.TPS_DECIMAL_PLACE, 1, 0, 6);
		displayInvisiblePlayer = getBoolean(config, Names.Config.DISPLAY_INVISIBLE_PLAYER, false);

		try {
			seed = Long.parseLong(getString(config, Names.Config.DEFAULT_SEED_IN_SERVER, "0"));
		} catch (final Exception e) {
			seed = 0L;
			InGameInfoXML.logger.error("The default seed is not a valid number!", e);
		}

		alignmentMiddleCenter = parseAlignment(getString(config, Names.Config.ALIGNMENT_MIDDLECENTER,
				Alignment.MIDDLECENTER.name()));

		Alignment.TOPLEFT.setXY(getString(config, "topleft", Alignment.TOPLEFT.getDefaultXY()));
		Alignment.TOPCENTER.setXY(getString(config, "topcenter", Alignment.TOPCENTER.getDefaultXY()));
		Alignment.TOPRIGHT.setXY(getString(config, "topright", Alignment.TOPRIGHT.getDefaultXY()));
		Alignment.MIDDLELEFT.setXY(getString(config, "middleleft", Alignment.MIDDLELEFT.getDefaultXY()));
		Alignment.MIDDLECENTER.setXY(getString(config, "middlecenter", Alignment.MIDDLECENTER.getDefaultXY()));
		Alignment.MIDDLERIGHT.setXY(getString(config, "middleright", Alignment.MIDDLERIGHT.getDefaultXY()));
		Alignment.BOTTOMLEFT.setXY(getString(config, "bottomleft", Alignment.BOTTOMLEFT.getDefaultXY()));
		Alignment.BOTTOMCENTER.setXY(getString(config, "bottomcenter", Alignment.BOTTOMCENTER.getDefaultXY()));
		Alignment.BOTTOMRIGHT.setXY(getString(config, "bottomright", Alignment.BOTTOMRIGHT.getDefaultXY()));

		if (!file.exists())
			applyConfiguration();
	}

	/** Persists the current values to disk. */
	public static void applyConfiguration() {
		final JsonObject config = new JsonObject();

		config.addProperty(Names.Config.FILENAME, configName);
		config.addProperty(Names.Config.SHOW_IN_CHAT, showInChat);
		config.addProperty(Names.Config.SHOW_ON_PLAYER_LIST, showOnPlayerList);
		config.addProperty(Names.Config.SCALE, scale);
		config.addProperty(Names.Config.FILE_INTERVAL, fileInterval);
		config.addProperty(Names.Config.SHOW_OVERLAY_POTIONS, showOverlayPotions);
		config.addProperty(Names.Config.SHOW_OVERLAY_ITEM_ICONS, showOverlayItemIcons);
		config.addProperty(Names.Config.NUMERIC_AMPLIFIER, numericAmplifier);
		config.addProperty(Names.Config.SEND_SEED_TO_CHAT, sendSeedToChat);
		config.addProperty(Names.Config.SHOW_SURVIVAL_HUD, showSurvivalHUD);
		config.addProperty(Names.Config.HEALTH_DECIMAL_PLACE, healthDecimalPlace);
		config.addProperty(Names.Config.HUNGER_DECIMAL_PLACE, hungerDecimalPlace);
		config.addProperty(Names.Config.MSPT_DECIMAL_PLACE, msptDecimalPlace);
		config.addProperty(Names.Config.TPS_DECIMAL_PLACE, tpsDecimalPlace);
		config.addProperty(Names.Config.DISPLAY_INVISIBLE_PLAYER, displayInvisiblePlayer);
		config.addProperty(Names.Config.DEFAULT_SEED_IN_SERVER, String.valueOf(seed));
		config.addProperty(Names.Config.ALIGNMENT_MIDDLECENTER,
				alignmentMiddleCenter != null ? alignmentMiddleCenter.name() : Alignment.MIDDLECENTER.name());

		config.addProperty("topleft", Alignment.TOPLEFT.getXY());
		config.addProperty("topcenter", Alignment.TOPCENTER.getXY());
		config.addProperty("topright", Alignment.TOPRIGHT.getXY());
		config.addProperty("middleleft", Alignment.MIDDLELEFT.getXY());
		config.addProperty("middlecenter", Alignment.MIDDLECENTER.getXY());
		config.addProperty("middleright", Alignment.MIDDLERIGHT.getXY());
		config.addProperty("bottomleft", Alignment.BOTTOMLEFT.getXY());
		config.addProperty("bottomcenter", Alignment.BOTTOMCENTER.getXY());
		config.addProperty("bottomright", Alignment.BOTTOMRIGHT.getXY());

		try {
			final File file = getConfigFile();
			final File parent = file.getParentFile();
			if (parent != null && !parent.exists())
				parent.mkdirs();
			try (final Writer writer = new FileWriter(file)) {
				GSON.toJson(config, writer);
			}
		} catch (final Exception e) {
			InGameInfoXML.logger.error("Could not save the configuration file!", e);
		}
	}

	private static Alignment parseAlignment(final String name) {
		try {
			return Alignment.valueOf(name.toUpperCase(java.util.Locale.ENGLISH));
		} catch (final Exception e) {
			return Alignment.MIDDLECENTER;
		}
	}

	private static String getString(final JsonObject config, final String key, final String fallback) {
		try {
			final JsonElement element = config.get(key);
			if (element != null && element.isJsonPrimitive())
				return element.getAsString();
		} catch (final Exception e) {
			// ignore and use the fallback
		}
		return fallback;
	}

	private static boolean getBoolean(final JsonObject config, final String key, final boolean fallback) {
		try {
			final JsonElement element = config.get(key);
			if (element != null && element.isJsonPrimitive())
				return element.getAsBoolean();
		} catch (final Exception e) {
			// ignore and use the fallback
		}
		return fallback;
	}

	private static int getInt(final JsonObject config, final String key, final int fallback, final int min,
			final int max) {
		try {
			final JsonElement element = config.get(key);
			if (element != null && element.isJsonPrimitive())
				return Math.max(min, Math.min(max, element.getAsInt()));
		} catch (final Exception e) {
			// ignore and use the fallback
		}
		return fallback;
	}

	private static double getDouble(final JsonObject config, final String key, final double fallback, final double min,
			final double max) {
		try {
			final JsonElement element = config.get(key);
			if (element != null && element.isJsonPrimitive())
				return Math.max(min, Math.min(max, element.getAsDouble()));
		} catch (final Exception e) {
			// ignore and use the fallback
		}
		return fallback;
	}
}
