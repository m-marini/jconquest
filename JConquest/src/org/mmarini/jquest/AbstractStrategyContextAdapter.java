package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AbstractStrategyContextAdapter.java,v 1.1.2.1 2005/04/30
 *          16:06:46 marco Exp $
 */
public abstract class AbstractStrategyContextAdapter implements
		IStrategyContext {
	private IStrategyContext context;

	/**
	 * @param context
	 */
	public AbstractStrategyContextAdapter(IStrategyContext context) {
		this.context = context;
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getOwner()
	 */
	@Override
	public IOwner getOwner() {
		return context.getOwner();
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getStrategy()
	 */
	@Override
	public IStrategy getStrategy() {
		return context.getStrategy();
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return context.getUniverse();
	}

	/**
	 * @see org.mmarini.jquest.IStrategyContext#setStrategy(org.mmarini.jquest.IStrategy)
	 */
	@Override
	public void setStrategy(IStrategy strategy) {
		context.setStrategy(strategy);
	}
}