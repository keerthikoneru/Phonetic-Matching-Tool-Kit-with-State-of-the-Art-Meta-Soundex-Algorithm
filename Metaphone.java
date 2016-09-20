package PhoneticMatching;

import PhoneticMatching.EncodeException;
import PhoneticMatching.StrEncoder;

public class Metaphone implements StrEncoder {

	/**
	 * Five values in the English language
	 */
	private static final String AEIOU = "AEIOU";

	/**
	 * Variable used in Metaphone algorithm
	 */
	private static final String EIY = "EIY";

	/**
	 * Variable used in Metaphone algorithm
	 */
	private static final String CSPTG = "CSPTG";

	/**
	 * The max opcode length for metaphone is 12
	 */
	private int maxLength = 12;

	/**
	 * Creates an instance of the Metaphone encoder
	 */
	public Metaphone() {
		super();
	}

	/**
	 *
	 * @param txt
	 *            input string
	 * @return A metaphone code corresponding to the String
	 */
	public String metaphone(final String input) {
		boolean isChk = false;
		int inputLength;
		if (input == null || (inputLength = input.length()) == 0) {
			return "";
		}
		// single character is itself
		if (inputLength == 1) {
			return input.toUpperCase(java.util.Locale.ENGLISH);
		}

		final char[] instr = input.toUpperCase(java.util.Locale.ENGLISH)
				.toCharArray();

		final StringBuilder lclcode = new StringBuilder(40); // manipulate
		final StringBuilder opcode = new StringBuilder(10); // output
		// handle initial 2 characters exceptions
		switch (instr[0]) {
		case 'K':
		case 'G':
		case 'P': /* looking for KN, etc */
			if (instr[1] == 'N') {
				lclcode.append(instr, 1, instr.length - 1);
			} else {
				lclcode.append(instr);
			}
			break;
		case 'A': /* looking for AE */
			if (instr[1] == 'E') {
				lclcode.append(instr, 1, instr.length - 1);
			} else {
				lclcode.append(instr);
			}
			break;
		case 'W': /* looking for WR or WH */
			if (instr[1] == 'R') { // WR -> R
				lclcode.append(instr, 1, instr.length - 1);
				break;
			}
			if (instr[1] == 'H') {
				lclcode.append(instr, 1, instr.length - 1);
				lclcode.setCharAt(0, 'W'); // WH -> W
			} else {
				lclcode.append(instr);
			}
			break;
		case 'X': /* initial X becomes S */
			instr[0] = 'S';
			lclcode.append(instr);
			break;
		default:
			lclcode.append(instr);
		} // now lclcode has working string with initials fixed

		final int len = lclcode.length();
		int n = 0;

		while (opcode.length() < this.getMaxLength() && n < len) { // max opcode
																	// size of 4
																	// works
																	// well
			final char code = lclcode.charAt(n);
			// remove duplicate letters except C
			if (code != 'C' && isPrevChar(lclcode, n, code)) {
				n++;
			} else { // not dup
				switch (code) {
				case 'A':
				case 'E':
				case 'I':
				case 'O':
				case 'U':
					if (n == 0) {
						opcode.append(code);
					}
					break; // only use vowel if leading char
				case 'B':
					if (isPrevChar(lclcode, n, 'M') && isLstChar(len, n)) {
						// added by keerthi
						if (opcode.charAt(opcode.length() - 1) == 'M') {
							opcode.deleteCharAt(opcode.length() - 1);
							opcode.append('B');
						}// added by keerthi// B is silent if word ends in MB
						break;
					}
					opcode.append(code);
					break;
				case 'C': // lots of C special cases
					/* discard if SCI, SCE or SCY */
					if (isPrevChar(lclcode, n, 'S') && !isLstChar(len, n)
							&& EIY.indexOf(lclcode.charAt(n + 1)) >= 0) {
						break;
					}
					if (matchReg(lclcode, n, "CIA")) { // "CIA" -> X
						opcode.append('X');
						break;
					}
					if (!isLstChar(len, n)
							&& EIY.indexOf(lclcode.charAt(n + 1)) >= 0) {
						opcode.append('S');
						break; // CI,CE,CY -> S
					}
					if (isPrevChar(lclcode, n, 'S')
							&& isNxtChar(lclcode, n, 'H')) {
						// added by keerthi
						if (opcode.charAt(opcode.length() - 1) == 'S') {
							opcode.deleteCharAt(opcode.length() - 1);
							opcode.append('K');
							break;
						}// added by keerthi// SCH->sk
						opcode.append('K');
						break;
					}
					if (isNxtChar(lclcode, n, 'H')) { // detect CH
						if (n == 0 && len >= 3 && isVowel(lclcode, 2)) { // CH
																			// consonant
																			// ->
																			// K
																			// consonant
							opcode.append('K');
						} else {
							opcode.append('X'); // CHvowel -> X
						}
					} else {
						opcode.append('K');
					}
					break;
				case 'D':
					if (!isLstChar(len, n + 1) && isNxtChar(lclcode, n, 'G')
							&& EIY.indexOf(lclcode.charAt(n + 2)) >= 0) { // DGE
																			// DGI
																			// DGY
																			// ->
																			// J
						opcode.append('J');
						n += 2;
					} else {
						opcode.append('T');
					}
					break;
				case 'G': // GH silent at end or before consonant
					if (isLstChar(len, n + 1) && isNxtChar(lclcode, n, 'H')) {
						break;
					}
					if (!isLstChar(len, n + 1) && isNxtChar(lclcode, n, 'H')
							&& !isVowel(lclcode, n + 2)) {
						break;
					}
					if (n > 0
							&& (matchReg(lclcode, n, "GN") || matchReg(lclcode,
									n, "GNED"))) {
						break; // silent G
					}
					if (isPrevChar(lclcode, n, 'G')) {
						// NOTE: Given that duplicated chars are removed, I
						// don't see how this can ever be true
						isChk = true;
					} else {
						isChk = false;
					}
					if (!isLstChar(len, n)
							&& EIY.indexOf(lclcode.charAt(n + 1)) >= 0
							&& !isChk) {
						opcode.append('J');
					} else {
						opcode.append('K');
					}
					break;
				case 'H':
					if (isLstChar(len, n)) {
						break; // terminal H
					}
					if (n > 0 && CSPTG.indexOf(lclcode.charAt(n - 1)) >= 0) {
						break;
					}
					if (isVowel(lclcode, n + 1)) {
						opcode.append('H'); // Hvowel
					}
					break;
				case 'F':
				case 'J':
				case 'L':
				case 'M':
				case 'N':
				case 'R':
					opcode.append(code);
					break;
				case 'K':
					if (n > 0) { // not initial
						if (!isPrevChar(lclcode, n, 'C')) {
							opcode.append(code);
						}
					} else {
						opcode.append(code); // initial K
					}
					break;
				case 'P':
					if (isNxtChar(lclcode, n, 'H')) {
						// PH -> F
						opcode.append('F');
					} else {
						opcode.append(code);
					}
					break;
				case 'Q':
					opcode.append('K');
					break;
				case 'S':
					if (matchReg(lclcode, n, "SH")
							|| matchReg(lclcode, n, "SIO")
							|| matchReg(lclcode, n, "SIA")) {
						opcode.append('X');
					} else {
						opcode.append('S');
					}
					break;
				case 'T':
					// if (matchReg(lclcode,n,"TIA") ||
					// matchReg(lclcode,n,"TIO")) {
					// opcode.append('X');
					// break;
					// }
					// added by keerthi
					if (matchReg(lclcode, n, "TIA")) {
						opcode.append("XIA");
						break;
					}
					if (matchReg(lclcode, n, "TIO")) {
						opcode.append("XIO");
						break;
					}
					// added by keerthi
					if (matchReg(lclcode, n, "TCH")) {
						opcode.append("CH");// added by keerthi// Silent if in
											// "TCH"
						break;
					}
					// substitute numeral 0 for TH (resembles theta after all)
					if (matchReg(lclcode, n, "TH")) {
						// opcode.append('0');
						opcode.append('O');// added by keerthi
					} else {
						opcode.append('T');
					}
					break;
				case 'V':
					opcode.append('F');
					break;
				case 'W':
				case 'Y': // silent if not followed by vowel
					if (!isLstChar(len, n) && isVowel(lclcode, n + 1)) {
						opcode.append(code);
					}
					break;
				case 'X':
					opcode.append('K');
					opcode.append('S');
					break;
				case 'Z':
					opcode.append('S');
					break;
				default:
					// do nothing
					break;
				} // end switch
				n++;
			} // end else from code != 'C'
				// commented by venkat for removing max length
			if (opcode.length() > this.getMaxLength()) {
				opcode.setLength(this.getMaxLength());
			}
		}
		return opcode.toString();
	}

