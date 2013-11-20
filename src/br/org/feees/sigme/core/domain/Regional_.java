package br.org.feees.sigme.core.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;
import br.com.engenhodesoftware.util.people.domain.City;

/**
 * Meta-model for the Regional domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Regional
 */
@StaticMetamodel(Regional.class)
public class Regional_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Regional, Integer> number;
	public static volatile SingularAttribute<Regional, String> name;
	public static volatile SetAttribute<Regional, City> cities;
}
