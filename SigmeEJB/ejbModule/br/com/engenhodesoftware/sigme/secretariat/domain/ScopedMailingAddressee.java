package br.com.engenhodesoftware.sigme.secretariat.domain;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Entity
@DiscriminatorValue("C")
public abstract class ScopedMailingAddressee extends MailingAddressee {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Scope of addressee (all, active-only, inactive-only). */
	@Basic
	@NotNull
	protected MailingAddresseeScope scope;

	/** Getter for scope. */
	public MailingAddresseeScope getScope() {
		return scope;
	}

	/** Setter for scope. */
	public void setScope(MailingAddresseeScope scope) {
		this.scope = scope;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#isScoped() */
	@Override
	public boolean isScoped() {
		// Overrides the default implementation in MailingAddressee.
		return true;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee#copyValues(br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee) */
	@Override
	public void copyValues(MailingAddressee src) {
		// Checks if the class is a match.
		if (!(src instanceof ScopedMailingAddressee))
			throw new IllegalArgumentException("Invalid class: cannot copy to a ScopedMailingAddressee the data of a " + src.getClass());

		// Copies the values defined in the superclass.
		super.copyValues(src);

		// Copies the values defined in this class.
		scope = ((ScopedMailingAddressee) src).getScope();
	}
}
