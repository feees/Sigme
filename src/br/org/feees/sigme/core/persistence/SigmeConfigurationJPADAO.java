package br.org.feees.sigme.core.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;
import br.org.feees.sigme.core.domain.SigmeConfiguration;
import br.org.feees.sigme.core.domain.SigmeConfiguration_;

/**
 * Stateless session bean implementing a DAO for objects of the SigmeConfiguration domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Attendance
 * @see br.org.feees.sigme.core.persistence.AttendanceDAO
 */
@Stateless
public class SigmeConfigurationJPADAO extends BaseJPADAO<SigmeConfiguration> implements SigmeConfigurationDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SigmeConfigurationJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<SigmeConfiguration> getDomainClass() {
		return SigmeConfiguration.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.org.feees.sigme.core.persistence.SigmeConfigurationDAO#retrieveCurrentConfiguration() */
	@Override
	public SigmeConfiguration retrieveCurrentConfiguration() throws PersistentObjectNotFoundException {
		logger.log(Level.FINE, "Retrieving current (latest) Sigme configuration...");

		// Constructs the query over the SigmeConfiguration class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SigmeConfiguration> cq = cb.createQuery(SigmeConfiguration.class);
		Root<SigmeConfiguration> root = cq.from(SigmeConfiguration.class);

		// Orders the query descending by date.
		cq.orderBy(cb.desc(root.get(SigmeConfiguration_.creationDate)));
		
		// Retrieves and returns the latest configuration (first entity returned). If the query returns an empty list, throws an exception.
		List<SigmeConfiguration> result = entityManager.createQuery(cq).getResultList();
		try {
			SigmeConfiguration cfg = result.get(0);
			logger.log(Level.INFO, "Retrieve current configuration returned a SigmeConfiguration with owner \"{0}\" and creation date \"{1}\"", new Object[] { cfg.getOwner().getName(), cfg.getCreationDate() });
			return cfg;
		}
		catch (IndexOutOfBoundsException e) {
			throw new PersistentObjectNotFoundException(e, getDomainClass());
		}
	}
}
