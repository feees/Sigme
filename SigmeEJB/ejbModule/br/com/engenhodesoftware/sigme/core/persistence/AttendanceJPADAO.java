package br.com.engenhodesoftware.sigme.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the Attendance domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Attendance
 * @see br.com.engenhodesoftware.sigme.core.persistence.AttendanceDAO
 */
@Stateless
public class AttendanceJPADAO extends BaseJPADAO<Attendance> implements AttendanceDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Attendance> getDomainClass() {
		return Attendance.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
