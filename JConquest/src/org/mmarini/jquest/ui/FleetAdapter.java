package org.mmarini.jquest.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import org.mmarini.jquest.Fleet;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: FleetAdapter.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class FleetAdapter {
	public static final BasicStroke NO_SIZE_LINE_STROKE = new BasicStroke(0);

	private Fleet fleet;
	private FleetShape shape = new FleetShape();

	/**
	 * @param g
	 */
	public void draw(Graphics2D g) {
		Fleet fleet = this.getFleet();
		Color col = OwnerUtils.getInstance().getColor(fleet.getOwner());
		g.setColor(col);
		Shape shape = this.getShape();
		g.fill(shape);
	}

	/**
	 * @return Returns the fleet.
	 */
	public Fleet getFleet() {
		return fleet;
	}

	/**
	 * @return
	 */
	public Shape getShape() {
		Fleet fleet = this.getFleet();
		Point fLoc = fleet.getLocation();
		double x = fLoc.getX();
		double y = fLoc.getY();
		shape.setLocation(x, y);

		Planet dest = fleet.getDestination();
		Point dLoc = dest.getLocation();
		double dx = dLoc.getX() - x;
		double dy = dLoc.getY() - y;
		double theta = Math.atan2(-dx, dy);
		shape.setRotation(theta);
		return shape;
	}

	/**
	 * @param fleet
	 *            The fleet to set.
	 */
	public void setFleet(Fleet fleet) {
		this.fleet = fleet;
	}
}