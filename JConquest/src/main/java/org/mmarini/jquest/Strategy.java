package org.mmarini.jquest;

/**
 * This is the interface of the automatic owner strategy algorithm.
 * 
 * @author US00852
 * @version $Id: IStrategy.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public interface Strategy {

	/**
	 * Applies the strategy to the context.
	 * 
	 * @param context
	 *            the strategy context
	 */
	public void apply(StrategyContext context);
}