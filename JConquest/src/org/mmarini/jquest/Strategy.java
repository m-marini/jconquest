package org.mmarini.jquest;

import java.util.List;

/**
 * The concrete strategy of automatic owner
 * 
 * @author US00852
 * @version $Id: DefenseStateStrategy.java,v 1.1.2.1 2005/04/30 16:06:46 marco
 *          Exp $
 */
public class Strategy implements IStrategy {
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
	 * @see org.mmarini.jquest.IStrategy#apply(org.mmarini.jquest.IStrategyContext)
	 */
	@Override
	public void apply(IStrategyContext context) {
		StrategyContextAdapter ctx = new StrategyContextAdapter(context);
		this.defense(ctx);
		this.prepare(ctx);
		this.attack(ctx);
	}

	/**
	 * Attacks a planet.
	 * <p>
	 * The strategy of attack sends from the attack planet (the one with the
	 * lowest kill rate) a fleet to the best choice enemy planet (?).
	 * </p>
	 * 
	 * @param ctx
	 *            the strategy context
	 */
	protected void attack(StrategyContextAdapter ctx) {
		Planet planet = ctx.getAttackPlanet();
		if (planet == null)
			return;
		double defense = ctx.getMaxDamage() / 2 / (1 - planet.getKillRate())
				+ 1;
		double defenseRisk = 1 - Math.random() * RISK_RATE;
		int defenseShips = (int) Math.round(defense * defenseRisk);
		defenseShips = Math.max(MIN_DEFENSE_SHIP, defenseShips);
		int attackShip = planet.getShipCount() - defenseShips;
		if (attackShip <= 0) {
			return;
		}
		List<StrategyContextPlanet> targetList = ctx.getTarget(planet);
		for (StrategyContextPlanet target : targetList) {
			int maxShips = target.getShipToWin(planet) + defenseShips;
			int ships = Math.min(maxShips, attackShip);
			attackShip -= ships;
			try {
				planet.lunchFleet(ctx.getOwner(), target, ships);
			} catch (NoShipsException e) {
				e.printStackTrace();
			} catch (InvalidOwnerException e) {
				e.printStackTrace();
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
	protected void defense(StrategyContextAdapter ctx) {
		// double maxDamage = ctx.getMaxDamage();
		// List myPlanet = new ArrayList();
		// ctx.retriveMyPlanet(myPlanet);
		// /**
		// * Ordina i pianeti per capacità difensiva
		// */
		// Collections.sort(myPlanet, planetComp);
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
	protected void prepare(StrategyContextAdapter ctx) {
		Planet target = ctx.getAttackPlanet();
		if (target == null)
			return;
		double defense = ctx.getMaxDamage() / 2
				* (1 - Math.random() * RISK_RATE);

		List<StrategyContextPlanet> targetList = ctx.getMyPlanet();
		for (StrategyContextPlanet planet : targetList) {
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