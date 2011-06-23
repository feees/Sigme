package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface InstitutionDAO extends BaseDAO<Institution> {
	/**
	 * TODO: document this method.
	 * 
	 * @param name
	 * @return
	 */
	Institution retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param param
	 * @return
	 */
	List<Institution> findByNameOrAcronym(String param);

	List<Institution> findByCity(String cityName);
}
