package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * Meta-model for the MailingList domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingList
 */
@StaticMetamodel(ScopedMailingAddressee.class)
public class ScopedMailingAddressee_ extends MailingAddressee_ {
	public static volatile SingularAttribute<ScopedMailingAddressee, MailingAddresseeScope> scope;
}
