package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * The configuration of the tool. Stores global information that Sigme needs to operate.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Entity
public class SigmeConfiguration extends PersistentObjectSupport {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The timestamp of the moment this configuration came in effect. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	/** The institution that owns this instance of Sigme. */
	@ManyToOne
	@NotNull
	private Institution owner;
	
	/** Address for the SMTP server that sends e-mail. */
	@NotNull
	private String smtpServerAddress;
	
	/** Port for the SMTP server that sends e-mail. */
	@NotNull
	private Integer smtpServerPort;
	
	/** Username to connect to the SMTP server that sends email. */
	@NotNull
	private String smtpUsername;
	
	/** Password to connect to the SMTP server that sends email. */
	@NotNull
	private String smtpPassword;

	/** Getter for creationDate. */
	public Date getCreationDate() {
		return creationDate;
	}

	/** Setter for creationDate. */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/** Getter for owner. */
	public Institution getOwner() {
		return owner;
	}

	/** Setter for owner. */
	public void setOwner(Institution owner) {
		this.owner = owner;
	}

	/** Getter for smtpServerAddress. */
	public String getSmtpServerAddress() {
		return smtpServerAddress;
	}

	/** Setter for smtpServerAddress. */
	public void setSmtpServerAddress(String smtpServerAddress) {
		this.smtpServerAddress = smtpServerAddress;
	}

	/** Getter for smtpServerPort. */
	public Integer getSmtpServerPort() {
		return smtpServerPort;
	}

	/** Setter for smtpServerPort. */
	public void setSmtpServerPort(Integer smtpServerPort) {
		this.smtpServerPort = smtpServerPort;
	}

	/** Getter for smtpUsername. */
	public String getSmtpUsername() {
		return smtpUsername;
	}

	/** Setter for smtpUsername. */
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	/** Getter for smtpPassword. */
	public String getSmtpPassword() {
		return smtpPassword;
	}

	/** Setter for smtpPassword. */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
}
