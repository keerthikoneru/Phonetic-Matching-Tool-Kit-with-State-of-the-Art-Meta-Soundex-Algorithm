package PhoneticMatching;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author keerthi
 *
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
// 50MB
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Name of the directory where uploaded files will be saved, relative to the
	 * web application directory.
	 */
	private static final String SAVE_DIR = "uploadFiles";
	public static String filename1 = null, filename2 = null;

	/**
	 * handles file upload
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
		/**
		 * gets absolute path of the web application
		 */
		String appPath = request.getServletContext().getRealPath("");
		System.out.println(appPath + " \n");
		System.out.println(File.separator);
		System.out.println(SAVE_DIR);

		/**
		 * constructs path of the directory to save uploaded file
		 */
		String savePath = appPath + SAVE_DIR + File.separator;

		/**
		 * creates the save directory if it does not exists
		 */
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		String language = request.getParameter("dropdown");

		ArrayList<Part> parts = new ArrayList<>(request.getParts());
		for (int i = 1; i < parts.size(); i++) {
			System.out.println(parts.get(i));
			String fileName = extractFileName(parts.get(i));
			System.out.println(fileName);
			/**
			 * The below line needs to be commented when run in Google Chrome
			 */
			// fileName =
			// fileName.substring(fileName.lastIndexOf("\\"),fileName.length());

			if (filename1 == null) {
				filename1 = fileName;
			} else
				filename2 = fileName;
			parts.get(i).write(savePath + fileName);
		}
		/*
		 * If the selected language is English, then following if block executes
		 */
		if (language.equalsIgnoreCase("English")) {
			float[] result = new float[18];
			List<WordSuggestions> WordSuggestions = new ArrayList<WordSuggestions>();
			Compare cmp = new Compare();
			try {
				result = cmp.comp(savePath, filename1, filename2,
						WordSuggestions);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * Displays output on web page start
			 */
			request.setAttribute("message1",
					"Phonetic Algorithms has been run.");
			request.setAttribute("message2",
					"Results are generated in the PDF file.");
			request.setAttribute("message3", "Note:The path is " + savePath);
			request.setAttribute("message4", "Recall is " + result[0]);// MetaphoneRecall
			request.setAttribute("message5", "Precision is " + result[5]);// MetaphonePrecision
			request.setAttribute("message6", "Recall is " + result[1]);// SoundexRecall
			request.setAttribute("message7", "Precision is " + result[6]);// SoundexPrecision
			request.setAttribute("message8", "Recall is " + result[2]);// CaverphoneRecall
			request.setAttribute("message9", "Precision is " + result[7]);// CaverphonePrecision
			request.setAttribute("message10", "Recall is " + result[3]);// DoubleMetaphoneRecall
			request.setAttribute("message11", "Precision is " + result[8]);// DoubleMetaphonePrecision
			request.setAttribute("message12", "Recall is " + result[4]);// NysiisRecall
			request.setAttribute("message13", "Precision is " + result[9]);// NysiisPrecision
			request.setAttribute("message19", "Recall is " + result[15]);// MetaSoundexRecall
			request.setAttribute("message20", "Precision is " + result[16]);// MetaSoundexPrecision
			request.setAttribute("message14", "F-measure is " + result[10]);// MetaphoneF-measure
			request.setAttribute("message15", "F-measure is " + result[11]);// SoundexF-measure
			request.setAttribute("message16", "F-measure is " + result[12]);// CaverphoneF-measure
			request.setAttribute("message17", "F-measure is " + result[13]);// DoubleMetaphoneF-measure
			request.setAttribute("message18", "F-measure is " + result[14]);// NysiisF-measure
			request.setAttribute("message21", "F-measure is " + result[17]);// MetaSoundexF-measure
			request.setAttribute("message22", savePath);
			getServletContext().getRequestDispatcher("/output.jsp").forward(
					request, response);

			int count_meta = 0;
			int count_meta_sgs = 0;
			int count_sound = 0;
			int count_sound_sgs = 0;
			int count_cav = 0;
			int count_cav_sgs = 0;
			int count_dou_meta = 0;
			int count_dou_meta_sgs = 0;
			int count_nys = 0;
			int count_nys_sgs = 0;
			int count_metas = 0;
			int count_metas_sgs = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Metaphone") && ws.corrected == true)
					count_meta++;
				else if (ws.type.matches("Metaphone") && ws.corrected == false)
					count_meta_sgs++;

				if (ws.type.matches("Soundex") && ws.corrected == true)
					count_sound++;
				else if (ws.type.matches("Soundex") && ws.corrected == false)
					count_sound_sgs++;

				if (ws.type.matches("Caverphone") && ws.corrected == true)
					count_cav++;
				else if (ws.type.matches("Caverphone") && ws.corrected == false)
					count_cav_sgs++;

				if (ws.type.matches("Double_Metaphone") && ws.corrected == true)
					count_dou_meta++;
				else if (ws.type.matches("Double_Metaphone")
						&& ws.corrected == false)
					count_dou_meta_sgs++;

				if (ws.type.matches("Nysiis") && ws.corrected == true)
					count_nys++;
				else if (ws.type.matches("Nysiis") && ws.corrected == false)
					count_nys_sgs++;

				if (ws.type.matches("MetaSoundex") && ws.corrected == true)
					count_metas++;
				else if (ws.type.matches("MetaSoundex")
						&& ws.corrected == false)
					count_metas_sgs++;
			}

			/**
			 * saving output data of metaphone to String Start
			 */
			String[] output_meta = new String[count_meta + 3];
			int value_meta = 0;
			String[] output_meta_suggested = new String[count_meta_sgs];
			int value_meta_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Metaphone") && ws.corrected == true) {
					output_meta[value_meta] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Metaphone")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_meta++;
				}
			}

			String word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Metaphone") && ws.printed != 1) {
					word = ws.actual_word;
					output_meta_suggested[value_meta_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_meta_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Metaphone")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_meta_suggested[value_meta_suggested] = sb.toString();
					value_meta_suggested++;
				}
			}
			/**
			 * saving output data of metaphone to String End
			 */

			/**
			 * saving output data of Soundex to String Start
			 */
			String[] output_sound = new String[count_sound + 3];
			int value_sound = 0;
			String[] output_sound_suggested = new String[count_sound_sgs];
			int value_sound_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Soundex") && ws.corrected == true) {
					output_sound[value_sound] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Soundex")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_sound++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Soundex") && ws.printed != 1) {
					word = ws.actual_word;
					output_sound_suggested[value_sound_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_sound_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Soundex")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_sound_suggested[value_sound_suggested] = sb
							.toString();
					value_sound_suggested++;
				}
			}
			/**
			 * saving output data of Soundex to String End
			 */
			/*
	    	
	    	
	    	*//**
			 * saving output data of Caverphone to String Start
			 */
			String[] output_cav = new String[count_cav];
			int value_cav = 0;
			String[] output_cav_suggested = new String[count_cav_sgs];
			int value_cav_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Caverphone") && ws.corrected == true) {
					output_cav[value_cav] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Caverphone")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_cav++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Caverphone") && ws.printed != 1) {
					word = ws.actual_word;
					output_cav_suggested[value_cav_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_cav_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Caverphone")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_cav_suggested[value_cav_suggested] = sb.toString();
					value_cav_suggested++;
				}
			}
			/**
			 * saving output data of Caverphone to String End
			 */

			/**
			 * saving output data of Double metaphone to String Start
			 */
			String[] output_dou_meta = new String[count_dou_meta + 3];
			int value_dou_meta = 0;
			String[] output_dou_meta_suggested = new String[count_dou_meta_sgs];
			int value_dou_meta_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Double_Metaphone") && ws.corrected == true) {
					output_dou_meta[value_dou_meta] = "The word "
							+ ws.actual_word + " is corrected as "
							+ ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Double_Metaphone")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_dou_meta++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Double_Metaphone") && ws.printed != 1) {
					word = ws.actual_word;
					output_dou_meta_suggested[value_dou_meta_suggested] = "The word "
							+ ws.actual_word
							+ " is not corrected."
							+ "The Suggested words are: ";
					value_dou_meta_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Double_Metaphone")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_dou_meta_suggested[value_dou_meta_suggested] = sb
							.toString();
					value_dou_meta_suggested++;
				}
			}
			/**
			 * saving output data of Double metaphone to String End
			 */

			/**
			 * saving output data of Nysiis to String Start
			 */
			String[] output_nys = new String[count_nys + 3];
			int value_nys = 0;
			String[] output_nys_suggested = new String[count_nys_sgs];
			int value_nys_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Nysiis") && ws.corrected == true) {
					output_nys[value_nys] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Nysiis")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_nys++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("Nysiis") && ws.printed != 1) {
					word = ws.actual_word;
					output_nys_suggested[value_nys_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_nys_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("Nysiis")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_nys_suggested[value_nys_suggested] = sb.toString();
					value_nys_suggested++;
				}
			}
			/**
			 * saving output data of Nysiis to String End
			 */

			/**
			 * saving output data of MetaSoundex to String Start
			 */
			String[] output_metas = new String[count_metas + 3];
			int value_metas = 0;
			String[] output_metas_suggested = new String[count_metas_sgs];
			int value_metas_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("MetaSoundex") && ws.corrected == true) {
					output_metas[value_metas] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("MetaSoundex")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_metas++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("MetaSoundex") && ws.printed != 1) {
					word = ws.actual_word;
					output_metas_suggested[value_metas_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_metas_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("MetaSoundex")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_metas_suggested[value_metas_suggested] = sb
							.toString();
					value_metas_suggested++;
				}
			}
			/**
			 * saving output data of MetaSoundex to String End
			 */

			/**
			 * Pdf file generation start
			 */
			Document document_corrected = new Document(); // Refers to corrected
															// word list
			Document document_suggstd = new Document(); // Refers to suggested
														// word list
			try {

				PdfWriter.getInstance(document_corrected, new FileOutputStream(
						savePath + "phonetic_corrected.pdf"));
				PdfWriter.getInstance(document_suggstd, new FileOutputStream(
						savePath + "phonetic_suggested.pdf"));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document_corrected.open();
			document_suggstd.open();
			try {

				/**
				 * Data is written into corrected document(first document) Start
				 */
				document_corrected.add(new Paragraph(
						"Phonetic Algorithms has been run Successfully.",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 18,
								Font.BOLD, new BaseColor(255, 0, 0))));
				document_corrected.add(new Paragraph("                  "));

				/**
				 * writing data related to metaphone start
				 */
				document_corrected.add(new Paragraph(
						"Metaphone Algorithm Recall is " + result[0],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"Metaphone Algorithm Precision is " + result[5],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_meta; i++)
					document_corrected.add(new Paragraph(output_meta[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to metaphone end
				 */

				/**
				 * writing data related to soundex start
				 */
				document_corrected.add(new Paragraph(
						"Soundex Algorithm Recall is " + result[1], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"Soundex Algorithm Precision is " + result[6],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_sound; i++)
					document_corrected.add(new Paragraph(output_sound[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to soundex end
				 */

				/**
				 * writing data related to caverphone start
				 */
				document_corrected.add(new Paragraph(
						"Caverphone Algorithm Recall is " + result[2],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"Caverphone Algorithm Precision is " + result[7],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_cav; i++)
					document_corrected.add(new Paragraph(output_cav[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to caverphone end
				 */
				/**
				 * writing data related to DMetaphone start
				 */
				document_corrected.add(new Paragraph(
						"DMetaphone Algorithm Recall is " + result[3],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"DMetaphone Algorithm Precision is " + result[8],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_dou_meta; i++)
					document_corrected.add(new Paragraph(output_dou_meta[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to DMetaphone end
				 */
				/**
				 * writing data related to NYSIIS start
				 */
				document_corrected.add(new Paragraph(
						"NYSIIS Algorithm Recall is " + result[4], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"NYSIIS Algorithm Precision is " + result[9],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_nys; i++)
					document_corrected.add(new Paragraph(output_nys[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to NYSIIS end
				 */
				/**
				 * writing data related to MetaSoundex start
				 */
				document_corrected.add(new Paragraph(
						"MetaSoundex Algorithm Recall is " + result[15],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"MetaSoundex Algorithm Precision is " + result[16],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_metas; i++)
					document_corrected.add(new Paragraph(output_metas[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to MetaSoundex end
				 */
				/**
				 * Data is written into corrected document(first document) End
				 */

				/**
				 * Data is written into suggested document(second document)
				 * Start
				 */
				document_suggstd.add(new Paragraph(
						"Phonetic Algorithms has been run Successfully.",
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 18,
								Font.BOLD, new BaseColor(250, 0, 0))));
				document_suggstd.add(new Paragraph("                  "));

				/**
				 * writing data related to metaphone start
				 */
				document_suggstd.add(new Paragraph(
						"Metaphone Algorithm Recall is " + result[0],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"Metaphone Algorithm Precision is " + result[5],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_meta_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_meta_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_meta_suggested[i]));

				}
				document_suggstd.add(new Paragraph("                  "));
				/**
				 * writing data related to metaphone end
				 */

				/**
				 * writing data related to soundex start
				 */
				document_suggstd.add(new Paragraph(
						"Soundex Algorithm Recall is " + result[1], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"Soundex Algorithm Precision is " + result[6],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_sound_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_sound_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_sound_suggested[i]));
				}
				document_suggstd.add(new Paragraph("                  "));
				/**
				 * writing data related to soundex end
				 */

				/**
				 * writing data related to caverphone start
				 */
				document_suggstd.add(new Paragraph(
						"Caverphone Algorithm Recall is " + result[2],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"Caverphone Algorithm Precision is " + result[7],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_cav_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_cav_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_cav_suggested[i]));
				}

				/**
				 * writing data related to caverphone end
				 */
				/**
				 * writing data related to DMetaphone start
				 */
				document_suggstd.add(new Paragraph(
						"DMetaphone Algorithm Recall is " + result[3],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"DMetaphone Algorithm Precision is " + result[8],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_dou_meta_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_dou_meta_suggested[i], FontFactory
										.getFont(FontFactory.TIMES_ROMAN, 13,
												new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_dou_meta_suggested[i]));
				}

				/**
				 * writing data related to DMetaphone end
				 */
				/**
				 * writing data related to NYSIIS start
				 */
				document_suggstd.add(new Paragraph(
						"NYSIIS Algorithm Recall is " + result[4], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"NYSIIS Algorithm Precision is " + result[9],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_nys_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_nys_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_nys_suggested[i]));
				}

				/**
				 * writing data related to NYSIIS end
				 */
				/**
				 * writing data related to MetaSoundex start
				 */
				document_suggstd.add(new Paragraph(
						"MetaSoundex Algorithm Recall is " + result[15],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"MetaSoundex Algorithm Precision is " + result[16],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_metas_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_metas_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_metas_suggested[i]));
				}

				/**
				 * writing data related to MetaSoundex end
				 */
				/**
				 * Data is written into suggested document(second document)
				 * Start
				 */
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document_corrected.close();
			document_suggstd.close();
		}
		/*
		 * If the selected language is Spanish, then following block else if
		 * executes
		 */
		else if (language.equalsIgnoreCase("Spanish")) {

			float[] result = new float[9];
			List<WordSuggestions> WordSuggestions = new ArrayList<WordSuggestions>();

			CompareSpanish cmpspn = new CompareSpanish();
			try {
				result = cmpspn.comp(savePath, filename1, filename2,
						WordSuggestions);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * Displays Spanish output on web page start
			 */
			request.setAttribute("message1",
					"Spanish Phonetic Algorithms has been run.");
			request.setAttribute("message2", "Recall is " + result[0]);// MetaphoneRecall
			request.setAttribute("message3", "Precision is " + result[3]);// MetaphonePrecision
			request.setAttribute("message4", "F-measure is " + result[6]);// MetaphoneF-measure
			request.setAttribute("message5", "Recall is " + result[1]);// SoundexRecall
			request.setAttribute("message6", "Precision is " + result[4]);// SoundexPrecision
			request.setAttribute("message7", "F-measure is " + result[7]);// SoundexF-measure
			request.setAttribute("message8", "Recall is " + result[2]);// MetaSoundexRecall
			request.setAttribute("message9", "Precision is " + result[5]);// MetaSoundexPrecision
			request.setAttribute("message10", "F-measure is " + result[8]);// MetaSoundexF-measure
			request.setAttribute("message11", savePath);
			getServletContext().getRequestDispatcher("/outputSpanish.jsp")
					.forward(request, response);

			int count_meta = 0;
			int count_meta_sgs = 0;
			int count_sound = 0;
			int count_sound_sgs = 0;
			int count_metas = 0;
			int count_metas_sgs = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishMetaphone") && ws.corrected == true)
					count_meta++;
				else if (ws.type.matches("SpanishMetaphone")
						&& ws.corrected == false)
					count_meta_sgs++;

				if (ws.type.matches("SpanishSoundex") && ws.corrected == true)
					count_sound++;
				else if (ws.type.matches("SpanishSoundex")
						&& ws.corrected == false)
					count_sound_sgs++;

				if (ws.type.matches("SpanishMetaSoundex")
						&& ws.corrected == true)
					count_metas++;
				else if (ws.type.matches("SpanishMetaSoundex")
						&& ws.corrected == false)
					count_metas_sgs++;
			}

			/**
			 * saving output data of metaphone to String Start
			 */
			String[] output_meta = new String[count_meta + 3];
			int value_meta = 0;
			String[] output_meta_suggested = new String[count_meta_sgs];
			int value_meta_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishMetaphone") && ws.corrected == true) {
					output_meta[value_meta] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishMetaphone")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_meta++;
				}
			}

			String word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishMetaphone") && ws.printed != 1) {
					word = ws.actual_word;
					output_meta_suggested[value_meta_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_meta_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishMetaphone")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_meta_suggested[value_meta_suggested] = sb.toString();
					value_meta_suggested++;
				}
			}
			/**
			 * saving output data of metaphone to String End
			 */
			/**
			 * saving output data of Soundex to String Start
			 */
			String[] output_sound = new String[count_sound + 3];
			int value_sound = 0;
			String[] output_sound_suggested = new String[count_sound_sgs];
			int value_sound_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishSoundex") && ws.corrected == true) {
					output_sound[value_sound] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishSoundex")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_sound++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishSoundex") && ws.printed != 1) {
					word = ws.actual_word;
					output_sound_suggested[value_sound_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_sound_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishSoundex")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_sound_suggested[value_sound_suggested] = sb
							.toString();
					value_sound_suggested++;
				}
			}
			/**
			 * saving output data of Soundex to String End
			 */

			/**
			 * saving output data of MetaSoundex to String Start
			 */
			String[] output_metas = new String[count_metas + 3];
			int value_metas = 0;
			String[] output_metas_suggested = new String[count_metas_sgs];
			int value_metas_suggested = 0;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishMetaSoundex")
						&& ws.corrected == true) {
					output_metas[value_metas] = "The word " + ws.actual_word
							+ " is corrected as " + ws.suggested_word;
					String printedWord = ws.actual_word;
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishMetaSoundex")
								&& wsPrint.actual_word.matches(printedWord)) {
							wsPrint.printed = 1;
						}
					}
					value_metas++;
				}
			}

			word = null;
			for (WordSuggestions ws : WordSuggestions) {
				if (ws.type.matches("SpanishMetaSoundex") && ws.printed != 1) {
					word = ws.actual_word;
					output_metas_suggested[value_metas_suggested] = "The word "
							+ ws.actual_word + " is not corrected."
							+ "The Suggested words are: ";
					value_metas_suggested++;
					StringBuffer sb = new StringBuffer();
					for (WordSuggestions wsPrint : WordSuggestions) {
						if (wsPrint.type.matches("SpanishMetaSoundex")
								&& wsPrint.actual_word.matches(word)) {
							sb.append(wsPrint.suggested_word + ",");
							wsPrint.printed = 1;
						}
					}
					output_metas_suggested[value_metas_suggested] = sb
							.toString();
					value_metas_suggested++;
				}
			}
			/**
			 * saving output data of MetaSoundex to String End
			 */

			/**
			 * Pdf file generation start
			 */
			Document document_corrected = new Document(); // correctedword list
			Document document_suggstd = new Document(); // suggestedword list
			try {

				PdfWriter.getInstance(document_corrected, new FileOutputStream(
						savePath + "phonetic_corrected_spanish.pdf"));
				PdfWriter.getInstance(document_suggstd, new FileOutputStream(
						savePath + "phonetic_suggested_spanish.pdf"));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document_corrected.open();
			document_suggstd.open();
			try {

				/**
				 * Data is written into corrected document(first document) Start
				 */
				document_corrected
						.add(new Paragraph(
								"Spanish Phonetic Algorithms has been run Successfully.",
								FontFactory
										.getFont(FontFactory.TIMES_ROMAN, 18,
												Font.BOLD, new BaseColor(255,
														0, 0))));
				document_corrected.add(new Paragraph("                  "));

				/**
				 * writing data related to metaphone start
				 */
				document_corrected.add(new Paragraph(
						"Metaphone Algorithm Recall is " + result[0],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"Metaphone Algorithm Precision is " + result[3],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_meta; i++)
					document_corrected.add(new Paragraph(output_meta[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to metaphone end
				 */

				/**
				 * writing data related to soundex start
				 */
				document_corrected.add(new Paragraph(
						"Soundex Algorithm Recall is " + result[1], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"Soundex Algorithm Precision is " + result[4],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_sound; i++)
					document_corrected.add(new Paragraph(output_sound[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to soundex end
				 */

				/**
				 * writing data related to MetaSoundex start
				 */
				document_corrected.add(new Paragraph(
						"MetaSoundex Algorithm Recall is " + result[2],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_corrected.add(new Paragraph(
						"MetaSoundex Algorithm Precision is " + result[5],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));

				for (int i = 0; i < value_metas; i++)
					document_corrected.add(new Paragraph(output_metas[i]));
				document_corrected.add(new Paragraph("                  "));
				/**
				 * writing data related to MetaSoundex end
				 */
				/**
				 * Data is written into corrected document(first document) End
				 */

				/**
				 * Data is written into suggested document(second document)
				 * Start
				 */
				document_suggstd
						.add(new Paragraph(
								"Spanish Phonetic Algorithms has been run Successfully.",
								FontFactory
										.getFont(FontFactory.TIMES_ROMAN, 18,
												Font.BOLD, new BaseColor(250,
														0, 0))));
				document_suggstd.add(new Paragraph("                  "));

				/**
				 * writing data related to metaphone start
				 */
				document_suggstd.add(new Paragraph(
						"Metaphone Algorithm Recall is " + result[0],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"Metaphone Algorithm Precision is " + result[3],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_meta_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_meta_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_meta_suggested[i]));

				}
				document_suggstd.add(new Paragraph("                  "));
				/**
				 * writing data related to metaphone end
				 */

				/**
				 * writing data related to soundex start
				 */
				document_suggstd.add(new Paragraph(
						"Soundex Algorithm Recall is " + result[1], FontFactory
								.getFont(FontFactory.TIMES_ROMAN, 13,
										Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"Soundex Algorithm Precision is " + result[4],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_sound_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_sound_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_sound_suggested[i]));
				}
				document_suggstd.add(new Paragraph("                  "));
				/**
				 * writing data related to soundex end
				 */

				/**
				 * writing data related to MetaSoundex start
				 */
				document_suggstd.add(new Paragraph(
						"MetaSoundex Algorithm Recall is " + result[2],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				document_suggstd.add(new Paragraph(
						"MetaSoundex Algorithm Precision is " + result[5],
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 13,
								Font.BOLD, new BaseColor(0, 0, 255))));
				for (int i = 0; i < value_metas_suggested; i++) {
					if (i % 2 == 0) {
						document_suggstd
								.add(new Paragraph("                  "));
						document_suggstd.add(new Paragraph(
								output_metas_suggested[i], FontFactory.getFont(
										FontFactory.TIMES_ROMAN, 13,
										new BaseColor(0, 0, 255))));
					} else
						document_suggstd.add(new Paragraph(
								output_metas_suggested[i]));
				}

				/**
				 * writing data related to MetaSoundex end
				 */
				/**
				 * Data is written into suggested document(second document)
				 * Start
				 */
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document_corrected.close();
			document_suggstd.close();
		}

	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}