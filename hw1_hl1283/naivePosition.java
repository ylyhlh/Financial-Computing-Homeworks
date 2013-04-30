package hw1_hl1283;
/**
 * One implementation of <code>Position</code>
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Position
 */
public class naivePosition implements Position {

	/** The symbol of <code>Position</code> */
	private String symbol;
	/** The quantity of <code>Position</code> */
	private int quantity;

	/**
	 * Create one <code>Position</code> with given <code>symbol</code> and <code>quantity</code>.
	 * @param  symbol   the symbol of <code>Position</code>
	 * @param  quantity the quantity of <code>Position</code>
	 * @see Position 
	 * @see Portfolio
	 */
	public naivePosition(String symbol, int quantity) {
		this.symbol = new String(symbol);
		this.quantity = quantity;
	}
	
	/**
	 * Implementation of hw1_hl1283.Position#getQuantity()
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Implementation of hw1_hl1283.Position#getSymbol()
	 */
	public String getSymbol() {
		return symbol;
	}
	
	

}
