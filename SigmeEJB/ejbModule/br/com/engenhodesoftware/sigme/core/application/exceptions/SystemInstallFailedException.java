package br.com.engenhodesoftware.sigme.core.application.exceptions;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class SystemInstallFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @see java.lang.Exception#Exception(java.lang.Throwable)
	 */
	public SystemInstallFailedException(Throwable t) {
		super(t);
	}
}
