package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: IUniverseObject.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public interface IUniverseObject {
	public abstract void attach(Universe universe);

	public abstract void detach(Universe universe);

	public abstract Universe getUniverse();
}