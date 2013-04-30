package hw2_hl1283.StockPath;

import hw2_hl1283.util.PriceNode;

import java.util.List;

/**
 *  The interface for creating StockPath. The returned list should be ordered by date
 * @author liuhao
 * 
 * @see PriceNode
 */
public interface StockPath{
	/**
	 * Generate and return a new prices array made by <code>PriceNode</code>
	 * @return new prices array made by <code>PriceNode</code>.
	 * @see PriceNode
	 */
	  public List<PriceNode> getPrices();
}