package hw2_hl1283.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
/**
 * <code>Option</code> class contains all information one Option has:
 * <code>initialPrice</code>,
 * <code>initialTime</code>,
 * <code>interestRate</code>,
 * <code>volatility</code>,
 * <code>strickPrice</code>,
 * <code>period</code>.
 * All these attitudes have setter and getter.
 * @author Hao Liu
 *
 */
public class Option {
	/** The Name of option */
	private String name;
	/** The start price */
	private double initialPrice;
	/** The start date */
	private DateTime initialTime;
	/** The expiration date */
	private DateTime expirationTime;
	/** The interest rate */
	private double interestRate;
	/** The volatility of option stock price by day */
	private double volatility;
	/** The strick price of option */
	private double strickPrice;
	/** The period between <code>initialTime</code> and <code>expirationTime</code> */
	private Period period;
	/** The payout type*/
	private String payOutType;


	public Option() {
		
	}
	/**
	 * Construct the <code>Option</code> with start date and period.
	 * @param initialPrice The start price
	 * @param initialTime The start date
	 * @param period The period between <code>initialTime</code> and <code>expirationTime</code>
	 * @param interestRate The interest rate
	 * @param volatility The volatility of option stock price by day
	 * @param strickPrice  The strick price of option
	 */
	public Option(String name, String payOutType, double initialPrice, DateTime initialTime, Period period, double interestRate, double volatility, double strickPrice) {
		this.setInitialPrice(initialPrice);
		this.setInitialTime(initialTime);
		this.setInterestRate(interestRate);
		this.setVolatility(volatility);
		this.setStrickPrice(strickPrice);
		this.setPeriod(period);
		this.setExpirationTime(initialTime.plus(getPeriod()));
		this.setName(name);
		this.setPayOutType(payOutType);
	}
	/**
	 * Construct the <code>Option</code> with start and expiration date.
	 * @param initialPrice The start price
	 * @param initialTime The start date
	 * @param expirationTime The expiration date
	 * @param interestRate The interest rate
	 * @param volatility The volatility of option stock price by day
	 * @param strickPrice  The strick price of option
	 */
	public Option(double initialPrice, DateTime initialTime, DateTime expirationTime, double interestRate, double volatility, double strickPrice) {
		this.setInitialPrice(initialPrice);
		this.setInitialTime(initialTime);
		this.setInterestRate(interestRate);
		this.setVolatility(volatility);
		this.setStrickPrice(strickPrice);
		this.setExpirationTime(expirationTime);
		this.setPeriod(new Period(initialTime,expirationTime));
		//???throw exception
	}

	/**
	 * @return the initialPrice
	 */
	public double getInitialPrice() {
		return initialPrice;
	}

	/**
	 * @param initialPrice the initialPrice to set
	 */
	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}

	/**
	 * @return the initialTime
	 */
	public DateTime getInitialTime() {
		return initialTime;
	}

	/**
	 * @param initialTime the initialTime to set
	 */
	public void setInitialTime(DateTime initialTime) {
		this.initialTime = initialTime;
	}

	/**
	 * @return the expirationTime
	 */
	public DateTime getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(DateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the volatility
	 */
	public double getVolatility() {
		return volatility;
	}

	/**
	 * @param volatility the volatility to set
	 */
	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}

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
	 * @return the period
	 */
	public Period getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Period period) {
		this.period = period;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayOutType() {
		return payOutType;
	}
	public void setPayOutType(String payOutType) {
		this.payOutType = payOutType;
	}
}
