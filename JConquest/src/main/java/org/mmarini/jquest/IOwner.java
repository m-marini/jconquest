package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: IOwner.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public interface IOwner extends IUniverseObject, ITickTimer {

	/**
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract int getOrdinal();
}