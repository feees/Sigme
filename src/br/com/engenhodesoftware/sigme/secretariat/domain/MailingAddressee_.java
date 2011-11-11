package br.com.engenhodesoftware.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the MailingList domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingList
 */
@StaticMetamodel(MailingAddressee.class)
public class MailingAddressee_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<MailingAddressee, MailingList> mailingList;
	public static volatile SingularAttribute<MailingAddressee, MailingAddresseeType> type;
}
