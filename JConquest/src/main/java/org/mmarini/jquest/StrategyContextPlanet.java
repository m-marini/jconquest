package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: StrategyContextPlanet.java,v 1.1.2.1 2005/05/01 10:01:38 marco
 *          Exp $
 */
public class StrategyContextPlanet {
	class TargetEvaluation implements Comparable<TargetEvaluation> {
		private static final int CLASS_COUNT = 10;
		private static final double KILL_RATE_CLASS_SIZE = (Constants.MAX_KILL_RATE - Constants.MIN_KILL_RATE)
				/ CLASS_COUNT;
		private static final double SHIP_RATE_CLASS_SIZE = (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
				/ CLASS_COUNT;
		private static final double SHIP_CLASS_SIZE = 2d;

		private final StrategyContextPlanet planet;
		private final int shipToWin;

		/**
		 * @param planet
		 * @param from
		 */
		public TargetEvaluation(StrategyContextPlanet planet,
				StrategyContextPlanet from) {
			this.planet = planet;
			shipToWin = planet.getShipToWin(from);
		}

		/**
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(TargetEvaluation other) {
			int diff;
			diff = getShipClass() - other.getShipClass();
			if (diff != 0)
				return diff;
			diff = getShipRateClass() - other.getShipRateClass();
			if (diff != 0)
				return diff;
			diff = this.getKillRateClass() - other.getKillRateClass();
			return diff;
		}

		protected int getClassValue(double value, double offset,
				double classSize) {
			return (int) Math.floor(value / classSize);
		}

		protected int getKillRateClass() {
			int classId = getClassValue(planet.getKillRate(),
					Constants.MIN_SHIP_RATE, KILL_RATE_CLASS_SIZE);
			return classId;
		}

		protected int getShipClass() {
			int classId = getClassValue(shipToWin, 0, SHIP_CLASS_SIZE);
			return classId;
		}

		protected int getShipRateClass() {
			int classId = getClassValue(planet.getShipRate(),
					Constants.MIN_SHIP_RATE, SHIP_RATE_CLASS_SIZE);
			return -classId;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.valueOf(planet);
		}
	}

	private final Planet planet;

	/**
	 * @param planet
	 */
	public StrategyContextPlanet(final Planet planet) {
		this.planet = planet;
	}

	/**
	 * @return
	 */
	public double getDefense() {
		double shipCount;
		if (getOwner() != null)
			shipCount = getShipCount();
		else
			shipCount = getShipRate() * 1.5;
		return shipCount * (1 - getKillRate());
	}

	public int getShipToWin(StrategyContextPlanet from) {
		double shipToWin = this.getDefense() / (1 + from.getKillRate());
		if (getOwner() != null)
			shipToWin += getTimeToArrive(from) * this.getShipRate();
		return (int) Math.round(shipToWin + 1);
	}

	/**
	 * 
	 * @param from
	 * @return
	 */
	public TargetEvaluation getTargetEvaluation(StrategyContextPlanet from) {
		return new TargetEvaluation(this, from);
	}

	/**
	 * 
	 * @param from
	 * @return
	 */
	public double getTimeToArrive(StrategyContextPlanet from) {
		double dist = getLocation().distance(from.getLocation());
		return dist * Constants.FLEET_SPEED;
	}

	/**
	 * @return
	 * @see org.mmarini.jquest.Planet#getKillRate()
	 */
	public double getKillRate() {
		return planet.getKillRate();
	}

	/**
	 * @return
	 * @see org.mmarini.jquest.Planet#getShipRate()
	 */
	public double getShipRate() {
		return planet.getShipRate();
	}

	/**
	 * @return
	 * @see org.mmarini.jquest.Planet#getShipCount()
	 */
	public int getShipCount() {
		return planet.getShipCount();
	}

	/**
	 * @return
	 * @see org.mmarini.jquest.Planet#getOwner()
	 */
	public Owner getOwner() {
		return planet.getOwner();
	}

	/**
	 * @return
	 * @see org.mmarini.jquest.Planet#getLocation()
	 */
	public Point getLocation() {
		return planet.getLocation();
	}

	/**
	 * @param owner
	 * @param destination
	 * @param ships
	 * @return
	 * @throws NoShipsException
	 * @throws InvalidOwnerException
	 * @see org.mmarini.jquest.Planet#lunchFleet(org.mmarini.jquest.Owner, org.mmarini.jquest.Planet, int)
	 */
	public Fleet lunchFleet(Owner owner, Planet destination, int ships)
			throws NoShipsException, InvalidOwnerException {
		return planet.lunchFleet(owner, destination, ships);
	}

	/**
	 * @return the planet
	 */
	public Planet getPlanet() {
		return planet;
	}
}