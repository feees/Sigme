package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import org.feees.sigme.people.domain.City;

import br.org.feees.sigme.core.domain.Regional;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Interface for a DAO for objects of the Regional domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Regional
 * @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO
 */
@Local
public interface RegionalDAO extends BaseDAO<Regional> {
	/**
	 * Retrieves the regional of a given city.
	 * 
	 * This method exists because of the design decision of not have the Regional - City association navigable from the
	 * city object. This way a small "Legal Entity framework" can be used to represent people's and institution's
	 * addresses.
	 * 
	 * @param city
	 *          The City object whose regional is to be retrieved.
	 * 
	 * @return The Regional object which is associated with the given city.
	 * 
	 * @throws PersistentObjectNotFoundException
	 *           If the city is not associated with any regionals.
	 * @throws MultiplePersistentObjectsFoundException
	 *           If there are multiple regionals associated with the given city.
	 */
	Regional retrieveByCity(City city) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
