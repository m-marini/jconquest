package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: StrategyContext.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class StrategyContextImpl implements StrategyContext {
	private final Owner owner;

	/**
	 * @param owner
	 */
	public StrategyContextImpl(final Owner owner) {
		this.owner = owner;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getOwner()
	 */
	@Override
	public Owner getOwner() {
		return owner;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return owner.getUniverse();
	}
}