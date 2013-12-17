package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: Fleet.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Fleet extends AbstractUniverseObject implements ITickTimer {
	private Point location;
	private int shipCount;
	private Planet destination;
	private IOwner owner;
	private double killRate;

	/**
	 * 
	 */
	public Fleet() {
	}

	/**
	 * @see org.mmarini.jquest.ITickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		double dist = years * Constants.FLEET_SPEED;
		Planet dest = this.getDestination();
		Point destPoint = dest.getLocation();
		Point loc = this.getLocation();
		double distTo = loc.distance(destPoint);
		if (dist >= distTo) {
			this.setLocation(destPoint);
			dest.handleFleetArrival(this);
			return;
		}
		/*
		 * Calculate new position
		 */
		double dx = (destPoint.x - loc.x) * dist / distTo;
		double dy = (destPoint.y - loc.y) * dist / distTo;
		loc = new Point(loc.x + dx, loc.y + dy);
		this.setLocation(loc);
	}

	/**
	 * @return
	 */
	public double getArrivalTime() {
		return this.getTimeToArrive() + this.getUniverse().getTime();
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
	public IOwner getOwner() {
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

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(Planet destination) {
		this.destination = destination;
	}

	/**
	 * @param killRate
	 *            The killRate to set.
	 */
	public void setKillRate(double killRate) {
		this.killRate = killRate;
	}

	/**
	 * @param location
	 *            The location to set.
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * @param owner
	 *            The owner to set.
	 */
	public void setOwner(IOwner owner) {
		this.owner = owner;
	}

	/**
	 * @param shipCount
	 *            The shipCount to set.
	 */
	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}
}