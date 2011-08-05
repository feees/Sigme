package br.com.engenhodesoftware.util.people.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents a city as part of people's addresses.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class City extends PersistentObjectSupport implements Comparable<City> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of the city. */
	@Basic
	@NotNull
	@Size(max = 250)
	protected String name;

	/** State where the city is located. */
	@ManyToOne(optional = false)
	@NotNull
	protected State state;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for state. */
	public State getState() {
		return state;
	}

	/** Setter for state. */
	public void setState(State state) {
		this.state = state;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(City o) {
		// First, order by state.
		if (state == null)
			return 1;
		if ((o == null) || (o.state == null))
			return -1;
		int cmp = state.compareTo(o.state);
		if (cmp != 0)
			return cmp;

		// If it's the same state, order by city names.
		if (name == null)
			return 1;
		if (o.name == null)
			return -1;
		cmp = name.compareTo(o.name);
		if (cmp != 0)
			return cmp;

		// If it's the same name and state, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		String city = name;
		if (state != null)
			city += ", " + state.getAcronym();
		return city;
	}
}
