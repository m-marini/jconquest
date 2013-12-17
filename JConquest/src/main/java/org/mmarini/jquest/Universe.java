package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US00852
 * @version $Id: Universe.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class Universe implements ITickTimer {
	public static final double MAX_UNIVERSE_COORDINATE = (Constants.UNIVERSE_CELL_COUNT - 1) / 2;

	private List<FleetListener> fleetListener;
	private List<PlanetListener> planetListener;
	private List<Planet> planets;
	private List<Fleet> fleets;
	private List<IOwner> owners;
	private double time;
	private Human human;

	/**
	 * 
	 */
	public Universe() {
		fleetListener = new ArrayList<FleetListener>(2);
		planetListener = new ArrayList<PlanetListener>(2);
		planets = new ArrayList<Planet>();
		fleets = new ArrayList<Fleet>();
		owners = new ArrayList<IOwner>();
		time = Constants.DEFAULT_INIT_TIME;
	}

	/**
	 * @param fleet
	 */
	public void addFleet(Fleet fleet) {
		synchronized (this) {
			List<Fleet> list = this.getFleet();
			list = new ArrayList<Fleet>(list);
			list.add(fleet);
			this.setFleet(list);
		}
		fleet.attach(this);
		this.fireFleetLunched(new FleetEvent(fleet));
	}

	public synchronized void addFleetListener(FleetListener l) {
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
	public void addOwner(IOwner owner) {
		List<IOwner> list = owners;
		list.add(owner);
		owner.attach(this);
	}

	/**
	 * @param planet
	 */
	public void addPlanet(Planet planet) {
		planets.add(planet);
		planet.attach(this);
	}

	public synchronized void addPlanetListener(PlanetListener l) {
		List<PlanetListener> listener = this.planetListener;
		if (listener.contains(l))
			return;
		listener = new ArrayList<PlanetListener>(listener);
		listener.add(l);
		this.planetListener = listener;
	}

	public Point createLocation() {
		Point location = new Point();
		boolean valid;
		do {
			this.generateLocation(location);
			valid = true;
			for (Planet planet : planets) {
				Point loc = planet.getLocation();
				if (loc.distance(location) < 1) {
					valid = false;
					break;
				}
			}
		} while (!valid);
		return location;
	}

	/**
	 * @see org.mmarini.jquest.ITickTimer#doTickTime(double)
	 */
	@Override
	public void doTickTime(double years) {
		double year = this.getTime() + years;
		this.setTime(year);
		this.doTickTime(fleets, years);
		IOwner winner = getWinner();
		if (winner != null)
			return;
		this.doTickTime(planets, years);
		this.doTickTime(owners, years);
	}

	/**
	 * @param list
	 * @param years
	 */
	protected void doTickTime(Iterable<? extends ITickTimer> list, double years) {
		for (ITickTimer tickTimer : list) {
			tickTimer.doTickTime(years);
		}
	}

	protected void fireFleetLunched(FleetEvent ev) {
		List<FleetListener> listener = this.fleetListener;
		for (FleetListener l : listener) {
			l.fleetLunched(ev);
		}
	}

	protected void fireOwnerChanged(PlanetEvent ev) {
		List<PlanetListener> listener = this.planetListener;
		for (PlanetListener l : listener) {
			l.ownerChanged(ev);
		}
	}

	protected void generateLocation(Point location) {
		double x = (Math.random() * 2 - 1) * MAX_UNIVERSE_COORDINATE;
		double y = (Math.random() * 2 - 1) * MAX_UNIVERSE_COORDINATE;
		location.setLocation(x, y);
	}

	/**
	 * @return Returns the fleet.
	 */
	public List<Fleet> getFleet() {
		return fleets;
	}

	/**
	 * @param idx
	 * @return
	 */
	public Fleet getFleet(int idx) {
		return this.getFleet().get(idx);
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
	 * @param idx
	 * @return
	 */
	public Planet getPlanet(int idx) {
		return planets.get(idx);
	}

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
	public IOwner getWinner() {
		IOwner winner = null;
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
		double year = Math.floor(this.getTime());
		return (int) year;
	}

	/**
	 * @param fleet
	 */
	public void removeFleet(Fleet fleet) {
		synchronized (this) {
			List<Fleet> list = this.getFleet();
			list = new ArrayList<Fleet>(list);
			list.remove(fleet);
			this.setFleet(list);
		}
		fleet.detach(this);
	}

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
	public void removeOwner(IOwner owner) {
		List<IOwner> list = owners;
		list.remove(owner);
		owner.detach(this);
	}

	public synchronized void removePlanetListener(PlanetListener l) {
		List<PlanetListener> listener = this.planetListener;
		if (!listener.contains(l))
			return;
		listener = new ArrayList<PlanetListener>(listener);
		listener.remove(l);
		this.planetListener = listener;
	}

	/**
	 * @param list
	 *            The fleet to set.
	 */
	protected void setFleet(List<Fleet> list) {
		this.fleets = list;
	}

	/**
	 * @param human
	 */
	public void setHuman(Human human) {
		this.human = human;
	}

	/**
	 * @param time
	 *            The year to set.
	 */
	public void setTime(double time) {
		this.time = time;
	}
}