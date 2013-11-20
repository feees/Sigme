package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;
import br.org.feees.sigme.core.domain.SigmeConfiguration;

/**
 * Interface for a DAO for objects of the SigmeConfiguration domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Attendance
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface SigmeConfigurationDAO extends BaseDAO<SigmeConfiguration> {
	/**
	 * TODO: document this method.
	 * 
	 * @return
	 * @throws PersistentObjectNotFoundException
	 */
	SigmeConfiguration retrieveCurrentConfiguration() throws PersistentObjectNotFoundException;
}
