package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.org.feees.sigme.core.domain.Attendance;

/**
 * Interface for a DAO for objects of the Attendance domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class. 
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Attendance
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface AttendanceDAO extends BaseDAO<Attendance> {}
