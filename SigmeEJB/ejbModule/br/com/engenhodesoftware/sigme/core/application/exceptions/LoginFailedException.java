package br.com.engenhodesoftware.sigme.core.application.exceptions;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class LoginFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Reason for the failed login. */
	private LoginFailedReason reason;

	/**
	 * Constructor.
	 * 
	 * @param reason
	 *          Reason for the failed login.
	 */
	public LoginFailedException(LoginFailedReason reason) {
		this.reason = reason;
	}

	/**
	 * Constructor.
	 * 
	 * @param reason
	 *          Reason for the failed login.
	 * 
	 * @see java.lang.Exception#Exception(java.lang.Throwable)
	 */
	public LoginFailedException(Throwable t, LoginFailedReason reason) {
		super(t);
		this.reason = reason;
	}

	/** Getter for reason. */
	public LoginFailedReason getReason() {
		return reason;
	}
}
