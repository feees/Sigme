package br.org.feees.sigme.core.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the InstitutionType domain class, which allows DAOs to perform programmatic queries involving this
 * class using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.InstitutionType
 */
@StaticMetamodel(InstitutionType.class)
public class InstitutionType_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<InstitutionType, String> type;
	public static volatile SingularAttribute<InstitutionType, Boolean> partOfRegional;
}
