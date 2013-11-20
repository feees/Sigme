package br.org.feees.sigme.secretariat.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;
import br.org.feees.sigme.secretariat.domain.MailingList;

/**
 * Interface for a DAO for objects of the MailingList domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.MailingList
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface MailingListDAO extends BaseDAO<MailingList> {
	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	MailingList retrieveSingleMailingList();

	/**
	 * Retrieves the mailing list that has the exact name specified in the parameter.
	 * 
	 * @param email
	 *          The exact name of the spiritist to be retrieved.
	 * 
	 * @return A MailingList object that matches the query.
	 * 
	 * @throws PersistentObjectNotFoundException
	 *           If there are no mailing lists with the exact email given.
	 * @throws MultiplePersistentObjectsFoundException
	 *           If there are more than one mailing lists with the exact email given.
	 */
	MailingList retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * Retrieves all mailing lists that have the specified parameter as part of its name.
	 * 
	 * @param param
	 *          The text to search for in the name of mailing lists.
	 * 
	 * @return A list of MailingList objects that matches the query (could be empty if there are no matches).
	 */
	List<MailingList> findByName(String param);
}
