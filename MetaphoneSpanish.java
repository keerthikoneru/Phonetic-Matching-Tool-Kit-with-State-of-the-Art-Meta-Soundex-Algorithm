package PhoneticMatching;

import PhoneticMatching.Utils;

/**
 * @author keerthi
 *
 */
public class MetaphoneSpanish {

	/**
	 * @param string
	 * @return Metaphone code for Spanish word.
	 */
	public String metaphone(String string) {
		String meta_key = "";
		int key_length = 6;
		int current_pos = 0;
		String original_string = string + "    ";
		original_string = strtr(original_string.toLowerCase());
		original_string = Utils.clear(original_string);
		char[] a = { 'D', 'F', 'J', 'K', 'M', 'N', 'P', 'T', 'V', 'L', 'Y' };
		// main loop
		while (meta_key.length() < key_length) {
			if (current_pos >= original_string.length())
				break;
			char current_char = original_string.charAt(current_pos);
			if ((is_vowel(original_string, current_pos)) && (current_pos == 0)) {
				meta_key += current_char;
				current_pos += 1;
			} else {
				if (string_at(original_string, current_pos, 1, a)) {
					meta_key += current_char;
					if (current_pos < original_string.length() - 1) {
						if (original_string.charAt(current_pos + 1) == current_char) {
							current_pos += 2;
						} else {
							current_pos += 1;
						}
					} else {
						current_pos += 1;
					}
				} else {
					if (current_char == 'C') {

						if (current_pos < original_string.length() - 1
								&& original_string.charAt(current_pos + 1) == 'C') {
							meta_key += 'X';
							current_pos += 2;
						}

						else if (current_pos + 1 < original_string.length() - 1
								&& (original_string.substring(current_pos,
										current_pos + 2) == "CE" || original_string
										.substring(current_pos, current_pos + 2) == "CI")) {
							meta_key += 'Z';
							current_pos += 2;
						} else {
							meta_key += 'K';
							current_pos += 1;
						}
					} else if (current_char == 'G') {
						if (current_pos + 1 < original_string.length() - 1
								&& (original_string.substring(current_pos,
										current_pos + 2) == "GE" || original_string
										.substring(current_pos, current_pos + 2) == "GI")) {
							meta_key += 'J';
							current_pos += 2;
						}

						else {
							meta_key += 'G';
							current_pos += 1;
						}
					} else if (current_char == 'H') {
						if (is_vowel(original_string, current_pos + 1)) {
							meta_key += original_string.charAt(current_pos + 1);
							current_pos += 2;
						} else {
							meta_key += 'H';
							current_pos += 1;
						}
					} else if (current_char == 'Q') {
						if (current_pos < original_string.length() - 1
								&& original_string.charAt(current_pos + 1) == 'U') {
							current_pos += 2;
						} else {
							current_pos += 1;
						}

						meta_key += 'K';
					} else if (current_char == 'W') {
						meta_key += 'U';
						current_pos += 1;
					} else if (current_char == 'R') {
						current_pos += 1;
						meta_key += 'R';
					} else if (current_char == 'S') {
						if (is_vowel(original_string, current_pos + 1)
								&& (current_pos == 0)) {
							meta_key += "ES";
							current_pos += 1;
						} else {
							current_pos += 1;
							meta_key += 'S';
						}
					} else if (current_char == 'Z') {
						current_pos += 1;
						meta_key += 'Z';
					} else if (current_char == 'X') {
						if (is_vowel(original_string, current_pos + 1)
								&& string.length() > 1 && (current_pos == 0)) {
							meta_key += "EX";
							current_pos += 1;
						} else {
							meta_key += 'X';
							current_pos += 1;
						}
					} else {
						current_pos += 1;
					}
				}
			}
		}
		meta_key = meta_key.trim();
		System.out.println(meta_key);
		return meta_key;
	}

	private boolean string_at(String original_string, int current_pos, int i,
			char[] a) {
		if ((current_pos < 0) || (current_pos >= original_string.length()))
			return false;
		for (int k = 0; k < a.length; k++) {
			if (original_string.charAt(current_pos) == a[k])
				return true;
		}
		return false;
	}

	/**
	 * @param original_string
	 * @param current_pos
	 * @return returns true if the character at current position is vowel
	 */
	private boolean is_vowel(String original_string, int current_pos) {
		if (current_pos >= original_string.length())
			return false;
		else {
			if (original_string.charAt(current_pos) == 'A'
					|| original_string.charAt(current_pos) == 'E'
					|| original_string.charAt(current_pos) == 'I'
					|| original_string.charAt(current_pos) == 'O'
					|| original_string.charAt(current_pos) == 'U')
				return true;
			else
				return false;
		}
	}

	/**
	 * @param stringlwr
	 * @return returns string with characters updated to vowels.
	 */
	private String strtr(String stringlwr) {
		if (stringlwr != "") {
			stringlwr = stringlwr.replace('á', 'A');
			stringlwr = stringlwr.replace("ch", "X");
			stringlwr = stringlwr.replace('ç', 'S');
			stringlwr = stringlwr.replace('é', 'E');
			stringlwr = stringlwr.replace('í', 'I');
			stringlwr = stringlwr.replace('ó', 'O');
			stringlwr = stringlwr.replace('ú', 'U');
			stringlwr = stringlwr.replace("ñ", "NY");
			stringlwr = stringlwr.replace("gü", "W");
			stringlwr = stringlwr.replace("ü", "U");
			stringlwr = stringlwr.replace('b', 'V');
			stringlwr = stringlwr.replace("ll", "Y");
			return stringlwr;
		} else
			return stringlwr;
	}

}
