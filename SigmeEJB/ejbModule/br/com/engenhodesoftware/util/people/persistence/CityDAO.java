package br.com.engenhodesoftware.util.people.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the City domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.persistence.City
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface CityDAO extends BaseDAO<City> {
	/**
	 * Retrieves all cities that have the specified parameter as the beginning of its name.
	 * 
	 * @param name
	 *          The text to search for in the beginning of the cities' names.
	 * 
	 * @return A list of City objects that match the query (could be empty if there are no matches).
	 */
	List<City> findByName(String name);

	/**
	 * Retrieves the city that has the exact name and state acronym.
	 * 
	 * @param cityName The exact name of the city to be retrieved.
	 * @param stateAcronym The exact acronym of the state of the city to be retrieved.
	 * 
	 * @return A City object that matches the query.
	 * 
	 * @throws PersistentObjectNotFoundException
	 *           If there are no cities with the exact name and state acronym.
	 * @throws MultiplePersistentObjectsFoundException
	 *           If there are more than one city with the exact name and state acronym given.
	 */
	City retrieveByNameAndStateAcronym(String cityName, String stateAcronym) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
