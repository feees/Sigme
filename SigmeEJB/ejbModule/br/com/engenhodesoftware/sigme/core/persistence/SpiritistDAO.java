package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface SpiritistDAO extends BaseDAO<Spiritist> {
	/**
	 * TODO: document this method.
	 * 
	 * @param email
	 * @return
	 */
	Spiritist retrieveByEmail(String email) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param param
	 * @return
	 */
	List<Spiritist> findByName(String param);
}
