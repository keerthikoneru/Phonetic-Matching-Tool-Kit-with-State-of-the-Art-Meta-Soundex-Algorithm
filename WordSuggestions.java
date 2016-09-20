package PhoneticMatching;

/**
 * @author keerthi
 *
 */
public class WordSuggestions {
	public String type;
	public String actual_word;
	public String suggested_word;
	public int number;
	public int count;
	public boolean corrected;
	public int printed;

	/**
	 * ArrayList for saving WordList Information
	 */
	public WordSuggestions() {
		type = "";
		actual_word = "";
		suggested_word = "";
		number = 0;
		corrected = false;
	}
}
