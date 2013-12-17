package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: StrategyContextPlanet.java,v 1.1.2.1 2005/05/01 10:01:38 marco
 *          Exp $
 */
public class StrategyContextPlanet extends AbstractPlanetAdapter {
	class TargetEvaluation implements Comparable<TargetEvaluation> {
		private static final int CLASS_COUNT = 10;
		private static final double KILL_RATE_CLASS_SIZE = (Constants.MAX_KILL_RATE - Constants.MIN_KILL_RATE)
				/ CLASS_COUNT;
		private static final double SHIP_RATE_CLASS_SIZE = (Constants.MAX_SHIP_RATE - Constants.MIN_SHIP_RATE)
				/ CLASS_COUNT;
		private static final double SHIP_CLASS_SIZE = 2d;

		private StrategyContextPlanet planet;
		private int shipToWin;

		/**
		 * @param planet
		 * @param from
		 */
		public TargetEvaluation(StrategyContextPlanet planet, Planet from) {
			this.planet = planet;
			shipToWin = planet.getShipToWin(from);
		}

		/**
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(TargetEvaluation other) {
			int diff;
			diff = this.getShipClass() - other.getShipClass();
			if (diff != 0)
				return diff;
			diff = this.getShipRateClass() - other.getShipRateClass();
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
			int classId = this.getClassValue(planet.getKillRate(),
					Constants.MIN_SHIP_RATE, KILL_RATE_CLASS_SIZE);
			return classId;
		}

		protected int getShipClass() {
			int classId = this.getClassValue(shipToWin, 0, SHIP_CLASS_SIZE);
			return classId;
		}

		protected int getShipRateClass() {
			int classId = this.getClassValue(planet.getShipRate(),
					Constants.MIN_SHIP_RATE, SHIP_RATE_CLASS_SIZE);
			return -classId;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer bfr = new StringBuffer();
			bfr.append(planet.getName());
			bfr.append(" value = ");
			bfr.append(this.shipToWin);
			bfr.append(", ");
			bfr.append(this.planet.getShipRate());
			bfr.append(", ");
			bfr.append(this.planet.getKillRate());
			return bfr.toString();
		}
	};

	/**
	 * @param planet
	 */
	public StrategyContextPlanet(Planet planet) {
		super(planet);
	}

	/**
	 * @return
	 */
	public double getDefense() {
		double shipCount;
		if (this.getOwner() != null)
			shipCount = this.getShipCount();
		else
			shipCount = this.getShipRate() * 1.5;
		return shipCount * (1 - this.getKillRate());
	}

	public int getShipToWin(Planet from) {
		double shipToWin = this.getDefense() / (1 + from.getKillRate());
		if (this.getOwner() != null)
			shipToWin += getTimeToArrive(from) * this.getShipRate();
		return (int) Math.round(shipToWin + 1);
	}

	public TargetEvaluation getTargetEvaluation(Planet from) {
		return new TargetEvaluation(this, from);
	}

	public double getTimeToArrive(Planet from) {
		double dist = this.getLocation().distance(from.getLocation());
		return dist * Constants.FLEET_SPEED;
	}
}