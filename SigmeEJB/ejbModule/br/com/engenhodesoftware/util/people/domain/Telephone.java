package br.com.engenhodesoftware.util.people.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
public class Telephone extends PersistentObjectSupport implements Comparable<Telephone> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The number. */
	@Basic
	@NotNull
	@Size(max = 25)
	protected String number;

	/** The type of contact. */
	@ManyToOne(optional = false)
	@NotNull
	protected ContactType type;

	/** Getter for number. */
	public String getNumber() {
		return number;
	}

	/** Setter for number. */
	public void setNumber(String number) {
		this.number = number;
	}

	/** Getter for type. */
	public ContactType getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(ContactType type) {
		this.type = type;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Telephone o) {
		// First, order by contact type.
		if (type == null)
			return 1;
		if ((o == null) || (o.type == null))
			return -1;
		int cmp = type.compareTo(o.type);
		if (cmp != 0)
			return cmp;

		// If it's the same, order by phone number.
		if (number == null)
			return 1;
		if (o.number == null)
			return -1;
		cmp = number.compareTo(o.number);
		if (cmp != 0)
			return cmp;

		// If it's the same type and number, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return number + " (" + type + ")";
	}
}
