package br.org.feees.sigme.secretariat.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
public class EmailDelivery extends PersistentObjectSupport implements Comparable<EmailDelivery> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** TODO: document this field. */
	@ManyToOne(cascade = CascadeType.MERGE)
	private Mailing mailing;

	/** TODO: document this field. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String recipientEmail;

	/** TODO: document this field. */
	@Basic
	private Boolean messageSent = Boolean.FALSE;

	/** TODO: document this field. */
	@Basic
	private String errorMessage;

	/** Constructor (protected, for JPA only). */
	protected EmailDelivery() {}

	/** Constructor. */
	public EmailDelivery(Mailing mailing, String recipientEmail) {
		this.mailing = mailing;
		this.recipientEmail = recipientEmail;

		if (mailing != null)
			mailing.addDelivery(this);
	}

	/** Getter for mailing. */
	public Mailing getMailing() {
		return mailing;
	}

	/** Setter for mailing. */
	public void setMailing(Mailing mailing) {
		this.mailing = mailing;
	}

	/** Getter for recipientEmail. */
	public String getRecipientEmail() {
		return recipientEmail;
	}

	/** Setter for recipientEmail. */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	/** Getter for messageSent. */
	public Boolean isMessageSent() {
		return messageSent;
	}

	/** Getter for messageSent. */
	public Boolean getMessageSent() {
		return messageSent;
	}

	/** Setter for messageSent. */
	public void setMessageSent(Boolean messageSent) {
		this.messageSent = messageSent;
	}

	/** Getter for errorMessage. */
	public String getErrorMessage() {
		return errorMessage;
	}

	/** Setter for errorMessage. */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(EmailDelivery o) {
		// Compares by email address.
		return recipientEmail.compareTo(o.recipientEmail);
	}
}
