import java.util.Arrays;

/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	
    // more instance variables
	private char [][] vcipher;
	private String cipherKey;
	private char encodeLetter, decodedLetter;
	
	/*Variable to keep track of row position in cipher array
	 * will be set to 0 when object is instantiated
	 */
	private int cipherArrayRow = 0;
	/*Integer to store length of keyword
	 * needs to be available for multiple methods
	 */
	private int rowsNo = 0;
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{	//Store keyword
		cipherKey = keyword;
		
		//Create alphabet array
		alphabet = new char [SIZE];
		for (int aIndex = 0; aIndex < SIZE; aIndex++)
		{	alphabet[aIndex] = (char)('A' + aIndex);
		}
		
		/*Create 2D array with
		 * keyword length number of rows
		 * and 26 columns
		 */
		
		//Store length of keyword
		rowsNo = cipherKey.length();
		
		//Initialise cipher array
		vcipher = new char [rowsNo][SIZE];
		
		//Loop to create each new row in cipher array
		for (int rowIndex = 0; rowIndex < rowsNo; rowIndex++)
		{	//set the first char of each row
			vcipher [rowIndex][0] = cipherKey.charAt(rowIndex);
			
			//index to keep track of cipher characters
			int alphaIndex =  alphabet[(cipherKey.charAt(rowIndex)+1) - 'A'];
			
			//Then loop through the remaining columns in each row to set the remaining alphabetic characters
			for (int colIndex = 1; colIndex < SIZE; colIndex++)
			{	vcipher[rowIndex][colIndex] = alphabet[((alphaIndex)-'A')];
				alphaIndex++;
				
				//Test to see if character is at Z yet (otherwise increment character index)
				if ((alphaIndex -'A') > ('Z' - 'A'))
					{	alphaIndex = 'A';		
					}
			}
		}
		
		//TRYING TO PRINT ARRAYS.....
		for (int i = 0; i < rowsNo; i++)
		{	System.out.println(Arrays.toString(vcipher[i]));
		}
		System.out.println(Arrays.toString(alphabet));
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{	//Store character to be encoded
		encodeLetter = ch;
				
			//Test to determine if char is alphabetic
			if (encodeLetter >= 'A' && encodeLetter <='Z')
			{	//Find the index of the character in the alphabet
				int cipherArrayCol = encodeLetter - 'A';
				
				//Test to see if we have reached the end of the cipher array rows
				if (cipherArrayRow >= rowsNo) //Reached the last row, so start at row 0
				{	
					cipherArrayRow = 0;
					decodedLetter = vcipher[cipherArrayRow][cipherArrayCol];					
					ch = decodedLetter;
					cipherArrayRow++;
				}				
				else
				{	decodedLetter = vcipher[cipherArrayRow][cipherArrayCol];
					cipherArrayRow++;
					ch = decodedLetter;
				}					
			}
				
			/*If not an upper case letter
			 * return original value as must be 
			 * punctuation, lower case, or non-alphabet character
			 */
			else
			{	ch = encodeLetter;
			}
	    
	    return ch;  
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{	//Store character to be decoded
		encodeLetter = ch;
		
		//Test to determine if char is alphabetic
		if (encodeLetter >= 'A' && encodeLetter <='Z')
		{	//Search each column in the correct row for the input char
			
			boolean encodeCharFound = false;
			int alphaIndex = 0;
			while (!encodeCharFound)
				{	//search for char in cipher
					if (encodeLetter == vcipher[cipherArrayRow][alphaIndex])
					{	encodeCharFound = true;
													
						//read decoded char from the alphabet
						decodedLetter = alphabet[alphaIndex];
						ch = decodedLetter;	
						cipherArrayRow++;
					}
					else
					{	alphaIndex++;
					}
				}
		}
		
		//Test to determine whether cipher rows have been exhausted
		if (cipherArrayRow == rowsNo)
		{	cipherArrayRow = 0;			
		}

	    return ch;
	}
}
