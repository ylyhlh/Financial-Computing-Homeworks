package hw2_hl1283.PayOut;

import hw2_hl1283.StockPath.StockPath;
import hw2_hl1283.util.Option;
import hw2_hl1283.util.PriceNode;

import java.util.List;

/**
 * Implement the Europe payout.
 * Payout is the positive value of the final price minus the strick price.
 * @see PayOut
 */
public class CallPayOut implements PayOut {

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
	 * Construct the <code>CallPayOut</code> object with given strick price.
	 * @param strickPrice The strick price used to calculate the payout.
	 */
	public CallPayOut(double strickPrice) {
		this.strickPrice = strickPrice;
	}
	public CallPayOut(Option option) {
		this.strickPrice = option.getStrickPrice();
	}
	
	/**
	 * Payout is the positive value of the final price minus the strick price.
	 */
	@Override
	public double getPayout(StockPath path) {
		List<PriceNode> price =  path.getPrices();
		return Math.max(price.get(price.size()-1).getPrice()-strickPrice,0);
	}

	
}
