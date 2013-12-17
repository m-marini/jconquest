package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AbstractUniverseObject.java,v 1.1 2005/04/27 00:31:02 marco Exp
 *          $
 */
public abstract class AbstractUniverseObject implements UniverseObject {
	private Universe universe;

	/**
	 * @see org.mmarini.jquest.UniverseObject#attach(org.mmarini.jquest.Universe)
	 */
	@Override
	public void attach(Universe universe) {
		this.setUniverse(universe);
	}

	/**
	 * @see org.mmarini.jquest.UniverseObject#detach(org.mmarini.jquest.Universe)
	 */
	@Override
	public void detach(Universe universe) {
		this.setUniverse(null);
	}

	/**
	 * @see org.mmarini.jquest.UniverseObject#getUniverse()
	 */
	@Override
	public Universe getUniverse() {
		return universe;
	}

	/**
	 * @param universe
	 *            The universe to set.
	 */
	protected void setUniverse(Universe universe) {
		this.universe = universe;
	}
}