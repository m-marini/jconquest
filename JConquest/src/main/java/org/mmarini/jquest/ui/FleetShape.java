package org.mmarini.jquest.ui;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

/**
 * @author US00852
 * @version $Id: FleetShape.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class FleetShape extends Area {
	private static final double BETA = Math.toRadians(30);
	private static final float X = (float) Math.sin(BETA);
	private static final float Y = (float) Math.cos(BETA);
	public static final double FLEET_SIZE = 1. / 3;

	/**
	 * 
	 */
	public FleetShape(final double x, final double y, final double rotation) {
		final GeneralPath shape = new GeneralPath();
		shape.moveTo(0, 1);
		shape.lineTo(X, -Y);
		shape.lineTo(-X, -Y);
		shape.closePath();

		reset();
		final AffineTransform trans = new AffineTransform();
		trans.translate(x, y);
		trans.rotate(rotation);
		trans.scale(FLEET_SIZE, FLEET_SIZE);
		add(new Area(shape.createTransformedShape(trans)));
	}
}