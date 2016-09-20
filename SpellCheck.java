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
public class SpellCheck {

	/**
	 * @param input
	 * @param lang
	 * @return list of misspelled words
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> spellcheck(String[] input, String lang)
			throws ClassNotFoundException, SQLException {

		ArrayList<String> misspelledlist = new ArrayList<String>();
		/*
		 * If the selected language is English, then following if block executes
		 */
		if (lang.equalsIgnoreCase("English")) {
			String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=InformationQuality;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con;
			con = DriverManager.getConnection(url);
			for (String item : input) {
				PreparedStatement stmt = con
						.prepareStatement("select word from tbldictionaryenglish where word = ?;");
				stmt.setString(1, item.toUpperCase());
				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					misspelledlist.add(item);
				}
			}

		}
		/*
		 * If the selected language is Spanish, then following else if block
		 * executes
		 */
		else if (lang.equalsIgnoreCase("Spanish")) {
			String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=PhoneticMatching;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con;
			con = DriverManager.getConnection(url);
			for (String item : input) {
				PreparedStatement stmt = con
						.prepareStatement("select spanishword from tbldictionaryspanish where spanishword = ?;");
				stmt.setString(1, item.toUpperCase());
				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					misspelledlist.add(item);
				}
			}
		}
		return misspelledlist;

	}

}
