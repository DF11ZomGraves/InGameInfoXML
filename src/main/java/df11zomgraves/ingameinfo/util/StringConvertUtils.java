package df11zomgraves.ingameinfo.util;

import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class StringConvertUtils {
	public static String numToRoman(int amplifier) {
		StringBuilder s = new StringBuilder();
		if (amplifier == 0)
			return "";
		amplifier++;
		if (amplifier >= 40)
			return String.format(" %d", amplifier);
		s.append(' ');
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int i = 0;
		while (amplifier > 0) {
			if (amplifier >= values[i]) {
				s.append(symbols[i]);
				amplifier -= values[i];
			} else {
				i++;
			}
		}
		return s.toString();
	}

	public static void sendSeedToChat(long seed) {
		if (seed != 0 && ConfigurationHandler.sendSeedToChat) {
			Minecraft mc = Minecraft.getInstance();
			Component component = ComponentUtils
					.wrapInSquareBrackets((new TextComponent(String.valueOf(seed))).withStyle((style) -> {
						return style.withColor(ChatFormatting.GREEN)
								.withClickEvent(
										new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(seed)))
								.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new TranslatableComponent("chat.copy.click")))
								.withInsertion(String.valueOf(seed));
					}));
			mc.gui.getChat().addMessage(new TranslatableComponent(Names.SHOW_SEED, component));
		}
	}
}
