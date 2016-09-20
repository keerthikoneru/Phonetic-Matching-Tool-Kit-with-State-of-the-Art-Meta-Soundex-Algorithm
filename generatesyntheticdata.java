package PhoneticMatching;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author keerthi
 *
 */
public class generatesyntheticdata {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ArithmeticException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws FileNotFoundException, ArithmeticException, IOException, Exception {
		String filename = "G:\\keerthi\\synthetic\\test.csv";
		Manipulatesingle(filename);
		Manipulatedouble(filename);
	}

	/**
	 * generates words with double errors for given correct words.
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 * @throws ArithmeticException
	 */
	private static void Manipulatedouble(String filename) throws FileNotFoundException, IOException, Exception, ArithmeticException {
	    ArrayList<String> al1=new ArrayList<String>();
	    ArrayList<String> al2=new ArrayList<String>();
	    String filedouble= "G:\\keerthi\\synthetic\\"+"syntheticdata_double.csv";
	    String originalfiledouble = "G:\\keerthi\\synthetic\\"+"original_double.csv";
	    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    BufferedReader CSVFile1 = new BufferedReader(new FileReader(filename));
		    String dataRow1 = CSVFile1.readLine();
		    while (dataRow1 != null)
		    {
		        String[] dataArray1 = dataRow1.split(",");
		        for (String item1:dataArray1)
		        { 
		        	al1.add(item1);
        			int j = getRandomNumber(0,item1.length()-1);
        			while(item1.charAt(j) == ' ')
        			{
        				j = getRandomNumber(0,item1.length()-1);
        			}
        			int N= alphabet.length();
        			Random r = new Random();
        			item1 = item1.replace(item1.charAt(j),alphabet.charAt(r.nextInt(N)));
        			
        			item1=item1.replace(item1.charAt(2),item1.charAt(1));
        			al2.add(item1);
		        }
	        	
		        dataRow1 = CSVFile1.readLine(); // Read next line of data.
		    }

		    CSVFile1.close();
		    int size = al2.size();
		    FileWriter writer=new FileWriter(filedouble);
		    FileWriter writer1=new FileWriter(originalfiledouble);
		    while (size!=0)
		    {
		    	size--;
		    	writer.append(""+al2.get(size));
	            writer.append('\n');
	            writer1.append(""+al1.get(size));
	            writer1.append('\n');
		    }
		writer.flush();
		writer.close();
		writer1.flush();
		writer1.close();
		System.out.println("done!!");
	}

	/**
	 * generates words with single error for given correct words.
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 * @throws ArithmeticException
	 */
	private static void Manipulatesingle(String filename) throws FileNotFoundException, IOException, Exception, ArithmeticException {
		
	    ArrayList<String> al1=new ArrayList<String>();
	    ArrayList<String> al2=new ArrayList<String>();
	    String filesingle= "G:\\keerthi\\synthetic\\"+"syntheticdata_single.csv";
	    String originalfilesingle = "G:\\keerthi\\synthetic\\"+"original_single.csv";
		 BufferedReader CSVFile1 = new BufferedReader(new FileReader(filename));
		 int i=0;
		    String dataRow1 = CSVFile1.readLine();
		    while (dataRow1 != null)
		    {
		        String[] dataArray1 = dataRow1.split(",");
		        for (String item1:dataArray1)
		        { 
		        	al1.add(item1);
		        	System.out.println("length: "+ item1.length());
		        	if(item1.length() >= 6)
		        	{
		        		char ch = item1.charAt(3);
		        		if(i%2 == 0)
		        		{
		        			item1=item1.replace(item1.charAt(3),item1.charAt(4));
		        			item1=item1.replace(item1.charAt(4),ch);
		        		}
		        		if(i%2 == 1)
		        		{
		        			int k = getRandomNumber(0,item1.length()-1);
		        			while(item1.charAt(k) == ' ')
		        			{
		        				k = getRandomNumber(0,item1.length()-1);
		        			}
		        			switch(i%10)
		        			{
			        			case 0:
			        				item1 = item1.replace(item1.charAt(k), 'V');
			        				break;
			        			case 1:
			        				item1 = item1.replace(item1.charAt(k), 'K');
			        				break;
			        			case 2:
			        				item1 = item1.replace(item1.charAt(k), 'U');
			        				break;
			        			case 3:
			        				item1 = item1.replace(item1.charAt(k), 'M');
			        				break;
			        			case 4:
			        				item1 = item1.replace(item1.charAt(k), 'J');
			        				break;
			        			case 5:
			        				item1 = item1.replace(item1.charAt(k), 'O');
			        				break;
			        			case 6:
			        				item1 = item1.replace(item1.charAt(k), 'P');
			        				break;
			        			case 7:
			        				item1 = item1.replace(item1.charAt(k), 'X');
			        				break;
			        			case 8:
			        				item1 = item1.replace(item1.charAt(k), 'Z');
			        				break;
			        			case 9:
			        				item1 = item1.replace(item1.charAt(k), 'N');
			        				break;
			        			default:
			        				break;
		        			}
		        		}
		        	}	
		        	if(item1.length() < 6)
		        	{
		        		char ch = item1.charAt(1);
		        		if(i%2 == 0)
		        		{
		        			item1=item1.replace(item1.charAt(1),item1.charAt(2));
		        			item1=item1.replace(item1.charAt(2),ch);
		        		}
		        		if(i%2 == 1)
		        		{
		        			int k = getRandomNumber(0,item1.length()-1);
		        			while(item1.charAt(k) == ' ')
		        			{
		        				k = getRandomNumber(0,item1.length()-1);
		        			}
		        			switch(i%10)
		        			{
			        			case 0:
			        				item1 = item1.replace(item1.charAt(k), 'O');
			        				break;
			        			case 1:
			        				item1 = item1.replace(item1.charAt(k), 'P');
			        				break;
			        			case 2:
			        				item1 = item1.replace(item1.charAt(k), 'X');
			        				break;
			        			case 3:
			        				item1 = item1.replace(item1.charAt(k), 'Z');
			        				break;
			        			case 4:
			        				item1 = item1.replace(item1.charAt(k), 'N');
			        				break;
			        			case 5:
			        				item1 = item1.replace(item1.charAt(k), 'V');
			        				break;
			        			case 6:
			        				item1 = item1.replace(item1.charAt(k), 'K');
			        				break;
			        			case 7:
			        				item1 = item1.replace(item1.charAt(k), 'U');
			        				break;
			        			case 8:
			        				item1 = item1.replace(item1.charAt(k), 'M');
			        				break;
			        			case 9:
			        				item1 = item1.replace(item1.charAt(k), 'J');
			        				break;
			        			default:
			        				break;
		        			}
		        		}
		        	}	
		        	al2.add(item1);
		        	i++;
		        }

		        dataRow1 = CSVFile1.readLine(); // Read next line of data.
		    }

		    CSVFile1.close();
		    int size = al2.size();
		    
		    FileWriter writer=new FileWriter(filesingle);
		    FileWriter writer1=new FileWriter(originalfilesingle);
		    while (size!=0)
		    {
		    	size--;
		    	writer.append(""+al2.get(size));
	            writer.append('\n');
	            writer1.append(""+al1.get(size));
	            writer1.append('\n');
		    }
		writer.flush();
		writer.close();
		writer1.flush();
		writer1.close();
	}

	/**
	 * @param min
	 * @param max
	 * @return random number
	 */
	public static int getRandomNumber(int min, int max) {
	    return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

}
