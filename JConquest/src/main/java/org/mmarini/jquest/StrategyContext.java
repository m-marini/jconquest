package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: StrategyContext.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class StrategyContext implements IStrategyContext {
	private IStrategy strategy;
	private IOwner owner;

	/**
	 * @param owner
	 */
	public StrategyContext(IOwner owner, IStrategy strategy) {
		this.owner = owner;
		this.strategy = strategy;
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getOwner()
	 */
	@Override
	public IOwner getOwner() {
		return owner;
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getStrategy()
	 */
	@Override
	public IStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return owner.getUniverse();
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#setStrategy(org.mmarini.jquest.IStrategy)
	 */
	@Override
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
}