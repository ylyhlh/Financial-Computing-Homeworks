package hw2_hl1283.RandomVectorGenerator;


import org.apache.commons.math3.random.*;

/**
 * The class generate the uncorrelated normal random vector with <code>UncorrelatedRandomVectorGenerator</code>
 * 
 * @author liuhao
 * 
 * @see RandomVectorGenerator
 * @see org.apache.commons.math3.random.UncorrelatedRandomVectorGenerator
 */
public class NormalRandomVectorGenerator implements RandomVectorGenerator {
	
	/** The mean vector of normal distribution*/
	private UncorrelatedRandomVectorGenerator generator;
	/** The mean vector of normal distribution*/
	private double[] mean;
	/** The mean variance of normal distribution*/
	private double[] var;
	private int dimension;
	

	/**
	 * Construct the <code>NormalRandomVectorGenerator</code> new normal random vector with given dimension.
	 * @param dimension  The given dimension of random number to generate.
	 */
	public NormalRandomVectorGenerator(int dimension) {
		this(dimension, (int) System.currentTimeMillis());
	 }
	
	/**
	 * Construct the <code>NormalRandomVectorGenerator</code> new normal random vector with given dimension and seed.
	 * @param dimension The given dimension of random number to generate.
	 * @param seed The given seed to generate random number.
	 */
	public NormalRandomVectorGenerator(int dimension, int seed) {
		this.dimension = dimension;
		RandomGenerator rg = new JDKRandomGenerator();
		rg.setSeed(seed);  // Fixed seed means same results every time
		// Create a GassianRandomGenerator using rg as its source of randomness
		GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);
		// Create a CorrelatedRandomVectorGenerator using rawGenerator for the components
		this.generator = 
		    new  UncorrelatedRandomVectorGenerator(this.dimension, rawGenerator);
	}
	
	/**
	 * Construct the <code>NormalRandomVectorGenerator</code> new normal random vector with given mean, variance and seed.
	 * @param mean The given mean for normal distribution
	 * @param var The given variance for normal distribution
	 * @param seed The given seed to generate random number.
	 */
	public NormalRandomVectorGenerator(double[] mean, double[] var, int seed) {
		this.mean = mean;
		this.var = var;
		RandomGenerator rg = new JDKRandomGenerator();
		rg.setSeed(seed);  // Fixed seed means same results every time
		// Create a GassianRandomGenerator using rg as its source of randomness
		GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);

		// Create a CorrelatedRandomVectorGenerator using rawGenerator for the components
		this.generator = 
		    new  UncorrelatedRandomVectorGenerator(this.mean, this.var, rawGenerator);
	}
	
	
	@Override
	public double[] getVector() { 
		return generator.nextVector();
	}

}
