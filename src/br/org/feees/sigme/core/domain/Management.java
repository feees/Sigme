package br.org.feees.sigme.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class Management  extends PersistentObjectSupport implements Comparable<Management>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Uma gestão está ligada a somente uma casa espírita
	 */
	@OneToOne
	private Institution institution;
	
	@OneToMany (mappedBy = "management")
	private List<Spiritist> spiritists;
	
	//@NotNull
	@ManyToOne
	private ManagementType type;

	@NotNull
	private String name;

	private String description;
	
	@NotNull
	@Temporal (TemporalType.DATE)
	private Date initialDate;
	
	@Temporal (TemporalType.DATE)
	private Date finalDate;
	
	@OneToMany (mappedBy="management")
	private List<ManagementPosition> managementPositions;
	
	@Override
	public int compareTo(Management o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ManagementType getType() {
		return type;
	}

	public void setType(ManagementType type) {
		this.type = type;
	}

	public List<Spiritist> getSpiritists() {
		return spiritists;
	}

	public void setSpiritists(List<Spiritist> spiritists) {
		this.spiritists = spiritists;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<ManagementPosition> getManagementPositions() {
		return managementPositions;
	}

	public void setManagementPositions(List<ManagementPosition> managementPositions) {
		this.managementPositions = managementPositions;
	}

}
