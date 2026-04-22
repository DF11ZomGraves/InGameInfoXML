package com.github.lunatrius.ingameinfo.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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

	public static String getFloatDisplayFormat(double value, int decimalPlace) {
		if (decimalPlace < 0)
			decimalPlace = 0;
		else if (decimalPlace > 6)
			decimalPlace = 6;
		StringBuilder s = new StringBuilder();
		if (decimalPlace == 0)
			s.append('0');
		else {
			s.append("0.");
			for (int i = 0; i < decimalPlace; i++)
				s.append('0');
		}
		DecimalFormat df = new DecimalFormat(s.toString());
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(value);
	}
}
