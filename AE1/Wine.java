public class Wine

//Create instance variables
{	private String wineName = new String();
	private double unitPrice = 0.0;
	private int unitQuantity = 0;

	// Create constructor
	public Wine(String wName, double bPrice, int bQuantity)
	{	wineName = wName;
		unitPrice = bPrice;
		unitQuantity = bQuantity;
	}

	// Create accessor methods
	public String getName()
	{	return wineName;
	}

	public double getBottlePrice()
	{	return unitPrice;
	}

	public int getNumBottles()
	{	return unitQuantity;
	}
}
