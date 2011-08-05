package br.com.engenhodesoftware.util.people.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.State;

/**
 * Meta-model for the City domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria API.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.domain.City
 */
@StaticMetamodel(City.class)
public class CityJPAMetamodel {
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, State> state;
}
