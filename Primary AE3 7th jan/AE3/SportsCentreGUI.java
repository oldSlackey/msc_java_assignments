import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;//domt know why this warning is here...
	
	//Declare FitnessProgram object
	private FitnessProgram classList;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	//Maximum number of timeslots
	private final static int MAX_TIME_SLOTS = 7;
	//Earliest possible start time
	private final static int CLASSES_OPEN = 9;
	//Number of rows in GUI timetable display
	private final static int TIMETABLEROWS = 3;
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea(TIMETABLEROWS, MAX_TIME_SLOTS);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		
		//Call method to create list of classes (FitnessProgram object)
		initLadiesDay();
		
		//Call method to add in attendances
		initAttendances();
		
		//Call method to format and display class timetable information
		updateDisplay();		
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay()
	{	//Instantiate FitnessProgram object
		classList = new FitnessProgram();
		
		//Declare filereader to open classesIn file
		FileReader readClassesData = null;
		
		//Declare scanner to read strings from file
		Scanner classesInStrings;
		
		//This try-catch solution taken from tutorial 10 solutions and lectures
		try
		{	try
			{	//Instantiate filereader for classesIn file
				readClassesData = new FileReader(classesInFile);
			
				//Instantiate scanner for classesIn file
				classesInStrings = new Scanner(readClassesData);
				
				//While loop to read in all lines
				while (classesInStrings.hasNextLine())
				{	/*read in the id, class name, tutor names and start time as a single string,
					and the start time as an integer, and then pass these as parameters to the addClass method in classList*/
				
					//String to store addClass method parameters.
					String classDesc = classesInStrings.nextLine();
				
					//Find and store the class start time, as this will always be the last integer on each line
					int startTime = Integer.parseInt(classDesc.substring((classDesc.length()-2), classDesc.length()).trim());
				
					//Call addClass method from fitnessprogram object to add a new fitnessclass object to the list (array of FitnessClass objects)
					classList.addClass(startTime, classDesc);
				}
			}
				
				finally
				{	//Test to see if filereader was initialised.
					if (readClassesData != null)
					{	//Close file
						readClassesData.close();	
					}
				}
		}
		
		//Catch file missing exception
		catch (FileNotFoundException i)
		{	System.err.println("File not found....well done.." + i);
			System.exit(1);
		}
		
		//Exception for closing file errors
		catch (IOException o)
		{	System.err.println("Somehow, could not read file, this is in GUI class: " + o);
			System.exit(1);
		}
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances()
	{	//Declare local filereader and scanner for AttendancesIn file
		FileReader readAttendanceFile = null;
		Scanner attendanceInString;
		
		try
		{	try
			{	//Instantiate filereader and scanner objects
				readAttendanceFile = new FileReader(attendancesFile);
				attendanceInString = new Scanner(readAttendanceFile);
			
				//Loop through each line
				while (attendanceInString.hasNextLine())
				{	//Read in line from attendances file and pass id to fitness program
					classList.addAttendances(attendanceInString.nextLine());
				}				
			}
			
			finally
			{	//Test to see if filereader was initialised.
				if (readAttendanceFile != null)
				{	//Close attendancesIn file
					readAttendanceFile.close();				
				}
			}
		}
		
		catch (FileNotFoundException g)
		{	System.err.println("Attendance file not found...where is it?: " + g);
			System.exit(1);
		}
		
		catch (IOException p)
		{	System.err.println("Eclipse made me catch this exception, as it wouldnt let me put in"
				+ "a 'finally' block without loads of jiggery pokery, nonsense: " + p);	
			System.exit(1);
		}
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay()
	{	/*add timetable to display JTextArea
		* For loop to cycle through class and tutor names and start times*/
		String className = "";
		String tutorName= "";
		String timeSlot = "";
		
		//Loop seven times
		for (int classIndex = 0; classIndex < MAX_TIME_SLOTS; classIndex++)
		{	/*Test to determine whether timeslot has a class(i.e. the array contains an object with that start time)
			* we need to add 9 to the index as the getClass method in fitnessclass subtracts 9 in order to
			* implement other functionality*/
			
			//Iteratively build the timeslot string (easier to format this way)
			timeSlot += String.format("%-12s", (classIndex + CLASSES_OPEN) + "-" + (classIndex + 1 + CLASSES_OPEN));
			
			//Iteratively concatenate fitnessclass instance variable values, formatted to be left justified with a fieldwidth of 12
			if (classList.getClass(classIndex + CLASSES_OPEN) == null)
			{	className += String.format("%-12s", "Available");
				tutorName += String.format("%-12s", "");
			}
			else //obtain and store instance variable values from FitnessClass objects in array
			{	className += String.format("%-12s", classList.getClass(classIndex + CLASSES_OPEN).getClassName());
				tutorName += String.format("%-12s",classList.getClass(classIndex + CLASSES_OPEN).getTutorName());
			}			
		}
		
		//Add platform-independent new line operators to each string
		className += String.format("%n");
		tutorName += String.format("%n");
		timeSlot += String.format("%n");
		
		//Add strings to the GUI
		display.setText(timeSlot + className + tutorName);
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding()
	{	//Check that all textfields have data (no validation of input format as per specification)
		if (idIn.getText().isEmpty() || classIn.getText().isEmpty() || tutorIn.getText().isEmpty() || idIn.getText().length() > 3)
		{	JOptionPane.showMessageDialog(null, "One or more of the required inputs is missing or the classId is incorrect, try again", "Error", JOptionPane.ERROR_MESSAGE);
			clearTextFields();			
		}
		//Use helper methods to check if class id already exists in array
		else if (checkId())//boolean method
			{	//if method returns true, then id already exists, display a warning
				JOptionPane.showMessageDialog(null, "A class with that ID already exists.\n"
						+ "Please check carefully and try again, if you wish", "Error", JOptionPane.ERROR_MESSAGE);
				clearTextFields();			
			}
		
			/*if class id doesnt already exist we can proceed to adding class, read in texfields,
			 * create string (class time using findAvailableTime method from fitnessprogram,classId,
			 * name and tutor name from textfields) and pass as parameter to add class method, plus
			 * update all necessary arrays and displays
			 * */
			else
			{	//Get first available timeslot
				int addStartTime = classList.findAvailableTime();
				//Create string in format required for addClass method
				String classNames = "" + idIn.getText().trim() + " " + classIn.getText().trim()
						+ " " + tutorIn.getText().trim() + " " + addStartTime;
				
				//Invoke addClass method passing parameters from textfield inputs
				classList.addClass(addStartTime, classNames);
				
				//Create attendance string for adding in attendances of this class (all set to 0)
				String newClassAttendance = "" + idIn.getText().trim() + " 0 0 0 0 0";
				
				//Set new class attendances passing attendance string as a paramater
				classList.addAttendances(newClassAttendance);
				clearTextFields();
				
				//Display new timetable on GUI
				updateDisplay();
			}
	}
	

	/**
	 * Processes deleting a class
	 */
	public void processDeletion()
	{	//Test if id matches an id in our list
		if (checkId())//id matches the list
		{	//Delete the FitnessClass object from the array
			classList.setClassNull(idIn.getText().trim());			
			clearTextFields();
			
			//Refresh the timetable display with updated classes
			updateDisplay();
		}
		else//id doesnt exist, display warning and clear textfields
		{	JOptionPane.showMessageDialog(null, "Sorry, there is no class with that ID, please check" + String.format("%n")
				+ " and try again", "Error", JOptionPane.ERROR_MESSAGE);
			clearTextFields();			
		}
	    
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport()
	{	/*Instantiate the reportframe object and pass classList object to it*/
		report = new ReportFrame(classList);
	}

	/**
	 * Writes lines to file representing class id, name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose()
	{	try
		{	//Declare local Printwriter
			PrintWriter classListFile = null;
			try
			{	//Instantiate local printwriter and pass filename to it need a method in fitness program to pass a formatted report to it
				classListFile = new PrintWriter(classesOutFile);
				classListFile.print(classList.getClassesOutFile());
			}
			finally
			{	//Test whether file was successfully initialised
				if (classListFile != null)
				{	//Close file
					classListFile.close();	
				}
				
			}
		}
		catch (IOException cf)
		{	System.err.println("Something went wrong: " + cf);			
		}
	
	//Exit from the program
	System.exit(0);
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae)
	{	//Test to determine which button has been activated
		if (ae.getSource() == addButton)//add class button has been clicked
		{	//Check if there is an available start time to add a class to
			if (classList.getNumClasses() < MAX_TIME_SLOTS)
			{	processAdding();//start time is available, proceed
			}
			else //no start time available, no further processing
			{	JOptionPane.showMessageDialog(null, "There are no avaialable time slots for adding a class.\n"
					+ "Please carefully check the timetable and delete a class before trying this again", "Error", JOptionPane.ERROR_MESSAGE);
				clearTextFields();
			}
		}
		else if (ae.getSource() == deleteButton)//delete class button has been clicked
			{	//Check if id field is empty, if so, display a warning
				if (idIn.getText().isEmpty())
				{	JOptionPane.showMessageDialog(null, "ID field is empty...., you figure out the next step:)", "Error", JOptionPane.ERROR_MESSAGE);
					clearTextFields();
				}
				else
				{	processDeletion();
				}
			}
			else if (ae.getSource() == attendanceButton)//view attendance button has been clicked
				{	//Call method to display attendance report window
					displayReport();
				}
				else if (ae.getSource() == closeButton)//save and exit button has been clicked
					{	//Call method to create output file and exit program
						processSaveAndClose();
					}
	}
	
	/**Method to check if id exists
	 * 
	 * @return whether a matching classId was found
	 */
	private boolean checkId()
	{	//Store user input in a local variable
		String addClassId = idIn.getText().trim();
			
		//Search class ids
		if (classList.searchClassId(addClassId) >= 0)
		{	return true;//Classid already exists			
		}
		else
		{	return false;//Classid doesnt exist
		}
	}
	
	
	/**Method to clear all textfields
	 */
	private void clearTextFields()
	{	idIn.setText("");//classId textfield
		classIn.setText("");//class name textfield
		tutorIn.setText("");//tutor name textfield		
	}
}
