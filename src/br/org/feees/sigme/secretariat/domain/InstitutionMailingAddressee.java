package br.org.feees.sigme.secretariat.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.org.feees.sigme.core.domain.Institution;

/**
 * Domain class that represents an entire institution as the addressee of a mailing. This type of addressee should be
 * used if you'd like to add all spiritists that attend an institution to a mailing list. This addressee is scoped,
 * meaning you can specify if all associated spiritist should receive messages or only the active or inactive ones
 * (i.e., those who are still or are no longer attending the institution, respectively).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingAddressee
 * @see br.org.feees.sigme.secretariat.domain.ScopedMailingAddressee
 * @see br.org.feees.sigme.secretariat.domain.MailingAddresseeScope
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
}
