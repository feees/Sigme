package br.com.engenhodesoftware.util.people.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.State;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(City.class)
public class CityJPAMetamodel {
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, State> state;
}
