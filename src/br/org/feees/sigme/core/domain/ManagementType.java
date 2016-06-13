package br.org.feees.sigme.core.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class ManagementType extends PersistentObjectSupport implements Comparable<ManagementType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@NotNull
	@Size(max = 100)
	private String name;
	
	@Override
	public int compareTo(ManagementType o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
