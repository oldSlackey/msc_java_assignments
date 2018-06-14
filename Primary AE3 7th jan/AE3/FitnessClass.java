import java.util.Arrays;

/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass>

{    //Class constant for number of weeks to record attendances
	private static final int NUM_WEEKS = 5;
	
	//More instance variables
	private String classId, className, tutorName;
	private int startTime;
	
	//array to store attendances for each class
	private int[] attendances;
	
	/**default constructor for FitnessProgram array instantiation
	 * 
	 */
	public FitnessClass()
	{	//initialise instance variables
		this(""); /*I saw this solution in tutorial 8 solutions
		 number 2, for default constructor in bank account class*/
	}
	
	
	/**non-default Constructor
	 * 
	 * @param classesInString parameter from FitnessProgram addClass method
	 */
	public FitnessClass(String classesIn)
	{	//Need to read data from String, use split (space-delimited) method and process each token
		String[] classesTokens = classesIn.split(" ");
		
		/*I used this classes 'set' methods in the constructor here, not sure if its bad practice....
		 * I could have equally just initialised the instance variables using assignment operators 
		 * (.equals for strings and = for the integer), however, I think I saw this in one of the tutorial or lecture solutions
		 */
		setClassId(classesTokens[0]);
		setClassName(classesTokens[1]);
		setTutorName(classesTokens[2]);
		setStartTime(Integer.parseInt(classesTokens[3])); //Is using 'magic numbers' acceptable here?	
		
		/*Instantiate the attendance array here, ready to store attendance
		 * data
		 */
		setAttendances(new int[NUM_WEEKS]);
	}
	
	/**Compare average (arithmetic mean) attendances for sorting 
	 * @param other is a fitnessclass object
	 * @return an integer depending on the outcome of comparison
	 */
    public int compareTo(FitnessClass other)
    {	try
    	{	if (this.calculateMeanAttendance() < other.calculateMeanAttendance())
    		{	return 1;    	
    		}
    		else if (this.calculateMeanAttendance() == other.calculateMeanAttendance())
    			{	return 0;    	
    			}
    			else
    			{	return -1;    			
    			}
    	}
    	catch (NullPointerException ct)
    	{	System.err.println("Not enough classes bud..." + ct);
    		return 0;
    	}
    }
    
    /**accessor and modifier methods for each instance variable
     * 
     * @return classId string
     */
	public String getClassId()
	{ return classId;
	}
	
	/**Modifier for classId
	 * 
	 * @param classIdin string with classId
	 */
	public void setClassId(String classIdin)
	{	this.classId = classIdin;
	}
	
	/**Accessor for ClassName
	 * 
	 * @return className string
	 */
	public String getClassName()
	{	return className;
	}
	
	/**Modifier for class name
	 * 
	 * @param classNameIn string with class name
	 */
	public void setClassName(String classNameIn)
	{	this.className = classNameIn;
	}
	
	/**Accessor for tutor name
	 * 
	 * @return string with tutor name
	 */
	public String getTutorName()
	{	return tutorName;
	}
	
	/**Modifier for tutor name
	 * 
	 * @param tutorNameIn string with tutor name
	 */
	public void setTutorName(String tutorNameIn)
	{	this.tutorName = tutorNameIn;
	}
	
	/**Accessor for class start time#
	 * 
	 * @return startTime integer between 9 and 15 inclusive
	 */
	public int getStartTime()
	{	return startTime;
	}
	
	/**Modifier for class start time
	 * 
	 * @param startTimeIn integer between 9 and 15 inclusive
	 */
	public void setStartTime(int startTimeIn)
	{	this.startTime = startTimeIn;
	}

	/**Return a string containing list of attendance integers
	 * 
	 * @return attendanceList string with five integers separated by spaces
	 */
	public String getAttendances()
	{	String attendanceList = "";
		
		///Loop through attendance array
		for (int i = 0; i < attendances.length; i++)
		//Cast, format and concatenate attendance integers to string
		{	attendanceList += String.format("%-4s", "" + attendances[i]);
		}
		
		//Return string
		return attendanceList;
	}
	
	/**Modifier to set attendance data
	 * 
	 * @param attendanceArray array of integers representing attendances
	 */
	public void setAttendances(int[] attendanceArray)
	{	this.attendances = attendanceArray;
	}
	
	/**Accessor to return the average(mean) attendance for this object
	 * 
	 * @return double representing mean attendance
	 */
	public double getMeanAttendance()
	{	double mean = calculateMeanAttendance();
		return mean;		
	}
	
	/**helper method to calculate mean attendance
	 * for comparisons of mean attendance
	 * 
	 * @return a double representing the mean attendance
	 */
	private double calculateMeanAttendance()
	{	//Generalised for loop to sum integers from attendance array
		int attendanceTotal = 0;
		
		//From beginning to end of array
		for (int meanIndex: attendances)
		{	//Sum the integers in the attendance array
			attendanceTotal = attendanceTotal + meanIndex;//[meanIndex];
		}
		
		/*Calculate and return the mean of the elements in the attendance array
		*we know these will always be filled so no error checking here
		*/		 
		return (double) attendanceTotal/attendances.length;		
	}
}
