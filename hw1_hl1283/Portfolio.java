package hw1_hl1283;
/**
 *  <code>Portfolio</code> is a bag of <code>Position</code>s and some update rule. 
 *  For example, portfolio will have two positions, one
 *  position is 100 shares of IBM and another is -200 
 *  shares of MSFT. The process in the portfolio level 
 *  is as follows. Suppose you bought a 100 shares of 
 *  IBM. You tell your portfolio object this information 
 *  and it will create a new position object. If you than sold 
 *  a 100 shares of IBM, you tell this to your portfolio 
 *  and it will "update" the already existing IBM position 
 *  with the new quantity. Finally, if the overall quantity
 *  in the position is zero, it will remove it from the bag 
 *  of position.
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Position
 * @see PositionIter
 */
public interface Portfolio {
	/**
	 * Process one new trade with given <code>symbol</code> and <code>quantity</code>
	 * @param  symbol the symbol of target <code>Position</code>
	 * @param  quantity the trade quantity of target <code>position</code>
	 * @see         Position
	 * @see			Portfolio
	 */
	public void newTrade(String symbol, int quantity);
	
	/**
	 * This function will make a <code>PositionIter</code> for this <code>Portfolio</code>
	 * @return 		<code>PositionIter</code> of this <code>Portfolio</code>
	 * @see         Position
	 * @see         PositionIter
	 * @see			Portfolio
	 */
	public PositionIter getPositionIter();
}
