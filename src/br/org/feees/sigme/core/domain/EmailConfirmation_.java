package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the EmailConfirmation domain class, which allows DAOs to perform programmatic queries using JPA2's
 * Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.EmailConfirmation
 */
@StaticMetamodel(EmailConfirmation.class)
public class EmailConfirmation_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<EmailConfirmation, String> email;
	public static volatile SingularAttribute<EmailConfirmation, String> confirmationCode;
	public static volatile SingularAttribute<Spiritist, Date> timestamp;
	public static volatile SingularAttribute<EmailConfirmation, Spiritist> spiritist;
}
