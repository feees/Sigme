package br.com.engenhodesoftware.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Regional;

/**
 * Meta-model for the RegionalMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee
 */
@StaticMetamodel(RegionalMailingAddressee.class)
public class RegionalMailingAddressee_ extends ScopedMailingAddressee_ {
	public static volatile SingularAttribute<RegionalMailingAddressee, Regional> regional;
}
