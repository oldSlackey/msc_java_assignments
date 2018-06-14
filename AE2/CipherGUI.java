import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.util.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private static final int SIZE = 26;
	private static final int KEYWORDCOUNT = 1;
	private static final int WHITESPACE = 2;
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies letterCount;
	private String inputFileName, outputFileName, inputKeyword;
	private Scanner textIn;
	private char [] coDec = new char [1]; 
	private char [] outputChars;
	private int [] repeatKeywordChars = new int [SIZE];
	private FileReader textInput;
	private FileWriter textOutput;
	private PrintWriter reportOutput;
	private boolean fileFound = true;
	private boolean codeType = true;
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{	if (e.getSource() == monoButton)
		{	monoCipherMethod();
		}
		else if (e.getSource() == vigenereButton)
		{	vCipherMethod();			
		}
	}
	
	/**
	 * Helper method to process monocipher
	 */
	private void monoCipherMethod()
	{	/*Determine whether to proceed to encoding/decoding
		*/
		if (processFileName() && getKeyword())
		{	findFile();
			if (processFile(false))
			{	//Prints letter frequencies file if all is well
				printReportFile();
				
			}
			else //This in the event of I/O operations failing
			{	System.out.println("Something went wrong..");
			}
		}
		else
		{	clearTextFields();
		}
	}
	
	private void vCipherMethod()
	{	if (processFileName() && getKeyword())
		{	findFile();	
			if (processFile(true))
			{	//Prints letter frequencies file if all is well
				printReportFile();
			}
			else //This in the event of I/O operations failing
			{	System.out.println("Something went wrong..");
			}
	}
		else
		{	clearTextFields();
		}		
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	
		//obtain keyword from textfield
	{	inputKeyword = keyField.getText().trim();
	
		//create integer for use as an index in a while loop
		int index = 0;
		
		//Create boolean to aid in case checking
		boolean keywordCaseCheck = true;
		
		/*While loop that checks the case, in turn,
		 * of each character in the keyword using
		 * another boolean helper method and
		 * that there are no repeat characters
		 */
		while (keywordCaseCheck && index < (inputKeyword.length())-1)
		{	if (!checkCase(index))
			{	//procedure in event of invalid keyword
				JOptionPane.showMessageDialog(null, "Your Keyword is invalid", "Error", JOptionPane.ERROR_MESSAGE);
				clearTextFields();
				//reset repeat character array
				resetKeyword();
				//set boolean
				keywordCaseCheck = false;
				/*Break to prevent infinite loop
				 * as index is not incremented if
				 * a lower case letter is found
				 */
				break;
			}
			else
			{	keywordCaseCheck = true;
				index++;
			}
		}
		//Test for keyword validity
		if (keywordCaseCheck)
		{	return true;
		}
		
		else
		{	return false;			
		}
	}
	
	/*Helper method to check keyword
	 * is upper case and not a repeat
	 * and return a boolean
	 */
	private boolean checkCase(int index)
	{	if (inputKeyword.charAt(index) >= 'A' && inputKeyword.charAt(index) <= 'Z' && keywordRepeats(inputKeyword.charAt(index))) //If keyword is capital and not repeated
		{	return true;		
		}
		else
		{	return false;
			
		}
		
	}
	
	//Helper method to check repeat keyword chars
	private boolean keywordRepeats(char repeat)
	{	//Integer to store value of incoming char
		int index = repeat - 'A';
			
		//Increment char count to be checked in array
		repeatKeywordChars[index] = repeatKeywordChars[index] + KEYWORDCOUNT;
		//Test to see if the char has been seen before (i.e. index count is greater than 1)
		if (repeatKeywordChars[index] > KEYWORDCOUNT)
		{	return false;
		}
		else
		{	return true;
		}
		
	}
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{	//Obtain filename from user input
		inputFileName = messageField.getText().trim();
		
		/*Truncate and store filename so we can
		 * add the correct letter ('C' or 'D', and 'F')
		 */
		outputFileName = inputFileName.substring(0, (inputFileName.length())-1);
		
		//test if user entered a filename
		if (inputFileName.isEmpty())
		
		/*give user an error message if no filename was entered
		 * and return false to method that called this method
		 * to prevent further processing
		 */
		{	JOptionPane.showMessageDialog(null, "You did not enter a filename",
			"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
		{	//Get length of filename to find last letter
			int fileNameLength = inputFileName.length();
			
			//Store last letter of filename
			String endFileName = "" + inputFileName.charAt(fileNameLength-1);
			
			//Store filename
			inputFileName = inputFileName + ".txt";
			
		/*Check the last letter of the filename
		 * boolean "codeType" is used to determine
		 * whether to encode or decode the input file
		 */
		
			if (endFileName.equals("P"))
			{	codeType = true;
				return true;
			}
			else if (endFileName.equals("C"))
				{	codeType = false;
					return true;			
				}
				else
				{	//Error message if filename was not appropriate
					JOptionPane.showMessageDialog(null, "You did not enter a valid filename",
							"Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
		}
	}	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{	/*Conditional test to determine whether to 
		 * use mono or Vigenere cipher
		 */
		
		//Process MonoAlphabetic encryption
		if (fileFound && vigenere == false && codeType == true)
		{	//Read in text
			readFileContents();
			
			//Instantiate MonoCipher and coDec array
			outputArray(false);
			
			//Pass output filename to helper methods
			fileOutput("..\\AE2\\"+ outputFileName + "C");
			reportOutputFile("..\\AE2\\"+ outputFileName + "F");
			
			//Instantiate letter counting object
			letterCount = new LetterFrequencies();
			
			//Pass each character to the encode method in mcipher object
			try
			{	for (int index = 0; index < coDec.length; index++)
				{	outputChars[index] =  mcipher.encode(coDec[index]);
					letterCount.addChar(outputChars[index]);
										
					//Write output to text file
					textOutput.write(outputChars[index]);
				}
				vigenere = true;
			}
			
			catch (IOException c)
			{	System.out.println("File not found: " + c);	
				vigenere = false;
			}			
		}
		
		//Process MonoAlphabetic decryption
		else if (fileFound && vigenere == false && codeType == false)
		{	readFileContents();
		
			//Instantiate MonoCipher object and text array
			outputArray(false);
			
			//Pass output filename to helper methods
			fileOutput("..\\AE2\\"+ outputFileName + "D");
			reportOutputFile("..\\AE2\\"+ outputFileName + "F");
			
			//Instantiate letter counting object
			letterCount = new LetterFrequencies();
			
			//Pass each character to the decode method in mcipher object
			try
			{	for (int index = 0; index < coDec.length; index++)
				{	outputChars[index] =  mcipher.decode(coDec[index]);
					letterCount.addChar(outputChars[index]);
										
					//Write output to text file
					textOutput.write(outputChars[index]);
				}
				
				vigenere = true;
			}
			
			
			catch (IOException c)
			{	System.out.println("File not found: " + c);	
				vigenere = false;
			}			
			
		}
				
		
		//Encrypt plain text using vigenere cipher
		else if (fileFound && vigenere == true && codeType == true)
		{	readFileContents();
			
			//Instantiate VCipher object
			outputArray(true);
			
			//Pass output filename to helper methods
			fileOutput("..\\AE2\\"+ outputFileName + "C");
			reportOutputFile("..\\AE2\\"+ outputFileName + "F");
		
			//Instantiate letter counting object
			letterCount = new LetterFrequencies();
		
			//Pass each character to the encode method in vcipher object
			try
			{	for (int index = 0; index < coDec.length; index++)
				{	outputChars[index] =  vcipher.encode(coDec[index]);
					letterCount.addChar(outputChars[index]);
									
					//Write output to text file
					textOutput.write(outputChars[index]);
				}
				vigenere = true;
			}
		
			catch (IOException c)
			{	System.out.println("File not found: " + c);	
				vigenere = false;
			}
		}
			
			//Decrypt cipher text using vigenere cipher
		else if (fileFound && vigenere == true && codeType == false)
		{	readFileContents();
			
			//Instantiate VCipher object
			outputArray(true);
				
			
			//Pass output filename to helper methods
			fileOutput("..\\AE2\\"+ outputFileName + "D");
			reportOutputFile("..\\AE2\\"+ outputFileName + "F");
		
			letterCount = new LetterFrequencies();
		
			//Pass each character to the decode method in vcipher object
			try
			{	for (int index = 0; index < coDec.length; index++)
				{	outputChars[index] =  vcipher.decode(coDec[index]);
					letterCount.addChar(outputChars[index]);
									
					//Write output to text file
					textOutput.write(outputChars[index]);
				}
			
				vigenere = true;
			}
		
		
			catch (IOException c)
			{	System.out.println("File not found: " + c);
				vigenere = false;
			}			
		
		}
			//Reset instance variables to let user try again with valid input
			else if (!fileFound)
			{	clearTextFields();
				resetKeyword();	
				resetFileName();
			}
	return vigenere;
	}
	
	
	
	/*Helper method to open the file and
	 * catch file not found exceptions
	 */
	private void findFile()
	
		//Open the requested file
	{	try
		{	textInput = new FileReader("..\\AE2\\"+ inputFileName);
			fileFound = true;
		}
		
		/*Catch IOException if file doesn't exist
		 * let user go again
		 */
		catch (IOException e)
		{	JOptionPane.showMessageDialog(null, "No file with that name found, anywhere...", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("File not found!");
			resetFileName();
			fileFound = false;
		}
		
				
	}
	
	/*Method to read text contained in file
	 * and store as an array of chars.
	 */
	private void readFileContents()
	{	textIn = new Scanner(textInput);
		
		//While loop to create array
		String inputText = "";
		while (textIn.hasNext())		
		{	//Create a string to store the next string from our input file
			inputText = textIn.next();
			
			/*Create a new array to grow it
			 * by the length of the next string
			 * plus a little bit to account for 
			 * whitespace, punctuation and numbers.
			 */
			char [] coDec2 = new char [inputText.length() + coDec.length + WHITESPACE];
				
			//Copy the contents of the old array to the new one
			System.arraycopy(coDec, 0, coDec2, 0, coDec.length);
				
			//Change pointer of old array to point at new one, old array disappears
			coDec = coDec2;
				

		}
		try
		{	/*New filereader from file to read characters
			*as initial filereader has been exhausted
			*/
			FileReader charsIn = new FileReader ("..\\AE2\\"+ inputFileName);
			for (int index = 0; index < coDec.length; index++)
			{	int s = charsIn.read();
				if (s > -1)
				{	char t = (char) s;
					coDec [index] = t;
				}
			}
			charsIn.close();
		}
			
		catch (IOException i)
		{	System.err.println("IOException: " + i);
		}
	}
	
		

	
	/*Helper method to clear textfields in the event
	 * of input/output errors or incorrect data entry
	 * by user, or any other eventuality
	 */
	private void clearTextFields()
	{	messageField.setText("");
		keyField.setText("");		
	}
	
	/*Helper method to instantiate
	 * MonoCipher object and an
	 * array to store encoded/decoded characters
	 */
	private void outputArray(boolean cipherType)
	{	if (!cipherType)
		{	mcipher = new MonoCipher(inputKeyword);
		}
		
		else
		{	vcipher = new VCipher(inputKeyword);
		}
	
		outputChars = new char[coDec.length];	
	}
		
	
	/*Helper method to process text
	 * file output
	 */
	private void fileOutput(String fileName)
	{	try
		{	String outFileName = fileName + ".txt";
			textOutput = new FileWriter(outFileName);
		}
		catch (IOException o)
		{	System.out.println("Cipher/Plain text file output error" + o);
			
		}
		
	}
	
	//Method to process letter frequency report file
	private void reportOutputFile(String reportName)
	{	try
		{	String reportFileName = reportName + ".txt";
			reportOutput = new PrintWriter(reportFileName);
		}
		catch (IOException r)
		{	System.out.println("Frequency output file error" + r);			
		}		
	}
	
	private void printReportFile()
	{	try
		{	textOutput.close();
			String s = letterCount.getReport();				
			reportOutput.println(s);
			reportOutput.close();
			System.exit(0);
		}
	
		catch (IOException to)
		{	System.out.println("Something went wrong" + to);	
		}
	}
	
	private void resetKeyword()
	{	//reset repeat character array
		int [] resetRepeatKeywords = new int[SIZE];
		repeatKeywordChars = resetRepeatKeywords;	
	}
	
	private void resetFileName()
	{	inputFileName = "";
		outputFileName = "";
		
	}
}

