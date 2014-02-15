package br.org.feees.sigme.core.application;

/**
 * Application exception that represents the fact that someone is trying to register using an e-mail that has already
 * been registered.
 * 
 * Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.code.application.RegisterService
 */
public class EmailAlreadyRegisteredException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** E-mail address that was attempted but is already registered. */
	private String email;

	/** Constructor. */
	public EmailAlreadyRegisteredException(String email) {
		super();
		this.email = email;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}
}
