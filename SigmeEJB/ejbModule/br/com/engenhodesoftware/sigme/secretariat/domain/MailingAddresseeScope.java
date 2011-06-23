package br.com.engenhodesoftware.sigme.secretariat.domain;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
public enum MailingAddresseeScope {
	ALL("MailingAddresseeScope.label.ALL"), ACTIVE("MailingAddresseeScope.label.ACTIVE"), INACTIVE("MailingAddresseeScope.label.INACTIVE");

	/** String that should be used as key for the label in i18n bundles. */
	private String labelKey;

	/**
	 * Constructor.
	 * 
	 * @param labelKey
	 */
	private MailingAddresseeScope(String labelKey) {
		this.labelKey = labelKey;
	}

	/** Getter for labelKey. */
	public String getLabelKey() {
		return labelKey;
	}
}
