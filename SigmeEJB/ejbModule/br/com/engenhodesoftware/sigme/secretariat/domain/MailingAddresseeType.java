package br.com.engenhodesoftware.sigme.secretariat.domain;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
public enum MailingAddresseeType {
	TO("MailingAddresseeType.label.TO"), CC("MailingAddresseeType.label.CC"), BCC("MailingAddresseeType.label.BCC");

	/** String that should be used as key for the label in i18n bundles. */
	private String labelKey;

	/**
	 * Constructor.
	 * 
	 * @param labelKey
	 */
	private MailingAddresseeType(String labelKey) {
		this.labelKey = labelKey;
	}

	/** Getter for labelKey. */
	public String getLabelKey() {
		return labelKey;
	}
}
