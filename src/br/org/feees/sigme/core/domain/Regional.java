package br.org.feees.sigme.core.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.feees.sigme.people.domain.City;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents administrative subdivisions of the state by the Spiritist Federation.
 * 
 * Spiritist Federations in Brazil are concerned with the Spiritist Movement in their state. To make it easier to
 * coordinate several of their actions, the state is subdivided in regions that can be called Regional Spiritist
 * Council, Regional Spiritist Unions, etc. In Sigme we refer to them generally as "regionals".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Regional extends PersistentObjectSupport implements Comparable<Regional> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Region's number. */
	@Basic
	@NotNull
	private Integer number;

	/** Region's name. */
	@Basic
	@NotNull
	@Size(max = 10)
	private String name;

	/** Cities that belong to this regional. */
	@OneToMany
	private Set<City> cities;

	/** Getter for number. */
	public Integer getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for cities. */
	public Set<City> getCities() {
		return cities;
	}

	/** Setter for cities. */
	public void setCities(Set<City> cities) {
		this.cities = cities;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Regional o) {
		return number.compareTo(o.number);
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}
}
