package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents email confirmations.
 * 
 * When registering, activating or changing e-mail, a Spiritist has to confirm that the e-mail address really belongs to
 * her. An object of this class is created an given a code which is sent to the user via e-mail for confirmation.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class EmailConfirmation extends PersistentObjectSupport {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The e-mail address being registered. */
	@Basic
	@Size(max = 100)
	@NotNull
	private String email;

	/** The code that is sent to the spiritist for confirmation. */
	@Basic
	@Size(max = 40)
	@NotNull
	private String confirmationCode;
	
	/** The date/time the confirmation code was generated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date timestamp;

	/** The spiritist that wants to change his e-mail or activate his registration. For new registrations, this is null. */
	@ManyToOne(cascade = CascadeType.ALL)
	private Spiritist spiritist;

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for confirmationCode. */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/** Setter for confirmationCode. */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	/** Getter for spiritist. */
	public Spiritist getSpiritist() {
		return spiritist;
	}

	/** Setter for spiritist. */
	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	/** Getter for timestamp. */
	public Date getTimestamp() {
		return timestamp;
	}

	/** Setter for timestamp. */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
