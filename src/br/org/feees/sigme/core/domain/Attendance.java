package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represent the relationship between a spiritist and an institution she attends (or used to attend).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Attendance extends PersistentObjectSupport implements Comparable<Attendance> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Spiritists that is attending the institution. */
	@ManyToOne
	@NotNull
	private Spiritist spiritist;

	/** Institution that is being attended. */
	@ManyToOne
	@NotNull
	private Institution institution;

	/** Date the attendance started. */
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/** Date the attendance ended. */
	@Temporal(TemporalType.DATE)
	private Date endDate;

	/** Getter for spiritist. */
	public Spiritist getSpiritist() {
		return spiritist;
	}

	/** Setter for spiritist. */
	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	/** Getter for institution. */
	public Institution getInstitution() {
		return institution;
	}

	/** Setter for institution. */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/** Getter for startDate. */
	public Date getStartDate() {
		return startDate;
	}

	/** Setter for startDate. */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/** Getter for endDate. */
	public Date getEndDate() {
		return endDate;
	}

	/** Setter for endDate. */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(Attendance o) {
		return institution.compareTo(o.institution);
	}
}
