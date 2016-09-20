package PhoneticMatching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author keerthi
 *
 */
public class SuggestionList {

	/**
	 * @param misspelled
	 * @param algorithm
	 * @param language
	 * @return suggested wordlist for misspelled words
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MisspelledWord suggestionlist(String misspelled, String algorithm,
			String language) throws ClassNotFoundException, SQLException {
		MisspelledWord mis = new MisspelledWord();
		ArrayList<String> suggestionlist = new ArrayList<String>();
		mis.setMisspelled(misspelled);
		mis.setLanguage(language);
		mis.setAlgorithm(algorithm);
		/*
		 * If the selected language is English, then following if block executes
		 */
		if (mis.getLanguage().equalsIgnoreCase("English")) {
			String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=InformationQuality;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con;
			con = DriverManager.getConnection(url);
			switch (mis.getAlgorithm()) {
			case "Caverphone":
				Caverphone cv = new Caverphone();
				String caverphone_code = cv.encode(misspelled);
				mis.setCode(caverphone_code);
				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where caverphone_code = ?;");
				stmt.setString(1, caverphone_code);
				ResultSet result = stmt.executeQuery();
				while (result.next()) {
					suggestionlist.add(result.getString("word"));
				}
				break;
			case "DMetaphone":
				Double_metaphone dm = new Double_metaphone();
				String doublemetaphone_code = dm.doubleMetaphone(misspelled);
				mis.setCode(doublemetaphone_code);
				PreparedStatement stmt1 = con
						.prepareStatement("select word from tbldictionaryenglish where doublemetaphone_code = ?;");
				stmt1.setString(1, doublemetaphone_code);
				ResultSet result1 = stmt1.executeQuery();
				while (result1.next()) {
					suggestionlist.add(result1.getString("word"));
				}
				break;
			case "Metaphone":
				Metaphone met = new Metaphone();
				String metaphone_code = met.metaphone(misspelled);
				mis.setCode(metaphone_code);
				PreparedStatement stmt2 = con
						.prepareStatement("select word from tbldictionaryenglish where metaphone_code = ?;");
				stmt2.setString(1, metaphone_code);
				ResultSet result2 = stmt2.executeQuery();
				while (result2.next()) {
					suggestionlist.add(result2.getString("word"));
				}
				break;
			case "MetaSoundex":
				MetaSoundexAlgorithm mets = new MetaSoundexAlgorithm();
				String metasoundex_code = mets.MetaSoundex(misspelled);
				LevenshteinEdit le = new LevenshteinEdit();
				mis.setCode(metasoundex_code);
				PreparedStatement stmt3 = con
						.prepareStatement("select word from tbldictionaryenglish where metasoundex_code = ?;");
				stmt3.setString(1, metasoundex_code);
				ResultSet result3 = stmt3.executeQuery();
				while (result3.next()) {
					String retrword = result3.getString("word");
					if (le.distance(misspelled.toUpperCase(), retrword) < 4) {
						suggestionlist.add(result3.getString("word"));
					}
				}
				break;
			case "NYSIIS":
				Nysiis nys = new Nysiis();
				String nysiis_code = nys.nysiis(misspelled);
				mis.setCode(nysiis_code);
				PreparedStatement stmt4 = con
						.prepareStatement("select word from tbldictionaryenglish where nysiis_code = ?;");
				stmt4.setString(1, nysiis_code);
				ResultSet result4 = stmt4.executeQuery();
				while (result4.next()) {
					suggestionlist.add(result4.getString("word"));
				}
				break;
			case "Soundex":
				Soundex soun = new Soundex();
				String soundex_code = soun.soundex(misspelled);
				mis.setCode(soundex_code);
				PreparedStatement stmt5 = con
						.prepareStatement("select word from tbldictionaryenglish where soundex_code = ?;");
				stmt5.setString(1, soundex_code);
				ResultSet result5 = stmt5.executeQuery();
				while (result5.next()) {
					suggestionlist.add(result5.getString("word"));
				}
				break;
			default:

			}

		}
		/*
		 * If the selected language is Spanish, then following else if block
		 * executes
		 */
		else if (mis.getLanguage().equalsIgnoreCase("Spanish")) {
			String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=PhoneticMatching;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con;
			con = DriverManager.getConnection(url);
			switch (mis.getAlgorithm()) {
			case "Metaphone":
				MetaphoneSpanish metspa = new MetaphoneSpanish();
				String metaphone_code = metspa.metaphone(misspelled);
				mis.setCode(metaphone_code);
				PreparedStatement stmt = con
						.prepareStatement("select spanishword from tbldictionaryspanish where metaphone_code = ?;");
				stmt.setString(1, metaphone_code);
				ResultSet result = stmt.executeQuery();
				while (result.next()) {
					suggestionlist.add(result.getString("spanishword"));
				}
				break;
			case "MetaSoundex":
				MetaSoundexAlgorithm metsou = new MetaSoundexAlgorithm();
				String metasoundex_code = metsou.MetaSoundexSpanish(misspelled);
				LevenshteinEdit le = new LevenshteinEdit();
				mis.setCode(metasoundex_code);
				PreparedStatement stmt1 = con
						.prepareStatement("select spanishword from tbldictionaryspanish where metasoundex_code = ?;");
				stmt1.setString(1, metasoundex_code);
				ResultSet result1 = stmt1.executeQuery();
				while (result1.next()) {
					String retrword = result1.getString("spanishword");
					if (le.distance(misspelled.toUpperCase(), retrword) < 4) {
						suggestionlist.add(result1.getString("spanishword"));
					}
				}
				break;
			case "Soundex":
				SpanishSoundex sounspa = new SpanishSoundex();
				String soundex_code = sounspa.spanishsoundex(misspelled);
				mis.setCode(soundex_code);
				PreparedStatement stmt2 = con
						.prepareStatement("select spanishword from tbldictionaryspanish where soundex_code = ?;");
				stmt2.setString(1, soundex_code);
				ResultSet result2 = stmt2.executeQuery();
				while (result2.next()) {
					suggestionlist.add(result2.getString("spanishword"));
				}
				break;
			default:
			}
		}
		mis.setSuggestionlist(suggestionlist);
		return mis;
	}

}
