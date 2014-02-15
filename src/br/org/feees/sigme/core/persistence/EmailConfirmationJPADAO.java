package br.org.feees.sigme.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.feees.sigme.core.domain.EmailConfirmation;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the EmailConfirmation domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.EmailConfirmation
 * @see br.org.feees.sigme.core.persistence.EmailConfirmationDAO
 */
@Stateless
public class EmailConfirmationJPADAO extends BaseJPADAO<EmailConfirmation> implements EmailConfirmationDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	//private static final Logger logger = Logger.getLogger(EmailConfirmationJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<EmailConfirmation> getDomainClass() {
		return EmailConfirmation.class;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
