package org.feees.sigme.people.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the City domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria API.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see org.feees.sigme.people.domain.City
 */
@StaticMetamodel(City.class)
public class City_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, State> state;
}
