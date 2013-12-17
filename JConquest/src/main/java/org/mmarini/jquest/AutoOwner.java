package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AutoOwner.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class AutoOwner extends AbstractOwner {
	private final Strategy strategy;
	private final double reaction;
	private double timer;

	/**
	 * @param name
	 * @param reaction
	 */
	public AutoOwner(final String name, final double reaction) {
		super(name);
		this.reaction = reaction;
		strategy = new StrategyImpl();
	}

	/**
	 * @see org.mmarini.jquest.TickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(final double years) {
		final double t = timer + years;
		if (t < reaction)
			timer = t;
		else {
			timer = 0;
			strategy.apply(new StrategyContextImpl(this));
		}
	}
}