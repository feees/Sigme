package br.com.engenhodesoftware.util.people.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.ContactType;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Stateless
public class ContactTypeJPADAO extends BaseJPADAO<ContactType> implements ContactTypeDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getDomainClass() */
	@Override
	protected Class<ContactType> getDomainClass() {
		return ContactType.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
