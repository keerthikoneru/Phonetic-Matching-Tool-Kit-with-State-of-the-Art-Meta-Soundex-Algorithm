package PhoneticMatching;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keerthi
 *
 */
public class MatchedComparison_Caverphone {

	/**
	 * To update the wordlist for corrected words and misspelled words.
	 * 
	 * @param savepath
	 * @param WordSuggestions
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public void matched_caverphone(String savepath,
			List<WordSuggestions> WordSuggestions)
			throws FileNotFoundException, IOException, Exception {
		/**
		 * Comparison of Caverphone word list with Original list
		 */
		String path = savepath;
		String file1 = path + "mismatched_caverphone.csv";
		String file2 = path + "matched_caverphone.csv";
		String file3 = path + "Correct.csv";
		ArrayList<String> al1 = new ArrayList<String>();// ArrayList for
														// Manipulated Data
		ArrayList<String> al2 = new ArrayList<String>();// ArrayList for matched
														// word list of
														// Caverphone
		ArrayList<String> al3 = new ArrayList<String>();// ArrayList for
														// Reference Data

		/**
		 * Reading Manipulated Data into Arraylist start
		 */
		BufferedReader CSVFile1 = new BufferedReader(new FileReader(file1));
		String dataRow1 = CSVFile1.readLine();

		while (dataRow1 != null) {
			String[] dataArray1 = dataRow1.split("\n");
			for (String item1 : dataArray1) {
				al1.add(item1);
			}
			dataRow1 = CSVFile1.readLine();
		}
		CSVFile1.close();
		/**
		 * Reading Manipulated Data into Arraylist start
		 */

		/**
		 * Reading Matched word list of Caverphone into Arraylist start
		 */
		BufferedReader CSVFile2 = new BufferedReader(new FileReader(file2));
		String dataRow2 = CSVFile2.readLine();

		while (dataRow2 != null) {
			String[] dataArray2 = dataRow2.split("\n");
			for (String item2 : dataArray2) {
				al2.add(item2);
				// System.out.println("matched cavrphone: " + item2);
			}
			dataRow2 = CSVFile2.readLine();
		}
		CSVFile2.close();
		/**
		 * Reading Matched word list of Caverphone into Arraylist start
		 */

		/**
		 * Reading Reference Data into Arraylist start
		 */
		BufferedReader CSVFile3 = new BufferedReader(new FileReader(file3));
		String dataRow3 = CSVFile3.readLine();

		while (dataRow3 != null) {
			String[] dataArray3 = dataRow3.split("\n");
			for (String item3 : dataArray3) {
				al3.add(item3);
			}
			dataRow3 = CSVFile3.readLine();
		}

		CSVFile3.close();
		/**
		 * Reading Reference Data into Arraylist start
		 */

		/**
		 * Updating Manipulated data based on Matched list of Caverphone start
		 */
		FileWriter writer = new FileWriter(file1);
		int k = 0;
		while (k < al3.size()) {
			for (String a2 : al2) {
				String[] parts = a2.split(",");
				String part1 = parts[0];
				String part2 = parts[1];
				String part3 = parts[2];
				if (al1.get(k).matches(part1)) {
					if (part2.matches(al3.get(k))
							&& Integer.parseInt(part3) == k) {
						writer.append(part2);
						writer.append('\n');
						for (WordSuggestions ws : WordSuggestions) {
							if (ws.type.matches("Caverphone")
									&& ws.suggested_word.matches(part2)) {
								ws.corrected = true;
							}
						}
					}
				}
			}
			k++;
		}
		writer.flush();
		writer.close();
		/**
		 * Updating Manipulated data based on Matched list of Caverphone end
		 */
	}
}
