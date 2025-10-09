package df11zomgraves.ingameinfo.util;

import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;

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
			Component component = ComponentUtils.copyOnClickText(String.valueOf(seed));
			Minecraft mc = Minecraft.getInstance();
			mc.gui.getChat().addMessage(Component.translatable(Names.SHOW_SEED, component));
		}
	}
}
