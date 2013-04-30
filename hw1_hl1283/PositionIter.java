package hw1_hl1283;
/**
 * <code>PositionIter</code> is an interface "like" an iterator for the <code>Position</code>s in the <code>Portfolio</code>.
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Portfolio
 * @see Position
 */
public interface PositionIter {
	/** 
	 * This method returns the next <code>Position</code> in the bag and null if we already iterated over all the position 		
	 * @return next <code>Position</code> in the bag or null when it reaches the end of bag.
	 * @see PositionIter
	 * @see Position
	 */
	public  Position getNextPosition();
}
