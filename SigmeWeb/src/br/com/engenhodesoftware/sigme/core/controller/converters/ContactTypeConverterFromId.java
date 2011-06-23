package br.com.engenhodesoftware.sigme.core.controller.converters;

import br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.persistence.ContactTypeDAO;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class ContactTypeConverterFromId extends EntityJSFConverterFromId<ContactType> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The DAO for ContactType objects. */
	private ContactTypeDAO contactTypeDAO;

	/** Constructor. */
	public ContactTypeConverterFromId(ContactTypeDAO contactTypeDAO) {
		this.contactTypeDAO = contactTypeDAO;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId#getDomainClass() */
	@Override
	protected Class<ContactType> getDomainClass() {
		return ContactType.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId#getDAO() */
	@Override
	protected BaseDAO<ContactType> getDAO() {
		return contactTypeDAO;
	}
}
