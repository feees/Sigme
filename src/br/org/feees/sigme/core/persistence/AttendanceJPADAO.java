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

import br.org.feees.sigme.core.domain.Attendance;
import br.org.feees.sigme.core.domain.Attendance_;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the Attendance domain class using JPA2.
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
public class AttendanceJPADAO extends BaseJPADAO<Attendance> implements AttendanceDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AttendanceJPADAO.class.getCanonicalName());
	
	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Attendance> getDomainClass() {
		return Attendance.class;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Attendance> retrieveByInstitution(Long institutionId) {
		logger.log(Level.FINE, "Retrieving the attendance for the institution \"{0}\"...", institutionId);

		// Constructs the query over the Spiritist class.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Attendance> cq = cb.createQuery(Attendance.class);
		Root<Attendance> root = cq.from(Attendance.class);

		// Filters the query with the email.
		cq.where(cb.equal(root.get(Attendance_.institution), institutionId));
		
		List<Attendance> list = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve attendace by institutionId containing \"{0}\" returned \"{1}\" spiritists", new Object[] { institutionId, list.size()});
		return list;
		
		
	}
}
