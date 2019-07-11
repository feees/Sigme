package br.org.feees.sigme.core.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class ManagementType extends PersistentObjectSupport implements Comparable<ManagementType> {

	private static final long serialVersionUID = -1045742128113609889L;

	@NotNull
	@Size(max = 100)
	private String name;

	@Size(max = 100)
	private String description;
	
	@NotNull
	private Boolean internoSistema = false;

	@Override
	public int compareTo(ManagementType o) {
		// Compare the persons' names
		if (name == null)
			return 1;
		if (o.name == null)
			return -1;
		int cmp = name.compareTo(o.name);
		if (cmp != 0)
			return cmp;

		// If it's the same name, check if it's the same entity.
		return uuid.compareTo(o.uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getInternoSistema() {
		return internoSistema;
	}

	public void setInternoSistema(Boolean internoSistema) {
		this.internoSistema = internoSistema;
	}

}
