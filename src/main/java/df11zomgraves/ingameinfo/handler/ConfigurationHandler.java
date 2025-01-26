package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.reference.Alignment;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Names.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigurationHandler {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Integer> FILE_INTERVAL_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Double> SCALE_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<String> CONFIG_NAME_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_DEBUG_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_IN_CHAT_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_ON_PLAYER_LIST_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_OVERLAY_POTIONS_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_OVERLAY_ITEM_ICONS_DEFAULT;
	public static final ForgeConfigSpec.ConfigValue<Long> SEED_DEFAULT;

	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_TOP_LEFT;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_TOP_CENTER;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_TOP_RIGHT;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_MIDDLE_LEFT;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_CENTER;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_MIDDLE_RIGHT;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_BOTTOM_LEFT;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_BOTTOM_CENTER;
	public static final ForgeConfigSpec.ConfigValue<String> ALIGN_BOTTOM_RIGHT;

	public static boolean showOverlayItemIcons;
	public static int fileInterval;
	public static boolean replaceDebug;
	public static boolean showOnPlayerList;
	public static Double scale;
	public static String configName;
	public static boolean showInChat;
	public static long seed;

	static {
		BUILDER.push(Names.Config.Category.GENERAL);
		CONFIG_NAME_DEFAULT = BUILDER.comment(Names.Config.FILENAME_DESC).define(Names.Config.FILENAME, "ingameinfo.xml");
		REPLACE_DEBUG_DEFAULT = BUILDER.comment(Names.Config.REPLACE_DEBUG_DESC).define(Names.Config.REPLACE_DEBUG, false);
		SHOW_IN_CHAT_DEFAULT = BUILDER.comment(Names.Config.SHOW_IN_CHAT_DESC).define(Names.Config.SHOW_IN_CHAT, true);
		SHOW_ON_PLAYER_LIST_DEFAULT = BUILDER.comment(Names.Config.SHOW_ON_PLAYER_LIST_DESC).define(Names.Config.SHOW_ON_PLAYER_LIST, true);
		SCALE_DEFAULT = BUILDER.comment(Names.Config.SCALE_DESC).defineInRange(Names.Config.SCALE, 1.0, 0.5, 2.0);
		FILE_INTERVAL_DEFAULT = BUILDER.comment(Names.Config.FILE_INTERVAL_DESC).defineInRange(Names.Config.FILE_INTERVAL, 5, 1, 60);
		SHOW_OVERLAY_POTIONS_DEFAULT = BUILDER.comment(Names.Config.SHOW_OVERLAY_POTIONS_DESC).define(Names.Config.SHOW_OVERLAY_POTIONS, true);
		SHOW_OVERLAY_ITEM_ICONS_DEFAULT = BUILDER.comment(Names.Config.SHOW_OVERLAY_ITEM_ICONS_DESC).define(Names.Config.SHOW_OVERLAY_ITEM_ICONS, true);
		SEED_DEFAULT = BUILDER.comment(Names.Config.DEFAULT_SEED_IN_SERVER_DESC).define(Names.Config.DEFAULT_SEED_IN_SERVER, (long)0);

		BUILDER.pop();
		BUILDER.push(Names.Config.Category.ALIGNMENT);

		ALIGN_TOP_LEFT = BUILDER.comment("Offsets for TOPLEFT (X<space>Y).").define("topleft", "2 2");
		ALIGN_TOP_CENTER = BUILDER.comment("Offsets for TOPCENTER (X<space>Y).").define("topcenter", "0 2");
		ALIGN_TOP_RIGHT = BUILDER.comment("Offsets for TOPRIGHT (X<space>Y).").define("topright", "-2 2");
		ALIGN_MIDDLE_LEFT = BUILDER.comment("Offsets for MIDDLELEFT (X<space>Y).").define("middleleft", "2 0");
		ALIGN_CENTER = BUILDER.comment("Offsets for MIDDLECENTER (X<space>Y).").define("middlecenter", "0 0");
		ALIGN_MIDDLE_RIGHT = BUILDER.comment("Offsets for MIDDLERIGHT (X<space>Y).").define("middleright", "-2 0");
		ALIGN_BOTTOM_LEFT = BUILDER.comment("Offsets for BOTTOMLEFT (X<space>Y).").define("bottomleft", "2 -2");
		ALIGN_BOTTOM_CENTER = BUILDER.comment("Offsets for BOTTOMCENTER (X<space>Y).").define("bottomcenter", "0 -45");
		ALIGN_BOTTOM_RIGHT = BUILDER.comment("Offsets for BOTTOMRIGHT (X<space>Y).").define("bottomright", "-2 -2");

		BUILDER.pop();
		SPEC = BUILDER.build();
	}

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		Alignment.TOPLEFT.setXY(ALIGN_TOP_LEFT.get());
		Alignment.TOPCENTER.setXY(ALIGN_TOP_CENTER.get());
		Alignment.TOPRIGHT.setXY(ALIGN_TOP_RIGHT.get());
		Alignment.MIDDLELEFT.setXY(ALIGN_MIDDLE_LEFT.get());
		Alignment.MIDDLECENTER.setXY(ALIGN_CENTER.get());
		Alignment.MIDDLERIGHT.setXY(ALIGN_MIDDLE_RIGHT.get());
		Alignment.BOTTOMLEFT.setXY(ALIGN_BOTTOM_LEFT.get());
		Alignment.BOTTOMCENTER.setXY(ALIGN_BOTTOM_CENTER.get());
		Alignment.BOTTOMRIGHT.setXY(ALIGN_BOTTOM_RIGHT.get());
		showOverlayItemIcons = SHOW_OVERLAY_ITEM_ICONS_DEFAULT.get();
		fileInterval = FILE_INTERVAL_DEFAULT.get();
		replaceDebug = REPLACE_DEBUG_DEFAULT.get();
		showOnPlayerList = SHOW_ON_PLAYER_LIST_DEFAULT.get();
		scale = SCALE_DEFAULT.get();
		configName = CONFIG_NAME_DEFAULT.get();
		showInChat = SHOW_IN_CHAT_DEFAULT.get();
		seed = SEED_DEFAULT.get();
	}
	
	public static void applyConfiguration() {
		SHOW_OVERLAY_ITEM_ICONS_DEFAULT.set(showOverlayItemIcons);
		FILE_INTERVAL_DEFAULT.set(fileInterval);
		REPLACE_DEBUG_DEFAULT.set(replaceDebug);
		SHOW_ON_PLAYER_LIST_DEFAULT.set(showOnPlayerList);
		SCALE_DEFAULT.set(scale);
		CONFIG_NAME_DEFAULT.set(configName);
		SHOW_IN_CHAT_DEFAULT.set(showInChat);
		SEED_DEFAULT.set(seed);
		ALIGN_TOP_LEFT.set(Alignment.TOPLEFT.getXY());
		ALIGN_TOP_CENTER.set(Alignment.TOPCENTER.getXY());
		ALIGN_TOP_RIGHT.set(Alignment.TOPRIGHT.getXY());
		ALIGN_MIDDLE_LEFT.set(Alignment.MIDDLELEFT.getXY());
		ALIGN_CENTER.set(Alignment.MIDDLECENTER.getXY());
		ALIGN_MIDDLE_RIGHT.set(Alignment.MIDDLERIGHT.getXY());
		ALIGN_BOTTOM_LEFT.set(Alignment.BOTTOMLEFT.getXY());
		ALIGN_BOTTOM_CENTER.set(Alignment.BOTTOMCENTER.getXY());
		ALIGN_BOTTOM_RIGHT.set(Alignment.BOTTOMRIGHT.getXY());
	}
}
