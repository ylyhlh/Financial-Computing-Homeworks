package hw2_hl1283.PayOut;

import hw2_hl1283.StockPath.StockPath;
/**
 * The interface for calculating the payout function.
 * @author liuhao
 * @see StockPath
 */
public interface PayOut{
	  /**
	   * Calculate the payout of given <code>StockPath</code>
	   * @param path The <code>StockPath</code> given to calculate the payout.
	   * @return The payout value of given <code>StockPath</code>
	   * @see StockPath
	   */
	   public double getPayout(StockPath path) ;
}