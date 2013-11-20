package br.org.feees.sigme.core.domain;

/**
 * Exception that is thrown when an invalid tax code is assigned to a spiritist.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public class InvalidTaxCodeException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
	
	/** The invalid tax code that was assigned. */
	private String taxCode;

	/** Constructor. */
	public InvalidTaxCodeException(String taxCode) {
		this.taxCode = taxCode;
	}

	/** Getter for taxCode. */
	public String getTaxCode() {
		return taxCode;
	}
	
	/** @see java.lang.Throwable#getMessage() */
	@Override
	public String getMessage() {
		return "Invalid tax code: " + taxCode;
	}
}
