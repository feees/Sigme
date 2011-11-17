package br.com.engenhodesoftware.util.ejb3.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.domain.DomainObjectSupport_;

/**
 * Meta-model for the DomainObjectSupport domain class, which allows DAOs to perform programmatic queries using JPA2's
 * Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.ejb3.domain.DomainObjectSupport
 */
@StaticMetamodel(PersistentObjectSupport.class)
public class PersistentObjectSupport_ extends DomainObjectSupport_ {
	public static volatile SingularAttribute<PersistentObjectSupport, Long> id;
	public static volatile SingularAttribute<PersistentObjectSupport, Long> version;
}
