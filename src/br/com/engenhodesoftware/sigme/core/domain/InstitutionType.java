package br.com.engenhodesoftware.sigme.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents the types of spiritist institutions that the Spiritist Federation is interested in.
 * 
 * Examples of types of institutions are: spiritist center that adheres to the federation, spiritist center that doesn't
 * adhere to the federation, other federations (in other states), other types of institutions (e.g., shelters, rest
 * homes, etc.) that claim to follow a spiritist philosophy in their actions, etc.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class InstitutionType extends PersistentObjectSupport implements Comparable<InstitutionType> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String type;

	/** If this type of institution belongs to a regional. */
	@Basic
	@NotNull
	private Boolean partOfRegional;

	/** Getter for type. */
	public String getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(String type) {
		this.type = type;
	}

	/** Getter for partOfRegional. */
	public Boolean isPartOfRegional() {
		return partOfRegional;
	}

	/** Alias for isPartOfRegional(). */
	public Boolean getPartOfRegional() {
		return isPartOfRegional();
	}

	/** Setter for partOfRegional. */
	public void setPartOfRegional(Boolean partOfRegional) {
		this.partOfRegional = partOfRegional;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(InstitutionType o) {
		if (type == null)
			return 1;
		if (o.type == null)
			return -1;
		return type.compareTo(o.type);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return type;
	}
}
