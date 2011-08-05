package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the Institution domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Institution
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface InstitutionDAO extends BaseDAO<Institution> {
	/**
	 * Retrieves the institution that has the exact name specified in the parameter.
	 * 
	 * @param name
	 *          The exact name of the institution to be retrieved.
	 * 
	 * @return An Institution object that matches the query.
	 * 
	 * @throws PersistentObjectNotFoundException
	 *           If there are no institutions with the exact name given.
	 * @throws MultiplePersistentObjectsFoundException
	 *           If there are more than one institutions with the exact name given.
	 */
	Institution retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * Retrieves all institutions that have the specified parameter as part of its name or its acronym.
	 * 
	 * @param param
	 *          The text to search for in the name and acronym of institutions.
	 * 
	 * @return A list of Institution objects that matches the query (could be empty if there are no matches).
	 */
	List<Institution> findByNameOrAcronym(String param);

	/**
	 * Retrieves all institutions that have the specified parameter as part of the name of the city in which they're
	 * located.
	 * 
	 * @param cityName
	 *          The text to search for in the name of the city in which institutions are located.
	 * 
	 * @return A list of Institution objects whose city names in their address match the query (could be empty if there
	 *         are no matches).
	 */
	List<Institution> findByCity(String cityName);
}
