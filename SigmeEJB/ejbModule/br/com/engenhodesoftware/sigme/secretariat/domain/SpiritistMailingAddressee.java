package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * Domain class that represents a spiritist as the addressee of a mailing. This type of addressee should be used if
 * you'd like to add a specific spiritist to a mailing list, independent of her attendances to institutions.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 */
@Entity
@DiscriminatorValue("S")
public class SpiritistMailingAddressee extends MailingAddressee {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Spiritist the messages will be sent to. */
	@ManyToOne
	private Spiritist spiritist;

	/** Getter for spiritist. */
	public Spiritist getSpiritist() {
		return spiritist;
	}

	/** Setter for spiritist. */
	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#getEmailAddresses() */
	@Override
	protected SortedSet<String> getEmailAddresses() {
		SortedSet<String> emails = new TreeSet<String>();
		emails.add(spiritist.getEmail());
		return emails;
	}
}
