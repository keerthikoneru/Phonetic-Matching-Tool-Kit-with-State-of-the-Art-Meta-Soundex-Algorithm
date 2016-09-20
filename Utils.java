package PhoneticMatching;

import PhoneticMatching.EncodeException;
import PhoneticMatching.StrEncoder;

final class Utils {

	/**
	 * Cleans up the input string before Soundex processing by only returning
	 * upper case letters.
	 *
	 * @param str
	 *            The String to clean.
	 * @return A clean String.
	 */
	static String clear(final String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		final int length = str.length();
		final char[] ch = new char[length];
		int cnt = 0;
		for (int i = 0; i < length; i++) {
			if (Character.isLetter(str.charAt(i))) {
				ch[cnt++] = str.charAt(i);
			}
		}
		if (cnt == length) {
			return str.toUpperCase(java.util.Locale.ENGLISH);
		}
		return new String(ch, 0, cnt).toUpperCase(java.util.Locale.ENGLISH);
	}

	/**
	 * @param encdr
	 * @param s1
	 * @param s2
	 * @return difference
	 * @throws EncodeException
	 */
	static int diff(final StrEncoder encdr, final String s1, final String s2)
			throws EncodeException {
		return diffEncoded(encdr.encode(s1), encdr.encode(s2));
	}

	/**
	 * @param str1
	 * @param str2
	 * @return difference
	 */
	static int diffEncoded(final String str1, final String str2) {

		if (str1 == null || str2 == null) {
			return 0;
		}
		final int matchingLength = Math.min(str1.length(), str2.length());
		int diff = 0;
		for (int i = 0; i < matchingLength; i++) {
			if (str1.charAt(i) == str2.charAt(i)) {
				diff++;
			}
		}
		return diff;
	}

}