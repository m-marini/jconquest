package org.mmarini.jquest.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Map;

import org.mmarini.jquest.Fleet;
import org.mmarini.jquest.Owner;
import org.mmarini.jquest.Planet;
import org.mmarini.jquest.Point;

/**
 * @author US00852
 * @version $Id: FleetAdapter.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class FleetAdapter {
	public static final BasicStroke NO_SIZE_LINE_STROKE = new BasicStroke(0);

	private final Fleet fleet;
	private final Map<Owner, Color> colorMap;

	/**
	 * s
	 * 
	 * @param colorMap
	 * 
	 */
	public FleetAdapter(final Fleet fleet, final Map<Owner, Color> colorMap) {
		this.fleet = fleet;
		this.colorMap = colorMap;
	}

	/**
	 * @param g
	 */
	public void draw(Graphics2D g) {
		final Color c = colorMap.get(fleet.getOwner());
		g.setColor(c != null ? c : Color.LIGHT_GRAY);
		g.fill(createShape());
	}

	/**
	 * @return
	 */
	private Shape createShape() {
		final Point fLoc = fleet.getLocation();
		final double x = fLoc.getX();
		final double y = fLoc.getY();

		final Planet dest = fleet.getDestination();
		final Point dLoc = dest.getLocation();
		final double dx = dLoc.getX() - x;
		final double dy = dLoc.getY() - y;
		final double theta = Math.atan2(-dx, dy);

		return new FleetShape(x, y, theta);
	}
}