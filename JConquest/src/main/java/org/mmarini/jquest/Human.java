package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: Human.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Human extends AbstractOwner {

	/**
	 * @param name
	 */
	public Human(final String name) {
		super(name);
	}

	/**
	 * @see org.mmarini.jquest.TickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(final double years) {
	}

}