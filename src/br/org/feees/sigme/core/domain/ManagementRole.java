package br.org.feees.sigme.core.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Classe que define um cargo de uma gestão.
 * Deve conter qual o tipo de cargo, o espírita associado, a instituição e qual a gestão.
 * @author rbpim
 *
 */
@Entity
public class ManagementRole extends PersistentObjectSupport implements Comparable<ManagementRole>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;
	
	@OneToOne
	@NotNull
	private ManagementRoleType managementRoleType;
	
	/**
	 * Spiritist that belongs to a specific Position
	 */
	@OneToOne
	@NotNull
	private Spiritist spiritist;
	
	/**
	 * Institution that the spiritist belongs
	 */
	@ManyToOne
	@NotNull
	@Deprecated
	private Institution institution;
	
	/**
	 * Management that the spiritist belongs
	 */
	@ManyToOne
	@NotNull
	private Management management;
	
	@Override
	public int compareTo(ManagementRole o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Spiritist getSpiritist() {
		return spiritist;
	}

	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Management getManagement() {
		return management;
	}

	public void setManagement(Management management) {
		this.management = management;
	}

	public ManagementRoleType getManagementRoleType() {
		return managementRoleType;
	}

	public void setManagementRoleType(ManagementRoleType managementRoleType) {
		this.managementRoleType = managementRoleType;
	}
	
	
}
