package br.com.engenhodesoftware.sigme.secretariat.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Domain class that represents a spiritist as the addressee of a mailing. This type of addressee should be used if
 * you'd like to add a specific spiritist to a mailing list, independent of her attendances to institutions.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 */
@Entity
@DiscriminatorValue("A")
public class AllMailingAddressee extends MailingAddressee {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;
}
