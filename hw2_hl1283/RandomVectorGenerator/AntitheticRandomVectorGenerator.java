package hw2_hl1283.RandomVectorGenerator;

	/**
	 * This is a decorator of <code>RandomVectorGenerator</code> for the antithetic trick of Monte Carlo simulation.
	 * @author liuhao
	 * @version 0.1
	 * 
	 * @see RandomVectorGenerator
	 */
	public class AntitheticRandomVectorGenerator implements RandomVectorGenerator {
		/** To store the given <code>RandomVectorGenerator</code>*/
		private RandomVectorGenerator generator;
		/** To indicate which step the generator in during the antithetic process */
		private Boolean flag = false;
		/** To store the vector in the last step*/
		private double[] oldVector;
		
		/**
		 * Construct <code>AntitheticRandomVectorGenerator</code> as a decorator of <code>RandomVectorGenerator</code>
		 * @param generator <code>RandomVectorGenerator</code> to generate random vectors
		 * @see RandomVectorGenerator
		 */
		public AntitheticRandomVectorGenerator(RandomVectorGenerator generator) {
			this.generator = generator;
		}
		
		@Override
		/**
		 * Generate a new random vector or return the opposite of <code>oldVector</code>
		 * @return The generated random vector
		 */
		public double[] getVector() { 
			if (flag) {
				flag = false;
				for (int i=0; i<oldVector.length; i++) {
					oldVector[i] *= -1;
				}
			} else {
				flag = true;
				oldVector = generator.getVector();	
			}
			return this.oldVector;	
		}

	}
