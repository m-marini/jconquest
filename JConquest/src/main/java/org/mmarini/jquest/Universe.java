package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Universe.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Universe implements TickTimer {
	public static final double MAX_UNIVERSE_COORDINATE = (Constants.UNIVERSE_CELL_COUNT - 1) / 2;

	private final List<Planet> planets;
	private final List<Owner> owners;
	private double time;
	private Human human;
	private List<Fleet> fleets;
	private List<FleetListener> fleetListener;
	private List<PlanetListener> planetListener;

	/**
	 * 
	 */
	public Universe() {
		fleetListener = new ArrayList<>();
		planetListener = new ArrayList<>();
		planets = new ArrayList<>();
		fleets = new ArrayList<>();
		owners = new ArrayList<>();
		time = Constants.DEFAULT_INIT_TIME;
	}

	/**
	 * @param fleet
	 */
	public void addFleet(final Fleet fleet) {
		final List<Fleet> list = new ArrayList<>(fleets);
		list.add(fleet);
		fleets = list;
		fleet.attach(this);
		fireFleetLunched(new FleetEvent(fleet));
	}

	/**
	 * 
	 * @param l
	 */
	public synchronized void addFleetListener(final FleetListener l) {
		List<FleetListener> listener = this.fleetListener;
		if (listener.contains(l))
			return;
		listener = new ArrayList<FleetListener>(listener);
		listener.add(l);
		this.fleetListener = listener;
	}

	/**
	 * @param owner
	 */
	public void addOwner(final Owner owner) {
		owners.add(owner);
		owner.attach(this);
	}

	/**
	 * @param planet
	 */
	public void addPlanet(final Planet planet) {
		planets.add(planet);
		planet.attach(this);
	}

	/**
	 * 
	 * @param l
	 */
	public synchronized void addPlanetListener(final PlanetListener l) {
		List<PlanetListener> listener = this.planetListener;
		if (listener.contains(l))
			return;
		listener = new ArrayList<PlanetListener>(listener);
		listener.add(l);
		this.planetListener = listener;
	}

	/**
	 * 
	 * @return
	 */
	public Point createLocation() {
		final Point location = new Point();
		boolean valid;
		do {
			generateLocation(location);
			valid = true;
			for (final Planet planet : planets) {
				final Point loc = planet.getLocation();
				if (loc.distance(location) < 1) {
					valid = false;
					break;
				}
			}
		} while (!valid);
		return location;
	}

	/**
	 * @see org.mmarini.jquest.TickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(final double years) {
		time += years;
		doTickTime(fleets, years);
		Owner winner = getWinner();
		if (winner != null)
			return;
		doTickTime(planets, years);
		doTickTime(owners, years);
	}

	/**
	 * @param list
	 * @param years
	 */
	private void doTickTime(final Iterable<? extends TickTimer> list,
			final double years) {
		for (final TickTimer t : list)
			t.doTickTime(years);
	}

	/**
	 * 
	 * @param ev
	 */
	private void fireFleetLunched(final FleetEvent ev) {
		for (final FleetListener l : fleetListener)
			l.fleetLunched(ev);
	}

	/**
	 * 
	 * @param location
	 */
	private void generateLocation(Point location) {
		location.setLocation((Math.random() * 2 - 1) * MAX_UNIVERSE_COORDINATE,
				(Math.random() * 2 - 1) * MAX_UNIVERSE_COORDINATE);
	}

	/**
	 * @return Returns the fleet.
	 */
	public List<Fleet> getFleet() {
		return fleets;
	}

	/**
	 * @return
	 */
	public Iterable<Fleet> getFleets() {
		return fleets;
	}

	/**
	 * @return
	 */
	public Human getHuman() {
		return human;
	}

	/**
	 * @return the owners
	 */
	public List<Owner> getOwners() {
		return owners;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Planet> getPlanets() {
		return planets;
	}

	/**
	 * @return Returns the year.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return
	 */
	public Owner getWinner() {
		Owner winner = null;
		for (Planet planet : planets) {
			if (winner == null)
				winner = planet.getOwner();
			else if (!winner.equals(planet.getOwner()))
				return null;
		}
		for (Fleet fleet : fleets) {
			if (!winner.equals(fleet.getOwner()))
				return null;
		}
		return winner;
	}

	/**
	 * @return
	 */
	public int getYear() {
		return (int) Math.floor(time);
	}

	/**
	 * @param fleet
	 */
	public void removeFleet(Fleet fleet) {
		final List<Fleet> l = new ArrayList<Fleet>(fleets);
		l.remove(fleet);
		fleets = l;
		fleet.detach(this);
	}

	/**
	 * 
	 * @param l
	 */
	public synchronized void removeFleetListener(FleetListener l) {
		List<FleetListener> listener = this.fleetListener;
		if (!listener.contains(l))
			return;
		listener = new ArrayList<FleetListener>(listener);
		listener.remove(l);
		this.fleetListener = listener;
	}

	/**
	 * @param owner
	 */
	public void removeOwner(Owner owner) {
		owners.remove(owner);
		owner.detach(this);
	}

	/**
	 * 
	 * @param l
	 */
	public synchronized void removePlanetListener(PlanetListener l) {
		List<PlanetListener> listener = this.planetListener;
		if (!listener.contains(l))
			return;
		listener = new ArrayList<PlanetListener>(listener);
		listener.remove(l);
		this.planetListener = listener;
	}

	/**
	 * @param human
	 */
	public void setHuman(Human human) {
		this.human = human;
	}
}