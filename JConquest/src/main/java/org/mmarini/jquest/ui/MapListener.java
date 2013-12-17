package org.mmarini.jquest.ui;

import java.util.EventListener;

/**
 * @author US00852
 * @version $Id: MapListener.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public interface MapListener extends EventListener {

	/**
	 * @param ev
	 */
	public abstract void fleetPlanCancelled(MapEvent ev);

	/**
	 * @param ev
	 */
	public abstract void fleetPlanChanged(MapEvent ev);

	/**
	 * @param ev
	 */
	public abstract void fleetPlanConfirmed(MapEvent ev);

	/**
	 * @param ev
	 */
	public abstract void planetEntered(MapEvent ev);

	/**
	 * @param ev
	 */
	public abstract void planetExited(MapEvent ev);
}