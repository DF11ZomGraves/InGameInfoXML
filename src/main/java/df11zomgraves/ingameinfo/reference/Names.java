package df11zomgraves.ingameinfo.reference;

public final class Names {
	public static final String MODID = "ingameinfoxml";
	public static final String VERSION = "2.8.2.98";
	public static final String VERSION_CHAT = "chat.ingameinfo.version";
	public static final String SHOW_SEED = "chat.ingameinfo.seed";
	
	public static final class Command {
		public static final class Message {
			public static final String RELOAD = "commands.ingameinfoxml.reload";
            public static final String LOAD = "commands.ingameinfoxml.load";
			public static final String SUCCESS = "commands.ingameinfoxml.success";
			public static final String FAILURE = "commands.ingameinfoxml.failure";
			public static final String ALIGNMENT_GET_SUCCESS = "commands.ingameinfoxml.alignment.get.success";
			public static final String ALIGNMENT_GET_FAILURE = "commands.ingameinfoxml.alignment.get.failure";
			public static final String ALIGNMENT_SET_SUCCESS = "commands.ingameinfoxml.alignment.set.success";
			public static final String ALIGNMENT_SET_FAILURE = "commands.ingameinfoxml.alignment.set.failure";
			public static final String SED_SEET_SUCCESS = "commands.ingameinfoxml.seed.set.success";
			public static final String SED_SEET_FAILURE = "commands.ingameinfoxml.seed.set.failure";
			public static final String ALIGNMENT_MIDDLECENTER_SET_SUCCESS = "commands.ingameinfoxml.alignmentmiddlecenter.set.success";
			public static final String ALIGNMENT_MIDDLECENTER_SET_FAILURE = "commands.ingameinfoxml.alignmentmiddlecenter.set.failure";
		}

        public static final String NAME = "igi";
		public static final String SET = "set";
		public static final String GET = "get";
		public static final String ALIENMENT = "alignment";
		public static final String TOP_LEFT = "TOPLEFT";
		public static final String TOP_CENTER = "TOPCENTER";
		public static final String TOP_RIGHT = "TOPRIGHT";
		public static final String MIDDLE_LEFT = "MIDDLELEFT";
		public static final String MIDDLE_CENTER = "MIDDLECENTER";
		public static final String MIDDLE_RIGHT = "MIDDLERIGHT";
		public static final String BOTTOM_LEFT = "BOTTOMLEFT";
		public static final String BOTTOM_CENTER = "BOTTOMCENTER";
		public static final String BOTTOM_RIGHT = "BOTTOMRIGHT";
	}

	public static final class Config {
		public static final class Category {
			public static final String GENERAL = "general";
			public static final String ALIGNMENT = "alignment";
		}

		public static final String FILENAME = "filename";
		public static final String FILENAME_DESC = "The configuration that should be loaded on startup.";
		public static final String SHOW_IN_CHAT = "showInChat";
		public static final String SHOW_IN_CHAT_DESC = "Display the overlay in chat.";
		public static final String SHOW_ON_PLAYER_LIST = "showOnPlayerList";
		public static final String SHOW_ON_PLAYER_LIST_DESC = "Display the overlay on the player list.";
		public static final String SCALE = "scale";
		public static final String SCALE_DESC = "The overlay will be scaled by this amount.";
		public static final String FILE_INTERVAL = "fileInterval";
		public static final String FILE_INTERVAL_DESC = "The interval between file reads for the 'file' tag (in seconds).";

		public static final String SHOW_OVERLAY_POTIONS = "showOverlayPotions";
		public static final String SHOW_OVERLAY_POTIONS_DESC = "Display the vanilla potion overlay.";

		public static final String SHOW_OVERLAY_ITEM_ICONS = "showOverlayItemIcons";
		public static final String SHOW_OVERLAY_ITEM_ICONS_DESC = "Display the item overlay on icon (durability, stack size).";

		public static final String DEFAULT_SEED_IN_SERVER = "DefaultSeedInServer";
		public static final String DEFAULT_SEED_IN_SERVER_DESC = "Default seed in server (if InGameInfo XML NOT INSTALLED in Server).";
		
		public static final String NUMERIC_AMPLIFIER = "NumericAmplifier";
		public static final String NUMERIC_AMPLIFIER_DESC = "Use numerical values to display effect amplifier instead of Roman numerals.";
		
		public static final String ALIGNMENT_MIDDLECENTER = "AlignmentForMiddleCenter";
		public static final String ALIGNMENT_MIDDLECENTER_DESC = "Alignment for the middle center side of the screen";
		
		public static final String SEND_SEED_TO_CHAT = "SendSeedToChat";
		public static final String SEND_SEED_TO_CHAT_DESC = "Send seed to chat when entering the world.";
		
		public static final String SHOW_SURVIVAL_HUD = "ShowSurvivalHUD";
		public static final String SHOW_SURVIVAL_HUD_DESC = "Display the vanilla survival hud (health, foodlevel, armor).";
		
		public static final String HEALTH_DECIMAL_PLACE = "HealthDigit";
		public static final String HEALTH_DECIMAL_PLACE_DESC = "Health value decimal places retained (health, absorption).";
		
		public static final String HUNGER_DECIMAL_PLACE = "HungerDigit";
		public static final String HUNGER_DECIMAL_PLACE_DESC = "Hunger value decimal places retained (saturation, exhaustion).";
	}

	public static final class Files {
		public static final String NAME = "InGameInfo";

		public static final String FILE_XML = "InGameInfo.xml";
		public static final String FILE_JSON = "InGameInfo.json";
		public static final String FILE_TXT = "InGameInfo.txt";

		public static final String EXT_XML = ".xml";
		public static final String EXT_JSON = ".json";
		public static final String EXT_TXT = ".txt";
	}

	public static final class Keys {
		public static final String CATEGORY = "key.ingameinfoxml.category";
		public static final String TOGGLE = "key.ingameinfoxml.toggle";
		public static final String RELOAD = "key.ingameinfoxml.reload";
	}
}
