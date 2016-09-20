package PhoneticMatching;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author keerthi
 *
 */
@WebServlet("/SuggestionServlet")
public class SuggestionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Name of the directory where uploaded files will be saved, relative to the
	 * web application directory.
	 */
	public static String filename1 = null, filename2 = null;

	/**
	 * handles input data from Suggestions webpage
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String inputtext = request.getParameter("inputtext");
			String language = request.getParameter("language");
			String btnsugges = request.getParameter("btnsugges");
			String btncheck = request.getParameter("btncheck");
			String[] inputlist = inputtext.split(" ");
			SpellCheck spc = new SpellCheck();
			ArrayList<String> misspell = spc.spellcheck(inputlist, language);
			if (btncheck != null) {
				request.setAttribute("misspell", misspell);
				request.setAttribute("input", inputtext);
				getServletContext().getRequestDispatcher("/Tool.jsp").forward(
						request, response);
			} else if (btnsugges != null) {
				String misspelled = request.getParameter("misspelled");
				String algorithm = request.getParameter("algorithms");
				SuggestionList sl = new SuggestionList();
				MisspelledWord mis = sl.suggestionlist(misspelled, algorithm,
						language);
				request.setAttribute("message", "RESULT");
				request.setAttribute("misspell", misspell);
				request.setAttribute("input", inputtext);
				request.setAttribute("message1", "Misspelled Word: ");
				request.setAttribute("misword", mis.getMisspelled());
				request.setAttribute("message2", "Algorithm: ");
				request.setAttribute("algo", mis.getAlgorithm());
				request.setAttribute("message3", "Generated Code: ");
				request.setAttribute("code", mis.getCode());
				request.setAttribute("message4", "Suggested Words: ");
				request.setAttribute("suggestList", mis.getSuggestionlist());
				getServletContext().getRequestDispatcher("/Tool.jsp").forward(
						request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("Error.jsp");
		}
	}

}