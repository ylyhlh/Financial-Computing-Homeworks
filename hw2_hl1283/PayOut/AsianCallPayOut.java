
package hw2_hl1283.PayOut;
import hw2_hl1283.StockPath.StockPath;
import hw2_hl1283.util.Option;
import hw2_hl1283.util.PriceNode;

import java.util.List;

/**
 * Implement the Asian payout.
 * Payout is the positive value of the average price minus the strick price.
 * @see PayOut
 */
public class AsianCallPayOut implements PayOut {
	
	/** The strick price of option used to calculate the payout. */
	private double strickPrice;
	
	/**
	 * @return the strickPrice
	 */
	public double getStrickPrice() {
		return strickPrice;
	}

	/**
	 * @param strickPrice the strickPrice to set
	 */
	public void setStrickPrice(double strickPrice) {
		this.strickPrice = strickPrice;
	}

	/**
	 * Construct the <code>AsianCallPayOut</code> object with given strick price.
	 * @param strickPrice The strick price used to calculate the payout.
	 */
	public AsianCallPayOut(double strickPrice) {
		this.strickPrice = strickPrice;
	}
	public AsianCallPayOut(Option option) {
		this.strickPrice = option.getStrickPrice();
	}
	
	/**
	 * Payout is the positive value of the average price minus the strick price.
	 */
	@Override 
	public double getPayout(StockPath path) {
		List<PriceNode> price =  path.getPrices();

		double ave = price.get(0).getPrice();  // sum of all the elements
	    for (int i=1; i<price.size(); i++) {
	         ave = i/(i+1.0)*ave + price.get(i).getPrice()/(i+1.0);
	    }
		return Math.max(ave- strickPrice,0);
	}

}
