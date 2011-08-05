package br.com.engenhodesoftware.sigme.core.application.exceptions;

/**
 * Application exception that represents the fact that the spiritist could not be authenticated.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public class LoginFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Reason for the failed login. */
	private LoginFailedReason reason;

	/** Constructor using fields. */
	public LoginFailedException(LoginFailedReason reason) {
		this.reason = reason;
	}

	/** Constructor from superclass, using fields. */
	public LoginFailedException(Throwable t, LoginFailedReason reason) {
		super(t);
		this.reason = reason;
	}

	/** Getter for reason. */
	public LoginFailedReason getReason() {
		return reason;
	}
}
