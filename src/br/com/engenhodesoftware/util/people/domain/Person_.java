package br.com.engenhodesoftware.util.people.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;
import br.org.feees.sigme.core.domain.Spiritist;

/**
 * Meta-model for the Person domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.domain.Person
 */
@StaticMetamodel(Person.class)
public class Person_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Spiritist, String> name;
	public static volatile SingularAttribute<Spiritist, Date> birthDate;
	public static volatile SingularAttribute<Spiritist, Character> gender;
}
