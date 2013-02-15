package br.com.engenhodesoftware.sigme.secretariat.application;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingDAO;
import br.com.engenhodesoftware.util.jms.InForeground;

/**
 * Stateless session bean implementing the "Send Mailing" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService
 */
@Stateless
@TransactionAttribute
public class SendMailingServiceBean implements SendMailingService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SendMailingServiceBean.class.getCanonicalName());

	/** The DAO for Mailing objects. */
	@EJB
	private MailingDAO mailingDAO;

	/** CDI Event to decouple the creation of the mailing and its distribution. */
	@Inject
	@InForeground
	private Event<SendMailingEvent> mailingEvent;

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#sendMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	public void sendMailing(Mailing mailing) throws InvalidMailingException {
		logger.log(Level.FINEST, "Executing use case \"Send Mailing\"...");
		
		// Checks if a mailing was properly supplied.
		if (mailing == null) {
			logger.log(Level.WARNING, "Received null mailing object. Throwing exception.");
			throw new InvalidMailingException();
		}
		if ((mailing.getRecipients() == null) || (mailing.getRecipients().isEmpty())) {
			logger.log(Level.WARNING, "Received mailing with no recipients. Throwing exception.");
			throw new InvalidMailingException();
		}
		if ((mailing.getSubject() == null) || (mailing.getSubject().isEmpty())) {
			logger.log(Level.WARNING, "Received mailing with no subject. Throwing exception.");
			throw new InvalidMailingException();
		}
		if ((mailing.getBody() == null) || (mailing.getBody().isEmpty())) {
			logger.log(Level.WARNING, "Received mailing with no body (message). Throwing exception.");
			throw new InvalidMailingException();
		}
		logger.log(Level.FINER, "Sending mailing to {0} recipient mailing list(s) with subject: {1}", new Object[] { mailing.getRecipients().size(), mailing.getSubject() });

		// Saves the mailing object.
		mailing.setSentDate(new Date(System.currentTimeMillis()));
		mailingDAO.save(mailing);

		// Fires a CDI event so the mailing can be processed in the background.
		mailingEvent.fire(new SendMailingEvent(mailing));
	}
}
