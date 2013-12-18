package org.mmarini.jquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The concrete strategy of automatic owner
 * 
 * @author US00852
 * @version $Id: DefenseStateStrategy.java,v 1.1.2.1 2005/04/30 16:06:46 marco
 *          Exp $
 */
public class StrategyImpl implements Strategy {
	private static final Logger logger = LoggerFactory
			.getLogger(StrategyImpl.class);
	/**
	 * The minimum number of ships to attack
	 */
	public static final int MIN_ATTACK_SHIP = 5;

	/**
	 * The minimum number of ships kept to defense a planet
	 */
	public static final int MIN_DEFENSE_SHIP = 10;

	/**
	 * The risk rate used to randomise the strategy
	 */
	public static final double RISK_RATE = 0.1;

	/**
	 * The current behaviour of the apply is in order:
	 * <ul>
	 * <li>defense the owned planets</li>
	 * <li>prepare to attack</li>
	 * <li>attack the enemies to conquest the planets</li>
	 * </ul>
	 * 
	 * @see org.mmarini.jquest.Strategy#apply(org.mmarini.jquest.StrategyContext)
	 */
	@Override
	public void apply(StrategyContext context) {
		defense(context);
		prepare(context);
		attack(context);
	}

	/**
	 * Attacks a planet.
	 * <p>
	 * The strategy of attack sends from the attack planet (the one with the
	 * lowest kill rate) a fleet to the best choice enemy planet (?).
	 * </p>
	 * 
	 * @param context
	 *            the strategy contexts
	 */
	private void attack(final StrategyContext context) {
		Planet planet = getAttackPlanet(context);
		if (planet == null)
			return;
		double defense = getMaxDamage(context) / 2 / (1 - planet.getKillRate())
				+ 1;
		double defenseRisk = 1 - Math.random() * RISK_RATE;
		int defenseShips = (int) Math.round(defense * defenseRisk);
		defenseShips = Math.max(MIN_DEFENSE_SHIP, defenseShips);
		int attackShip = planet.getShipCount() - defenseShips;
		if (attackShip <= 0) {
			return;
		}
		List<Planet> targetList = getTarget(context, planet);
		for (Planet target : targetList) {
			int maxShips = getShipToWin(target, planet) + defenseShips;
			int ships = Math.min(maxShips, attackShip);
			attackShip -= ships;
			try {
				planet.lunchFleet(context.getOwner(), target, ships);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Defense stratetegy.
	 * <p>
	 * The defense strategy balances the ships in the owned planets in order to
	 * have the minimum required defense level for any planets.
	 * </p>
	 * <p>
	 * The way to do this is to divide the owned planets in two sets:
	 * <ul>
	 * <li>The weak planets with the the ships count less than the required</li>
	 * <li>The strong planets with the the ships count greater than the
	 * required.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Than the two sets are iterated to send data from the strong planet to the
	 * weak planet to send the exceedening ships to reach the minimum required
	 * defese level. In the current level of defense is to be considered alse
	 * the fleet lunched to the weak planet.
	 * </p>
	 * 
	 * @param ctx
	 *            the strategy context
	 */
	private void defense(StrategyContext ctx) {
		// double maxDamage = ctx.getMaxDamage();
		// List myPlanet = new ArrayList();
		// ctx.retriveMyPlanet(myPlanet);
		// /**
		// * Ordina i pianeti per capacità difensiva
		// */
		// Collections.sort(myPlanet, planetComp);
	}

	/**
	 * 
	 * @param ctx
	 * @return
	 */
	private Planet getAttackPlanet(StrategyContext ctx) {
		Planet t = null;
		for (final Planet p : ctx.getMyPlanet())
			if (t == null || p.getKillRate() < t.getKillRate())
				t = p;
		return t;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private double getDefense(final Planet p) {
		final double s = (p.getOwner() != null) ? p.getShipCount() : p
				.getShipRate() * 1.5;
		return s * (1 - p.getKillRate());
	}

	private double getMaxDamage(StrategyContext ctx) {
		double x = 0;
		for (final Planet p : ctx.getEnemyPlanet()) {
			final double d = getDefense(p);
			if (x < d)
				x = d;
		}
		return x;
	}

	/**
	 * 
	 * @param target
	 * @param from
	 * @return
	 */
	private int getShipToWin(final Planet target, final Planet from) {
		double shipToWin = getDefense(target) / (1 + from.getKillRate());
		if (target.getOwner() != null)
			shipToWin += getTimeToArrive(target, from) * target.getShipRate();
		return (int) Math.round(shipToWin + 1);
	}

	/**
	 * 
	 * @param context
	 * @param planet
	 * @return
	 */
	private List<Planet> getTarget(final StrategyContext context,
			final Planet planet) {
		final List<Planet> target = new ArrayList<Planet>(
				context.getNativePlanet());
		target.addAll(context.getEnemyPlanet());
		Collections.sort(target, new Comparator<Planet>() {

			@Override
			public int compare(final Planet p, final Planet q) {
				final int scd = compareShipCount(p, q);
				if (scd != 0)
					return scd;
				final int srd = compareShipRate(p, q);
				if (srd != 0)
					return srd;
				return compareKillRate(p, q);
			}

			private int compareDouble(final double d) {
				return (d < 0) ? -1 : (d > 0) ? 1 : 0;
			}

			private int compareKillRate(final Planet p, final Planet q) {
				return compareDouble(p.getKillRate() - q.getKillRate());
			}

			private int compareShipCount(final Planet p, final Planet q) {
				return getShipToWin(p) - getShipToWin(q);
			}

			private int compareShipRate(final Planet p, final Planet q) {
				return compareDouble(p.getShipRate() - q.getShipRate());
			}

			private int getShipToWin(Planet p) {
				double shipToWin = getDefense(p) / (1 + planet.getKillRate());
				if (p.getOwner() != null)
					shipToWin += getTimeToArrive(p, planet) * p.getShipRate();
				return (int) Math.round(shipToWin + 1);
			}
		});
		return target;
	}

	/**
	 * 
	 * @param target
	 * @param from
	 * @return
	 */
	private double getTimeToArrive(Planet target, Planet from) {
		final double dist = target.getLocation().distance(from.getLocation());
		return dist * Constants.FLEET_SPEED;
	}

	/**
	 * Prepares to attack.
	 * <p>
	 * The prepare strategy consists of sending all exceeding ships from the
	 * owned planets to the attack planet (the planet where the attack will
	 * start from, the one with the lowest kill rate).
	 * </p>
	 * 
	 * @param ctx
	 *            the stratey context
	 */
	private void prepare(StrategyContext ctx) {
		Planet target = getAttackPlanet(ctx);
		if (target == null)
			return;
		double defense = getMaxDamage(ctx) / 2
				* (1 - Math.random() * RISK_RATE);

		for (Planet planet : ctx.getMyPlanet()) {
			if (!planet.equals(target)) {
				int defenseShips = (int) Math.round(defense
						/ (1 - target.getKillRate()) + 1);
				defenseShips = Math.max(MIN_DEFENSE_SHIP, defenseShips);
				int transferShip = planet.getShipCount() - defenseShips;
				if (transferShip > 0) {
					try {
						planet.lunchFleet(ctx.getOwner(), target, transferShip);
					} catch (NoShipsException e) {
						e.printStackTrace();
					} catch (InvalidOwnerException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}