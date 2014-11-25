package br.org.feees.sigme.core.exceptions;

/**
 * Application exception that represents the fact that the spiritist could not be authenticated.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
	 * @version 1.0
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
	
	/**
	 * Enumeration of the reasons that a spiritist's authentication might fail.
	 *
	 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
	 * @version 1.0
	 */
	public enum LoginFailedReason {
		/** The provided username is unknown, i.e., there's no spiritist with the given username in the database. */
		UNKNOWN_USERNAME("unknownUsername"),

		/** The provided password is incorrect. The spiritist with the given username has a different password. */
		INCORRECT_PASSWORD("incorrectPassword"),

		/** Multiple spiritists with the given username exist in the database, which is an inconsistency! */
		MULTIPLE_USERS("multipleUsers"),

		/** The application could not produce the MD5 hash of the given password to match with the spiritist's password. */
		MD5_ERROR("md5Error"),

		/** Sigme itself is OK with the authentication, but the Java EE container is not, responding with an exception. */
		CONTAINER_REJECTED("containerRejected"),

		/** Sigme could not check with the container if the authentication is OK because the HTTP request doesn't exist. */
		NO_HTTP_REQUEST("noHttpRequest");

		/** A unique identifier for the reason. */
		private String id;

		/** Constructor using fields. */
		private LoginFailedReason(String id) {
			this.id = id;
		}

		/** @see java.lang.Enum#toString() */
		@Override
		public String toString() {
			return id;
		}
	}
}
