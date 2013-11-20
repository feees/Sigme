package br.org.feees.sigme.secretariat.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.org.feees.sigme.core.domain.Spiritist;

/**
 * Domain class that represents a spiritist as the addressee of a mailing. This type of addressee should be used if
 * you'd like to add a specific spiritist to a mailing list, independent of her attendances to institutions.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingAddressee
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
}
