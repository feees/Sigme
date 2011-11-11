package br.com.engenhodesoftware.util.ejb3.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Meta-model for the DomainObjectSupport domain class, which allows DAOs to perform programmatic queries using JPA2's
 * Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.ejb3.domain.DomainObjectSupport
 */
@StaticMetamodel(DomainObjectSupport.class)
public class DomainObjectSupport_ {
	public static volatile SingularAttribute<DomainObjectSupport, String> uuid;
}
