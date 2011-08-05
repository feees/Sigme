package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Institution;

/**
 * Domain class that represents an entire institution as the addressee of a mailing. This type of addressee should be
 * used if you'd like to add all spiritists that attend an institution to a mailing list. This addressee is scoped,
 * meaning you can specify if all associated spiritist should receive messages or only the active or inactive ones
 * (i.e., those who are still or are no longer attending the institution, respectively).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.ScopedMailingAddressee
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope
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

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#getEmailAddresses() */
	@Override
	protected SortedSet<String> getEmailAddresses() {
		SortedSet<String> emails = new TreeSet<String>();

		// FIXME: implement the retrieval of emails. Remember this is scoped (active, inactive, all)!

		return emails;
	}
}