	/**
	 * @param str1
	 * @param i
	 * @return true if character at the position is vowel.
	 */
	private boolean isVowel(final StringBuilder str1, final int i) {
		return AEIOU.indexOf(str1.charAt(i)) >= 0;
	}

	/**
	 * @param str2
	 * @param j
	 * @param ch
	 * @return true if the previous character is given ch at j.
	 */
	private boolean isPrevChar(final StringBuilder str2, final int j,
			final char ch) {
		boolean cmp = false;
		if (j > 0 && j < str2.length()) {
			cmp = str2.charAt(j - 1) == ch;
		}
		return cmp;
	}

	/**
	 * @param str3
	 * @param k
	 * @param ch
	 * @return true if the next character is given ch at k.
	 */
	private boolean isNxtChar(final StringBuilder str3, final int k,
			final char ch) {
		boolean cmp = false;
		if (k >= 0 && k < str3.length() - 1) {
			cmp = str3.charAt(k + 1) == ch;
		}
		return cmp;
	}

	/**
	 * @param str4
	 * @param p
	 * @param eg
	 * @return true if the match exits
	 */
	private boolean matchReg(final StringBuilder str4, final int p,
			final String eg) {
		boolean cmp = false;
		if (p >= 0 && p + eg.length() - 1 < str4.length()) {
			final String substring = str4.substring(p, p + eg.length());
			cmp = substring.equals(eg);
		}
		return cmp;
	}

