package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: AbstractOwner.java,v 1.2 2006/03/16 22:35:24 marco Exp $
 */
public abstract class AbstractOwner extends AbstractUniverseObject implements
		Owner {
	private final String name;

	/**
	 * @param name
	 */
	public AbstractOwner(final String name) {
		this.name = name;
	}

	/**
	 * @see org.mmarini.jquest.Owner#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(name);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractOwner other = (AbstractOwner) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}