package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.org.feees.sigme.core.domain.Regional;

/**
 * Meta-model for the RegionalMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.RegionalMailingAddressee
 */
@StaticMetamodel(RegionalMailingAddressee.class)
public class RegionalMailingAddressee_ extends ScopedMailingAddressee_ {
	public static volatile SingularAttribute<RegionalMailingAddressee, Regional> regional;
}
