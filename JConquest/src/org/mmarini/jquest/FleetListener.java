package org.mmarini.jquest;

import java.util.EventListener;

/**
 * @author US00852
 * @version $Id: FleetListener.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public interface FleetListener extends EventListener {

	/**
	 * @param ev
	 */
	public abstract void fleetDestroyed(FleetEvent ev);

	/**
	 * @param ev
	 */
	public abstract void fleetLunched(FleetEvent ev);
}