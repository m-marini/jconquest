package org.mmarini.jquest;

import java.util.EventObject;

/**
 * @author US00852
 * @version $Id: FleetEvent.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class FleetEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public FleetEvent(Fleet source) {
		super(source);
	}

	public Fleet getFleet() {
		return (Fleet) this.getSource();
	}
}