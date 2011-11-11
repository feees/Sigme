package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * Domain class that represents mailing lists to which Sigme is able to send mailings, i.e., e-mail messages that are
 * distributed to a large amount of people.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class MailingList extends PersistentObjectSupport implements Comparable<MailingList> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Name of this mailing list. */
	@Basic
	@NotNull
	@Size(max = 100)
	private String name;

	/** Description of this mailing list. */
	@Lob
	private String description;

	/** Addressees to which messages are sent. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mailingList")
	private Set<MailingAddressee> addressees;

	/** Getter for name. */
	public String getName() {
		return name;
	}

	/** Setter for name. */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for description. */
	public String getDescription() {
		return description;
	}

	/** Setter for description. */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Getter for addressees. */
	public Set<MailingAddressee> getAddressees() {
		return addressees;
	}

	/** Setter for addressees. */
	public void setAddressees(Set<MailingAddressee> addressees) {
		this.addressees = addressees;
	}

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(MailingList o) {
		// Compares mailing lists by name.
		int result = 0;
		if (name != null)
			result = name.compareTo(o.name);

		// If it's a tie, compare by the superclass' criteria (namely, by UUID).
		if (result == 0)
			result = super.compareTo(o);

		return result;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport#toString() */
	@Override
	public String toString() {
		return name;
	}
}
