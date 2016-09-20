package PhoneticMatching;

import PhoneticMatching.Utils;

/**
 * @author keerthi
 *
 */
public class SpanishSoundex {
	/**
	 * @param inputstring
	 * @return string updated with vowels
	 */
	private String strtr(String inputstring) {
		if (inputstring != "") {
			inputstring = inputstring.replace('á', 'A');
			inputstring = inputstring.replace('ç', 'C');
			inputstring = inputstring.replace('é', 'E');
			inputstring = inputstring.replace('í', 'I');
			inputstring = inputstring.replace('ó', 'O');
			inputstring = inputstring.replace('ú', 'U');
			inputstring = inputstring.replace("ü", "U");
			return inputstring;
		} else
			return inputstring;
	}

	/**
	 * @param input
	 * @return soundex encoded code
	 */
	public String spanishsoundex(String input) {
		input = strtr(input.toLowerCase());
		input = Utils.clear(input);
		if (input.length() == 0) {
			return input;
		}
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'
					|| ch == 'W') {
				input = input.substring(0, i) + input.substring(i + 1);
				i--;
			}
			if (ch == 'H' && ch != input.charAt(0)) {
				input = input.substring(0, i) + input.substring(i + 1);
				i--;
			}
		}
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			switch (ch) {
			case 'P':
				input = input.replace(input.charAt(i), '0');
				break;
			case 'B':
			case 'V':
				input = input.replace(input.charAt(i), '1');
				break;
			case 'F':
			case 'H':
				input = input.replace(input.charAt(i), '2');
				break;
			case 'D':
			case 'T':
				input = input.replace(input.charAt(i), '3');
				break;
			case 'S':
			case 'Z':
			case 'C':
			case 'X':
				input = input.replace(input.charAt(i), '4');
				break;
			case 'Y':
				input = input.replace(input.charAt(i), '5');
				break;
			case 'L':
				if (i < input.length() - 1 && input.charAt(i + 1) == 'L') {
					input = input.replace("LL", "5");
					i = i - 1;
				} else
					input = input.replace(input.charAt(i), '5');
				break;
			case 'N':
			case 'Ñ':
			case 'M':
				input = input.replace(input.charAt(i), '6');
				break;
			case 'Q':
			case 'K':
				input = input.replace(input.charAt(i), '7');
				break;
			case 'G':
			case 'J':
				input = input.replace(input.charAt(i), '8');
				break;
			case 'R':
				if (i < input.length() - 1 && input.charAt(i + 1) == 'R') {
					input = input.replace("RR", "9");
					i = i - 1;
				} else
					input = input.replace(input.charAt(i), '9');
				break;
			default:
			}
		}

		return input;
	}

}
