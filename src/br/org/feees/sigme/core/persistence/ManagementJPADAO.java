package br.org.feees.sigme.core.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.domain.Management_;
import br.org.feees.sigme.event.domain.Subscriber;
import br.org.feees.sigme.event.domain.Subscriber_;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Stateless
public class ManagementJPADAO extends BaseJPADAO<Management> implements ManagementDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementJPADAO.class.getCanonicalName());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Class<Management> getDomainClass() {
		return Management.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public Management retrieveByInstitutionId(Long id) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Management> cq = cb.createQuery(Management.class);
		Root<Management> root = cq.from(Management.class);

		cq.where(cb.equal(root.get(Management_.institution), id));

		Management management = executeSingleResultQuery(cq, new Object[] { id});
		logger.log(Level.INFO,
				"Retrieve Management by the Institution with id \"{0}\" returned \"{1}\"",
				new Object[] { id, management});
		return management;
	}

}
