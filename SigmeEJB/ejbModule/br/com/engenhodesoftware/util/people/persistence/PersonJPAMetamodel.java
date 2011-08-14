package br.com.engenhodesoftware.util.people.persistence;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.people.domain.Person;

/**
 * Meta-model for the Spiritist domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Spiritist
 */
@StaticMetamodel(Person.class)
public class PersonJPAMetamodel {
	public static volatile SingularAttribute<Spiritist, String> name;
	public static volatile SingularAttribute<Spiritist, Date> birthDate;
	public static volatile SingularAttribute<Spiritist, Character> gender;
}
