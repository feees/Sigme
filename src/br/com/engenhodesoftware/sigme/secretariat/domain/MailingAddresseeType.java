package br.com.engenhodesoftware.sigme.secretariat.domain;

/**
 * Enumeration of possible types of mailing addressees. Types of addressees specify if the message recipients should be
 * added to the "To:", "Cc:" or "Bcc:" fields.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
public enum MailingAddresseeType {
	/** Recipients should be added to the "To:" field. */
	TO("MailingAddresseeType.label.TO"), 
	
	/** Recipients should be added to the "Cc:" field. */
	CC("MailingAddresseeType.label.CC"), 
	
	/** Recipients should be added to the "Bcc:" field. */
	BCC("MailingAddresseeType.label.BCC");

	/** String that should be used as key for the label in i18n bundles. */
	private String labelKey;

	/** Constructor using fields. */
	private MailingAddresseeType(String labelKey) {
		this.labelKey = labelKey;
	}

	/** Getter for labelKey. */
	public String getLabelKey() {
		return labelKey;
	}
}
