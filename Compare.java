package PhoneticMatching;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * @author keerthi
 *
 */
public class Compare {
	/**
	 * Compares uploaded input files and generated intermediate files to
	 * calculate precision and Recall for six Phonetic Matching Algorithms
	 * (Metaphone, Soundex, Caverphone, NYSIIS, Double Metaphone, and
	 * MetaSoundex)
	 * 
	 * @param savepath
	 *            -- Dynamic path to save Intermediate files
	 * @param filename1
	 *            -- Reference File
	 * @param filename2
	 *            -- Incorrect File
	 * @param WordSuggestions
	 *            -- ArrayList depicting suggested word list.
	 * @return Precision, Recall and F-Measure values of Metaphone, Soundex,
	 *         Caverphone, NYSIIS, Double Metaphone, and MetaSoundex
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
		String file3 = path + "mismatched_metaphone.csv";
		String file4 = path + "mismatched_soundex.csv";
		String file5 = path + "mismatched_caverphone.csv";
		String file10 = path + "mismatched_doublemetaphone.csv";
		String file12 = path + "mismatched_nysiis.csv";
		String file15 = path + "mismatched_metasoundex.csv";
		String file6 = path + "Correct.csv";
		String file7 = path + "matched_metaphone.csv";
		String file8 = path + "matched_soundex.csv";
		String file9 = path + "matched_caverphone.csv";
		String file11 = path + "matched_doublemetaphone.csv";
		String file13 = path + "matched_nysiis.csv";
		String file16 = path + "matched_metasoundex.csv";
		String file14 = path + "mismatchedforreference.csv";
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
		ArrayList<String> al6 = new ArrayList<String>();// ArrayList for matched
														// word list of
														// Caverphone
		ArrayList<String> al10 = new ArrayList<String>();// ArrayList for
															// matched word list
															// of Double
															// Metaphone
		ArrayList<String> al12 = new ArrayList<String>();// ArrayList for
															// matched word list
															// of Nysiis
		ArrayList<String> al14 = new ArrayList<String>();// ArrayList for
															// matched word list
															// of MetaSoundex
		ArrayList<String> al7 = new ArrayList<String>();// ArrayList for saving
														// words corrected by
														// Metaphone
		ArrayList<String> al8 = new ArrayList<String>();// ArrayList for saving
														// words corrected by
														// Soundex
		ArrayList<String> al9 = new ArrayList<String>();// ArrayList for saving
														// words corrected by
														// Caverphone
		ArrayList<String> al11 = new ArrayList<String>();// ArrayList for saving
															// words corrected
															// by Double
															// Metaphone
		ArrayList<String> al13 = new ArrayList<String>();// ArrayList for saving
															// words corrected
															// by Nysiis
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
			FileWriter writer3 = new FileWriter(file5);
			FileWriter writer8 = new FileWriter(file10);
			FileWriter writer9 = new FileWriter(file12);
			FileWriter writer12 = new FileWriter(file15);
			FileWriter writer11 = new FileWriter(file14);
			while (size2 != 0) {
				size2--;
				writer1.append("" + al2.get(size2));
				writer1.append('\n');
				writer2.append("" + al2.get(size2));
				writer2.append('\n');
				writer3.append("" + al2.get(size2));
				writer3.append('\n');
				writer8.append("" + al2.get(size2));
				writer8.append('\n');
				writer9.append("" + al2.get(size2));
				writer9.append('\n');
				writer12.append("" + al2.get(size2));
				writer12.append('\n');
				writer11.append("" + al2.get(size2));
				writer11.append('\n');
			}
			writer1.flush();
			writer1.close();
			writer2.flush();
			writer2.close();
			writer3.flush();
			writer3.close();
			writer8.flush();
			writer8.close();
			writer9.flush();
			writer9.close();
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
		String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=InformationQuality;integratedSecurity=true";
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
				Metaphone obj1 = new Metaphone();
				String metaphone_code = obj1.metaphone(item3);

				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where metaphone_code = ?;");

				stmt.setString(1, metaphone_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al4.add(result.getString("word"));
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
						ws_meta.type = "Metaphone";
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
				Soundex obj2 = new Soundex();
				String soundex_code = obj2.soundex(item4);
				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where soundex_code = ?;");

				stmt.setString(1, soundex_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al5.add(result.getString("word"));
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
						ws_sound.type = "Soundex";
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
		 * Matched list generation for Caverphone code start
		 */
		BufferedReader CSVFile5 = new BufferedReader(new FileReader(file5));
		String dataRow5 = CSVFile5.readLine();
		int caver = -1;
		FileWriter writer6 = new FileWriter(file9);

