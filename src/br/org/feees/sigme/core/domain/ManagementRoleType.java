package br.org.feees.sigme.core.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Classe que explicita um tipo de cargo para um determinado cargo da gestão
 * @author rbpim
 *
 */
@Entity
public class ManagementRoleType extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 100)
	private String name;
	
	@NotNull
	@Size(max = 1000)
	private String description;

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


	@Override
	public String toString() {
		return this.name;
	}
}
