package hw1_hl1283;
import java.util.HashMap;
/**
 * One implementation of <code>Portfolio</code> with <code>HashMap</code>
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Portfolio
 * @see PositionIter
 */
public class naivePortfolio implements Portfolio {
	
	/** The HashMap to store all positions */
	private HashMap<String,Position> positions;
	
	/**
	 * Create one <code>Portfolio</code> with <code>HashMap</code>
	 */
	public naivePortfolio() {
		positions = new HashMap<String,Position>();
	}
	
	/**
	 * Implementation of hw1_hl1283.Portfolio#newTrade(java.lang.String, int)
	 */
	public void newTrade(String symbol, int quantity) {
		if (quantity != 0) {
			
			Position position= positions.get(symbol);
			
			if (position != null) {
				quantity = position.getQuantity() + quantity;
				positions.remove(symbol);		
			}
			
			if (quantity != 0) {
				position = new naivePosition(symbol, quantity);
				positions.put(symbol, position);
			}

		}
	}

	/**
	 * Implementation of hw1_hl1283.Portfolio#getPositionIter()
	 */
	public PositionIter getPositionIter() { 
		return new naivePositionIter(positions);
	}
	
}
