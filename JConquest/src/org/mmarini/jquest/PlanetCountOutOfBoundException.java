package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: PlanetCountOutOfBoundException.java,v 1.2 2006/03/16 22:35:24
 *          marco Exp $
 */
public class PlanetCountOutOfBoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4061330632949739322L;

	/**
	 * 
	 */
	public PlanetCountOutOfBoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public PlanetCountOutOfBoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PlanetCountOutOfBoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public PlanetCountOutOfBoundException(Throwable cause) {
		super(cause);
	}

}