import javax.swing.JOptionPane;

public class AssEx1
{	public static void main(String[] args) 
	{	// Create input dialog boxes to get customer name and balance
		String custName = null;
		
		/*Try-catch block for nullpointer exceptions (i.e. press cancel or cross off) between these two,
		 * the name dialog box works as specified, but i don't completely understand how they implement the functionality
		 */
		try
		{	custName = JOptionPane.showInputDialog("Please tell me your name: ").trim();
		}
		catch (NullPointerException m)
		{	System.exit(0);
		}
		
		//Test to determine if a non-empty name was input
		if (custName.isEmpty())
		{	System.exit(0);
		}

		// Ask for initial balance, catch exceptions until user exits or enters
		// a valid number
		Double initialBalance = null;

		while (initialBalance == null)
		{	try 
			{	initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Please tell me your current balance, using a '-' sign if you are in credit: "));
			}
				
			//Catch non-double input in dialog box
			catch (NumberFormatException e)
			{	JOptionPane.showMessageDialog(null, "Please enter a numeric balance", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			//Catch section for empty balance dialog
			catch (NullPointerException n)
			{	System.exit(0);
			}
		}

			// Create a CustomerAccount object to store the input values from the JOptionPanes
			CustomerAccount nameBal = new CustomerAccount(custName, initialBalance);

			// Instantiate the GUI, passing CustomerAccount object as parameter in constructor
			LWMGUI transaction = new LWMGUI(nameBal);
			transaction.setVisible(true);
	}		
}

