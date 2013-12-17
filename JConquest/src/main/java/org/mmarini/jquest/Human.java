package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: Human.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Human extends AbstractOwner {

	/**
	 * @param ordinal
	 * @param name
	 */
	public Human(int ordinal, String name) {
		super(ordinal, name);
	}

	/**
	 * @see org.mmarini.jquest.ITickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
	}

}