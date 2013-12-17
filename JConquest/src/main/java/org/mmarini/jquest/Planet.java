package org.mmarini.jquest;

/**
 * The class rappresents a planet.
 * <p>
 * A planet has a name, an owner, a shipRate that defines how many ships are to
 * be created in an unit of time (year), a location in the universe, a shipCount
 * with the number of ship ready to be used, a shipBuilding with a value of how
 * much is built for the next ship and the killRate of the planet that
 * determines the kill rate of the planet ships.
 * </p>
 * <p>
 * The planet is also a ITickTimer that behaves in the universe. role of the
 * planet is
 * </p>
 * 
 * @author US00852
 * @version $Id: Planet.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Planet extends AbstractUniverseObject implements ITickTimer {
	private String name;
	private IOwner owner;
	private double shipRate;
	private Point location;
	private int shipCount;
	private double shipBuilding;
	private double killRate;

	/**
	 * 
	 */
	public Planet() {
	}

	/**
	 * @param p
	 * @return
	 */
	protected boolean choose(double p) {
		return Math.random() < p;
	}

	/**
	 * The behavior of the planet in the universe is to build and store ships if
	 * the planet is owned by someone.
	 * 
	 * @see org.mmarini.jquest.ITickTimer#doTickTime(double)
	 */
	@Override
	public synchronized void doTickTime(double years) {
		if (this.getOwner() == null)
			return;
		double sb = this.getShipBuilding();
		sb += years * this.getShipRate();
		if (sb >= 1f) {
			int ships = (int) Math.floor(sb);
			sb -= ships;
			this.setShipCount(this.getShipCount() + ships);
		}
		this.setShipBuilding(sb);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (!(other instanceof Planet))
			return false;
		String name = this.getName();
		String otherName = ((Planet) other).getName();
		if (name == null)
			return false;
		return name.equals(otherName);
	}

	/**
	 * @return Returns the killRate.
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the owner.
	 */
	public IOwner getOwner() {
		return owner;
	}

	/**
	 * @return Returns the shipBuilding.
	 */
	protected double getShipBuilding() {
		return shipBuilding;
	}

	/**
	 * @return Returns the shipCount.
	 */
	public int getShipCount() {
		return shipCount;
	}

	/**
	 * @return Returns the shipRate.
	 */
	public double getShipRate() {
		return shipRate;
	}

	/**
	 * The method handles the arrival of a Fleet in a planet.
	 * <p>
	 * If the fleet is owned by the same owner of the planet than the ships of
	 * fleet are added to the planet else a combact is engaged.
	 * </p>
	 * <p>
	 * The probablity of fleet win in a single combact iteration is
	 * planetKillRate/(fleetKillRate+planetKillRate)
	 * 
	 * @param fleet
	 *            the fleet arrived
	 */
	public void handleFleetArrival(Fleet fleet) {
		synchronized (this) {
			IOwner planetOwner = this.getOwner();
			IOwner fleetOwner = fleet.getOwner();
			int fleetShip = fleet.getShipCount();
			int planetShip = this.getShipCount();
			if (fleetOwner.equals(planetOwner)) {
				/*
				 * Enforce
				 */
				this.setShipCount(planetShip + fleetShip);
			} else {
				/*
				 * Combact
				 */
				;
				double fleetKillRate = fleet.getKillRate();
				double planetKillRate = this.getKillRate();
				double p = fleetKillRate / (fleetKillRate + planetKillRate);
				while (fleetShip > 0 && planetShip > 0) {
					/*
					 * 
					 */
					if (this.choose(p)) {
						--fleetShip;
					} else {
						--planetShip;
					}
				}
				if (fleetShip > 0) {
					planetShip = fleetShip;
					this.setOwner(fleetOwner);
				}
				this.setShipCount(planetShip);
			}
		}
		this.getUniverse().removeFleet(fleet);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		String name = this.getName();
		if (name == null)
			return 0;
		return name.hashCode();
	}

	/**
	 * @param owner
	 * @param destination
	 * @param ships
	 * @return
	 * @throws NoShipsException
	 * @throws InvalidOwnerException
	 */
	public Fleet lunchFleet(IOwner owner, Planet destination, int ships)
			throws NoShipsException, InvalidOwnerException {
		synchronized (this) {
			/*
			 * Validate request
			 */
			if (this.equals(destination))
				throw new InvalidOwnerException(destination + "==" + this);

			if (owner != this.getOwner()) {
				throw new InvalidOwnerException(owner.getName()
						+ " is not the owner of planet");
			}
			int shipCount = this.getShipCount() - ships;
			if (shipCount < 0)
				throw new NoShipsException("No ship available in planet "
						+ this.getName());
			this.setShipCount(shipCount);
		}
		Fleet fleet = new Fleet();
		fleet.setOwner(owner);
		fleet.setDestination(destination);
		fleet.setShipCount(ships);
		fleet.setLocation(this.getLocation());
		fleet.setKillRate(this.getKillRate());
		this.getUniverse().addFleet(fleet);
		return fleet;
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
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param owner
	 *            The owner to set.
	 */
	public void setOwner(IOwner owner) {
		this.owner = owner;
	}

	/**
	 * @param shipBuilding
	 *            The shipBuilding to set.
	 */
	protected void setShipBuilding(double shipBuilding) {
		this.shipBuilding = shipBuilding;
	}

	/**
	 * @param shipCount
	 *            The shipCount to set.
	 */
	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}

	/**
	 * @param shipRate
	 *            The shipRate to set.
	 */
	public void setShipRate(double shipRate) {
		this.shipRate = shipRate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}