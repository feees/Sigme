package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
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

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#copyValues(br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee) */
	@Override
	public void copyValues(MailingAddressee src) {
		// Checks if the class is a match.
		if (!(src instanceof SpiritistMailingAddressee))
			throw new IllegalArgumentException("Invalid class: cannot copy to a SpiritistMailingAddressee the data of a " + src.getClass());

		// Copies the values defined in the superclass.
		super.copyValues(src);

		// Copies the values defined in this class.
		spiritist = ((SpiritistMailingAddressee) src).getSpiritist();
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#createCopy() */
	@Override
	public MailingAddressee createCopy() {
		// Creates a new addressee of this type and copies the values from the current object.
		SpiritistMailingAddressee newAddressee = new SpiritistMailingAddressee();
		newAddressee.copyValues(this);

		return newAddressee;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#getEmailAddresses() */
	@Override
	protected SortedSet<String> getEmailAddresses() {
		SortedSet<String> emails = new TreeSet<String>();
		emails.add(spiritist.getEmail());
		return emails;
	}
}
