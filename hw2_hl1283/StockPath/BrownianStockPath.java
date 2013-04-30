package hw2_hl1283.StockPath;

import hw2_hl1283.RandomVectorGenerator.AntitheticRandomVectorGenerator;
import hw2_hl1283.RandomVectorGenerator.NormalRandomVectorGenerator;
import hw2_hl1283.RandomVectorGenerator.RandomVectorGenerator;
import hw2_hl1283.util.Option;
import hw2_hl1283.util.PriceNode;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
/**
 * This class is an implementation of <code>StockPath</code> to generate geometric brownian motion stock prices
 * @author liuhao
 * @version 0.1
 * 
 * @see StockPath
 * @see Option
 * @see RandomVectorGenerator
 * 
 */
public class BrownianStockPath implements StockPath {
	
	private RandomVectorGenerator generator;
	private Option option;
	private ArrayList<PriceNode> path;
	
	/**
	 * Construct the BrownianStockPath for <code>option</code> with <code>RandomVectorGenerator</code> <code>generator</code>
	 * @param option one <code>Option</code> that stores all information one option have.
	 * @param generator one <code>RandomVectorGenerator</code> to generate the random vector
	 * @see Option
	 * @see RandomVectorGenerator
	 */
	public BrownianStockPath (Option option, RandomVectorGenerator generator) {
		this.generator = generator;
		this.option = option;
		this.generateNewPath();
	}
	public BrownianStockPath (Option option) {
		RandomVectorGenerator normGenerator = new NormalRandomVectorGenerator(option.getPeriod().getDays());
		RandomVectorGenerator generator = new AntitheticRandomVectorGenerator(normGenerator);
		this.generator = generator;
		this.option = option;
		this.generateNewPath();
	}

	/**
	 * Generate geometric brownian motion stock prices.
	 */
	@Override
	public List<PriceNode> getPrices() {
		this.generateNewPath();
		return path;
	}
	
	/**
	 * Generate new random path.
	 */
	public void generateNewPath() {
		this.path = new ArrayList<PriceNode>();
		this.path.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		double[] eta = generator.getVector();
		double St = option.getInitialPrice();
		DateTime t = option.getInitialTime();
		for(int i=0; i<eta.length;i++) {
			t = t.plusDays(1);
			double volatility = option.getVolatility();
			St = St * Math.exp((option.getInterestRate() - volatility*volatility/2)+volatility*eta[i]);
			this.path.add(new PriceNode(t,St));
		}
	}
	
}
