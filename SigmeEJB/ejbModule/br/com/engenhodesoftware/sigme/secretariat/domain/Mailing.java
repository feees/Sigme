package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
public class Mailing extends PersistentObjectSupport implements Comparable<Mailing> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Recipient mailing lists of this mailing. */
	@OneToMany
	private Set<MailingList> recipients = new HashSet<MailingList>();

	/** Subject of the message. */
	@Basic
	@NotNull
	@Size(max = 255)
	private String subject;

	/** Content of the message. */
	@Lob
	private String body;

	/** Date/time the message was sent. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date sentDate;

	/** Default constructor. */
	public Mailing() {}

	/**
	 * Constructor.
	 * 
	 * @param recipients
	 * @param subject
	 * @param body
	 */
	public Mailing(Set<MailingList> recipients, String subject, String body) {
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
	}

	/** Getter for recipients. */
	public Set<MailingList> getRecipients() {
		return recipients;
	}

	/** Setter for recipients. */
	public void setRecipients(Set<MailingList> recipients) {
		this.recipients = recipients;
	}

	/** Getter for subject. */
	public String getSubject() {
		return subject;
	}

	/** Setter for subject. */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** Getter for body. */
	public String getBody() {
		return body;
	}

	/** Setter for body. */
	public void setBody(String body) {
		this.body = body;
	}

	/** Getter for sentDate. */
	public Date getSentDate() {
		return sentDate;
	}

	/** Setter for sentDate. */
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	public int compareTo(Mailing o) {
		if (sentDate == null)
			return -1;
		if (o.sentDate == null)
			return 1;
		return o.sentDate.compareTo(sentDate);
	}
}
