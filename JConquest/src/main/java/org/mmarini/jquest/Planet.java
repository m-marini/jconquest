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
public class Planet extends AbstractUniverseObject implements TickTimer {
	private final String name;
	private final double shipRate;
	private final double killRate;
	private Owner owner;
	private Point location;
	private int shipCount;
	private double shipBuilding;

	/**
	 * @param name
	 * @param owner
	 * @param location
	 * @param shipRate
	 * @param shipCount
	 * @param killRate
	 */
	public Planet(final String name, final Owner owner, final Point location,
			final double shipRate, final int shipCount, final double killRate) {
		super();
		this.name = name;
		this.owner = owner;
		this.location = location;
		this.shipRate = shipRate;
		this.shipCount = shipCount;
		this.killRate = killRate;
	}

	/**
	 * @param p
	 * @return
	 */
	private boolean choose(final double p) {
		return Math.random() < p;
	}

	/**
	 * The behavior of the planet in the universe is to build and store ships if
	 * the planet is owned by someone.
	 * 
	 * @see org.mmarini.jquest.TickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		if (owner == null)
			return;
		double sb = shipBuilding;
		sb += years * shipRate;
		if (sb >= 1f) {
			int ships = (int) Math.floor(sb);
			sb -= ships;
			shipCount += ships;
		}
		shipBuilding = sb;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planet other = (Planet) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
		final Owner planetOwner = owner;
		final Owner fleetOwner = fleet.getOwner();
		int fleetShip = fleet.getShipCount();
		int planetShip = shipCount;
		if (fleetOwner.equals(planetOwner)) {
			/*
			 * Enforce
			 */
			shipCount = planetShip + fleetShip;
		} else {
			/*
			 * Combact
			 */
			;
			final double fleetKillRate = fleet.getKillRate();
			final double p = fleetKillRate / (fleetKillRate + killRate);
			while (fleetShip > 0 && planetShip > 0)
				if (choose(p))
					--fleetShip;
				else
					--planetShip;
			if (fleetShip > 0) {
				planetShip = fleetShip;
				owner = fleetOwner;
			}
			shipCount = planetShip;
		}
		getUniverse().removeFleet(fleet);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @param owner
	 * @param destination
	 * @param ships
	 * @return
	 * @throws NoShipsException
	 * @throws InvalidOwnerException
	 */
	public Fleet lunchFleet(Owner owner, Planet destination, int ships)
			throws NoShipsException, InvalidOwnerException {
		/*
		 * Validate request
		 */
		if (this.equals(destination))
			throw new InvalidOwnerException(destination + "==" + this);

		if (owner != this.owner) {
			throw new InvalidOwnerException(owner.getName()
					+ " is not the owner of planet");
		}
		final int sc = shipCount - ships;
		if (sc < 0)
			throw new NoShipsException("No ship available in planet " + name);
		shipCount = sc;
		final Fleet fleet = new Fleet(owner, location, destination, ships,
				killRate);
		this.getUniverse().addFleet(fleet);
		return fleet;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(name);
	}
}