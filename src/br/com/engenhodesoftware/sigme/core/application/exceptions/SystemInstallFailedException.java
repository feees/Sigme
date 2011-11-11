package br.com.engenhodesoftware.sigme.core.application.exceptions;

/**
 * Application exception that represents the fact that the system installation process has failed to complete.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemService
 */
public class SystemInstallFailedException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public SystemInstallFailedException(Throwable t) {
		super(t);
	}
}
