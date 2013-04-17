package br.com.engenhodesoftware.sigme.secretariat.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.util.ejb3.application.ListingService;

/**
 * Local EJB interface for the "Send Mailing" use case.
 * 
 * Waiting for definition of a possible integration of this functionality with the Email Manager tool or something
 * similar.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Local
public interface SendMailingService extends ListingService<EmailDelivery> {
	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	MailingList retrieveSingleMailingList();

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @throws InvalidMailingException
	 */
	void sendMailing(Mailing mailing) throws InvalidMailingException;

	/**
	 * TODO: document this method.
	 * 
	 * @param uuid
	 * @return
	 */
	Mailing retrieveSentMailing(String uuid) throws InvalidMailingException;

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	Boolean isMailingDelivered(Mailing mailing);
}
