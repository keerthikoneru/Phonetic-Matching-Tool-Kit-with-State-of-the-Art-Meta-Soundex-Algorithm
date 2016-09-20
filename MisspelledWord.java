package PhoneticMatching;

import java.util.ArrayList;

/**
 * @author keerthi
 *
 */
public class MisspelledWord {

	private String misspelled;
	private String algorithm;
	private String code;
	private String language;
	private ArrayList<String> suggestionlist;

	/**
	 * @return the misspelled
	 */
	public String getMisspelled() {
		return misspelled;
	}

	/**
	 * @param misspelled
	 *            the misspelled to set
	 */
	public void setMisspelled(String misspelled) {
		this.misspelled = misspelled;
	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm
	 *            the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the suggestionlist
	 */
	public ArrayList<String> getSuggestionlist() {
		return suggestionlist;
	}

	/**
	 * @param suggestionlist
	 *            the suggestionlist to set
	 */
	public void setSuggestionlist(ArrayList<String> suggestionlist) {
		this.suggestionlist = suggestionlist;
	}

}
