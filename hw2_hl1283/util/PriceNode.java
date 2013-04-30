package hw2_hl1283.util;

import org.joda.time.DateTime;

/**
 * A <code>PriceNode</code> is a class contains the price of option and corresponding datetime.
 * @author liuhao
 *
 */
public class PriceNode {
	/** The <code>DateTime</code> time of price node*/
	private DateTime time;
	/** The stock price of option in the time node */
	private double price;
	
	/**
	 *  Construct the <code>PriceNode</code>.
	 * @param time The <code>DateTime</code> time of price node
	 * @param price The stock price of option in the time node
	 */
	public PriceNode(DateTime time, double price){
		this.time = time;
		this.price = price;
	}
	
	
	/**
	 * @return the time
	 */
	public DateTime getTime() {
		return time;
	}


	/**
	 * @param time the time to set
	 */
	public void setTime(DateTime time) {
		this.time = time;
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}




}
