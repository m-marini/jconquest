package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is an adapter to a strategy context the add the functionality to retrive
 * the enemy planets, the owned planets, the native planets, the target planet,
 * the attack planet.
 * 
 * @author US00852
 * @version $Id: DefenseStrategyContextAdapter.java,v 1.1.2.1 2005/04/30
 *          16:06:46 marco Exp $
 */
public class StrategyContextAdapter {

	private final StrategyContext context;
	private final List<StrategyContextPlanet> myPlanet;
	private final List<StrategyContextPlanet> enemyPlanet;
	private final List<StrategyContextPlanet> nativePlanet;
	private double maxDamage;

	/**
	 * Construtcs an adapter
	 * 
	 * @param context
	 *            the adaptee strategy contexts
	 */
	public StrategyContextAdapter(StrategyContext context) {
		this.context = context;
		myPlanet = new ArrayList<>();
		nativePlanet = new ArrayList<>();
		enemyPlanet = new ArrayList<>();

		load();
	}

	/**
	 * @return
	 */
	public StrategyContextPlanet getAttackPlanet() {
		List<StrategyContextPlanet> list = getMyPlanet();
		StrategyContextPlanet attackPlanet = null;
		for (final StrategyContextPlanet planet : list)
			if (attackPlanet == null
					|| planet.getKillRate() < attackPlanet.getKillRate())
				attackPlanet = planet;
		return attackPlanet;
	}

	/**
	 * @return Returns the opponentPlanet.
	 */
	public List<StrategyContextPlanet> getEnemyPlanet() {
		return enemyPlanet;
	}

	/**
	 * @return
	 */
	public double getMaxDamage() {
		return maxDamage;
	}

	/**
	 * 
	 * @return
	 */
	public List<StrategyContextPlanet> getMyPlanet() {
		return myPlanet;
	}

	/**
	 * @return Returns the nativePlanet.
	 */
	public List<StrategyContextPlanet> getNativePlanet() {
		return nativePlanet;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getOwner()
	 */
	public Owner getOwner() {
		return context.getOwner();
	}

	/**
	 * @return
	 */
	public List<StrategyContextPlanet> getTarget(
			final StrategyContextPlanet from) {
		List<StrategyContextPlanet> target = new ArrayList<StrategyContextPlanet>(
				getNativePlanet());
		target.addAll(getEnemyPlanet());
		Collections.sort(target, new Comparator<StrategyContextPlanet>() {

			@Override
			public int compare(StrategyContextPlanet o1,
					StrategyContextPlanet o2) {
				return o1.getTargetEvaluation(from).compareTo(
						o2.getTargetEvaluation(from));
			}
		});
		return target;
	}

	/**
	 * @see org.mmarini.jquest.StrategyContext#getUniverse()
	 */
	public Universe getUniverse() {
		return context.getUniverse();
	}

	/**
	 * 
	 */
	private void load() {
		final Universe universe = getUniverse();
		final Owner owner = getOwner();
		maxDamage = 0;
		for (final Planet pl : universe.getPlanets()) {
			final StrategyContextPlanet planet = new StrategyContextPlanet(pl);
			final Owner planetOwner = pl.getOwner();
			if (planetOwner == null)
				nativePlanet.add(planet);
			else if (planetOwner.equals(owner))
				myPlanet.add(planet);
			else {
				enemyPlanet.add(planet);
				final double damage = planet.getDefense();
				if (maxDamage < damage)
					maxDamage = damage;
			}
		}
	}
}