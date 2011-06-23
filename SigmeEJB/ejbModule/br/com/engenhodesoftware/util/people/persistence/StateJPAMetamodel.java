package br.com.engenhodesoftware.util.people.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.people.domain.State;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(State.class)
public class StateJPAMetamodel {
	public static volatile SingularAttribute<State, String> name;
	public static volatile SingularAttribute<State, String> acronym;
}
