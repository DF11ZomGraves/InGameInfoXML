package com.github.lunatrius.ingameinfo.util;

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
}
