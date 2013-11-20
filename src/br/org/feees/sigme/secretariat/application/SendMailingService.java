package br.org.feees.sigme.secretariat.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.application.ListingService;
import br.org.feees.sigme.secretariat.domain.EmailDelivery;
import br.org.feees.sigme.secretariat.domain.Mailing;
import br.org.feees.sigme.secretariat.domain.MailingList;

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

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countTotalDeliveries(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countPendingDeliveries(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countSentDeliveries(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countErrorDeliveries(Mailing mailing);
}
