package org.mmarini.jquest.ui;

import java.util.EventObject;

import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: MapEvent.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class MapEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3669380003328374700L;
	private Planet sourcePlanet;
	private Planet destinationPlanet;
	private Point location;

	/**
	 * @param source
	 */
	public MapEvent(Object source) {
		super(source);
	}

	/**
	 * @return Returns the destinationPlanet.
	 */
	public Planet getDestinationPlanet() {
		return destinationPlanet;
	}

	/**
	 * @return Returns the location.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return Returns the sourcePlanet.
	 */
	public Planet getSourcePlanet() {
		return sourcePlanet;
	}

	/**
	 * @param destinationPlanet
	 *            The destinationPlanet to set.
	 */
	public void setDestinationPlanet(Planet destinationPlanet) {
		this.destinationPlanet = destinationPlanet;
	}

	/**
	 * @param location
	 *            The location to set.
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * @param sourcePlanet
	 *            The sourcePlanet to set.
	 */
	public void setSourcePlanet(Planet sourcePlanet) {
		this.sourcePlanet = sourcePlanet;
	}
}