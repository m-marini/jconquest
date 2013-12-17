package org.mmarini.jquest;

/**
 * The strategy context is the interface used by concrete IStrategy to interact
 * with the universe
 * 
 * @author US00852
 * @version $Id: IStrategyContext.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public interface IStrategyContext {

	/**
	 * Gets the owner subject of the strategy
	 * 
	 * @return the owner
	 */
	public abstract IOwner getOwner();

	/**
	 * Gets the concrete strategy
	 * 
	 * @return the concrete strategy
	 */
	public abstract IStrategy getStrategy();

	/**
	 * Gets the universe
	 * 
	 * @return the universe
	 */
	public abstract Universe getUniverse();

	/**
	 * Sets the concrete strategy
	 * 
	 * @param strategy
	 *            the strategy
	 */
	public abstract void setStrategy(IStrategy strategy);
}