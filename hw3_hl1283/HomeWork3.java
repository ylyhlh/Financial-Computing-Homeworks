package hw3_hl1283;

import hw2_hl1283.util.Option;

import org.joda.time.DateTime;
import org.joda.time.Period;


public class HomeWork3 {
	private static Option option;
	private static Option option1;
	
    public static void main(String[] args) throws Exception {
		double accuPoss = 0.96;
		double accuRate = 0.01;
		generateSamplePath();
		
        thread(new MonteCarloServer(option, accuPoss, accuRate), false);
        thread(new MonteCarloServer(option1, accuPoss, accuRate), false);
        thread(new MonteCarloClient(), false);
        thread(new MonteCarloClient(), false);
        //Thread.sleep(5000);
    }

    /**
     * Run the runable with new Thread
     * @param runnable
     * @param daemon use daemon or not
     */
    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
    
    /**
     * To setup the Options we used to test program
     */
    public static void generateSamplePath(){
    	String optionName = "IBM"; 
		int optionDays = 252;
		double initialPrice = 152.35;
		double interestRate = 0.0001;
		double volatility = 0.01;
		double strickPrice = 165;
		String payOutType = "EuropeCall";
		
		DateTime initialTime= new DateTime(2013,3,22,16,25);
		Period period = Period.days(optionDays);
		
		option = new Option(optionName, payOutType, initialPrice, initialTime, period,interestRate, volatility, strickPrice);
		
		String optionName1 = "IBM1"; 
		int optionDays1 = 252;
		double initialPrice1 = 152.35;
		double interestRate1 = 0.0001;
		double volatility1 = 0.01;
		double strickPrice1 = 164;
		String payOutType1 = "AsianCall";
		
		DateTime initialTime1 = new DateTime(2013,3,22,16,25);
		Period period1 = Period.days(optionDays1);	
		option1 = new Option(optionName1, payOutType1, initialPrice1, initialTime1, period1,interestRate1, volatility1, strickPrice1);
		
		
    }



   
}