package br.org.feees.sigme.secretariat.domain;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Abstract domain class that represents a mailing list addressee that has a scope, i.e., can specify if the recipients
 * are only the active spiritists, the inactive ones or both. See the MailingAddresseeScope enumeration for more
 * details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingAddresseeScope
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

	/** @see br.org.feees.sigme.secretariat.domain.MailingAddressee#isScoped() */
	@Override
	public boolean isScoped() {
		// Overrides the default implementation in MailingAddressee.
		return true;
	}
}
