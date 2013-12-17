package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * This is an adapter to a strategy context the add the functionality to retrive
 * the enemy planets, the owned planets, the native planets, the target planet,
 * the attack planet.
 * <p>
 * It implements a lazy load pattern to access the data.
 * </p>
 * 
 * @author US00852
 * @version $Id: DefenseStrategyContextAdapter.java,v 1.1.2.1 2005/04/30
 *          16:06:46 marco Exp $
 */
public class StrategyContextAdapter extends AbstractStrategyContextAdapter {
	/**
	 * @author US00852
	 * @version $Id: StrategyContextAdapter.java,v 1.2 2006/03/16 22:35:24 marco
	 *          Exp $
	 */
	class TargetComparator implements Comparator<StrategyContextPlanet> {
		private Planet from;

		/**
		 * @param from
		 */
		public TargetComparator(Planet from) {
			this.from = from;
		}

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(StrategyContextPlanet p1, StrategyContextPlanet p2) {
			Planet from = this.getFrom();
			return p1.getTargetEvaluation(from).compareTo(
					p2.getTargetEvaluation(from));
		}

		/**
		 * @return Returns the from.
		 */
		protected Planet getFrom() {
			return from;
		}
	}

	private Double maxDamage;
	private List<StrategyContextPlanet> myPlanet;
	private List<StrategyContextPlanet> enemyPlanet;

	private List<StrategyContextPlanet> nativePlanet;

	/**
	 * Construtcs an adapter
	 * 
	 * @param context
	 *            the adaptee strategy contexts
	 */
	public StrategyContextAdapter(IStrategyContext context) {
		super(context);
	}

	/**
	 * @return
	 */
	public Planet getAttackPlanet() {
		List<StrategyContextPlanet> list = this.getMyPlanet();
		Planet attackPlanet = null;
		for (Iterator<StrategyContextPlanet> iter = list.iterator(); iter
				.hasNext();) {
			Planet planet = iter.next();
			if (attackPlanet == null
					|| planet.getKillRate() < attackPlanet.getKillRate()) {
				attackPlanet = planet;
			}
		}
		return attackPlanet;
	}

	/**
	 * @return Returns the opponentPlanet.
	 */
	public List<StrategyContextPlanet> getEnemyPlanet() {
		if (enemyPlanet == null)
			load();
		return enemyPlanet;
	}

	/**
	 * @return
	 */
	public double getMaxDamage() {
		if (maxDamage == null) {
			load();
		}
		return this.maxDamage.doubleValue();
	}

	public List<StrategyContextPlanet> getMyPlanet() {
		if (myPlanet == null) {
			load();
		}
		return myPlanet;
	}

	/**
	 * @return Returns the nativePlanet.
	 */
	public List<StrategyContextPlanet> getNativePlanet() {
		if (nativePlanet == null)
			load();
		return nativePlanet;
	}

	/**
	 * @return
	 */
	public List<StrategyContextPlanet> getTarget(Planet from) {
		List<StrategyContextPlanet> target = new ArrayList<StrategyContextPlanet>(
				this.getNativePlanet());
		target.addAll(this.getEnemyPlanet());
		Comparator<StrategyContextPlanet> targetComparator = new TargetComparator(
				from);
		Collections.sort(target, targetComparator);
		// for (Iterator iter = target.iterator(); iter.hasNext();) {
		// System.out.println( ((StrategyContextPlanet) iter.next())
		// .getTargetEvaluation(from));
		// }
		return target;
	}

	/**
	 * 
	 */
	protected void load() {
		List<StrategyContextPlanet> myPlanet = new ArrayList<StrategyContextPlanet>();
		List<StrategyContextPlanet> opponentPlanet = new ArrayList<StrategyContextPlanet>();
		List<StrategyContextPlanet> nativePlanet = new ArrayList<StrategyContextPlanet>();
		Universe universe = this.getUniverse();
		IOwner owner = this.getOwner();
		double maxDamage = 0;
		for (Planet pl : universe.getPlanets()) {
			StrategyContextPlanet planet = new StrategyContextPlanet(pl);
			IOwner planetOwner = planet.getOwner();
			if (planetOwner != null) {
				if (planetOwner.equals(owner)) {
					myPlanet.add(planet);
				} else {
					opponentPlanet.add(planet);
					double damage = planet.getDefense();
					if (maxDamage < damage)
						maxDamage = damage;
				}
			} else {
				nativePlanet.add(planet);
			}
		}
		this.maxDamage = new Double(maxDamage);
		this.myPlanet = myPlanet;
		this.nativePlanet = nativePlanet;
		this.enemyPlanet = opponentPlanet;
	}
}