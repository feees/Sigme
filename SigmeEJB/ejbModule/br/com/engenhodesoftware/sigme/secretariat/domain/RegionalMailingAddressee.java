package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Regional;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
@DiscriminatorValue("R")
public class RegionalMailingAddressee extends ScopedMailingAddressee {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Regional whose institutions' spiritists the messages will be sent to. */
	@ManyToOne
	private Regional regional;

	/** Getter for regional. */
	public Regional getRegional() {
		return regional;
	}

	/** Setter for regional. */
	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#copyValues(br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee) */
	@Override
	public void copyValues(MailingAddressee src) {
		// Checks if the class is a match.
		if (!(src instanceof RegionalMailingAddressee))
			throw new IllegalArgumentException("Invalid class: cannot copy to a RegionalMailingAddressee the data of a " + src.getClass());

		// Copies the values defined in the superclass.
		super.copyValues(src);

		// Copies the values defined in this class.
		regional = ((RegionalMailingAddressee) src).getRegional();
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#createCopy() */
	@Override
	public MailingAddressee createCopy() {
		// Creates a new addressee of this type and copies the values from the current object.
		RegionalMailingAddressee newAddressee = new RegionalMailingAddressee();
		newAddressee.copyValues(this);

		return newAddressee;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#getEmailAddresses() */
	@Override
	protected SortedSet<String> getEmailAddresses() {
		SortedSet<String> emails = new TreeSet<String>();

		// FIXME: implement the retrieval of emails. Remember this is scoped (active, inactive, all)!

		return emails;
	}
}
