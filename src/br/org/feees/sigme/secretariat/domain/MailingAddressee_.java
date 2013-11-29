package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the MailingList domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingList
 */
@StaticMetamodel(MailingAddressee.class)
public class MailingAddressee_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<MailingAddressee, MailingList> mailingList;
}
