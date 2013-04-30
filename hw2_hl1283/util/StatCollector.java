package hw2_hl1283.util;

import java.util.ArrayList;

/**
 * A statistic collector to store a double array and carry the mean of standard deviation of this array.
 * @author liuhao
 *
 */
public class StatCollector {
	
	private ArrayList<Double> array;
	/** The mean of array */
	private double mean = 0;
	/** The standard deviation of array */
	private double stdVar = 0;
	/** The second order moment */
	private double meanSq = 0;

	public StatCollector() {
		this.array = new ArrayList<Double>();
	}
	
	/**
	 * Add new element into the <code>StatCollector</code>
	 * @param x new element.
	 */
	public void add(double x) {
		int n = array.size()+1;
		array.add(new Double(x));
		mean =(n-1.0)/n*mean + x/n;
		meanSq =(n-1.0)/n*meanSq + x*x/n;
		stdVar = Math.sqrt(meanSq-mean*mean);
	}

	
	/**
	 * @return the mean
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * @return the stdVar
	 */
	public double getStdVar() {
		return stdVar;
	}

}
