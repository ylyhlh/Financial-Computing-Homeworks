package hw2_hl1283;

import hw2_hl1283.PayOut.*;
import hw2_hl1283.RandomVectorGenerator.*;
import hw2_hl1283.StockPath.BrownianStockPath;
import hw2_hl1283.util.*;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

/**
 * The Junit test class to test <code>PayOut</code> and <code>RandomVectorGenerator</code>
 * 
 * Result ------------------------------------------------------------
 * =======Problem-1:==========================================
 * 7135205 times simulations to converge!
 * The value of option in expiration date is :6.368064247205953 +- 0.01
 * The option should be priced as :6.209594137705505 +- 0.00975115
 * 
 * =======Problem-2:==========================================
 * 1352071 times simulations to converge!
 * The value of option in expiration date is :2.24634034190401 +- 0.01
 * The option should be priced as :2.1904398694624843 +- 0.00975115
 * 
 * @author liuhao
 * @version 0.1
 *
 * @see PayOut
 * @see RandomVectorGenerator
 * @see StockPath.StockPath
 * 
 *
 */
public class HomeWork2 {
	
	private static final double DELTA = 1e-12;
	private static int optionDays = 100;
	private static String optionName = "IBM";
	private static double initialPrice = 200;
	private static double interestRate = 0.0001;
	private static double volatility = 0.01;
	private static double strickPrice = 0;
	private static DateTime initialTime= new DateTime(2013,3,22,16,25);
	private static Period period = Period.days(optionDays);	
	private static String payOutType = "AsianCall";
	private static Option option = new Option(optionName, payOutType, initialPrice, initialTime, period,interestRate, volatility, strickPrice);
	
	/** The testing path of $1 increasing per day */
	private static ArrayList<PriceNode> path = new ArrayList<PriceNode>();
	/** The testing path of $1 decreasing per day */
	private static ArrayList<PriceNode> path1 = new ArrayList<PriceNode>();
	/** The testing path of constant price */
	private static ArrayList<PriceNode> path2 = new ArrayList<PriceNode>();
	/** The testing path of $1 in creasing per day in the first half and $1 decreasing per day in the last half */
	private static ArrayList<PriceNode> path3 = new ArrayList<PriceNode>();
	/** The testing path of one initial price only */
	private static ArrayList<PriceNode> path4 = new ArrayList<PriceNode>();
	
	@org.junit.Rule 
	public ExpectedException expected = ExpectedException.none();
	
	/** 
	 * Generate all need testing paths 
	 */
	@org.junit.Before
	public void generateTestPath(){
		path = new ArrayList<PriceNode>();
		path1 = new ArrayList<PriceNode>();
		path2 = new ArrayList<PriceNode>();
		path3 = new ArrayList<PriceNode>();
		path4 = new ArrayList<PriceNode>();	
		path.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		path1.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		path2.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		path3.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		path4.add(new PriceNode(option.getInitialTime(),option.getInitialPrice()));
		double[] eta = new double[option.getPeriod().getDays()];
		double St = option.getInitialPrice();
		double St1 = option.getInitialPrice();
		double St2 = option.getInitialPrice();
		double St3 = option.getInitialPrice();
		DateTime t = option.getInitialTime();
		
		for(int i=0; i<eta.length;i++) {
			t = t.plusDays(1);
			St = St + 1;
			St1 = St1 - 1;
			if (i< eta.length/2)
				St3 = St3 + 1;
			else
				St3 = St3 - 1;
			path.add(new PriceNode(t,St));
			path1.add(new PriceNode(t,St1));
			path2.add(new PriceNode(t,St2));
			path3.add(new PriceNode(t,St3));
		}
	}
	
	/** 
	 * Test PayOut with empty path
	 */
	@org.junit.Test ()
	public void testPayoutWithEmptyPath() {
		expected.expect(ArrayIndexOutOfBoundsException.class);
		BrownianStockPath paths = mock(BrownianStockPath.class);
		when(paths.getPrices()).thenReturn(new ArrayList<PriceNode>());
		PayOut payOut = new CallPayOut(strickPrice);
		payOut.getPayout(paths);
	}
	
	/** 
	 * Test Europe Call <code>PayOut</code> with paths
	 */
	@org.junit.Test
	public void testEuropeCallPayOut(){
		BrownianStockPath paths = mock(BrownianStockPath.class) ;
		when(paths.getPrices()).thenReturn(path).thenReturn(path1).thenReturn(path2).thenReturn(path3).thenReturn(path4);
		PayOut payOut = new CallPayOut(strickPrice);
		org.junit.Assert.assertEquals("failure - wrong Payout",300, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",100, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",200, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",200, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",200, payOut.getPayout(paths), DELTA);
	}
	
