package br.com.engenhodesoftware.sigme.core.persistence;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface RegionalDAO extends BaseDAO<Regional> {
	/**
	 * TODO: document this method.
	 * 
	 * @param city
	 * @return
	 */
	Regional retrieveByCity(City city) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
