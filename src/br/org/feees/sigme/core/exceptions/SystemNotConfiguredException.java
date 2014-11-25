package br.org.feees.sigme.core.exceptions;

/**
 * Application exception that represents the fact that the system is not configured (the current configuration could not be loaded).
 *
 * Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public class SystemNotConfiguredException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public SystemNotConfiguredException(Throwable t) {
		super(t);
	}
}
