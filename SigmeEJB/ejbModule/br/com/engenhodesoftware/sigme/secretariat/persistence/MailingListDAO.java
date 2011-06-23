package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface MailingListDAO extends BaseDAO<MailingList> {
	/**
	 * TODO: document this method.
	 * 
	 * @param name
	 * @return
	 * @throws NoMailingListFoundException
	 */
	MailingList retrieveByName(String name) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

	/**
	 * TODO: document this method.
	 * 
	 * @param param
	 * @return
	 */
	List<MailingList> findByName(String param);
}