	/**
	 * @param len
	 * @param n
	 * @return true if character at n is last.
	 */
	private boolean isLstChar(final int len, final int n) {
		return n + 1 == len;
	}

	/**
	 *
	 * @param obj
	 *            Object to encode
	 * @return An object (or type java.lang.String) containing the metaphone
	 *         opcode which corresponds to the String supplied.
	 * @throws EncoderException
	 *             if the parameter supplied is not of type java.lang.String
	 */
	/* @Override */
	public Object encode(final Object input) throws EncodeException {
		if (!(input instanceof String)) {
			throw new EncodeException(
					"Parameter supplied to Metaphone encode is not of type java.lang.String");
		}
		return metaphone((String) input);
	}

	/**
	 * Encodes a String using the Metaphone algorithm.
	 *
	 * @param str
	 *            String object to encode
	 * @return The metaphone opcode corresponding to the String supplied
	 */
	/* @Override */
	public String encode(final String str) {
		return metaphone(str);
	}

	/**
	 * Tests is the metaphones of two strings are identical.
	 *
	 * @param str1
	 *            First of two strings to compare
	 * @param str2
	 *            Second of two strings to compare
	 * @return <opcode>true</opcode> if the metaphones of these strings are
	 *         identical, <opcode>false</opcode> otherwise.
	 */
	public boolean isCodeEqual(final String str1, final String str2) {
		return metaphone(str1).equals(metaphone(str2));
	}

	/**
	 * Returns the maxCodeLen.
	 * 
	 * @return int
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * Sets the maxCodeLen.
	 * 
	 * @param maxCodeLen
	 *            The maxCodeLen to set
	 */
	public void setMaxLength(final int maxLength) {
		this.maxLength = maxLength;
	}

}
