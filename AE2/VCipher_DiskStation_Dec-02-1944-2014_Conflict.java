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
		
		//Integer to store length of keyword
		int rowsNo = cipherKey.length();
		System.out.println(cipherKey);
		System.out.println(rowsNo);
		vcipher = new char [rowsNo][SIZE];
		
		for (int rowIndex = 0; rowIndex < rowsNo; rowIndex++)
		{	//vcipher [rowIndex][0] = 'A';
			vcipher [rowIndex][0] = cipherKey.charAt(rowIndex);
			int alphaIndex =  alphabet[(cipherKey.charAt(rowIndex)+1) - 'A'];
			for (int colIndex = 1; colIndex < SIZE; colIndex++)
			{	vcipher[rowIndex][colIndex] = alphabet[((alphaIndex)-'A')];
				alphaIndex++;
				if ((alphaIndex -'A') > ('Z' - 'A'))
					{	alphaIndex = 'A';		
					}
			}
		}
		
		for (int i = 0; i > rowsNo; i++)
		{	System.out.println(Arrays.toString(vcipher[i]));
		}
		System.out.println(Arrays.toString(alphabet));
		
	    // your code
	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{
	    return ' ';  // replace with your code
	}
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
	    return ' ';  // replace with your code
	}
}
