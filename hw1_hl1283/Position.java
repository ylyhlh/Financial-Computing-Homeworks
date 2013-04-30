package hw1_hl1283;
/**
 * <code>Position</code> is an interface describing the holding. 
 * In that respect, position will describe 
 * what instrument you hold, e.g. IBM. It 
 * will have quantity, 100 shares, -100 
 * shares. So in our position we can have 
 * two fields say name and quantity.
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Portfolio
 * @see PositionIter
 */
public interface Position {
	/**
	 * Returns the <code>quantity</code> of <code>Position</code>.
	 * @return      the quantity of <code>Position</code>
	 */
	public int getQuantity();
	
	/**
	 * Returns the <code>symbol</code> of <code>Position</code>.
	 * @return      the symbol of <code>Position</code>
	 */
	public String getSymbol();
}