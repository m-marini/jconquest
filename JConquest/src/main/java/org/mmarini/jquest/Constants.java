package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: Constants.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public interface Constants {
	/**
	 * Max kill rate
	 */
	public static final double MAX_KILL_RATE = 0.3;

	/**
	 * Min kill rate
	 */
	public static final double MIN_KILL_RATE = 0.1;

	/**
	 * Owner kill rate
	 */
	public static final double OWNER_KILL_RATE = (MAX_KILL_RATE + MIN_KILL_RATE) / 2;

	/**
	 * Fleet speed (units/year)
	 */
	public final static double FLEET_SPEED = 2;

	/**
	 * Cell counts
	 */
	public final static int UNIVERSE_CELL_COUNT = 31;

	/**
	 * Owner ship count
	 */
	public static final int OWNER_SHIP_COUNT = 10;

	/**
	 * Minimum ship building rate (ships/year)
	 */
	public static final double MIN_SHIP_RATE = 8.;

	/**
	 * Maximum ship building rate (ships/year)
	 */
	public static final double MAX_SHIP_RATE = 12.;

	/**
	 * Owner ship building rate (ships/year)
	 */
	public static final double OWNER_SHIP_RATE = (MAX_SHIP_RATE + MIN_SHIP_RATE) / 2.;

	/**
	 * Default inital year
	 */
	public static final double DEFAULT_INIT_TIME = 20437;
}