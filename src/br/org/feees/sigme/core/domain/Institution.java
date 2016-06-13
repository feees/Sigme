package br.org.feees.sigme.core.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.feees.sigme.people.domain.Address;
import org.feees.sigme.people.domain.Telephone;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents spiritist institutions that the Spiritist Federation is interested in.
 * 
 * The federation's interest in registering an institution in the system is related to many of the actions it does to
 * promote Spiritism in its state, such as knowing the institution's information to send them mail or call their
 * directors, know about events, collect money contributions for federative work, among others. With Sigme, services
 * such as providing a search for institutions for visitors on the Internet also depend on having information from said
 * institutions.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Institution extends PersistentObjectSupport implements Comparable<Institution> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The institution's name. */
	@Basic
	@NotNull
	@Size(max = 200)
	private String name;

	/** THe institution's acronym. */
	@Basic
	@NotNull
	@Size(max = 10)
	private String acronym;

	/** The physical address where the institution is located. */
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private Address address;

	/** The regional to which the institution belongs. */
	@ManyToOne
	private Regional regional;

	/** The institution's web page address. */
	@Basic
	@Size(max = 150)
	private String webPage;

	/** Main electronic address of the institution. */
	@Basic
	@Size(max = 100)
	private String email;

	/** Phone numbers. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Telephone> telephones;

	/** The type of institution (spiritist center, spiritist federation, non-spiritist, etc.). */
	@ManyToOne(optional = false)
	@NotNull
	private InstitutionType type;

	/** The last time the data about the user was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	/*
	 * Gestão atualmente ativa na Instituição
	 */
	@OneToOne (mappedBy="institution")
	private Management management;

	/*
	 * Histórica de gestões da instituição	
	 */
	@OneToMany 
	private List<Management> managementHistory;
	
	@OneToMany (mappedBy="institution")
	private List<ManagementPosition> managementPositions;
	
	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for acronym. */
	public String getAcronym() {
		return acronym;
	}

	/** Setter for acronym. */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/** Getter for address. */
	public Address getAddress() {
		return address;
	}

	/** Setter for address. */
	public void setAddress(Address address) {
		this.address = address;
	}

	/** Getter for regional. */
	public Regional getRegional() {
		return regional;
	}

	/** Setter for regional. */
	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	/** Getter for webPage. */
	public String getWebPage() {
		return webPage;
	}

	/** Setter for webPage. */
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for telephones. */
	public Set<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for type. */
	public InstitutionType getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(InstitutionType type) {
		this.type = type;
	}

	/** Getter for lastUpdateDate. */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/** Setter for lastUpdateDate. */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Institution o) {
		// Compare the institutions' names
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

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}

	public Management getManagement() {
		return management;
	}

	public void setManagement(Management management) {
		this.management = management;
	}

	public List<Management> getManagementHistory() {
		return managementHistory;
	}

	public void setManagementHistory(List<Management> managementHistory) {
		this.managementHistory = managementHistory;
	}

	public List<ManagementPosition> getManagementPositions() {
		return managementPositions;
	}

	public void setManagementPositions(List<ManagementPosition> managementPositions) {
		this.managementPositions = managementPositions;
	}
}
