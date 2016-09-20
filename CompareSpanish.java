package PhoneticMatching;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * @author keerthi
 *
 */
public class CompareSpanish {

	/**
	 * Compares uploaded input files and generated intermediate files to
	 * calculate precision and Recall for three Phonetic Matching Algorithms
	 * (Metaphone,Soundex,MetaSoundex)
	 * 
	 * @param savepath
	 *            -- Dynamic path to save Intermediate files
	 * @param filename1
	 *            -- Reference File
	 * @param filename2
	 *            -- Incorrect File
	 * @param WordSuggestions
	 *            -- ArrayList depicting suggested word list.
	 * @return Precision, Recall and F-Measure values of Spanish Soundex,
	 *         Spanish Metaphone and Spanish MetaSoundex
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 * @throws ArithmeticException
	 */
	public float[] comp(String savepath, String filename1, String filename2,
			List<WordSuggestions> WordSuggestions)
			throws FileNotFoundException, IOException, Exception,
			ArithmeticException {
		long timeStart = System.currentTimeMillis();
		String path = savepath;
		String file1 = path + filename1;// Reference data
		String file2 = path + filename2;// Manipulated data
		String file3 = path + "mismatched_spanishmetaphone.csv";
		String file4 = path + "mismatched_spanishsoundex.csv";
		String file5 = path + "mismatched_spanishmetasoundex.csv";
		String file6 = path + "SpanishCorrect.csv";
		String file7 = path + "matched_spanishmetaphone.csv";
		String file8 = path + "matched_spanishsoundex.csv";
		String file16 = path + "matched_spanishmetasoundex.csv";
		String filems = path + "spanishmismatchedforreference.csv";
		ArrayList<String> al1 = new ArrayList<String>();// ArrayList for reading
														// Reference Data
		ArrayList<String> al2 = new ArrayList<String>();// ArrayList for reading
														// Manipulated Data
		ArrayList<String> al3 = new ArrayList<String>();// ArrayList for
														// Mismatched Reference
														// Data
		ArrayList<String> al4 = new ArrayList<String>();// ArrayList for matched
														// word list of
														// Metaphone
		ArrayList<String> al5 = new ArrayList<String>();// ArrayList for matched
														// word list of Soundex
		ArrayList<String> al14 = new ArrayList<String>();// ArrayList for
															// matched word list
															// of MetaSoundex
		ArrayList<String> al7 = new ArrayList<String>();// ArrayList for saving
														// words corrected by
														// Metaphone
		ArrayList<String> al8 = new ArrayList<String>();// ArrayList for saving
														// words corrected by
														// Soundex
		ArrayList<String> al15 = new ArrayList<String>();// ArrayList for saving
															// words corrected
															// by MetaSoundex
		/**
		 * Reading Reference Data into Arraylist start
		 */
		BufferedReader CSVFile1 = new BufferedReader(new FileReader(file1));
		String dataRow1 = CSVFile1.readLine();
		while (dataRow1 != null) {
			dataRow1 = dataRow1.toUpperCase();
			dataRow1 = dataRow1.replaceAll("[^a-zA-Z ]+", "");
			String[] dataArray1 = dataRow1.split(" ");
			for (String item1 : dataArray1) {
				al1.add(item1);
				al3.add(item1);
			}
			dataRow1 = CSVFile1.readLine(); // Read next line of data.
		}

		CSVFile1.close();

		System.out.println(al1.size());
		/**
		 * Reading Reference Data into Arraylist end
		 */

		/**
		 * Reading Manipulated Data into Arraylist start
		 */
		BufferedReader CSVFile2 = new BufferedReader(new FileReader(file2));
		String dataRow2 = CSVFile2.readLine();
		while (dataRow2 != null) {
			dataRow2 = dataRow2.toUpperCase();
			dataRow2 = dataRow2.replaceAll("[^a-zA-Z ]+", "");
			String[] dataArray2 = dataRow2.split(" ");
			for (String item2 : dataArray2) {
				al2.add(item2);
			}
			dataRow2 = CSVFile2.readLine(); // Read next line of data.
		}
		CSVFile2.close();
		System.out.println("********************************");

		System.out.println(al2.size());
		/**
		 * Reading Manipulated Data into Arraylist end
		 */

		/**
		 * Removing the matched words in the Arraylists of Reference and
		 * Manipulated Data start
		 */
		int u = 0;
		while (u < al3.size()) {
			if (al3.get(u).matches(al2.get(u))) {
				al2.remove(u);
				al3.remove(u);
				u--;
			}
			u++;
		}
		/**
		 * Removing the matched words in the Arraylists of Reference and
		 * Manipulated Data end
		 */

		int size1 = al3.size();
		float size3 = size1;
		System.out.println("Words in the correct file: " + size1);

		int size2 = al2.size();
		System.out.println("Words in mismatched file: " + size2);

		/**
		 * Cloning the mismatched Data into three files start
		 */
		try {
			FileWriter writer1 = new FileWriter(file3);
			FileWriter writer2 = new FileWriter(file4);
			FileWriter writer12 = new FileWriter(file5);
			FileWriter writer11 = new FileWriter(filems);
			while (size2 != 0) {
				size2--;
				writer1.append("" + al2.get(size2));
				writer1.append('\n');
				writer2.append("" + al2.get(size2));
				writer2.append('\n');
				writer12.append("" + al2.get(size2));
				writer12.append('\n');
				writer11.append("" + al2.get(size2));
				writer11.append('\n');
			}
			writer1.flush();
			writer1.close();
			writer2.flush();
			writer2.close();
			writer12.flush();
			writer12.close();
			writer11.flush();
			writer11.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * Cloning the mismatched Data into three files end
		 */

		/**
		 * Cloning the Reference Data into correct file start
		 */
		try {
			FileWriter writer = new FileWriter(file6);
			while (size1 != 0) {
				size1--;
				writer.append("" + al3.get(size1));
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * Cloning the Reference Data into correct file end
		 */
		String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=PhoneticMatching;integratedSecurity=true";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con;
		con = DriverManager.getConnection(url);

		/**
		 * Matched list generation for Metaphone code start
		 */
		BufferedReader CSVFile3 = new BufferedReader(new FileReader(file3));
		String dataRow3 = CSVFile3.readLine();

		FileWriter writer4 = new FileWriter(file7);
		int meta = -1;
		while (dataRow3 != null) {
			String[] dataArray3 = dataRow3.split(",");

			for (String item3 : dataArray3) {
				MetaphoneSpanish obj1 = new MetaphoneSpanish();
				String metaphone_code = obj1.metaphone(item3);

				PreparedStatement stmt = con
						.prepareStatement("select spanishword from tbldictionaryspanish where metaphone_code = ?;");

				stmt.setString(1, metaphone_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al4.add(result.getString("spanishword"));
				}
				meta++;
				try {
					int size4 = al4.size();
					int meta_word_count = size4;

					while (size4 != 0) {
						size4--;
						writer4.append(item3 + "," + al4.get(size4) + ","
								+ meta);
						writer4.append('\n');
						WordSuggestions ws_meta = new WordSuggestions();
						ws_meta.type = "SpanishMetaphone";
						ws_meta.actual_word = item3;
						ws_meta.suggested_word = al4.get(size4);
						ws_meta.number = meta_word_count - size4;
						ws_meta.count = meta_word_count;
						ws_meta.corrected = false;
						WordSuggestions.add(ws_meta);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al4.clear();

			}
			dataRow3 = CSVFile3.readLine();
		}
		CSVFile3.close();
		writer4.flush();
		writer4.close();

		/**
		 * Matched list generation for Metaphone code end
		 */

		/**
		 * Matched list generation for Soundex code start
		 */
		BufferedReader CSVFile4 = new BufferedReader(new FileReader(file4));
		String dataRow4 = CSVFile4.readLine();

		FileWriter writer5 = new FileWriter(file8);
		int soundex = -1;
		while (dataRow4 != null) {
			String[] dataArray4 = dataRow4.split(",");

			for (String item4 : dataArray4) {
				SpanishSoundex obj2 = new SpanishSoundex();
				String soundex_code = obj2.spanishsoundex(item4);
				PreparedStatement stmt = con
						.prepareStatement("select spanishword from tbldictionaryspanish where soundex_code = ?;");

				stmt.setString(1, soundex_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al5.add(result.getString("spanishword"));
				}
				soundex++;
				try {
					int size5 = al5.size();
					int sound_word_count = size5;

					while (size5 != 0) {
						size5--;
						writer5.append(item4 + "," + al5.get(size5) + ","
								+ soundex);
						writer5.append('\n');
						WordSuggestions ws_sound = new WordSuggestions();
						ws_sound.type = "SpanishSoundex";
						ws_sound.actual_word = item4;
						ws_sound.suggested_word = al5.get(size5);
						ws_sound.number = sound_word_count - size5;
						ws_sound.count = sound_word_count;
						ws_sound.corrected = false;
						WordSuggestions.add(ws_sound);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al5.clear();
			}
			dataRow4 = CSVFile4.readLine();
		}
		CSVFile4.close();
		writer5.flush();
		writer5.close();
		/**
		 * Matched list generation for Soundex code end
		 */

		/**
		 * Matched list generation for MetaSoundex code start
		 */
		BufferedReader CSVFile14 = new BufferedReader(new FileReader(file5));
		String dataRow14 = CSVFile14.readLine();
		int metasoundex = -1;
		FileWriter writer14 = new FileWriter(file16);

		while (dataRow14 != null) {
			String[] dataArray14 = dataRow14.split(",");

			for (String item14 : dataArray14) {
				MetaSoundexAlgorithm obj = new MetaSoundexAlgorithm();
				String metasoundex_code = obj.MetaSoundexSpanish(item14);
				LevenshteinEdit le = new LevenshteinEdit();

				PreparedStatement stmt = con
						.prepareStatement("select spanishword from tbldictionaryspanish where metasoundex_code = ?;");

				stmt.setString(1, metasoundex_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					String retrword = result.getString("spanishword");
					if (le.distance(item14.toUpperCase(), retrword) < 4) {
						al14.add(result.getString("spanishword"));
					}
				}
				metasoundex++;
				try {
					int size14 = al14.size();
					int metasoundex_word_count = size14;

					while (size14 != 0) {
						size14--;
						writer14.append(item14 + "," + al14.get(size14) + ","
								+ metasoundex);
						writer14.append('\n');
						WordSuggestions ws_metasoundex = new WordSuggestions();
						ws_metasoundex.type = "SpanishMetaSoundex";
						ws_metasoundex.actual_word = item14;
						ws_metasoundex.suggested_word = al14.get(size14);
						ws_metasoundex.number = metasoundex_word_count - size14;
						ws_metasoundex.count = metasoundex_word_count;
						ws_metasoundex.corrected = false;
						WordSuggestions.add(ws_metasoundex);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al14.clear();
			}
			dataRow14 = CSVFile14.readLine();
		}
		CSVFile14.close();
		writer14.flush();
		writer14.close();

		/**
		 * Matched list generation for MetaSoundex code end
		 */

		/**
		 * Method to correct mismatched words using Metaphone
		 */
		MatchedComparison_SpanishMetaphone met = new MatchedComparison_SpanishMetaphone();
		met.matched_spanishmetaphone(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Soundex
		 */
		MatchedComparison_SpanishSoundex sou = new MatchedComparison_SpanishSoundex();
		sou.matched_spanishsoundex(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using MetaSoundex
		 */
		MatchedComparison_SpanishMetaSoundex metsoun = new MatchedComparison_SpanishMetaSoundex();
		metsoun.matched_spanishmetasoundex(savepath, WordSuggestions);

		/**
		 * Reading Metaphone corrected words into Arraylist start
		 */
		BufferedReader CSVFile6 = new BufferedReader(new FileReader(file3));
		String dataRow6 = CSVFile6.readLine();
		while (dataRow6 != null) {
			String[] dataArray6 = dataRow6.split(",");
			for (String item6 : dataArray6) {
				al7.add(item6);
			}

			dataRow6 = CSVFile6.readLine(); // Read next line of data.
		}

		CSVFile6.close();
		int size7 = al7.size();
		/**
		 * Reading Metaphone corrected words into Arraylist end
		 */

		/**
		 * Reading Soundex corrected words into Arraylist start
		 */
		BufferedReader CSVFile7 = new BufferedReader(new FileReader(file4));
		String dataRow7 = CSVFile7.readLine();
		while (dataRow7 != null) {
			String[] dataArray7 = dataRow7.split(",");
			for (String item7 : dataArray7) {
				al8.add(item7);
			}

			dataRow7 = CSVFile7.readLine(); // Read next line of data.
		}

		CSVFile7.close();
		int size8 = al8.size();
		/**
		 * Reading Soundex corrected words into Arraylist end
		 */

		/**
		 * Reading MetaSoundex corrected words into Arraylist start
		 */
		BufferedReader CSVFile15 = new BufferedReader(new FileReader(file5));
		String dataRow15 = CSVFile15.readLine();
		while (dataRow15 != null) {
			String[] dataArray15 = dataRow15.split(",");
			for (String item14 : dataArray15) {
				al15.add(item14);
			}

			dataRow15 = CSVFile15.readLine(); // Read next line of data.
		}

		CSVFile15.close();
		int size15 = al15.size();
		/**
		 * Reading MetaSOundex corrected words into Arraylist end
		 */

		/**
		 * calculating Precision and Recall code start
		 */
		float[] result = new float[9];

		float spmetaphone_recall = 0;
		float spsoundex_recall = 0;
		float spmetasoundex_recall = 0;
		if (size3 != 0) {
			spmetaphone_recall = (size7 / size3);
			spsoundex_recall = (size8 / size3);
			spmetasoundex_recall = (size15 / size3);
			result[0] = spmetaphone_recall;
			result[1] = spsoundex_recall;
			result[2] = spmetasoundex_recall;
		}

		System.out.println("size 3 --- " + size3);

		float spmetaphone_precision = 0;
		float spsoundex_precision = 0;
		float spmetasoundex_precision = 0;
		float spmeta_num = 0;
		float spmeta_dem = 0;
		float spsou_num = 0;
		float spsou_dem = 0;
		float spmetasoun_num = 0;
		float spmetasoun_dem = 0;
		for (WordSuggestions ws : WordSuggestions) {
			for (int i = 0; i < al2.size(); i++) {
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("SpanishMetaphone")) {
					spmeta_num += (float) 1;
					spmeta_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("SpanishSoundex")) {
					spsou_num += (float) 1;
					spsou_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("SpanishMetaSoundex")) {
					spmetasoun_num += (float) 1;
					spmetasoun_dem += (float) ws.count;
				}
			}
		}
		spmetaphone_precision = (spmeta_num / spmeta_dem);
		spsoundex_precision = (spsou_num / spsou_dem);
		spmetasoundex_precision = (spmetasoun_num / spmetasoun_dem);
		result[3] = spmetaphone_precision;
		result[4] = spsoundex_precision;
		result[5] = spmetasoundex_precision;

		/**
		 * calculating Precision and Recall code end
		 */
		/**
		 * calculating F-measure code start
		 */
		result[6] = (2 * spmetaphone_precision * spmetaphone_recall)
				/ (spmetaphone_precision + spmetaphone_recall);
		result[7] = (2 * spsoundex_precision * spsoundex_recall)
				/ (spsoundex_precision + spsoundex_recall);
		result[8] = (2 * spmetasoundex_precision * spmetasoundex_recall)
				/ (spmetasoundex_precision + spmetasoundex_recall);
		/**
		 * calculating F-measure code end
		 */
		long timeEnd = System.currentTimeMillis();
		System.out.println("timeStart: " + timeStart + "timeEnd: " + timeEnd);
		System.out.println("Process time: " + (timeEnd - timeStart) / 1000);

		return result;

	}
}
