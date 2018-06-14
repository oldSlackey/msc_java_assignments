/**
 * Programming AE2
 * Processes report on letter frequencies
 */

public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	//Frequencies for each letter
	private double [] charFrequencies;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars, inputChar, maxFreqIndex;
	
	//Used to create report string in reportColumns method
	private String columns = "";
	
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{	/*initialise array to store letter frequencies
		*default for int arrays is to set values at all indices
		*to 0, so we dont need to do anything else
		*/
		alphaCounts = new int [SIZE];
		
		//start count of total characters
		totChars = 0;
		
		//Initialise alphabet array (copied from MonoCipher...)
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
	   
	}
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{	//store incoming char in instance variable
	   if (ch >= 'A' && ch <= 'Z')
	   {	inputChar = ch- 'A';
	    
	    //Call method to increment alphacount array
	    countLetter(inputChar);
	   }
	}
	
	/*Method to count individual
	 * and total alphabetic character
	 * frequencies
	 */
	private void countLetter(int indexValue)
		//Count individual character frequencies
	{	alphaCounts[indexValue] = alphaCounts[indexValue] + 1;
		
		//Count total characters
		totChars++;
	}
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {	double maxPercent = charFrequencies[maxFreqIndex];
	    return maxPercent;
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	//As above...
	public String getReport()
	{	inputCharFrequencies();
		mostFrequent();
		reportColumns();
		String report = "" + String.format("LETTER ANALYSIS%n%n") + "Letter  " + 
				"Freq  " + "Freq%  " + "AvgFreq%  " + String.format("Diff%n") +
				columns + "The most frequent letter is " + maxCh + 
				" with a percent frequency of " + String.format("%4.1f", getMaxPC());
		
	    return report;
	}
	
	//Calculate and store character frequencies
	private void inputCharFrequencies()
	{	charFrequencies = new double [SIZE];	
		for (int index = 0; index < SIZE; index++)
		{	//Calculate and store percent frequencies of input chars
			charFrequencies[index] =  (alphaCounts[index]/(double) totChars)*100;
		}
		
	}
	
	/*Calculate most frequent character
	 * and store its index
	 */
	private void mostFrequent()
	{	//Initialise most frequent char variable
		maxCh = alphabet[0];
		
		/*Integer to store index
		 * of most frequent char so far
		 */
		int maxIndex = 0;
		
		/*Initialise integer to store index
		 * of most frequent char to use in getMaxPC method
		 */
		maxFreqIndex = 0;
		
		//Loop to find most frequent char and its index
		for (int i = 0; i < SIZE; i++)
		{	if (alphaCounts[i] > maxIndex)
			{	maxCh = alphabet[i];
				maxIndex = alphaCounts[i];
				maxFreqIndex = i;			
			}
		}
		
	}
	
	/*Create formatted string with letters
	 * and frequencies for report
	 */
	private void reportColumns()
	{	//String builder for appending strings of array values
		StringBuilder letterData = new StringBuilder();
		for (int i = 0; i < SIZE; i++)
		{	//Build string iteratively so columns can be created
			columns = String.format("" + letterData.append("   " + alphabet[i] + "   " + String.format("%4d", alphaCounts[i]) + "   "
				+String.format("%4.1f",charFrequencies[i]) + "     " + "" + avgCounts[i] + "    " +
				String.format("%4.1f", (avgCounts[i])-charFrequencies[i]) + "%n"));
			
		}
		
	}
}