		while (dataRow5 != null) {
			String[] dataArray5 = dataRow5.split(",");
			for (String item5 : dataArray5) {
				Caverphone obj3 = new Caverphone();
				String caverphone_code = obj3.encode(item5);

				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where caverphone_code = ?;");

				stmt.setString(1, caverphone_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al6.add(result.getString("word"));
				}
				caver++;
				try {
					int size6 = al6.size();
					int cav_word_count = size6;

					while (size6 != 0) {
						size6--;
						writer6.append(item5 + "," + al6.get(size6) + ","
								+ caver);
						writer6.append('\n');
						WordSuggestions ws_cav = new WordSuggestions();
						ws_cav.type = "Caverphone";
						ws_cav.actual_word = item5;
						ws_cav.suggested_word = al6.get(size6);
						ws_cav.number = cav_word_count - size6;
						ws_cav.count = cav_word_count;
						ws_cav.corrected = false;
						WordSuggestions.add(ws_cav);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al6.clear();
			}
			dataRow5 = CSVFile5.readLine();
		}
		CSVFile5.close();
		writer6.flush();
		writer6.close();
		/**
		 * Matched list generation for Caverphone code end
		 */

		/**
		 * Matched list generation for Double Metaphone code start
		 */
		BufferedReader CSVFile9 = new BufferedReader(new FileReader(file10));
		String dataRow9 = CSVFile9.readLine();
		int doubmeta = -1;
		FileWriter writer7 = new FileWriter(file11);

		while (dataRow9 != null) {
			String[] dataArray9 = dataRow9.split(",");

			for (String item9 : dataArray9) {
				Double_metaphone obj4 = new Double_metaphone();
				String double_metaphone_code = obj4.doubleMetaphone(item9);

				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where doublemetaphone_code = ?;");

				stmt.setString(1, double_metaphone_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al10.add(result.getString("word"));
				}
				doubmeta++;
				try {
					int size10 = al10.size();
					int double_meta_word_count = size10;

					while (size10 != 0) {
						size10--;
						writer7.append(item9 + "," + al10.get(size10) + ","
								+ doubmeta);
						writer7.append('\n');
						WordSuggestions ws_double_meta = new WordSuggestions();
						ws_double_meta.type = "Double_Metaphone";
						ws_double_meta.actual_word = item9;
						ws_double_meta.suggested_word = al10.get(size10);
						ws_double_meta.number = double_meta_word_count - size10;
						ws_double_meta.count = double_meta_word_count;
						ws_double_meta.corrected = false;
						WordSuggestions.add(ws_double_meta);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al10.clear();
			}
			dataRow9 = CSVFile9.readLine();
		}
		CSVFile9.close();
		writer7.flush();
		writer7.close();

		/**
		 * Matched list generation for Double Metaphone code end
		 */

		/**
		 * Matched list generation for Nysiis code start
		 */
		BufferedReader CSVFile11 = new BufferedReader(new FileReader(file12));
		String dataRow11 = CSVFile11.readLine();
		int nysiis = -1;
		FileWriter writer10 = new FileWriter(file13);

		while (dataRow11 != null) {
			String[] dataArray11 = dataRow11.split(",");

			for (String item11 : dataArray11) {
				Nysiis obj5 = new Nysiis();
				String nysiis_code = obj5.nysiis(item11);

				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where nysiis_code = ?;");

				stmt.setString(1, nysiis_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					al12.add(result.getString("word"));
				}
				nysiis++;
				try {
					int size12 = al12.size();
					int nysiis_word_count = size12;

					while (size12 != 0) {
						size12--;
						writer10.append(item11 + "," + al12.get(size12) + ","
								+ nysiis);
						writer10.append('\n');
						WordSuggestions ws_nysiis = new WordSuggestions();
						ws_nysiis.type = "Nysiis";
						ws_nysiis.actual_word = item11;
						ws_nysiis.suggested_word = al12.get(size12);
						ws_nysiis.number = nysiis_word_count - size12;
						ws_nysiis.count = nysiis_word_count;
						ws_nysiis.corrected = false;
						WordSuggestions.add(ws_nysiis);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				al12.clear();
			}
			dataRow11 = CSVFile11.readLine();
		}
		CSVFile11.close();
		writer10.flush();
		writer10.close();

		/**
		 * Matched list generation for Nysiis code end
		 */

		/**
		 * Matched list generation for MetaSoundex code start
		 */
		BufferedReader CSVFile14 = new BufferedReader(new FileReader(file15));
		String dataRow14 = CSVFile14.readLine();
		int metasoundex = -1;
		FileWriter writer14 = new FileWriter(file16);

		while (dataRow14 != null) {
			String[] dataArray14 = dataRow14.split(",");

			for (String item14 : dataArray14) {
				MetaSoundexAlgorithm obj = new MetaSoundexAlgorithm();
				String metasoundex_code = obj.MetaSoundex(item14);
				LevenshteinEdit le = new LevenshteinEdit();

				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where metasoundex_code = ?;");

				stmt.setString(1, metasoundex_code);

				ResultSet result = stmt.executeQuery();

				while (result.next()) {
					String retrword = result.getString("word");
					if (le.distance(item14.toUpperCase(), retrword) < 4) {
						al14.add(result.getString("word"));
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
						ws_metasoundex.type = "MetaSoundex";
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
		MatchedComparison_Metaphone met = new MatchedComparison_Metaphone();
		met.matched_metaphone(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Soundex
		 */
		MatchedComparison_Soundex sou = new MatchedComparison_Soundex();
		sou.matched_soundex(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Caverphone
		 */
		MatchedComparison_Caverphone cav = new MatchedComparison_Caverphone();
		cav.matched_caverphone(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Double Metaphone
		 */
		MatchedComparison_DoubleMetaphone dou_met = new MatchedComparison_DoubleMetaphone();
		dou_met.matched_double_metaphone(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Nysiis
		 */
		MatchedComparison_nysiis nys = new MatchedComparison_nysiis();
		nys.matched_nysiis(savepath, WordSuggestions);

		/**
		 * Method to correct mismatched words using Nysiis
		 */
		MatchedComparison_MetaSoundex metsoun = new MatchedComparison_MetaSoundex();
		metsoun.matched_metasoundex(savepath, WordSuggestions);

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
		 * Reading Caverphone corrected words into Arraylist start
		 */
		BufferedReader CSVFile8 = new BufferedReader(new FileReader(file5));
		String dataRow8 = CSVFile8.readLine();
		while (dataRow8 != null) {
			String[] dataArray8 = dataRow8.split(",");
			for (String item8 : dataArray8) {
				al9.add(item8);
			}

			dataRow8 = CSVFile8.readLine(); // Read next line of data.
		}

		CSVFile8.close();
		int size9 = al9.size();
		/**
		 * Reading Caverphone corrected words into Arraylist end
		 */

		/**
		 * Reading Double Metaphone corrected words into Arraylist start
		 */
		BufferedReader CSVFile10 = new BufferedReader(new FileReader(file10));
		String dataRow10 = CSVFile10.readLine();
		while (dataRow10 != null) {
			String[] dataArray10 = dataRow10.split(",");
			for (String item10 : dataArray10) {
				al11.add(item10);
			}

			dataRow10 = CSVFile10.readLine(); // Read next line of data.
		}

		CSVFile10.close();
		int size11 = al11.size();
		/**
		 * Reading Double Metaphone corrected words into Arraylist end
		 */

		/**
		 * Reading Nysiis corrected words into Arraylist start
		 */
		BufferedReader CSVFile12 = new BufferedReader(new FileReader(file12));
		String dataRow12 = CSVFile12.readLine();
		while (dataRow12 != null) {
			String[] dataArray12 = dataRow12.split(",");
			for (String item12 : dataArray12) {
				al13.add(item12);
			}

			dataRow12 = CSVFile12.readLine(); // Read next line of data.
		}

		CSVFile12.close();
		int size13 = al13.size();
		/**
		 * Reading Nysiis corrected words into Arraylist end
		 */

		/**
		 * Reading Nysiis corrected words into Arraylist start
		 */
		BufferedReader CSVFile15 = new BufferedReader(new FileReader(file15));
		String dataRow15 = CSVFile15.readLine();
		while (dataRow15 != null) {
			String[] dataArray15 = dataRow15.split(",");
			for (String item14 : dataArray15) {
				al15.add(item14);
				System.out.println("item14 --- " + item14);
			}

			dataRow15 = CSVFile15.readLine(); // Read next line of data.
		}

		CSVFile15.close();
		int size15 = al15.size();
		/**
		 * Reading Nysiis corrected words into Arraylist end
		 */

		/**
		 * calculating Precision and Recall code start
		 */
		float[] result = new float[18];

		float metaphone_recall = 0;
		float soundex_recall = 0;
		float caverphone_recall = 0;
		float double_metaphone_recall = 0;
		float nysiis_recall = 0;
		float metasoundex_recall = 0;
		if (size3 != 0) {
			metaphone_recall = (size7 / size3);
			soundex_recall = (size8 / size3);
			caverphone_recall = (size9 / size3);
			double_metaphone_recall = (size11 / size3);
			nysiis_recall = (size13 / size3);
			metasoundex_recall = (size15 / size3);
			result[0] = metaphone_recall;
			result[1] = soundex_recall;
			result[2] = caverphone_recall;
			result[3] = double_metaphone_recall;
			result[4] = nysiis_recall;
			result[15] = metasoundex_recall;
		}

		System.out.println("size 3 --- " + size3);

		float metaphone_precision = 0;
		float soundex_precision = 0;
		float caverphone_precision = 0;
		float double_metaphone_precision = 0;
		float nysiis_precision = 0;
		float metasoundex_precision = 0;
		float meta_num = 0;
		float meta_dem = 0;
		float sou_num = 0;
		float sou_dem = 0;
		float dmeta_num = 0;
		float dmeta_dem = 0;
		float cav_num = 0;
		float cav_dem = 0;
		float nys_num = 0;
		float nys_dem = 0;
		float metasoun_num = 0;
		float metasoun_dem = 0;
		for (WordSuggestions ws : WordSuggestions) {
			for (int i = 0; i < al2.size(); i++) {
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("Metaphone")) {
					meta_num += (float) 1;
					meta_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("Soundex")) {
					sou_num += (float) 1;
					sou_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("Caverphone")) {
					cav_num += (float) 1;
					cav_dem += (float) ws.count;
				} else if (ws.actual_word.matches(al2.get(i))
						&& ws.corrected == true
						&& ws.type.matches("Double_Metaphone")) {
					dmeta_num += (float) 1;
					dmeta_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("Nysiis")) {
					nys_num += (float) 1;
					nys_dem += (float) ws.count;
				}
				if (ws.actual_word.matches(al2.get(i)) && ws.corrected == true
						&& ws.type.matches("MetaSoundex")) {
					metasoun_num += (float) 1;
					metasoun_dem += (float) ws.count;
				}
			}
		}
		metaphone_precision = (meta_num / meta_dem);
		soundex_precision = (sou_num / sou_dem);
		caverphone_precision = (cav_num / cav_dem);
		double_metaphone_precision = (dmeta_num / dmeta_dem);
		nysiis_precision = (nys_num / nys_dem);
		metasoundex_precision = (metasoun_num / metasoun_dem);
		result[5] = metaphone_precision;
		result[6] = soundex_precision;
		result[7] = caverphone_precision;
		result[8] = double_metaphone_precision;
		result[9] = nysiis_precision;
		result[16] = metasoundex_precision;
		/**
		 * calculating Precision and Recall code end
		 */
		/**
		 * calculating F-measure code start
		 */
		result[10] = (2 * metaphone_precision * metaphone_recall)
				/ (metaphone_precision + metaphone_recall);
		result[11] = (2 * soundex_precision * soundex_recall)
				/ (soundex_precision + soundex_recall);
		result[12] = (2 * caverphone_precision * caverphone_recall)
				/ (caverphone_precision + caverphone_recall);
		result[13] = (2 * double_metaphone_precision * double_metaphone_recall)
				/ (double_metaphone_precision + double_metaphone_recall);
		result[14] = (2 * nysiis_precision * nysiis_recall)
				/ (nysiis_precision + nysiis_recall);
		result[17] = (2 * metasoundex_precision * metasoundex_recall)
				/ (metasoundex_precision + metasoundex_recall);
		/**
		 * calculating F-measure code end
		 */
		long timeEnd = System.currentTimeMillis();
		System.out.println("timeStart: " + timeStart + "timeEnd: " + timeEnd);
		System.out.println("Process time: " + (timeEnd - timeStart) / 1000);

		return result;

	}
}
