package br.com.engenhodesoftware.sigme.core.application.exceptions;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public enum LoginFailedReason {
	UNKNOWN_USERNAME("unknownUsername"), INCORRECT_PASSWORD("incorrectPassword"), MULTIPLE_USERS("multipleUsers"), MD5_ERROR("md5Error"), CONTAINER_REJECTED("containerRejected"), NO_HTTP_REQUEST("noHttpRequest");

	/** A unique identifier for the reason. */
	private String id;

	/** Constructor. */
	private LoginFailedReason(String id) {
		this.id = id;
	}

	/** @see java.lang.String#toString() */
	@Override
	public String toString() {
		return id;
	}
}
