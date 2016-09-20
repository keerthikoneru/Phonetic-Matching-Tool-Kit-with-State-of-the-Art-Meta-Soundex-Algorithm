package PhoneticMatching;

import PhoneticMatching.EncodeException;
import PhoneticMatching.Soundex;
import PhoneticMatching.Utils;
import PhoneticMatching.StrEncoder;

public class Soundex implements StrEncoder {

	/**
	 *
	 * @see #US_ENGLISH_MAPPING
	 */
	public static final String ENG_MAP_STR = "01230120022455012623010202";

	/**
	 *
	 * @see Soundex#Soundex(char[])
	 */
	private static final char[] ENG_MAP = ENG_MAP_STR.toCharArray();

	/**
	 * An instance of Soundex using the US_ENGLISH_MAPPING mapping.
	 *
	 * @see #US_ENGLISH_MAPPING
	 */
	public static final Soundex ENG = new Soundex();

	/**
	 *
	 * @deprecated This feature is not needed since the encoding size must be
	 *             constant. Will be removed in 2.0.
	 */
	/* @Deprecated */
	private int lgthMax = 4;

	private final char[] mapSdx;

	/**
	 * Creates an instance using US_ENGLISH_MAPPING
	 *
	 * @see Soundex#Soundex(char[])
	 * @see Soundex#US_ENGLISH_MAPPING
	 */
	public Soundex() {
		this.mapSdx = ENG_MAP;
	}

	/**
	 *
	 * @param mapping
	 *            Mapping array to use when finding the corresponding code for a
	 *            given character
	 */
	public Soundex(final char[] map) {
		this.mapSdx = new char[map.length];
		System.arraycopy(map, 0, this.mapSdx, 0, map.length);
	}

	/**
	 *
	 * @param mapping
	 *            Mapping string to use when finding the corresponding code for
	 *            a given character
	 * @since 1.4
	 */
	public Soundex(final String map) {
		this.mapSdx = map.toCharArray();
	}

	/**
	 *
	 * @param s1
	 *            A String that will be encoded and compared.
	 * @param s2
	 *            A String that will be encoded and compared.
	 * @return The number of characters in the two encoded Strings that are the
	 *         same from 0 to 4.
	 * @throws EncoderException
	 *             if an error occurs encoding one of the strings
	 */
	public int diff(final String str1, final String str2)
			throws EncodeException {
		return Utils.diff(this, str1, str2);
	}

	/**
	 *
	 * @param obj
	 *            Object to encode
	 * @return An object (or type java.lang.String) containing the soundex code
	 *         which corresponds to the String supplied.
	 * @throws EncoderException
	 *             if the parameter supplied is not of type java.lang.String
	 * @throws IllegalArgumentException
	 *             if a character is not mapped
	 */
	/* @Override */
	public Object encode(final Object input) throws EncodeException {
		if (!(input instanceof String)) {
			throw new EncodeException(
					"Parameter supplied to Soundex encode is not of type java.lang.String");
		}
		return soundex((String) input);
	}

	/**
	 * Encodes a String using the soundex algorithm.
	 *
	 * @param str
	 *            A String object to encode
	 * @return A Soundex code corresponding to the String supplied
	 * @throws IllegalArgumentException
	 *             if a character is not mapped
	 */
	/* @Override */
	public String encode(final String input) {
		return soundex(input);
	}

	/**
	 *
	 * @param str
	 *            the cleaned working string to encode (in upper case).
	 * @param index
	 *            the character position to encode
	 * @return Mapping code for a particular character
	 * @throws IllegalArgumentException
	 *             if the character is not mapped
	 */
	private char getCode(final String str, final int i) {
		// map() throws IllegalArgumentException
		final char mapch = this.map(str.charAt(i));
		// HW rule check
		if (i > 1 && mapch != '0') {
			final char hw = str.charAt(i - 1);
			if ('H' == hw || 'W' == hw) { // hw -- related to H and W
				final char preHW = str.charAt(i - 2);
				final char priorCode = this.map(preHW);
				if (priorCode == mapch || 'H' == preHW || 'W' == preHW) {
					return 0;
				}
			}
		}
		return mapch;
	}

	/**
	 * Returns the maxLength. Standard Soundex
	 *
	 * @deprecated This feature is not needed since the encoding size must be
	 *             constant. Will be removed in 2.0.
	 * @return int
	 */
	/* @Deprecated */
	public int getLgthMax() {
		return this.lgthMax;
	}

	/**
	 * Returns the soundex mapping.
	 *
	 * @return soundexMapping.
	 */
	private char[] getMapSdx() {
		return this.mapSdx;
	}

	/**
	 * Maps the given upper-case character to its Soundex code.
	 *
	 * @param ch
	 *            An upper-case character.
	 * @return A Soundex code.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>ch</code> is not mapped.
	 */
	private char map(final char ch) {
		final int j = ch - 'A';
		if (j < 0 || j >= this.getMapSdx().length) {
			throw new IllegalArgumentException("The character is not mapped: "
					+ ch);
		}
		return this.getMapSdx()[j];
	}

	/**
	 * Sets the maxLength.
	 *
	 * @deprecated This feature is not needed since the encoding size must be
	 *             constant. Will be removed in 2.0.
	 * @param maxLength
	 *            The maxLength to set
	 */
	/* @Deprecated */
	public void setLgthMax(final int lgthMax) {
		this.lgthMax = lgthMax;
	}

	/**
	 * Retrieves the Soundex code for a given String object.
	 *
	 * @param str
	 *            String to encode using the Soundex algorithm
	 * @return A soundex code for the String supplied
	 * @throws IllegalArgumentException
	 *             if a character is not mapped
	 */
	public String soundex(String input) {
		if (input == null) {
			return null;
		}
		input = Utils.clear(input);
		if (input.length() == 0) {
			return input;
		}
		final char output[] = { '0', '0', '0', '0' };
		char end, k; // k --- mapped variable
		int incnt = 1, outcnt = 1;
		output[0] = input.charAt(0);
		// getCode() throws IllegalArgumentException
		end = getCode(input, 0);
		while (incnt < input.length() && outcnt < output.length) {
			k = getCode(input, incnt++);
			if (k != 0) {
				if (k != '0' && k != end) {
					output[outcnt++] = k;
				}
				end = k;
			}
		}
		return new String(output);
	}

}