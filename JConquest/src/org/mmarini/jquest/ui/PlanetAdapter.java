package org.mmarini.jquest.ui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.mmarini.jquest.Constants;
import org.mmarini.jquest.IOwner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: PlanetAdapter.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class PlanetAdapter {
	public static final double MAX_PLANET_SIZE = 1;
	public static final double MIN_PLANET_SIZE = MAX_PLANET_SIZE / 3;
	private Planet planet;
	private Ellipse2D.Double shape = new Ellipse2D.Double();

	/**
	 * 
	 */
	public PlanetAdapter() {
	}

	/**
	 * @param planet
	 */
	public PlanetAdapter(Planet planet) {
		this.planet = planet;
	}

	/**
	 * @param point
	 * @return
	 */
	public boolean contains(Point2D point) {
		Shape shape = this.getShape();
		return shape.contains(point);
	}

	/**
	 * @param g
	 */
	public void draw(Graphics2D g) {
		IOwner owner = this.getOwner();
		g.setColor(OwnerUtils.getInstance().getColor(owner));
		g.fill(this.getShape());
	}

	/**
	 * @return
	 */
	public Point getLocation() {
		return planet.getLocation();
	}

	/**
	 * @return
	 */
	public String getName() {
		return planet.getName();
	}

	/**
	 * @return
	 */
	public IOwner getOwner() {
		return planet.getOwner();
	}

	/**
	 * @return Returns the planet.
	 */
	public Planet getPlanet() {
		return planet;
	}

	/**
	 * @return
	 */
	public Shape getShape() {
		Point location = this.getLocation();
		Ellipse2D.Double shape = this.shape;
		double size = this.getSize();
		double halfSize = size / 2f;
		shape.x = location.x - halfSize;
		shape.y = location.y - halfSize;
		shape.width = size;
		shape.height = size;
		return shape;
	}

	/**
	 * @return
	 */
	public int getShipCount() {
		return planet.getShipCount();
	}

	/**
	 * @return
	 */
	public double getShipRate() {
		return planet.getShipRate();
	}

	protected double getSize() {
		double size = MAX_PLANET_SIZE
				* (this.getShipRate() - Constants.MIN_SHIP_RATE)
				/ (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
				+ MIN_PLANET_SIZE;
		return size;
	}

	/**
	 * @param planet
	 *            The planet to set.
	 */
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return planet.toString();
	}
}