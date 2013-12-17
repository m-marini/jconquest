package org.mmarini.jquest.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Map;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.Owner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: PlanetAdapter.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class PlanetAdapter {
	public static final double MAX_PLANET_SIZE = 1;
	public static final double MIN_PLANET_SIZE = MAX_PLANET_SIZE / 3;
	private final Planet planet;
	private final Ellipse2D.Double shape;
	private final Map<Owner, Color> colorMap;

	/**
	 * @param planet
	 * @param colorMap
	 */
	public PlanetAdapter(final Planet planet, final Map<Owner, Color> colorMap) {
		this.planet = planet;
		this.colorMap = colorMap;
		final Point location = getLocation();
		final double size = getSize();
		final double halfSize = size / 2f;
		shape = new Ellipse2D.Double(location.x - halfSize, location.y
				- halfSize, size, size);
	}

	/**
	 * @param point
	 * @return
	 */
	public boolean contains(final Point2D point) {
		return shape.contains(point);
	}

	/**
	 * @param g
	 */
	public void draw(final Graphics2D g) {
		final Color c = colorMap.get(planet.getOwner());
		g.setColor(c != null ? c : Color.LIGHT_GRAY);
		g.fill(shape);
	}

	/**
	 * @return
	 */
	public Point getLocation() {
		return planet.getLocation();
	}

	/**
	 * @return Returns the planet.
	 */
	public Planet getPlanet() {
		return planet;
	}

	/**
	 * 
	 * @return
	 */
	private double getSize() {
		return MAX_PLANET_SIZE
				* (planet.getShipRate() - Constants.MIN_SHIP_RATE)
				/ (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
				+ MIN_PLANET_SIZE;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(planet);
	}
}