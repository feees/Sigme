package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;

/**
 * Interface for a DAO for objects of the MailingAddressee domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface MailingAddresseeDAO extends BaseDAO<MailingAddressee> {
	/**
	 * TODO: document this method.
	 * 
	 * @param addressee
	 * @return
	 */
	List<String> retrieveEmailsFromAddressee(MailingAddressee addressee);

	/**
	 * Retrieves the e-mail addresses of all spiritists included in an institution mailing addressee. This means all
	 * spiritists which have the appropriate attendance (depends on the scope -- active, inactive, all) for the given
	 * institution.
	 * 
	 * @param addressee
	 *          The InstitutionMailingAddressee object from which the recipients should be retrieved.
	 * 
	 * @return A list of strings, each of which an e-mail address (could be empty if there are no spiritists associated
	 *         with the institution).
	 */
	List<String> retrieveEmailsFromInstitutionMailingAddressee(InstitutionMailingAddressee addressee);

	/**
	 * Retrieves the e-mail addresses of all spiritists included in an regional mailing addressee. This means all
	 * spiritists which have the appropriate attendance (depends on the scope -- active, inactive, all) for all
	 * institutions in the given regional.
	 * 
	 * @param addressee
	 *          The RegionalMailingAddressee object from which the recipients should be retrieved.
	 * 
	 * @return A list of strings, each of which an e-mail address (could be empty if there are no spiritists associated
	 *         with the institutions of the regional).
	 */
	List<String> retrieveEmailsFromRegionalMailingAddressee(RegionalMailingAddressee addressee);

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	List<String> retrieveOptInValidEmails();
}
