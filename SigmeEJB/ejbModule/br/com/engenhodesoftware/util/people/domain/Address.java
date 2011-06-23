package br.com.engenhodesoftware.util.people.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
public class Address extends PersistentObjectSupport implements Comparable<Address> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of the street. */
	@Basic
	@Size(max = 250)
	protected String street;

	/** Number of the house, building, etc. */
	@Basic
	@Size(max = 15)
	protected String number;

	/** Complement of the number, such as the apartment number. */
	@Basic
	@Size(max = 15)
	protected String numberComplement;

	/** Complement of the street, such as the name of the neighborhood or district. */
	@Basic
	@Size(max = 250)
	protected String streetComplement;

	/** City where the address is located in. */
	@ManyToOne
	protected City city;

	/** Zip code. */
	@Basic
	@Size(max = 9)
	@Pattern(regexp = "[0-9]{5}-[0-9]{3}|^$")
	protected String zipCode;

	/** Indicates if the address has been returning mail. */
	@Basic
	protected Boolean invalid = false;

	/** Getter for street. */
	public String getStreet() {
		return street;
	}

	/** Setter for street. */
	public void setStreet(String street) {
		this.street = street;
	}

	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for numberComplement. */
	public String getNumberComplement() {
		return numberComplement;
	}

	/** Setter for numberComplement. */
	public void setNumberComplement(String numberComplement) {
		this.numberComplement = numberComplement;
	}

	/** Getter for streetComplement. */
	public String getStreetComplement() {
		return streetComplement;
	}

	/** Setter for streetComplement. */
	public void setStreetComplement(String streetComplement) {
		this.streetComplement = streetComplement;
	}

	/** Getter for city. */
	public City getCity() {
		return city;
	}

	/** Setter for city. */
	public void setCity(City city) {
		this.city = city;
	}

	/** Getter for invalid. */
	public Boolean getInvalid() {
		return invalid;
	}

	/** Setter for invalid. */
	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	/** Getter for zipCode. */
	public String getZipCode() {
		return zipCode;
	}

	/** Setter for zipCode. */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Address o) {
		// First, order by city.
		if (city == null)
			return 1;
		if ((o == null) || (o.city == null))
			return -1;
		int cmp = city.compareTo(o.city);
		if (cmp != 0)
			return cmp;

		// If the city is the same, order by street name.
		if (street == null)
			return 1;
		if (o.street == null)
			return -1;
		cmp = street.compareTo(o.street);
		if (cmp != 0)
			return cmp;

		// If the street name is the same, order by number.
		if (number == null)
			return 1;
		if (o.number == null)
			return -1;
		cmp = number.compareTo(o.number);
		if (cmp != 0)
			return cmp;

		// If everything is the same, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		String address = street + ", " + number;
		if (numberComplement != null)
			address += " / " + numberComplement;
		if (streetComplement != null)
			address += " - " + streetComplement;
		address += " - " + city;
		return address;
	}
}
