package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AbstractPlanetAdapter.java,v 1.2 2006/03/16 22:35:24 marco Exp
 *          $
 */
public abstract class AbstractPlanetAdapter extends Planet {

	private Planet planet;

	/**
	 * @param planet
	 */
	public AbstractPlanetAdapter(Planet planet) {
		this.planet = planet;
	}

	/**
	 * @see org.mmarini.jquest.AbstractUniverseObject#attach(org.mmarini.jquest.Universe)
	 */
	@Override
	public void attach(Universe universe) {
		planet.attach(universe);
	}

	/**
	 * @see org.mmarini.jquest.Planet#choose(double)
	 */
	@Override
	protected boolean choose(double prob) {
		return planet.choose(prob);
	}

	/**
	 * @see org.mmarini.jquest.AbstractUniverseObject#detach(org.mmarini.jquest.Universe)
	 */
	@Override
	public void detach(Universe universe) {
		planet.detach(universe);
	}

	/**
	 * @see org.mmarini.jquest.Planet#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		planet.doTickTime(years);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return planet.equals(obj);
	}

	/**
	 * @see org.mmarini.jquest.Planet#getKillRate()
	 */
	@Override
	public double getKillRate() {
		return planet.getKillRate();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getLocation()
	 */
	@Override
	public Point getLocation() {
		return planet.getLocation();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getName()
	 */
	@Override
	public String getName() {
		return planet.getName();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getOwner()
	 */
	@Override
	public IOwner getOwner() {
		return planet.getOwner();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getShipBuilding()
	 */
	@Override
	protected double getShipBuilding() {
		return planet.getShipBuilding();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getShipCount()
	 */
	@Override
	public int getShipCount() {
		return planet.getShipCount();
	}

	/**
	 * @see org.mmarini.jquest.Planet#getShipRate()
	 */
	@Override
	public double getShipRate() {
		return planet.getShipRate();
	}

	/**
	 * @see org.mmarini.jquest.AbstractUniverseObject#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return planet.getUniverse();
	}

	/**
	 * @see org.mmarini.jquest.Planet#handleFleetArrival(org.mmarini.jquest.Fleet)
	 */
	@Override
	public void handleFleetArrival(Fleet fleet) {
		planet.handleFleetArrival(fleet);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return planet.hashCode();
	}

	/**
	 * @see org.mmarini.jquest.Planet#lunchFleet(org.mmarini.jquest.IOwner,
	 *      org.mmarini.jquest.Planet, int)
	 */
	@Override
	public Fleet lunchFleet(IOwner owner, Planet destination, int ships)
			throws NoShipsException, InvalidOwnerException {
		return planet.lunchFleet(owner, destination, ships);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setKillRate(double)
	 */
	@Override
	public void setKillRate(double killRate) {
		planet.setKillRate(killRate);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setLocation(org.mmarini.jquest.Point)
	 */
	@Override
	public void setLocation(Point location) {
		planet.setLocation(location);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		planet.setName(name);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setOwner(org.mmarini.jquest.IOwner)
	 */
	@Override
	public void setOwner(IOwner owner) {
		planet.setOwner(owner);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setShipBuilding(double)
	 */
	@Override
	protected void setShipBuilding(double shipBuilding) {
		planet.setShipBuilding(shipBuilding);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setShipCount(int)
	 */
	@Override
	public void setShipCount(int shipCount) {
		planet.setShipCount(shipCount);
	}

	/**
	 * @see org.mmarini.jquest.Planet#setShipRate(double)
	 */
	@Override
	public void setShipRate(double shipRate) {
		planet.setShipRate(shipRate);
	}

	/**
	 * @see org.mmarini.jquest.AbstractUniverseObject#setUniverse(org.mmarini.jquest.Universe)
	 */
	@Override
	protected void setUniverse(Universe universe) {
		planet.setUniverse(universe);
	}

	/**
	 * @see org.mmarini.jquest.Planet#toString()
	 */
	@Override
	public String toString() {
		return planet.toString();
	}
}