package PhoneticMatching;

import java.util.regex.Pattern;

import PhoneticMatching.EncodeException;
import PhoneticMatching.StrEncoder;

public class Nysiis implements StrEncoder {

	private static final char[] CHARS_A = new char[] { 'A' };
	private static final char[] CHARS_AF = new char[] { 'A', 'F' };
	private static final char[] CHARS_C = new char[] { 'C' };
	private static final char[] CHARS_FF = new char[] { 'F', 'F' };
	private static final char[] CHARS_G = new char[] { 'G' };
	private static final char[] CHARS_N = new char[] { 'N' };
	private static final char[] CHARS_NN = new char[] { 'N', 'N' };
	private static final char[] CHARS_S = new char[] { 'S' };
	private static final char[] CHARS_SSS = new char[] { 'S', 'S', 'S' };

	private static final Pattern PAT_MAC = Pattern.compile("^MAC");
	private static final Pattern PAT_KN = Pattern.compile("^KN");
	private static final Pattern PAT_K = Pattern.compile("^K");
	private static final Pattern PAT_PH_PF = Pattern.compile("^(PH|PF)");
	private static final Pattern PAT_SCH = Pattern.compile("^SCH");
	private static final Pattern PAT_EE_IE = Pattern.compile("(EE|IE)$");
	private static final Pattern PAT_DT_ETC = Pattern
			.compile("(DT|RT|RD|NT|ND)$");

	private static final char SPACE = ' ';
	private static final int TRUE_LENGTH = 6;

	/**
	 * @param c
	 * @return true if character is vowel
	 */
	private static boolean isVowel(final char c) {
		return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
	}

	/**
	 * @param prev
	 * @param curr
	 * @param next
	 * @param aNext
	 * @return transcodes remaining part of string and sends characters
	 */
	private static char[] transcodeRemaining(final char prev, final char curr,
			final char next, final char aNext) {
		// 1. EV -> AF
		if (curr == 'E' && next == 'V') {
			return CHARS_AF;
		}

		// A, E, I, O, U -> A
		if (isVowel(curr)) {
			return CHARS_A;
		}

		// 2. Q -> G, Z -> S, M -> N
		if (curr == 'Q') {
			return CHARS_G;
		} else if (curr == 'Z') {
			return CHARS_S;
		} else if (curr == 'M') {
			return CHARS_N;
		}

		// 3. KN -> NN else K -> C
		if (curr == 'K') {
			if (next == 'N') {
				return CHARS_NN;
			} else {
				return CHARS_C;
			}
		}
		// 4. SCH -> SSS
		if (curr == 'S' && next == 'C' && aNext == 'H') {
			return CHARS_SSS;
		}

		// PH -> FF
		if (curr == 'P' && next == 'H') {
			return CHARS_FF;
		}

		// 5. H -> If previous or next is a non vowel, previous.
		if (curr == 'H' && (!isVowel(prev) || !isVowel(next))) {
			return new char[] { prev };
		}

		// 6. W -> If previous is vowel, previous.
		if (curr == 'W' && isVowel(prev)) {
			return new char[] { prev };
		}

		return new char[] { curr };
	}

	private final boolean strict;

	public Nysiis() {
		this(true);
	}

	public Nysiis(final boolean strict) {
		this.strict = strict;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PhoneticMatching.Encode#encode(java.lang.Object)
	 */
	@Override
	public Object encode(final Object obj) throws EncodeException {
		if (!(obj instanceof String)) {
			throw new EncodeException(
					"Parameter supplied to Nysiis encode is not of type java.lang.String");
		}
		return this.nysiis((String) obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PhoneticMatching.StrEncoder#encode(java.lang.String)
	 */
	@Override
	public String encode(final String str) {
		return this.nysiis(str);
	}

	/**
	 * @return true if encoder is configured to strict mode
	 */
	public boolean isStrict() {
		return this.strict;
	}

	/**
	 * @param str
	 * @return nysiis code for given string object.
	 */
	public String nysiis(String str) {
		if (str == null) {
			return null;
		}

		if (str.length() == 0) {
			return str;
		}

		// Translate first characters of name:
		// MAC -> MCC, KN -> NN, K -> C, PH | PF -> FF, SCH -> SSS
		str = PAT_MAC.matcher(str).replaceFirst("MCC");
		str = PAT_KN.matcher(str).replaceFirst("NN");
		str = PAT_K.matcher(str).replaceFirst("C");
		str = PAT_PH_PF.matcher(str).replaceFirst("FF");
		str = PAT_SCH.matcher(str).replaceFirst("SSS");

		// Translate last characters of name:
		// EE -> Y, IE -> Y, DT | RT | RD | NT | ND -> D
		str = PAT_EE_IE.matcher(str).replaceFirst("Y");
		str = PAT_DT_ETC.matcher(str).replaceFirst("D");

		// First character of key = first character of name.
		final StringBuilder key = new StringBuilder(str.length());
		key.append(str.charAt(0));

		// Transcode remaining characters, incrementing by one character each
		// time
		final char[] chars = str.toCharArray();
		final int len = chars.length;

		for (int i = 1; i < len; i++) {
			final char next = i < len - 1 ? chars[i + 1] : SPACE;
			final char aNext = i < len - 2 ? chars[i + 2] : SPACE;
			final char[] transcoded = transcodeRemaining(chars[i - 1],
					chars[i], next, aNext);
			System.arraycopy(transcoded, 0, chars, i, transcoded.length);

			// only append the current char to the key if it is different from
			// the last one
			if (chars[i] != chars[i - 1]) {
				key.append(chars[i]);
			}
		}

		if (key.length() > 1) {
			char lastChar = key.charAt(key.length() - 1);

			// If last character is S, remove it.
			if (lastChar == 'S') {
				key.deleteCharAt(key.length() - 1);
				lastChar = key.charAt(key.length() - 1);
			}

			if (key.length() > 2) {
				final char last2Char = key.charAt(key.length() - 2);
				// If last characters are AY, replace with Y.
				if (last2Char == 'A' && lastChar == 'Y') {
					key.deleteCharAt(key.length() - 2);
				}
			}

			// If last character is A, remove it.
			if (lastChar == 'A') {
				key.deleteCharAt(key.length() - 1);
			}
		}

		final String string = key.toString();
		return this.isStrict() ? string.substring(0,
				Math.min(TRUE_LENGTH, string.length())) : string;
	}

}
