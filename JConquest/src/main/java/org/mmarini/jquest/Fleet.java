package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: Fleet.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Fleet extends AbstractUniverseObject implements TickTimer {
	private final Point location;
	private final int shipCount;
	private final Planet destination;
	private final Owner owner;
	private final double killRate;

	/**
	 * @param owner
	 * @param location
	 * @param destination
	 * @param shipCount
	 * @param killRate
	 */
	protected Fleet(Owner owner, Point location, Planet destination,
			int shipCount, double killRate) {
		this.owner = owner;
		this.location = new Point(location);
		this.destination = destination;
		this.shipCount = shipCount;
		this.killRate = killRate;
	}

	/**
	 * @see org.mmarini.jquest.TickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		double dist = years * Constants.FLEET_SPEED;
		Planet dest = this.getDestination();
		Point destPoint = dest.getLocation();
		Point loc = this.getLocation();
		double distTo = loc.distance(destPoint);
		if (dist >= distTo) {
			location.setLocation(destPoint);
			dest.handleFleetArrival(this);
			return;
		}
		/*
		 * Calculate new position
		 */
		double dx = (destPoint.x - loc.x) * dist / distTo;
		double dy = (destPoint.y - loc.y) * dist / distTo;
		location.setLocation(loc.x + dx, loc.y + dy);
	}

	/**
	 * @return
	 */
	public double getArrivalTime() {
		return getTimeToArrive() + getUniverse().getTime();
	}

	/**
	 * @return Returns the destination.
	 */
	public Planet getDestination() {
		return destination;
	}

	/**
	 * @return
	 */
	public double getKillRate() {
		return killRate;
	}

	/**
	 * @return Returns the location.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return Returns the owner.
	 */
	public Owner getOwner() {
		return owner;
	}

	/**
	 * @return Returns the shipCount.
	 */
	public int getShipCount() {
		return shipCount;
	}

	/**
	 * @return
	 */
	public double getTimeToArrive() {
		return this.getLocation().distance(this.getDestination().getLocation())
				/ Constants.FLEET_SPEED;
	}
}