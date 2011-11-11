package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.engenhodesoftware.sigme.core.domain.Regional;

/**
 * Domain class that represents an entire reginoal as the addressee of a mailing. This type of addressee should be used
 * if you'd like to add all spiritists that attend all institutions of a regional to a mailing list. This addressee is
 * scoped, meaning you can specify if all associated spiritist should receive messages or only the active or inactive
 * ones (i.e., those who are still or are no longer attending an institution at the regional, respectively).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.ScopedMailingAddressee
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope
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

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#getEmailAddresses() */
	@Override
	protected SortedSet<String> getEmailAddresses() {
		SortedSet<String> emails = new TreeSet<String>();

		// Implement the retrieval of emails. Remember this is scoped (active, inactive, all)!

		return emails;
	}
}
