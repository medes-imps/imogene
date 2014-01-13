package org.imogene.android.util.base26;

public class Base26 {

	public static String encode(long num) {
		if (num < 0) {
			throw new IllegalArgumentException("Only positive numbers are supported");
		}
		StringBuilder s = new StringBuilder("AAAA");
		for (int pos = 3; pos >= 0 && num > 0; pos--) {
			char digit = (char) ('A' + num % 26);
			s.setCharAt(pos, digit);
			num = num / 26;
		}
		return s.toString();
	}
	
	public static long decode(String data) {
		if (!data.matches("[A-Z]*")) {
			throw new IllegalArgumentException("Not a base26 integer representation");
		}
		int length = data.length();
		long result = 0;
		for (int i = 0; i < length; i++) {
			result += (data.charAt(i) - 'A')*Math.pow(26, length-i-1);
		}
		return result;
	}

}
