package br.org.feees.sigme.secretariat.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * A domain class that represents e-mail messages that are distributed to a large amount of people. Mailings are sent to
 * addressees of mailing lists (its recipients).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Mailing extends PersistentObjectSupport implements Comparable<Mailing> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Recipient mailing lists of this mailing. */
	@ManyToMany
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

	/** TODO: document this field. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mailing")
	private Set<EmailDelivery> deliveries;

	/** Default constructor. */
	public Mailing() {}

	/** Constructor using fields. */
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

	/** Getter for deliveries. */
	public Set<EmailDelivery> getDeliveries() {
		if (deliveries == null) deliveries = new HashSet<>();
		return deliveries;
	}

	/** Setter for deliveries. */
	public void setDeliveries(Set<EmailDelivery> deliveries) {
		this.deliveries = deliveries;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param delivery
	 */
	public void addDelivery(EmailDelivery delivery) {
		getDeliveries().add(delivery);
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
