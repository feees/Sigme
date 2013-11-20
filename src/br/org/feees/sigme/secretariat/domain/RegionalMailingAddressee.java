package br.org.feees.sigme.secretariat.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.org.feees.sigme.core.domain.Regional;

/**
 * Domain class that represents an entire reginoal as the addressee of a mailing. This type of addressee should be used
 * if you'd like to add all spiritists that attend all institutions of a regional to a mailing list. This addressee is
 * scoped, meaning you can specify if all associated spiritist should receive messages or only the active or inactive
 * ones (i.e., those who are still or are no longer attending an institution at the regional, respectively).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingAddressee
 * @see br.org.feees.sigme.secretariat.domain.ScopedMailingAddressee
 * @see br.org.feees.sigme.secretariat.domain.MailingAddresseeScope
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
}
