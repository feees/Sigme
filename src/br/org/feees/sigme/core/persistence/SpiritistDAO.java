package br.org.feees.sigme.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;
import br.org.feees.sigme.core.domain.Spiritist;

/**
 * Interface for a DAO for objects of the Spiritist domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Spiritist
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface SpiritistDAO extends BaseDAO<Spiritist> {
	/**
	 * Retrieves the spiritist that has the exact email specified in the parameter.
	 * 
	 * @param email
	 *          The exact email address of the spiritist to be retrieved.
	 * 
	 * @return A Spiritist object that matches the query.
	 * 
	 * @throws PersistentObjectNotFoundException
	 *           If there are no spiritists with the exact email given.
	 * @throws MultiplePersistentObjectsFoundException
	 *           If there are more than one spiritist with the exact email given.
	 */
	Spiritist retrieveByEmail(String email) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * Retrieves all spiritists that have the specified parameter as part of its name.
	 * 
	 * @param param
	 *          The text to search for in the name of spiritists.
	 * 
	 * @return A list of Spiritist objects that match the query (could be empty if there are no matches).
	 */
	List<Spiritist> findByName(String param);
}
