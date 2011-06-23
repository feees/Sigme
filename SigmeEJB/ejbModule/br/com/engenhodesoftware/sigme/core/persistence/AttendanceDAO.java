package br.com.engenhodesoftware.sigme.core.persistence;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface AttendanceDAO extends BaseDAO<Attendance> {}
