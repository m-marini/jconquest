package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author US00852
 * @version $Id: UniverseBuilder.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class UniverseBuilder {
	private static UniverseBuilder instance = new UniverseBuilder();

	/**
	 * @return Returns the instance.
	 */
	public static UniverseBuilder getInstance() {
		return instance;
	}

	private ResourceBundle bundle;
	private List<String> planet;
	private List<String> owner;

	/**
	 * 
	 */
	private UniverseBuilder() {
		planet = new ArrayList<String>();
		owner = new ArrayList<String>();
		bundle = ResourceBundle.getBundle(this.getClass().getName() + "Res");
		try {
			String planetName = bundle.getString("planet.names");
			List<String> list = this.getPlanet();
			for (StringTokenizer token = new StringTokenizer(planetName, ","); token
					.hasMoreTokens();) {
				list.add(token.nextToken().trim());
			}
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		try {
			String ownerName = bundle.getString("owner.names");
			List<String> list = this.getOwner();
			for (StringTokenizer token = new StringTokenizer(ownerName, ","); token
					.hasMoreTokens();) {
				list.add(token.nextToken().trim());
			}
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param planetCount
	 * @param owner
	 * @return
	 * @throws PlanetCountOutOfBoundException
	 */
	public Universe createUniverse(int opponentCount, int planetCount,
			double reaction) throws PlanetCountOutOfBoundException {
		int maxPlanetCount = this.getMaxPlanetCount();
		if (planetCount > maxPlanetCount)
			throw new PlanetCountOutOfBoundException("The planet count "
					+ planetCount + " exceeds the maximum value "
					+ maxPlanetCount);
		int maxOwnerCount = this.getMaxOwnerCount();
		if (opponentCount > maxOwnerCount)
			throw new PlanetCountOutOfBoundException("The opponent count "
					+ opponentCount + " exceeds the maximum value "
					+ maxOwnerCount);
		if (opponentCount > planetCount - 1)
			throw new PlanetCountOutOfBoundException("The opponent count "
					+ opponentCount + " exceeds the maximum value "
					+ (planetCount - 1));

		List<String> ownerName = this.getOwner();
		Collections.shuffle(ownerName);
		List<String> planetName = this.getPlanet();
		Collections.shuffle(planetName);

		Universe universe = new Universe();
		for (int i = 0; i < opponentCount + 1; ++i) {
			IOwner owner;
			String name = ownerName.get(i);
			if (i == 0) {
				Human human = new Human(i, name);
				universe.setHuman(human);
				owner = human;
			} else {
				AutoOwner autoOwner = new AutoOwner(i, name);
				autoOwner.setReaction(reaction);
				owner = autoOwner;
			}
			universe.addOwner(owner);
			Planet planet = new Planet();
			planet.setName(planetName.get(i));
			Point location = universe.createLocation();
			planet.setLocation(location);
			planet.setOwner(owner);
			planet.setShipCount(Constants.OWNER_SHIP_COUNT);
			planet.setShipRate(Constants.OWNER_SHIP_RATE);
			planet.setKillRate(Constants.OWNER_KILL_RATE);
			universe.addPlanet(planet);
		}

		for (int i = opponentCount + 1; i < planetCount; ++i) {
			Planet planet = new Planet();
			planet.setName(planetName.get(i));
			Point location = universe.createLocation();
			planet.setLocation(location);
			double shipRate = (Constants.MIN_SHIP_RATE + (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
					* Math.random());
			planet.setShipRate(shipRate);
			int ship = (int) Math.round(shipRate * (Math.random() + .5));
			planet.setShipCount(ship);
			double killRate = (Math.random() * (Constants.MAX_KILL_RATE - Constants.MIN_KILL_RATE))
					+ Constants.MIN_KILL_RATE;
			planet.setKillRate(killRate);
			universe.addPlanet(planet);
		}
		return universe;
	}

	/**
	 * @return Returns the bundle.
	 */
	protected ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @return
	 */
	public int getMaxOwnerCount() {
		return this.getOwner().size();
	}

	/**
	 * @return
	 */
	public int getMaxPlanetCount() {
		return this.getPlanet().size();
	}

	/**
	 * @return Returns the owner.
	 */
	protected List<String> getOwner() {
		return owner;
	}

	/**
	 * @return Returns the name.
	 */
	protected List<String> getPlanet() {
		return planet;
	}
}