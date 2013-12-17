package org.mmarini.jquest.ui;

import java.awt.Color;

import org.mmarini.jquest.IOwner;

/**
 * @author US00852
 * @version $Id: OwnerUtils.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class OwnerUtils {
	private static OwnerUtils instance = new OwnerUtils();

	/**
	 * @return Returns the instance.
	 */
	public static OwnerUtils getInstance() {
		return instance;
	}

	private Color[] colors = new Color[] { Color.YELLOW, Color.BLUE, Color.RED,
			Color.GREEN, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK };

	/**
	 * 
	 */
	private OwnerUtils() {
	}

	public Color getColor(IOwner owner) {
		if (owner == null)
			return Color.LIGHT_GRAY;
		return colors[owner.getOrdinal() % colors.length];
	}
}