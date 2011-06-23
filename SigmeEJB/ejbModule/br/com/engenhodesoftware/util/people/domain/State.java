package br.com.engenhodesoftware.util.people.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
public class State extends PersistentObjectSupport implements Comparable<State> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of the state. */
	@Basic
	@NotNull
	@Size(max = 100)
	protected String name;

	/** Two/three letter acronym that represents the state. */
	@Basic
	@NotNull
	@Size(max = 5)
	protected String acronym;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for acronym. */
	public String getAcronym() {
		return acronym;
	}

	/** Setter for acronym. */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(State o) {
		// Compare the state's names.
		if (name == null)
			return 1;
		if ((o == null) || (o.name == null))
			return -1;
		int cmp = name.compareTo(o.name);
		if (cmp != 0)
			return cmp;

		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return name;
	}
}
