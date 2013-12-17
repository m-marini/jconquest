package org.mmarini.jquest.ui;

import java.awt.Shape;
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
	private GeneralPath shape = new GeneralPath();
	private double x;
	private double y;
	private double rotation;

	/**
	 * 
	 */
	public FleetShape() {
		shape.moveTo(0, 1);
		shape.lineTo(X, -Y);
		shape.lineTo(-X, -Y);
		shape.closePath();
		createArea();
	}

	/**
	 * 
	 */
	private void createArea() {
		this.reset();
		AffineTransform trans = new AffineTransform();
		trans.translate(this.getX(), this.getY());
		trans.rotate(this.getRotation());
		trans.scale(FLEET_SIZE, FLEET_SIZE);
		GeneralPath path = this.getShape();
		Shape shape = path.createTransformedShape(trans);
		this.add(new Area(shape));
	}

	/**
	 * @return Returns the theta.
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * @return Returns the shape.
	 */
	protected GeneralPath getShape() {
		return shape;
	}

	/**
	 * @return Returns the x.
	 */
	protected double getX() {
		return x;
	}

	/**
	 * @return Returns the y.
	 */
	protected double getY() {
		return y;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y) {
		this.setX(x);
		this.setY(y);
		this.createArea();
	}

	/**
	 * @param theta
	 *            The theta to set.
	 */
	public void setRotation(double theta) {
		this.rotation = theta;
		this.createArea();
	}

	/**
	 * @param x
	 *            The x to set.
	 */
	protected void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            The y to set.
	 */
	protected void setY(double y) {
		this.y = y;
	}
}