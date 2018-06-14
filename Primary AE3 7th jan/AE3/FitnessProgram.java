import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram
{	//Instance variables
	
	/*Class constants for maximum number of classes ie 
	 * max number of FitnessClass objects in array
	 * to subtract 9 from class start time and
	 * for maximum number of weeks for attendance data
	 */
	private static final int MAX_CLASSES = 7;
	private static final int EARLIEST_TIME = 9;
	private static final int NUM_WEEKS = 5;
	
	//Arrays (list) of FitnessClass objects
	private FitnessClass [] classes;//primary array
	private FitnessClass[] sortedMeanAttendances = new FitnessClass[MAX_CLASSES];//secondary array for attendance report
	
	/*Integer to maintain count of FitnessClass objects in classes array*/
	private int numFitnessObjects = 0;
	
	/**Default constructor, i.e. no parameters as input
	 * instantiates the array of FitnessClass objects
	 */
    public FitnessProgram()
    {	//Instantiate FitnessClass array to store list of classes
    	classes = new FitnessClass[MAX_CLASSES];    	
    }
    
    
    /**Accessor method to find the first free timeslot
     * for a class to be added, or -1 if not found
     * @return integer freeTime
     */
    public int findAvailableTime()
    {	int freeTime = 0;
    	boolean slotFound = false;
    	
    	//Loop to search for null or empty entries in FitnessClass array
    	while (!slotFound && freeTime <= classes.length)
    	{	if (classes[freeTime] == null)
    		{	slotFound = true;    		
    		}
    		else
    		{	freeTime++;    		
    		}
    	}
    	
    	if (slotFound)//there is an empty slot, return index plus 9 (this equals the start time free)
    	{	freeTime += EARLIEST_TIME;    		
    	}
    	
    	return freeTime;
    }
    
    /**Modifier method to add FitnessClass object to array, needs to
     * take FitnessClass string and start time as parameters (from GUI)
     * to pass to the fitnessclass object and to determine which position in the
     * FitnessClass array to store it in, ordered by start time
     * @param integer startTime between 9 and 15
     * @param classDescription string containing class data
     */
    public void addClass(int startTime, String classDescription)
    {	//Instantiate FitnessClass object and store in array at index according to start time
    	classes[startTime-EARLIEST_TIME] = new FitnessClass(classDescription);
    	
    	//Increment object counter
    	numFitnessObjects++;    	
    }
    
    /**Method to return the number of classes in list
     * 
     * @return integer with count of number of classes
     */
    public int getNumClasses()
    {	return numFitnessObjects;    	
    }
    
    /*Method to return a fitnessclass object with that start time
     * input parameter is an integer between 9 and 15
     */
    public FitnessClass getClass(int classTime)
    {	return classes[classTime-EARLIEST_TIME];    	
    }
    
    /**This method sets array entry to null, for deletion of that class
     * 
     * @param string with only a class Id
     */
    public void setClassNull(String classId)
    {	//Search class list to find index of class with matching id
    	int deleteClassIndex = searchClassId(classId);
    	
    	//set array entry to null
    	classes[deleteClassIndex] = null;
    	
    	//Increment class counter by -1
    	numFitnessObjects--;
    }
    
    /**Method to pass attendance data to a fitnessclass object
     *@param a string with a class Id and five integers 
     * with a space delimiter between each one
     */
    public void addAttendances(String data)
    {	try
    	{	//Get the array index of the matching class
    		String classId = data.substring(0, 3);
    		int classIndex = searchClassId(classId);
    	
    		//Create integer array from integers in string
    		int [] attendances = new int[NUM_WEEKS];
    	
    		//Split string using alphabetic characters and spaces as delimiters (copied from lecture 11 by David Manlove)
    		String [] attendanceIntegers = data.split("[ a-zA-Z]+");
    	
    		//Loop through array and set attendance integers
    		for (int attendanceIndex = 0; attendanceIndex < NUM_WEEKS; attendanceIndex++)
    		{	//Discard first integer in string array as this is from the classID.
    			attendances[attendanceIndex] = Integer.parseInt(attendanceIntegers[(attendanceIndex + 2)]);//+2 to skip first token
    		}
    	
    	
    	//Pass integer array to the appropriate fitnessclass in the fitnessclass array
    	classes[classIndex].setAttendances(attendances);
    	}
    
    	catch (ArrayIndexOutOfBoundsException ai)
    	{	System.err.println("Something was wrong with the input: " + ai);    		
    	}
    }
    
  
  /**Search through the array to find the index of the fitness class
   * 
   * @param a string with a class id
   * @return either the class array index or -1 if not found
   */
    public int searchClassId(String id)
    {	//Search the class ids
    	boolean idFound = false;
    	int classIndex = 0;
    	while (!idFound && classIndex < MAX_CLASSES)
    	{	//Order of nested if statements important here to avoid nullpointerexceptions.
    		if (classes[classIndex] == null)
			{	//Increment array index counter if array entry is null
				classIndex++;  
    		}    		
    		else if	(classes[classIndex].getClassId().equals(id)) //Test for matching class id
    			{	//Set boolean to break out of while loop
    				idFound = true; 		
    			}    			
    			else
    			{	//Increment if the classId doesnt match
    				classIndex++;    			
    			}
    	}
    	
    	//Test to see if id was found
    	if (idFound)
    	{//Return the FitnessClass with the matching id
    		return classIndex;
    	}
    	else
    	{	return -1;    		
    	}
    	
    }
    
    /**Method to sort class list by mean attendance
     * 
     */
    private void sortClasses()
    {  	//Instantiate/reset sorting array
    	sortedMeanAttendances = new FitnessClass[MAX_CLASSES];
    	
    	//Integer to count null entries (i.e. missing classes in 'classes' array of fitnessclass objects
    	int nullClassCount = 0;
    	
    	//Integer to keep track of index position in array to be sorted on attendance means
    	int sortArrayCounter = 0;
    	
    	//Loop to go through each array index in the 'classes' array
    	for (int i = 0; i < classes.length; i++)
    	{	//Test if the array index is empty
    		if (classes[i] == null)
    		{	nullClassCount++; //increment the null entry counter    		
    		}
    	
    		else
    		{	sortedMeanAttendances[sortArrayCounter] = classes[i]; //otherwise copy the first non-null entry to the array for sorting   
    			/*Increment the counter that is keeping track of where the next empty position is in the sorting array,
    			 * this ensures null entries always end up at the end of this array
    			 */
				sortArrayCounter++;
    		}
    	}
    	
    	//Create a new array with a length shorter by the number of null entries (nullClassCount) than the maximum (7)
    	FitnessClass[] smallArray = new FitnessClass[MAX_CLASSES - nullClassCount];
    	
    	//Copy the array entries for sorting across to the new shorter array, starting at the beginning, ensuring null entries are removed
    	System.arraycopy(sortedMeanAttendances, 0, smallArray, 0, MAX_CLASSES-nullClassCount);
    	
    	//shallow copy the old array to the smaller one, smaller array disappears (pointer points to nothing)
    	sortedMeanAttendances = smallArray;
    	
    	//Sort attendance array by non-increasing order of mean attendance (uses compareTo method in fitnessclass)
    	Arrays.sort(sortedMeanAttendances);
    }
    
    /**Method to return formatted string with attendance report
     * 
     * @return String with attendance report
     */
    public String getMeanAttendanceReport()
    {	//Create sorted attendance array
    	sortClasses();
    	
    	/*create formatted string with fitnessclass objects
    	 * and mean attendances for reportframe window
    	 */
    	String attendanceReport = " " + String.format("%-5s %-16s %-16s %14s %29s %n",
    			"Id", "Class", "Tutor", "Attendances", "Average Attendance");
    	attendanceReport += "======================================================="
    			+ "===============================" + String.format("%n");//First part of report, this will not change
    	
    	//Local variables for creating attendance string
    	//mean for each fitnessclass
    	double classMean = 0.0;
    	//Store sum of means
    	double meanSum = 0.0;
    	//store overall mean
    	double overallMean = 0.0;
    	//Strings to store fitnessclass instance variables
    	String idString = "";
    	String classNameString = "";
    	String tutorNameString = "";
    	String attendanceList = "";
    	String attendanceAverage = "";
    	
    	//Loop to create attendance report string
    	for (int i = 0; i < sortedMeanAttendances.length; i++)
    	{	//Get and store values from fitnessclass objects
    		idString = sortedMeanAttendances[i].getClassId();
    		classNameString = sortedMeanAttendances[i].getClassName();
    		tutorNameString = sortedMeanAttendances[i].getTutorName();
    		attendanceList = sortedMeanAttendances[i].getAttendances();
    		classMean = sortedMeanAttendances[i].getMeanAttendance();
    		attendanceAverage = String.format("%4.2f", classMean);
    		
    		//Store sum of attendances
    		meanSum += classMean;
    		    		
    		//Concatenate local variables to report string
    		attendanceReport += " " + String.format("%-5s %-16s %-16s %-10s %15s %n", idString, classNameString,
    				tutorNameString, attendanceList, attendanceAverage);
    	}   	
    	
    	//calculate overall mean attendance
    	overallMean = meanSum/numFitnessObjects;
    	
    	//Add overall mean to report string
    	attendanceReport += String.format("%n %65s %4.2f", "Overall Average:     ", overallMean);
    	return attendanceReport;    	
    }  
    
    /**Helper method to return formatted string with each class instance variable
     * 
     * @return string for writing to classesout text file
     */
    public String getClassesOutFile()
    {	//Instantiate local string to store formatted report
    	String classVariables = "";
    	
    	//Loop to build string for report
    	for (int outIndex = 0; outIndex < classes.length; outIndex++)
    	{	if (classes[outIndex] != null)//obtain variables only from existing classes
    		{	classVariables += "" + classes[outIndex].getClassId() + " " + classes[outIndex].getClassName() + " " +
    				classes[outIndex].getTutorName() + " " + classes[outIndex].getStartTime() + String.format("%n");
    		}
    	}
    	
    	return classVariables;    	
    }
}
