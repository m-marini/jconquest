package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author US00852
 * @version $Id: StrategyContext.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public class StrategyContextImpl implements StrategyContext {
	private final Owner owner;

	/**
	 * @param owner
	 */
	public StrategyContextImpl(final Owner owner) {
		this.owner = owner;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getEnemyPlanet()
	 */
	@Override
	public List<Planet> getEnemyPlanet() {
		final List<Planet> l = new ArrayList<>();
		for (final Planet p : owner.getUniverse().getPlanets()) {
			final Owner o = p.getOwner();
			if (o != null && !o.equals(owner))
				l.add(p);
		}
		return l;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getMyPlanet()
	 */
	@Override
	public List<Planet> getMyPlanet() {
		final List<Planet> l = new ArrayList<>();
		for (final Planet p : owner.getUniverse().getPlanets())
			if (owner.equals(p.getOwner()))
				l.add(p);
		return l;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getNativePlanet()
	 */
	@Override
	public List<Planet> getNativePlanet() {
		final List<Planet> l = new ArrayList<>();
		for (final Planet p : owner.getUniverse().getPlanets())
			if (p.getOwner() == null)
				l.add(p);
		return l;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getOwner()
	 */
	@Override
	public Owner getOwner() {
		return owner;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return owner.getUniverse();
	}
}