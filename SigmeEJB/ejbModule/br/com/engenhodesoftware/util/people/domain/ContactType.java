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
public class ContactType extends PersistentObjectSupport implements Comparable<ContactType> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Type of contact: home, work, cell, fax, etc. */
	@Basic
	@NotNull
	@Size(max = 50)
	protected String type;

	/** Getter for type. */
	public String getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(String type) {
		this.type = type;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(ContactType o) {
		// Compare the names of the types.
		if (type == null)
			return 1;
		if ((o == null) || (o.type == null))
			return -1;
		int cmp = type.compareTo(o.type);
		if (cmp != 0)
			return cmp;

		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return type;
	}
}
