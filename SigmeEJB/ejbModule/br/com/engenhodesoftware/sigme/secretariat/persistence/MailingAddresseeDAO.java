package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface MailingAddresseeDAO extends BaseDAO<MailingAddressee> {
	/**
	 * TODO: document this method.
	 * 
	 * @param addressee
	 * @return
	 */
	List<String> retrieveEmailsFromInstitutionMailingAddressee(InstitutionMailingAddressee addressee);

	/**
	 * TODO: document this method.
	 * 
	 * @param addressee
	 * @return
	 */
	List<String> retrieveEmailsFromRegionalMailingAddressee(RegionalMailingAddressee addressee);
}
