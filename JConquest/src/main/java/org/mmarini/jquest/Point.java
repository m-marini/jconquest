package org.mmarini.jquest;

import java.awt.geom.Point2D;

/**
 * @author US00852
 * @version $Id: Point.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class Point extends Point2D.Double {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6494900194602704873L;

	/**
	 * 
	 */
	public Point() {
	}

	public Point(double x, double y) {
		this.setLocation(x, y);
	}

	public Point(Point point) {
		this.setLocation(point);
	}
}