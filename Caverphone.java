package PhoneticMatching;

import PhoneticMatching.AbsCaverphone;

/**
 * @author keerthi
 *
 */
public class Caverphone extends AbsCaverphone {

    private static final String char_ten = "1111111111";

    /**
     * Generates Caverphone code for the given String.
     *
     */
    /* (non-Javadoc)
     * @see PhoneticMatching.StrEncoder#encode(java.lang.String)
     */
    public String encode(final String input) {
        String val = input;
        if (val == null || val.length() == 0) {
            return char_ten;
        }

        /**
         *  Convert all letters to lowercase
         */
        val = val.toLowerCase(java.util.Locale.ENGLISH);

        /**
         *  Remove anything not A-Z
         */
        val = val.replaceAll("[^a-z]", "");

        val = val.replaceAll("e$", "");

        /**
         *  Handle various replacements to generate Phonetic code using Caverphone Algorithm
         */
        val = val.replaceAll("^cough", "cou2f");
        val = val.replaceAll("^rough", "rou2f");
        val = val.replaceAll("^tough", "tou2f");
        val = val.replaceAll("^enough", "enou2f");
        val = val.replaceAll("^trough", "trou2f");
                                            
        val = val.replaceAll("^gn", "2n");

        val = val.replaceAll("mb$", "m2");

        val = val.replaceAll("cq", "2q");
        val = val.replaceAll("ci", "si");
        val = val.replaceAll("ce", "se");
        val = val.replaceAll("cy", "sy");
        val = val.replaceAll("tch", "2ch");
        val = val.replaceAll("c", "k");
        val = val.replaceAll("q", "k");
        val = val.replaceAll("x", "k");
        val = val.replaceAll("v", "f");
        val = val.replaceAll("dg", "2g");
        val = val.replaceAll("tio", "sio");
        val = val.replaceAll("tia", "sia");
        val = val.replaceAll("d", "t");
        val = val.replaceAll("ph", "fh");
        val = val.replaceAll("b", "p");
        val = val.replaceAll("sh", "s2");
        val = val.replaceAll("z", "s");
        val = val.replaceAll("^[aeiou]", "A");
        val = val.replaceAll("[aeiou]", "3");
        val = val.replaceAll("j", "y");
        val = val.replaceAll("^y3", "Y3");
        val = val.replaceAll("^y", "A");
        val = val.replaceAll("y", "3");
        val = val.replaceAll("3gh3", "3kh3");
        val = val.replaceAll("gh", "22");
        val = val.replaceAll("g", "k");
        val = val.replaceAll("s+", "S");
        val = val.replaceAll("t+", "T");
        val = val.replaceAll("p+", "P");
        val = val.replaceAll("k+", "K");
        val = val.replaceAll("f+", "F");
        val = val.replaceAll("m+", "M");
        val = val.replaceAll("n+", "N");
        val = val.replaceAll("w3", "W3");
        val = val.replaceAll("wh3", "Wh3");
        val = val.replaceAll("w$", "3");
        val = val.replaceAll("w", "2");
        val = val.replaceAll("^h", "A");
        val = val.replaceAll("h", "2");
        val = val.replaceAll("r3", "R3");
        val = val.replaceAll("r$", "3");
        val = val.replaceAll("r", "2");
        val = val.replaceAll("l3", "L3");
        val = val.replaceAll("l$", "3");
        val = val.replaceAll("l", "2");

        /**
         *  Handles replacement of numbers
         */
        val = val.replaceAll("2", "");
        val = val.replaceAll("3$", "A");
        val = val.replaceAll("3", "");

        /**
         * appending 1s if needed on the end
         */
        val = val + char_ten;

        return val.substring(0, char_ten.length());
    }

}
