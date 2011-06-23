package br.com.engenhodesoftware.sigme.core.controller.converters;

import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionTypeDAO;
import br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class InstitutionTypeConverterFromId extends EntityJSFConverterFromId<InstitutionType> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The DAO for InstitutionType objects. */
	private InstitutionTypeDAO institutionTypeDAO;

	/** Constructor. */
	public InstitutionTypeConverterFromId(InstitutionTypeDAO institutionTypeDAO) {
		this.institutionTypeDAO = institutionTypeDAO;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId#getDomainClass() */
	@Override
	protected Class<InstitutionType> getDomainClass() {
		return InstitutionType.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.EntityJSFConverterFromId#getDAO() */
	@Override
	protected BaseDAO<InstitutionType> getDAO() {
		return institutionTypeDAO;
	}
}
