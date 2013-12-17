package org.mmarini.jquest;

import java.util.EventListener;

/**
 * @author US00852
 * @version $Id: PlanetListener.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public interface PlanetListener extends EventListener {

	/**
	 * @param ev
	 */
	public abstract void ownerChanged(PlanetEvent ev);
}