package br.com.engenhodesoftware.sigme.core.domain;

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

	/** @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return type;
	}
}
