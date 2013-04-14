package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;

/**
 * Local EJB interface for the "Send Mailing" use case.
 * 
 * Waiting for definition of a possible integration of this functionality with the Email Manager tool or something
 * similar.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Local
public interface SendMailingService extends Serializable {
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
	List<EmailDelivery> viewMailingDeliveries(Mailing mailing);
}
