package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AbstractOwner.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public abstract class AbstractOwner extends AbstractUniverseObject implements
		IOwner {
	private String name;
	private int ordinal;

	/**
	 * @param name
	 */
	public AbstractOwner(int ordinal, String name) {
		this.name = name;
		this.ordinal = ordinal;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof AbstractOwner))
			return false;
		return this.getOrdinal() == ((AbstractOwner) other).getOrdinal();
	}

	/**
	 * @see org.mmarini.jquest.IOwner#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see org.mmarini.jquest.IOwner#getOrdinal()
	 */
	@Override
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getOrdinal();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}
}