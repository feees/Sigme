package br.com.engenhodesoftware.sigme.secretariat.domain;

import java.util.SortedSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("M")
public abstract class MailingAddressee extends PersistentObjectSupport implements Comparable<MailingAddressee> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Mailing list to which this addressee belongs. */
	@ManyToOne(cascade = CascadeType.MERGE)
	private MailingList mailingList;

	/** Type of addressee (normal, copy or carbon copy). */
	@Basic
	@NotNull
	private MailingAddresseeType type;

	/** Getter for mailingList. */
	public MailingList getMailingList() {
		return mailingList;
	}

	/** Setter for mailingList. */
	public void setMailingList(MailingList mailingList) {
		this.mailingList = mailingList;
	}

	/** Getter for type. */
	public MailingAddresseeType getType() {
		return type;
	}

	/** Setter for type. */
	public void setType(MailingAddresseeType type) {
		this.type = type;
	}

	/**
	 * Indicates if the addressee is scoped. When scoped, the scope of the addressee should also be indicated. The scope
	 * is implemented by the MailingAddresseeScope enumeration and indicates if the message should be sent to all related
	 * spiritists, to active spiritists only or to inactive spiritists only.
	 * 
	 * @return <code>true</code> if the addressee is scoped, <code>false</code> otherwise.
	 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope
	 */
	public boolean isScoped() {
		// By default, addressees aren't scoped. ScopedMailingAddressee overrides this.
		return false;
	}

	/**
	 * Copies the values from the source addressee to this object. This method is intended to be overridden by all
	 * subclasses, because each of them is responsible for copying the properties they define.
	 * 
	 * @param src
	 *          The source addressee.
	 */
	public void copyValues(MailingAddressee src) {
		// Copies the values defined in this class.
		mailingList = src.getMailingList();
		type = src.getType();
	}

	/**
	 * Creates a new object of the same class and copies the data from this object. This method is intended to be
	 * overridden by concrete subclasses.
	 * 
	 * @return A new object from the same class with the same data as this object.
	 */
	public abstract MailingAddressee createCopy();

	/**
	 * Returns a set with all e-mail addresses of the recipients. This method is intended to be overridden by concrete
	 * subclasses.
	 * 
	 * @return A set of strings, each of which an e-mail address.
	 */
	protected abstract SortedSet<String> getEmailAddresses();

	/** @see java.lang.Comparable#compareTo(java.lang.Object) */
	@Override
	public int compareTo(MailingAddressee o) {
		// Compares by type.
		int result = 0;
		if (type != null)
			result = type.compareTo(o.type);

		// If it's a tie, compare by the superclass' criteria (namely, by UUID).
		if (result == 0)
			result = super.compareTo(o);

		return result;
	}
}
