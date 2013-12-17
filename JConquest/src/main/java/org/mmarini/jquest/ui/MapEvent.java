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
	private final Planet sourcePlanet;
	private final Planet destinationPlanet;
	private final Point location;

	/**
	 * @param source
	 */
	public MapEvent(final Object source) {
		this(source, null, null, null);
	}

	/**
	 * @param source
	 * @param sourcePlanet
	 * @param destinationPlanet
	 * @param location
	 */
	public MapEvent(final Object source, final Planet sourcePlanet,
			final Planet destinationPlanet, final Point location) {
		super(source);
		this.sourcePlanet = sourcePlanet;
		this.destinationPlanet = destinationPlanet;
		this.location = location;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MapEvent [from=").append(sourcePlanet).append(", to=")
				.append(destinationPlanet).append(", location=")
				.append(location).append("]");
		return builder.toString();
	}
}