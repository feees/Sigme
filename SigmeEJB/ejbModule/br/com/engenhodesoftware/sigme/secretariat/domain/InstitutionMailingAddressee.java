package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Institution;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
@DiscriminatorValue("I")
public class InstitutionMailingAddressee extends ScopedMailingAddressee {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Institution whose spiritists the messages will be sent to. */
	@ManyToOne
	private Institution institution;

	/** Getter for institution. */
	public Institution getInstitution() {
		return institution;
	}

	/** Setter for institution. */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#copyValues(br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee) */
	@Override
	public void copyValues(MailingAddressee src) {
		// Checks if the class is a match.
		if (!(src instanceof InstitutionMailingAddressee))
			throw new IllegalArgumentException("Invalid class: cannot copy to a InstitutionMailingAddressee the data of a " + src.getClass());

		// Copies the values defined in the superclass.
		super.copyValues(src);

		// Copies the values defined in this class.
		institution = ((InstitutionMailingAddressee) src).getInstitution();
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#createCopy() */
	@Override
	public MailingAddressee createCopy() {
		// Creates a new addressee of this type and copies the values from the current object.
		InstitutionMailingAddressee newAddressee = new InstitutionMailingAddressee();
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
