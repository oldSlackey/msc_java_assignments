/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */

//import java.util.*;
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	
	//More instance variables
	private String keywordIn;
	private int cipherIndex, alphaIndex, cIndex;
	private char encodeLetter, decodedLetter;
	

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//create alphabet
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++)
			alphabet[i] = (char)('A' + i);
		
		// create first part of cipher from keyword
		cipher = new char [SIZE];
		keywordIn = keyword.trim();
		cipherIndex = 0;
		alphaIndex = (SIZE-1);
		/*Loop to insert cipher characters into
		 * first part of array
		 */
		try
		{	//Set integer for maximum iterations in while loop
			int len = keywordIn.length();
			
			//Loop to insert keyword characters into cipher array
			while (cipherIndex < len)
			{	cipher[cipherIndex] = keywordIn.charAt(cipherIndex);
				cipherIndex++;			
			}

		}
		catch (StringIndexOutOfBoundsException s)
		{	System.err.println("StringIndexEXCEPTION: " + cipherIndex);
		}
		/* create remainder of cipher from the remaining characters of the alphabet
		*/
		try
		{	for (int counter = SIZE-1; counter >= 0; counter--)			
			{ 	//Start at 'Z'
				char nextChar = (char) ('A' + counter);
				
				//Search the keyword for the alphabetic character
				if (keywordIn.indexOf(nextChar) == -1)//Insert nextChar if not found
				{	cipher[cipherIndex] = nextChar;
					cipherIndex++;
				}
			
			}
				
		}
		catch (ArrayIndexOutOfBoundsException a)
		{ System.err.println(a);
		}
		
		// print cipher array for testing and tutors
		System.out.println(alphabet);
		System.out.println(cipher);
	}
		
	
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{	//Store character to be encoded
		encodeLetter = ch;
				
		//Find the index of the character in the alphabet
			if (encodeLetter >= 'A' && encodeLetter <='Z')
			{	decodedLetter = cipher[encodeLetter - 'A'];
				ch = decodedLetter;
			}
			/*If not an upper case letter
			 * return original value as must be 
			 * punctuation or non-alphabet character
			 */
			else
			{	ch = encodeLetter;
			}
	    
		return ch;	
}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{	//Store character to be encoded
		encodeLetter = ch;
				
		//Find the index of the character in the alphabet
		boolean encodeCharFound = false;
		alphaIndex = 0;
		while (!encodeCharFound && alphaIndex < SIZE)
		{	if (encodeLetter == cipher[alphaIndex])
			{	encodeCharFound = true;
				decodedLetter = alphabet[alphaIndex];
				ch = decodedLetter;
			}
			else
			{	alphaIndex++;
			}
	    }
		return ch;  // replace with your code
	}
	
	
}
