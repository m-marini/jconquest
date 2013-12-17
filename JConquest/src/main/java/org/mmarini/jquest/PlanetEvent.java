package org.mmarini.jquest;

import java.util.EventObject;

/**
 * @author US00852
 * @version $Id: PlanetEvent.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class PlanetEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7296004338751963317L;

	/**
	 * @param source
	 */
	public PlanetEvent(Planet source) {
		super(source);
	}

	public Planet getPlanet() {
		return (Planet) this.getSource();
	}
}