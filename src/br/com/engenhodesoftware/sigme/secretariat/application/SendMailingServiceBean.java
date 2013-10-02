package br.com.engenhodesoftware.sigme.secretariat.application;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingDAO;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO;
import br.com.engenhodesoftware.util.ejb3.application.ListingServiceBean;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.jms.InForeground;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Stateless session bean implementing the "Send Mailing" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService
 */
@Stateless
@TransactionAttribute
public class SendMailingServiceBean extends ListingServiceBean<EmailDelivery> implements SendMailingService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SendMailingServiceBean.class.getCanonicalName());

	/** The DAO for Mailing objects. */
	@EJB
	private MailingDAO mailingDAO;

	/** The DAO for MailingList objects. */
	@EJB
	private MailingListDAO mailingListDAO;

	/** TODO: document this field. */
	@EJB
	private EmailDeliveryDAO emailDeliveryDAO;

	/** CDI Event to decouple the creation of the mailing and its distribution. */
	@Inject
	@InForeground
	private Event<SendMailingEvent> mailingEvent;

	/** @see br.com.engenhodesoftware.util.ejb3.application.ListingService#getDAO() */
	@Override
	public BaseDAO<EmailDelivery> getDAO() {
		return emailDeliveryDAO;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#retrieveSingleMailingList() */
	@Override
	public MailingList retrieveSingleMailingList() {
		return mailingListDAO.retrieveSingleMailingList();
	}

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

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#retrieveSentMailing(java.lang.String) */
	@Override
	public Mailing retrieveSentMailing(String uuid) throws InvalidMailingException {
		try {
			return mailingDAO.retrieveByUuid(uuid);
		}
		catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
			throw new InvalidMailingException();
		}
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#isMailingDelivered(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public Boolean isMailingDelivered(Mailing mailing) {
		long count = emailDeliveryDAO.countPendingDeliveriesFromMailing(mailing);
		return (count == 0);
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#countTotalDeliveries(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countTotalDeliveries(Mailing mailing) {
		return emailDeliveryDAO.countDeliveriesFromMailing(mailing);
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#countPendingDeliveries(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countPendingDeliveries(Mailing mailing) {
		return emailDeliveryDAO.countPendingDeliveriesFromMailing(mailing);
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#countSentDeliveries(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countSentDeliveries(Mailing mailing) {
		return emailDeliveryDAO.countSentDeliveriesFromMailing(mailing);
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#countErrorDeliveries(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	@Override
	public long countErrorDeliveries(Mailing mailing) {
		return emailDeliveryDAO.countErrorDeliveriesFromMailing(mailing);
	}
}
