package hw1_hl1283;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * One implementation of <code>PositionIter</code>
 * @author      Hao Liu (hl1283)
 * @version 0.1
 * @see Portfolio
 * @see PositionIter
 */
public class naivePositionIter implements PositionIter {

	/** Using the <code>Iterator</code> of <code>Set</code> from <code>HashMap</code>*/
	private Iterator<Map.Entry<String, Position>> positionsIter;
	
	/**
	 * Creat one <code>PositionIter</code> from given <code>HashMap<String,Position></code>
	 * @param positions <code>HashMap<String,Position></code> of <code>Position</code>s from <code>Portfolio</code>
	 * @see Position
	 * @see PositionIter
	 */
	public naivePositionIter(HashMap<String,Position> positions) {
		this.positionsIter = positions.entrySet().iterator();
	}
	
	/**
	 * Implementation of hw1_hl1283.PositionIter#getNextPosition()
	 */
	public Position getNextPosition() {
		if (positionsIter.hasNext())
			return positionsIter.next().getValue();
		else
			return null;
	}

}
