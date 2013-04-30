package hw1_hl1283;

import java.util.HashMap;

/**
 * This is the homework 1 of Financial Computing course.
 * In this home work, I implement basic <code>Position</code>, <code>Portfolio</code> and  <code>PositionIter</code> interfaces
 * And do some documents(use Javadoc) and testings(use Junit).
 * @author Hao Liu
 * @version 0.1
 * @see Position
 * @see Portfolio
 * @see PositionIter
 */
public class HomeWork1 {

	/**
	 * test the <code>Position</code> class 
	 * @see Position
	 */
	@org.junit.Test
	public void testPosition() {
		Position pos1 = new naivePosition("IBM", 200);
		Position pos2 = new naivePosition("APPL", -200);
		
		org.junit.Assert.assertEquals("failure - quantities not same", 200,pos1.getQuantity() );
		org.junit.Assert.assertEquals("failure - quantities not same", -200, pos2.getQuantity() );
		
		org.junit.Assert.assertEquals("failure - symbols not same", "IBM", pos1.getSymbol());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", pos2.getSymbol());
	}
	/**
	 * test the <code>PositionIter</code> class 
	 * @see Position
	 * @see PositionIter
	 */
	@org.junit.Test
	public void testPositionIter() {
		
		HashMap<String,Position> positions = new HashMap<String,Position>();
		
		PositionIter it = new naivePositionIter(positions);
		org.junit.Assert.assertNull("failure - iterator return not null", it.getNextPosition());
		
		positions.put("IBM", new naivePosition("IBM",123));
		positions.put("APPL", new naivePosition("APPL",342));
		
		it = new naivePositionIter(positions);
		Position position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 123, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "IBM", position.getSymbol());
		
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 342, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", position.getSymbol());
		org.junit.Assert.assertNull("failure - iterator return not null", it.getNextPosition());
		
	}
	
	/**
	 * test the <code>Portfolio</code> class and <code>PositionIter</code> class
	 * @see Portfolio
	 * @see PositionIter
	 */
	@org.junit.Test
	public void testPortfolio() {
		Portfolio port = new naivePortfolio();

		//trade on non-existing position test
		port.newTrade("IBM", 100);
		port.newTrade("APPL", 400);
		
		
		PositionIter it = port.getPositionIter();
		
		Position position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 100, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "IBM", position.getSymbol());
		
		
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 400, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", position.getSymbol());
		
		//trade on existing position test
		port.newTrade("IBM", 200);
		it = port.getPositionIter();
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 300, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "IBM", position.getSymbol());
	
		//kick out zero Position test
		port.newTrade("IBM", -300);
		it = port.getPositionIter();
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 400, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", position.getSymbol());
		position = it.getNextPosition();
		org.junit.Assert.assertNull("failure - position not null", position);
		
		//zeros trade on existing position test
		port.newTrade("APPL", 0);
		it = port.getPositionIter();
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 400, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", position.getSymbol());
		
		//zeros trade on non existed position test
		port.newTrade("GOOL", 0);
		it = port.getPositionIter();
		position = it.getNextPosition();
		org.junit.Assert.assertNotNull("failure - iterator return is null", position);
		org.junit.Assert.assertEquals("failure - quantities not same", 400, position.getQuantity());
		org.junit.Assert.assertEquals("failure - symbols not same", "APPL", position.getSymbol());
		position = it.getNextPosition();
		org.junit.Assert.assertNull("failure - position not null", position);

		//empty portfolio test
		port.newTrade("APPL", -400);
		it = port.getPositionIter();
		position = it.getNextPosition();
		org.junit.Assert.assertNull("failure - position not null", position);
		
	}
	
	/**
	 * print all <code>Position</code>s in the <code>Portfolio</code> using <code>PositionIter</code>
	 * @param port the <code>Portfolio</code> to print out
	 * @see Portfolio
	 * @see PositionIter
	 * @see Position
	 */
	public static void printPortfolio(Portfolio port) {
		PositionIter it =port.getPositionIter();
		printWithIter(it);
	}
	
	/**
	 * print all <code>Position</code> with <code>Iterator</code>
	 * @param it <code>PositionIter</code> on a bag of <code>Position</code>s
	 * @see PositionIter
	 * @see Position
	 */
	public static void printWithIter(PositionIter it) {
		while ( true ) {
			Position position = it.getNextPosition();
			if( position == null)
				break;
			System.out.println ("-------------" +"Position:: Symbol:\t"+position.getSymbol() + "\tShares:" +position.getQuantity());
		}
	}
}


