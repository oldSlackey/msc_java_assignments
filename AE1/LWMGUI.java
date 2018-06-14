/*Import the required java classes for constructing
 * and managing a GUI
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

//Tell java we will be adding to JFrame class methods with our own methods

public class LWMGUI extends JFrame implements ActionListener

{	// Instance variables
	private JButton pSaleButton, pReturnButton;
	private JLabel label;
	private JTextField wineNameInput, quantityBottlesInput, bottlePriceInput, transAmount, currBalField;
	private String wineName;
	private double price;
	private double initialBalance;
	private int quantity;
	public Wine winTrans;
	public CustomerAccount saleReturn;
	private boolean validData;

	// Constructor for creating GUI with correct details
	public LWMGUI(CustomerAccount nameBal)
	{	// Store CustomerAccount object
		saleReturn = nameBal;
		
		// Get variables from CustomerAccount object
		String customerName = saleReturn.getName().trim();
		initialBalance = saleReturn.getInitBal();

		// Create JFrame for adding layout components to
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		setLocation(450, 300);
		setTitle("Lilybank Wine Merchants: " + customerName);

		// Call helper methods for layout
		layoutGuiTop();
		layoutGuiMiddle();
		layoutGuiBottom();
	}

	// Helper method (layoutGuiTop) to layout components at top of JFrame window
	private void layoutGuiTop()
	{	// Create JPanel and JTextField components
		JPanel northArea = new JPanel();
		northArea.setBackground(Color.red);
		wineNameInput = new JTextField(20);
		quantityBottlesInput = new JTextField(10);
		bottlePriceInput = new JTextField(10);

		// Wine name JLabel
		JLabel wineNameLabel = new JLabel("Wine Name:");
		wineNameLabel.setFont(new Font("Sans_Serif", Font.PLAIN, 20));

		// Bottle quantity JLabel
		JLabel quantityBottlesLabel = new JLabel("Quantity:");
		quantityBottlesLabel.setFont(new Font("Sans_Serif", Font.PLAIN, 20));

		// Bottle price JLabel
		JLabel bottlePriceLabel = new JLabel("Price: £");
		bottlePriceLabel.setFont(new Font("Sans_Serif", Font.PLAIN, 20));

		// Add JPanel to JFrame NORTH
		this.add(northArea, BorderLayout.NORTH);

		// Add components to JPanel according to flow layout
		northArea.add(wineNameLabel);
		northArea.add(wineNameInput);
		northArea.add(quantityBottlesLabel);
		northArea.add(quantityBottlesInput);
		northArea.add(bottlePriceLabel);
		northArea.add(bottlePriceInput);
	}

	// Helper method layoutGuiMiddle to layout components in middle of JFrame window
	private void layoutGuiMiddle()
	{	// Create JPanel to hold two nested JPanels with box layout (central column)
		JPanel centerArea = new JPanel();
		centerArea.setLayout(new GridLayout(2,1));

		// Create JPanels for nesting
		JPanel centerArea1 = new JPanel();
		JPanel centerArea2 = new JPanel();

		// Create sales button and add actionListener
		pSaleButton = new JButton("Process Sale");
		pSaleButton.addActionListener(this);
		centerArea1.add(pSaleButton);
		
		//Create returns button and add actionlistener
		pReturnButton = new JButton("Process Return");
		pReturnButton.addActionListener(this);
		centerArea1.add(pReturnButton);

		// Add JPanel to JFrame CENTER
		this.add(centerArea, BorderLayout.CENTER);
		centerArea.add(centerArea1, BorderLayout.NORTH);
		centerArea.add(centerArea2, BorderLayout.SOUTH);

		// Add display label to JPanels
		label = new JLabel("");
		label.setFont(new Font("Sans_Serif", Font.PLAIN, 20));
		centerArea2.add(label, BorderLayout.SOUTH);
	}

	// Helper method layoutGuiBottom to layout components to bottom of JFrame
	private void layoutGuiBottom()
	{	// Create JPanel
		JPanel southArea = new JPanel();
		southArea.setBackground(Color.red);

		// JLabel for textfield displaying amount of most recent transaction
		JLabel transactionAmount = new JLabel("Amount of transaction: £");
		transactionAmount.setFont(new Font("Sans_Serif", Font.PLAIN, 20));

		/* JLabel for textfield displaying account balance, including most
		* recent transaction
		*/
		JLabel currBalance = new JLabel("Current Balance: £");
		currBalance.setFont(new Font("Sans_Serif", Font.PLAIN, 20));

		// JTextfields for recent transaction and current balance both uneditable by user
		transAmount = new JTextField(7);
		transAmount.setEditable(false);

		// Test to determine whether customer is in credit and format JTextfield accordingly
		if (initialBalance < 0.0)
		{	String minusBal = String.format("%7.2f", (initialBalance/-1));
			currBalField = new JTextField("" + minusBal + "CR", 9);
		}
		else
		{	String plusBal = String.format("%7.2f", initialBalance);
			currBalField = new JTextField("" + plusBal, 9);
		}
		currBalField.setEditable(false);

		// Add JPanel to JFrame SOUTH
		this.add(southArea, BorderLayout.SOUTH);

		// Add components to JPanel in SOUTH
		southArea.add(transactionAmount);
		southArea.add(transAmount);
		southArea.add(currBalance);
		southArea.add(currBalField);
	}
	
	//Action performed to tell the program what to do depending on which button was pressed
	public void actionPerformed(ActionEvent e)
	{	if (e.getSource() == pSaleButton)
			dataCaptureSales();
		else if (e.getSource() == pReturnButton)
			dataCaptureReturns();
	}

	/* Capture values entered into textfields as a sale and
	 * use captured values as parameters in wine object
	 */
	private void dataCapture()
	{	/*Obtain wine name from texfield. Set boolean value to true
		*this boolean will be used for data entry validation
		*/
			wineName = wineNameInput.getText().trim();
			validData = true;
			
		//Obtain bottle price and check if a double was input
		try
		{	price = Double.parseDouble(bottlePriceInput.getText());
		}
		catch (NumberFormatException p)
		{	clearTextFields();
			validData = false;
		}
		
		//Obtain quantity of bottles and test whether an integer was entered	
		try
		{	quantity = Integer.parseInt(quantityBottlesInput.getText());
		}
			
		catch (NumberFormatException q)
		{	clearTextFields();
			validData = false;
		}
		
		/*If statement to test for non-valid data that wont throw an exception
		 * else statement is not required here
		 */
		if (quantity < 0 || price < 0.0 || wineName.isEmpty())
		{	validData = false;
		}
	}
	
	//Helper method to display error message in the event of incorrect data entry
	private void invalidData()
	{	JOptionPane.showMessageDialog(null, "Please enter valid data", "Error", JOptionPane.ERROR_MESSAGE);
		quantity = 0;
		price = 0.0;
		wineName = "";
		clearTextFields();	
	}
		
	//Helper method to obtain values from a sales transaction
	private void dataCaptureSales()
	{	//Call datacapture helper method
		dataCapture();
		
		//Test boolean here for valid data entry to prevent incorrect processing
		if (!validData)
		{	invalidData();
		}
		else
		{ saleMethod();
		}			
	}	
	
	//Helper method to obtain values from a return transaction
	private void dataCaptureReturns()
	{	//Call datacapture helper method
		dataCapture();
				
		//Test here for valid data to prevent invalid processing
		if (!validData)
		{	invalidData();
		}
		else
		{ returnMethod();
		}				
	}	
		
	//Helper method to display sales
	private void saleMethod()
	{	// Create wine object, pass to CustomerAccount as a sale
		winTrans = new Wine(wineName, price, quantity);
		saleReturn.setWineObj(winTrans);
		
		//Get balance from CustomerAccount object
		double saleBalance = saleReturn.getSaleBal();

		// Test to see if balance is in credit (merchant owes customer)
		if (saleBalance < 0.0) 
		{	// Format and display credit balances
			String sBalanceMinus = String.format("%7.2f", saleBalance / -1);
			currBalField.setText("" + sBalanceMinus + "CR");
		}
		else
		{	// Format and display debit balances
			String sBalancePlus = String.format("%7.2f", saleBalance);
			currBalField.setText("" + sBalancePlus);
		}

		// Format and display most recent sale cost
		String transAmt = String.format("%7.2f", saleReturn.getSaleCost());
		transAmount.setText("" + transAmt);

		// Display name of wine bought and clear textfields
		label.setText("Wine purchased: " + wineName);
		clearTextFields();
	}
	
	//Helper method to display returns
	private void returnMethod()
	{	//Set wine object in CustomerAccount as a sale
		winTrans = new Wine(wineName, price, quantity);
		saleReturn.setWineObj(winTrans);

		double returnBalance = saleReturn.getReturnBal();

		/*
		 * Display transaction amount and current balance (from CustomerAccount
		 * method) Test to see if balance is in credit (merchant owes customer)
		 * and format accordingly
		 */
		if (returnBalance < 0.0)
		{	String sBalanceMinus = String.format("%7.2f", returnBalance / -1);
			currBalField.setText("" + sBalanceMinus + "CR");
		}
		else
		{	String sBalancePlus = String.format("%7.2f", returnBalance);
			currBalField.setText("" + sBalancePlus);
		}

		// Format and display return transaction amount
		String transAmt = String.format("%7.2f", saleReturn.getReturnCost());
		transAmount.setText("" + transAmt);

		// Display name of wine returned and clear textfields
		label.setText("Wine returned: " + wineName);
		clearTextFields();
	}
	
	//Helper method to clear textfields
	private void clearTextFields()
	{	wineNameInput.setText("");
		quantityBottlesInput.setText("");
		bottlePriceInput.setText("");
	}
}
