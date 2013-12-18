package org.mmarini.jquest;

import java.util.List;

/**
 * The strategy context is the interface used by concrete Strategy to interact
 * with the universe
 * 
 * @author US00852
 * @version $Id: IStrategyContext.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public interface StrategyContext {

	/**
	 * Gets the owner subject of the strategy
	 * 
	 * @return the owner
	 */
	public abstract Owner getOwner();

	/**
	 * Gets the universe
	 * 
	 * @return the universe
	 */
	public abstract Universe getUniverse();

	/**
	 * 
	 * @return
	 */
	public abstract List<Planet> getMyPlanet();

	/**
	 * 
	 * @return
	 */
	public abstract List<Planet> getNativePlanet();

	/**
	 * 
	 * @return
	 */
	public abstract List<Planet> getEnemyPlanet();
}