package br.com.engenhodesoftware.sigme.core.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Person;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * Domain class that represents spiritists, i.e., the users of the system.
 * 
 * Any user of the system, be them spiritist in real life or not, is represented by this class. Although this might seem
 * an inconsistency, I thought it would be better than calling everyone "User", which is such an overloaded term.
 * 
 * The term "spiritist" is taken from the translation from the Portuguese "Esp’rita", which as a noun means a person
 * that adheres to the religion Spiritism (see, e.g., http://en.wikipedia.org/wiki/Spiritism). It is consistent with the
 * name of the "International Spiritist Council" (http://www.spirites.org/isc/portal/), which is "an organisation
 * resulting from the union, on a world-wide scale, of the Associations representative of the National Spiritist
 * Movements".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Spiritist extends Person {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Short name to use when there isn't much space. */
	@Basic
	@NotNull
	@Size(max = 15)
	private String shortName;

	/** Electronic address, which also serves as username for identification. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String email;

	/** The password, which identifies the user. */
	@Basic
	@Size(max = 32)
	private String password;

	/** The physical address where the person lives. */
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;

	/** Phone numbers. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Telephone> telephones;

	/** Institutions that the spiritist attend. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "spiritist")
	private Set<Attendance> attendances;

	/** The last time the data about the user was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	/** The last time the user logged in the system. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	/** Getter for shortName. */
	public String getShortName() {
		return shortName;
	}

	/** Setter for shortName. */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Getter for address. */
	public Address getAddress() {
		return address;
	}

	/** Setter for address. */
	public void setAddress(Address address) {
		this.address = address;
	}

	/** Getter for telephones. */
	public Set<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for attendances. */
	public Set<Attendance> getAttendances() {
		return attendances;
	}

	/** Setter for attendances. */
	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	/** Getter for lastUpdateDate. */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/** Setter for lastUpdateDate. */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/** Getter for lastLoginDate. */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/** Setter for lastLoginDate. */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/** @see br.com.engenhodesoftware.util.people.domain.Person#toString() */
	@Override
	public String toString() {
		return name;
	}
}
