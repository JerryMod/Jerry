package pet.jerry.util;

import java.util.*;

public enum RomanNumerals {
	M(1000),
	D(500),
	C(100),
	L(50),
	X(10),
	V(5),
	I(1);

	private final int number;

	RomanNumerals(int number) {
		this.number = number;
	}

	public static int parseRomanNumeral(String roman) {
		roman = roman.toUpperCase(Locale.ROOT).trim();
		int last = -1;
		int value = 0;
		for (int i = roman.toCharArray().length - 1; i >= 0; i--) {
			int charValue = RomanNumerals.valueOf(roman.charAt(i) + "").number;
			if(charValue < last) {
				value -= charValue;
			} else {
				value += charValue;
			}
			last = charValue;
		}

		return value;
	}
}
