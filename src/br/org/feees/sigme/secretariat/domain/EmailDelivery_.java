package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the EmailDelivery domain class, which allows DAOs to perform programmatic queries using JPA2's
 * Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.EmailDelivery
 */
@StaticMetamodel(EmailDelivery.class)
public class EmailDelivery_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<EmailDelivery, Mailing> mailing;
	public static volatile SingularAttribute<EmailDelivery, String> recipientEmail;
	public static volatile SingularAttribute<EmailDelivery, Boolean> messageSent;
	public static volatile SingularAttribute<EmailDelivery, String> errorMessage;
}
