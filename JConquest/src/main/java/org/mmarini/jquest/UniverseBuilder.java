package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author US00852
 * @version $Id: UniverseBuilder.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class UniverseBuilder {
	private static final UniverseBuilder instance = new UniverseBuilder();

	private static final String[] PLANET_NAMES = { "Phobeus", "Gruenge",
			"Lutor", "Zurg", "Astor", "Bolteus", "Castor", "Yultot", "Nemesi",
			"Filteo", "Proto", "Vega", "Gianto", "Shanti", "Alamir", "Zhango",
			"Thetra", "Klindon", "Ork", "Degobar" };

	private static final int MAX_PLANET_COUNT = PLANET_NAMES.length;

	private static final String[] OWNER_NAMES = { "Raman", "Kirk", "Ian",
			"Zhang", "Ibram", "Marcus", "Romulo", "Jabu", "Franzo", "Alhimah",
			"Khan", "Ular", "Shimoko", "Ngana", "Pacha", "Khoal", "Krungen",
			"Mhara", "Hari", "Olrich" };
	private static final int MAX_OWNER_COUNT = OWNER_NAMES.length;

	/**
	 * @return Returns the instance.
	 */
	public static UniverseBuilder getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	private UniverseBuilder() {
	}

	/**
	 * 
	 * @param pn
	 * @param owner
	 * @param location
	 * @return
	 */
	private Planet createPlanet(final String pn, final Owner owner,
			final Point location) {
		return new Planet(pn, owner, location, Constants.OWNER_SHIP_RATE,
				Constants.OWNER_SHIP_COUNT, Constants.OWNER_KILL_RATE);
	}

	/**
	 * 
	 * @param name
	 * @param location
	 * @return
	 */
	private Planet createRandomPlanet(final String name, final Point location) {
		final double sr = (Constants.MIN_SHIP_RATE + (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
				* Math.random());
		final int sc = (int) Math.round(sr * (Math.random() + .5));
		final double kr = (Math.random() * (Constants.MAX_KILL_RATE - Constants.MIN_KILL_RATE))
				+ Constants.MIN_KILL_RATE;
		return new Planet(name, null, location, sr, sc, kr);
	}

	/**
	 * @param planetCount
	 * @param owner
	 * @return
	 * @throws PlanetCountOutOfBoundException
	 */
	public Universe createUniverse(final int opponentCount,
			final int planetCount, final double reaction)
			throws PlanetCountOutOfBoundException {
		if (planetCount > MAX_PLANET_COUNT)
			throw new PlanetCountOutOfBoundException("The planet count "
					+ planetCount + " exceeds the maximum value "
					+ MAX_PLANET_COUNT);

		if (opponentCount > MAX_OWNER_COUNT)
			throw new PlanetCountOutOfBoundException("The opponent count "
					+ opponentCount + " exceeds the maximum value "
					+ MAX_OWNER_COUNT);

		if (opponentCount > planetCount - 1)
			throw new PlanetCountOutOfBoundException("The opponent count "
					+ opponentCount + " exceeds the maximum value "
					+ (planetCount - 1));

		final List<String> owners = new ArrayList<>(Arrays.asList(OWNER_NAMES));
		final List<String> planets = new ArrayList<>(
				Arrays.asList(PLANET_NAMES));
		Collections.shuffle(owners);
		Collections.shuffle(planets);

		final Universe universe = new Universe();
		final Human human = new Human(owners.get(0));
		universe.setHuman(human);
		universe.addOwner(human);
		universe.addPlanet(createPlanet(planets.get(0), human,
				universe.createLocation()));

		for (int i = 0; i < opponentCount; ++i) {
			final Owner o = new AutoOwner(owners.get(i + 1), reaction);
			universe.addOwner(o);
			universe.addPlanet(createPlanet(planets.get(i + 1), o,
					universe.createLocation()));
		}

		for (final String n : planets.subList(opponentCount + 1, planetCount))
			universe.addPlanet(createRandomPlanet(n, universe.createLocation()));

		return universe;
	}

	/**
	 * @return
	 */
	public int getMaxOwnerCount() {
		return MAX_OWNER_COUNT;
	}

	/**
	 * @return
	 */
	public int getMaxPlanetCount() {
		return MAX_PLANET_COUNT;
	}
}