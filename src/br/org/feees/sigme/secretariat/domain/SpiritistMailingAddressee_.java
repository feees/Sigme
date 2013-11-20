package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.org.feees.sigme.core.domain.Spiritist;


/**
 * Meta-model for the MailingList domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingList
 */
@StaticMetamodel(SpiritistMailingAddressee.class)
public class SpiritistMailingAddressee_ extends MailingAddressee_ {
	public static volatile SingularAttribute<SpiritistMailingAddressee, Spiritist> spiritist;
}
