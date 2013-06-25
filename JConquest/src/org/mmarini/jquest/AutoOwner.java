package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AutoOwner.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class AutoOwner extends AbstractOwner {
	private IStrategyContext strategyContext;
	private double reaction;
	private double timer;

	/**
	 * @param ordinal
	 * @param name
	 */
	public AutoOwner(int ordinal, String name) {
		super(ordinal, name);
		strategyContext = new StrategyContext(this, new Strategy());
	}

	/**
	 * @see org.mmarini.jquest.ITickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		double timer = this.getTimer() + years;
		if (timer < this.getReaction()) {
			this.setTimer(timer);
			return;
		}
		this.setTimer(0);
		IStrategyContext strategyContext = this.getStrategyContext();
		strategyContext.getStrategy().apply(strategyContext);
	}

	/**
	 * @return Returns the reaction.
	 */
	public double getReaction() {
		return reaction;
	}

	/**
	 * @return Returns the strategyCOntext.
	 */
	protected IStrategyContext getStrategyContext() {
		return strategyContext;
	}

	/**
	 * @return Returns the timer.
	 */
	protected double getTimer() {
		return timer;
	}

	/**
	 * @param reaction
	 *            The reaction to set.
	 */
	public void setReaction(double reaction) {
		this.reaction = reaction;
	}

	/**
	 * @param timer
	 *            The timer to set.
	 */
	protected void setTimer(double timer) {
		this.timer = timer;
	}
}