	/** 
	 * Test Asian Call <code>PayOut</code> with paths
	 */
	@org.junit.Test
	public void testAsianCallPayOut(){		
		BrownianStockPath paths = mock(BrownianStockPath.class) ;
		when(paths.getPrices()).thenReturn(path).thenReturn(path1).thenReturn(path2).thenReturn(path3).thenReturn(path4);;
		PayOut payOut = new AsianCallPayOut(strickPrice);
		org.junit.Assert.assertEquals("failure - wrong Payout",250, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",150, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",200, payOut.getPayout(paths), DELTA);
		int n = option.getPeriod().getDays()/2;
		org.junit.Assert.assertEquals("failure - wrong Payout",(n*(n+1)-50+101*200)/101.0, payOut.getPayout(paths), DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout",200, payOut.getPayout(paths), DELTA);
	}
	
	
	/**
	 * Test <code>AntitheticRandomVectorGenerator</code>
	 */
	@org.junit.Test
	public void testAnthithetic() {
		RandomVectorGenerator normGenerator = mock(NormalRandomVectorGenerator.class);
		when(normGenerator.getVector()).thenReturn(new double[]{1,3,5,0});
		RandomVectorGenerator generator = new AntitheticRandomVectorGenerator(normGenerator);
		double[] vector = generator.getVector();
		org.junit.Assert.assertEquals("failure - wrong Payout", 1, vector[0], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", 3, vector[1], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", 5, vector[2], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", 0, vector[3], DELTA);
		vector = generator.getVector();
		org.junit.Assert.assertEquals("failure - wrong Payout", -1, vector[0], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", -3, vector[1], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", -5, vector[2], DELTA);
		org.junit.Assert.assertEquals("failure - wrong Payout", 0, vector[3], DELTA);
	}
	
	
	/**
	 * Test <code>StockPrice</code> classes with Europe <code>CallPayOut</code>.
	 */
	@org.junit.Test
	public void problem_1() {

		String optionName = "IBM"; 
		double accuPoss = 0.96;
		double accuRate = 0.01;
		int optionDays = 252;
		double initialPrice = 152.35;
		double interestRate = 0.0001;
		double volatility = 0.01;
		double strickPrice = 165;
		String payOutType = "AsianCall";
		
		DateTime initialTime= new DateTime(2013,3,22,16,25);
		Period period = Period.days(optionDays);
		
		Option option = new Option(optionName, payOutType, initialPrice, initialTime, period,interestRate, volatility, strickPrice);
		
		RandomVectorGenerator normGenerator = new NormalRandomVectorGenerator(option.getPeriod().getDays());
		RandomVectorGenerator generator = new AntitheticRandomVectorGenerator(normGenerator);
		BrownianStockPath pathes = new BrownianStockPath(option, generator);

		StatCollector stat = new StatCollector();
		
		PayOut payOut = new CallPayOut(strickPrice);

		double value = SimulationManager.simulate(pathes, payOut, stat, accuPoss, accuRate);
		double price = value * Math.exp(- option.getInterestRate() * option.getPeriod().getDays() );
		System.out.println("The value of option in expiration date is :" + value);
		System.out.println("The option should be priced as :" + price);
		
	}
	
	/**
	 * Test <code>StockPrice</code> classes with <code>AsianCallPayOut</code>.
	 */
	@org.junit.Test
	public void problem_2() {

		String optionName = "IBM"; 
		double accuPoss = 0.96;
		double accuRate = 0.01;
		int optionDays = 252;
		double initialPrice = 152.35;
		double interestRate = 0.0001;
		double volatility = 0.01;
		double strickPrice = 164;
		String payOutType = "AsianCall";
		
		DateTime initialTime= new DateTime(2013,3,22,16,25);
		Period period = Period.days(optionDays);	
		Option option = new Option(optionName, payOutType, initialPrice, initialTime, period,interestRate, volatility, strickPrice);
		
		RandomVectorGenerator normGenerator = new NormalRandomVectorGenerator(optionDays);
		RandomVectorGenerator generator = new AntitheticRandomVectorGenerator(normGenerator);
		BrownianStockPath pathes = new BrownianStockPath(option, generator);

		StatCollector stat = new StatCollector();
		
		PayOut payOut = new AsianCallPayOut(strickPrice);

		double value = SimulationManager.simulate(pathes, payOut, stat, accuPoss, accuRate);
		double price = value * Math.exp(- option.getInterestRate() * option.getPeriod().getDays() );
		System.out.println("The value of option in expiration date is :" + value);
		System.out.println("The option should be priced as :" + price);
		
	}
	public static void main(String[] args) {

	}

}
