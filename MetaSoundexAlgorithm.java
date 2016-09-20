package PhoneticMatching;


/**
 * @author keerthi
 *
 */
public class MetaSoundexAlgorithm {
	
	/**
	 * @param inputString
	 * @return MetaSoundex code for English word.
	 */
	public String MetaSoundex(String inputString){
		Soundex obj =new Soundex();
		Metaphone met = new Metaphone();
		FirstLetterEncoding fle = new FirstLetterEncoding();
		String meta_code= met.metaphone(inputString);
		String mtsoundex_code = obj.soundex(meta_code);
		String code = fle.firstletterencode(mtsoundex_code);
		return code;
	}
	
	/**
	 * @param inputString
	 * @return MetaSoundex code for Spanish
	 */
	public String MetaSoundexSpanish(String inputString){
		SpanishSoundex obj2 = new SpanishSoundex();
		MetaphoneSpanish pa = new MetaphoneSpanish();
		String smeta_code= pa.metaphone(inputString);
		String smtsoundex_code = obj2.spanishsoundex(smeta_code);
		return smtsoundex_code;
	}
}
