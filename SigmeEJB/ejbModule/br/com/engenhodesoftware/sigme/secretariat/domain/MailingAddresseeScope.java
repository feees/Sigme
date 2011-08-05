package br.com.engenhodesoftware.sigme.secretariat.domain;

/**
 * Enumeration of possible scopes of a scoped addressee. Possible scopes are: spiritists which are currently attending
 * the institution, spiritists that have attended in the past, all of the above.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.ScopedMailingAddressee
 */
public enum MailingAddresseeScope {
	
	/** All spiritists associated with the institution, either currently or in the past. */
	ALL("MailingAddresseeScope.label.ALL"), 
	
	/** Spiritists that are currently attending the institution. */
	ACTIVE("MailingAddresseeScope.label.ACTIVE"), 
	
	/** Spiritist that have attended the institution in the past, but are not longer doing so. */
	INACTIVE("MailingAddresseeScope.label.INACTIVE");

	/** String that should be used as key for the label in i18n bundles. */
	private String labelKey;

	/** Constructor using fields. */
	private MailingAddresseeScope(String labelKey) {
		this.labelKey = labelKey;
	}

	/** Getter for labelKey. */
	public String getLabelKey() {
		return labelKey;
	}
}
