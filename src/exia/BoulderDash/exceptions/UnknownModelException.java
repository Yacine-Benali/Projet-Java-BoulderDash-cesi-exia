package exia.BoulderDash.exceptions;


/**
 * UnknownModelException
 *
 * Raises an 'UnknownSpriteException' exception.
 * Given the exception message.
 *
 */
public class UnknownModelException extends Exception {
	/**
	 * Class constructor
	 *
	 * @param  message  Exception backtrace message
	 */
	public UnknownModelException(String message) {
		super(message);
	}
}
