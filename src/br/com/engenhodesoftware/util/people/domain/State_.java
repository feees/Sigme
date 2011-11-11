package br.com.engenhodesoftware.util.people.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the State domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria API.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.domain.State
 */
@StaticMetamodel(State.class)
public class State_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<State, String> name;
	public static volatile SingularAttribute<State, String> acronym;
}
