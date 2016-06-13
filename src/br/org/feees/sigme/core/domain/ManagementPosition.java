package br.org.feees.sigme.core.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class ManagementPosition extends PersistentObjectSupport implements Comparable<ManagementPosition>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;
	
	//private ManagementPostitionType
	
	/**
	 * Spiritist that belongs to a specific Position
	 */
	@OneToOne
	private Spiritist spiritist;
	
	/**
	 * Institution that the spiritist belongs
	 */
	@ManyToOne
	private Institution institution;
	
	/**
	 * Management that the spiritist belongs
	 */
	@ManyToOne
	private Management management;
	
	@Override
	public int compareTo(ManagementPosition o) {
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
}
