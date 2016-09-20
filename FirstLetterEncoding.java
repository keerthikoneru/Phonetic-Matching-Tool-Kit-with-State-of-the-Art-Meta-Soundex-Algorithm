package PhoneticMatching;

/**
 * @author keerthi
 *
 */
public class FirstLetterEncoding {

	/**
	 * @param inputstring
	 * @return string after encoding first letter in the generated MetaSoundex code.
	 */
	public String firstletterencode(String inputstring){
		if (inputstring.length() == 0) {
            return inputstring;
        }
		char ch = inputstring.charAt(0);
		switch(ch)
		{
		case 'A':
		case 'E':
		case 'I':
		case 'O':
		case 'U':
			inputstring = inputstring.replace(inputstring.charAt(0), '0');
			break;
		case 'Y':
			inputstring = inputstring.replace(inputstring.charAt(0), '1');
			break;
		case 'D':
		case 'T':
			inputstring = inputstring.replace(inputstring.charAt(0), '3');
			break;
		case 'C':
		case 'J':
		case 'S':
		case 'Z':
			inputstring = inputstring.replace(inputstring.charAt(0), '4');
			break;
		case 'G':
		case 'H':
		case 'K':
		case 'Q':
		case 'X':
			inputstring = inputstring.replace(inputstring.charAt(0), '5');
			break;
		case 'M':
		case 'N':
			inputstring = inputstring.replace(inputstring.charAt(0), '6');
			break;
		case 'B':
		case 'F':
		case 'P':
		case 'V':
		case 'W':
			inputstring = inputstring.replace(inputstring.charAt(0), '7');
			break;
		case 'L':
			inputstring = inputstring.replace(inputstring.charAt(0), '8');
			break;
		case 'R':
			inputstring = inputstring.replace(inputstring.charAt(0), '9');
			break;
		default:
		}
		return inputstring;
		
	}

}
