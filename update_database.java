package PhoneticMatching;

import java.sql.*;

/**
 * @author keerthi
 *
 */
public class update_database {

	/**
	 * @param args
	 * @throws Exception
	 *             for creating dataset having phonetic codes for the dictionary
	 *             word list.
	 */
	public static void main(String[] args) throws Exception {

		String url = "jdbc:sqlserver://C412\\SQLEXPRESS;databaseName=InformationQuality;integratedSecurity=true";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con;
		con = DriverManager.getConnection(url);

		PreparedStatement stmt1 = con
				.prepareStatement("select * from tbldictionaryenglish;");

		PreparedStatement stmt2 = con
				.prepareStatement("update tbldictionaryenglish "
						+ "set soundex_code = ?, metaphone_code = ?, caverphone_code = ?, doublemetaphone_code = ?, nysiis_code = ? where word=?;");

		ResultSet result1 = stmt1.executeQuery();

		while (result1.next()) {
			System.out.println("" + result1.getString("word") + "\t"
					+ result1.getString("ID") + "\t"
					+ result1.getString("nysiis_code"));

			Metaphone obj1 = new Metaphone();
			String metaphone_code = obj1.metaphone(result1.getString("word"));

			Nysiis obj5 = new Nysiis();
			String nysiis_code = obj5.nysiis(result1.getString("word"));

			Soundex obj2 = new Soundex();
			String soundex_code = obj2.soundex(result1.getString("word"));

			Caverphone obj3 = new Caverphone();
			String caverphone_code = obj3.encode(result1.getString("word"));

			Double_metaphone obj4 = new Double_metaphone();
			String doublemetaphone_code = obj4
					.encode(result1.getString("word"));

			stmt2.setString(1, soundex_code);
			stmt2.setString(2, metaphone_code);
			stmt2.setString(3, caverphone_code);
			stmt2.setString(4, doublemetaphone_code);
			stmt2.setString(5, nysiis_code);
			stmt2.setString(6, result1.getString("word"));

			stmt2.executeUpdate();
		}
	}
}
