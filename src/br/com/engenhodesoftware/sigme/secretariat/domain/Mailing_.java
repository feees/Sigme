package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
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
@StaticMetamodel(Mailing.class)
public class Mailing_ extends PersistentObjectSupport_ {
	public static volatile SetAttribute<Mailing, MailingList> recipients;
	public static volatile SingularAttribute<Mailing, String> subject;
	public static volatile SingularAttribute<Mailing, String> body;
	public static volatile SingularAttribute<Mailing, Date> sentDate;
}
