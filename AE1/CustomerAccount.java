public class CustomerAccount
{	// Create instance variables
	private static final double serviceCharge = 1 - 0.2;
	private double costBottle = 0.0, currentBalance = 0.0;
	private int numBottles = 0, transactionCost = 0;
	private String custName = "";
	private Wine winTrans;

	// Constructor for customer name and initial balance. These values come from
	// JOptionPanes used only once each.
	public CustomerAccount(String name, double initBal)
	{	custName = name;
		currentBalance = initBal;
	}

	// Helper method to calculate sale balance, perhaps could have used another helper method...
	private void saleBalance()
	{	double saleBal = currentBalance	+ ((transactionCost / 100) + ((transactionCost % 100) / 100.0));
		currentBalance = saleBal;
	}

	// Helper method to calculate return balances
	private void returnBalance()
	{	double returnBal = currentBalance - ((transactionCost) / 100 + ((transactionCost) % 100) / 100.0)*serviceCharge;
		currentBalance = returnBal;
	}

	// Accessor methods

	// Return customer name and initial balance
	public String getName()
	{	return custName;
	}

	public double getInitBal()
	{	return currentBalance;
	}

	// Return total cost of most recent customer purchase
	public double getSaleCost()
	{	return ((transactionCost / 100) + ((transactionCost % 100) / 100.0));
	}

	// Return total cost of most recent customer return
	public double getReturnCost()
	{	return ((transactionCost) / 100 + ((transactionCost) % 100) / 100.0)*serviceCharge;
	}

	// Return current balance after a customer return
	public double getReturnBal()
	{	returnBalance();
		return currentBalance;
	}

	// Return current balance after a customer purchase
	public double getSaleBal()
	{	saleBalance();
		return currentBalance;

	}

	//Method to get Wine object as a parameter
	public void setWineObj(Wine newObj)
	{	this.winTrans = newObj;
		costBottle = winTrans.getBottlePrice();
		numBottles = winTrans.getNumBottles();
		transactionCost = (int) (costBottle * 100) * numBottles;
	}
}
