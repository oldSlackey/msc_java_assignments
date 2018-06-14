import java.awt.*;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame
{	//Instance variables
	private FitnessProgram attendanceList;
	private JTextArea attendanceReport;
	private JFrame reportFrame;
	
	//Constants for JTextArea and JFrame sizes
	private final int FRAME_WIDTH = 750;
	private final int FRAME_HEIGHT = 350;
	private final int ROWS = 100;
	private final int COLUMNS = 300;
	
	/**Constructor for attendance report
	 * 
	 * @param classList is a fitnessclass object
	 */
	public ReportFrame(FitnessProgram classList)
	{	//Initialise fitnessprogram object
		attendanceList = classList;
		
		//Layout JTextArea
		layoutAttendanceWindow();
		formatAttendanceData();		
	}
	
	/**Method to layout JTextArea and display attendance report
	 * 
	 */
	private void layoutAttendanceWindow()
	{	//Instantiate JFrame to contain JTextArea
		reportFrame = new JFrame();
		reportFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		//Set location to prevent obscuring GUI
		reportFrame.setLocation(450, 300);
		reportFrame.setVisible(true);
		reportFrame.setTitle("Attendance Report - all arithmetic means");
		
		//Set close behaviour (just remove window, dont terminate program)
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Instantiate JTextArea for attendance report
		attendanceReport = new JTextArea(ROWS, COLUMNS);
		
		//Use this font for consistency and correct formatting (Monospaced font)
		attendanceReport.setFont(new Font("Courier", Font.PLAIN, 14));
		
		
		//Prevent user from editing attendance window
		attendanceReport.setEditable(false);
		
		//Add JTextArea to JFrame
		reportFrame.add(attendanceReport, BorderLayout.CENTER);		
		
	}
	
	/**method to get attendance report
	 * 
	 */
	private void formatAttendanceData()
	{	//FitnessProgram object contains method to return formatted attendance string
		attendanceReport.setText(attendanceList.getMeanAttendanceReport());
	}
}

