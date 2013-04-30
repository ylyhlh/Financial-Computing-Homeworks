package hw2_hl1283;

import hw2_hl1283.PayOut.PayOut;
import hw2_hl1283.StockPath.*;
import hw2_hl1283.util.ProgressBar;
import hw2_hl1283.util.StatCollector;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * A class to do the simulation with a <code>static</code> function <code>simulate<code>.
 * @author liuhao
 * @version 0.1
 * @see StockPath
 * @see PayOut
 * @see StatCollector 
 */
public class SimulationManager {

	/**
	 * Simulation function used to make simulation with given <code>accuPoss</code> and  <code>accuRate</code> 
	 * @param pathes <code>stockPath</code> to generate the random paths
	 * @param payOut <code>PayOut</code> to compute the payout of given path.
	 * @param stat 	<code>StatCollector</code> to collect all payouts and compute the mean and standard deviation.
	 * @param accuPoss Possibility tolerance of the simulation.
	 * @param accuRate Absolute error tolerance of the simulation.
	 * @see StockPath
	 * @see PayOut
	 * @see StatCollector
	 * @return The value of option in expiration date
	 */
	public static double simulate(StockPath pathes, PayOut payOut, StatCollector stat, double accuPoss, double accuRate) {
		//computing the needed bound for variance using Central Limit Theorem.
		double bound = - new NormalDistribution().inverseCumulativeProbability( (1 - accuPoss) / 2 ) ;
		//System.out.println(bound);
		
		//Begin the simulation process.
		for (int i=1; i<30000000; i++) {
			stat.add( payOut.getPayout(pathes) );
			//System.out.println(i+"-th simulation with error: "+bound * stat.getStdVar()/ Math.sqrt(i)+"   with mean: "+stat.getMean()+"    with standard deviation "+stat.getStdVar());
			double estEnding = bound * stat.getStdVar()/accuRate;
			if (i % 10000== 0)
				System.out.print(ProgressBar.showBarByPoint(i, estEnding*estEnding, 100)+"\r");
			if (bound * stat.getStdVar()/ Math.sqrt(i) < accuRate /* stat.getMean() */&& i >20) {
				System.out.println(i+" times simulations to converge!");
				break;
			}
		}
		return stat.getMean();
	}
}
