package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: NoShipsException.java,v 1.1 2005/04/27 00:31:02 marco Exp $
 */
public class NoShipsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9137576649776925680L;

	/**
	 * 
	 */
	public NoShipsException() {
	}

	/**
	 * @param message
	 */
	public NoShipsException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoShipsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public NoShipsException(Throwable cause) {
		super(cause);
	}
}