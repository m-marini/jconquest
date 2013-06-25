package org.mmarini.jquest;

/**
 * @author US00852
 * @version $Id: InvalidOwnerException.java,v 1.1 2005/04/27 00:31:02 marco Exp
 *          $
 */
public class InvalidOwnerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4606330299532067048L;

	/**
	 * 
	 */
	public InvalidOwnerException() {
	}

	/**
	 * @param message
	 */
	public InvalidOwnerException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidOwnerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public InvalidOwnerException(Throwable cause) {
		super(cause);
	}
}