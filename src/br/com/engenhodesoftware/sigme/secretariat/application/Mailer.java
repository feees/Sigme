package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.UserTransaction;

import br.com.engenhodesoftware.sigme.secretariat.persistence.EmailDeliveryDAO;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingAddresseeDAO;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO;
import br.com.engenhodesoftware.util.jms.BackgroundEvent;
import br.com.engenhodesoftware.util.jms.InBackground;

/**
 * TODO: document this type.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@ApplicationScoped
@Singleton
public class Mailer implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(Mailer.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private SecretariatInformation secretariatInformation;
	
	/** TODO: document this field. */
	@EJB
	private MailingListDAO mailingListDAO;
	
	/** TODO: document this field. */
	@EJB
	private MailingAddresseeDAO mailingAddresseeDAO;
	
	/** TODO: document this field. */
	@EJB
	private EmailDeliveryDAO emailDeliveryDAO;
	
	@Resource
	private UserTransaction transaction;

	/**
	 * TODO: document this method.
	 * 
	 * @param event
	 */
	public void sendMailing(@Observes @InBackground BackgroundEvent event) {
		// TODO: send mailing is disabled. It hasn't been finished yet and will receive a lower priority now.
		logger.log(Level.WARNING, "Background email sender has been disabled.");
		
//		// Checks if the event was properly received.
//		if (event == null) {
//			logger.log(Level.WARNING, "Background email sender received null event object. Aborting.");
//			return;
//		}
//		if (! (event instanceof SendMailingEvent)) {
//			logger.log(Level.WARNING, "Background email sender received incompatible event type: {0}.", event.getClass().getName());
//			return;
//		}
//		
//		// Checks if the mailing event contains a proper mailing object.
//		SendMailingEvent mailingEvent = (SendMailingEvent) event;
//		Mailing mailing = mailingEvent.getMailing();
//		if (mailing == null) {
//			logger.log(Level.WARNING, "Background email sender received mailing event with null mailing object. Aborting.");
//			return;
//		}
//		
//		// Checks if the mailing object contains at least one recipient.
//		Set<MailingList> mailingLists = mailing.getRecipients();
//		if ((mailingLists == null) || (mailingLists.size() == 0)) {
//			logger.log(Level.WARNING, "Background email sender received mailing event whose mailing object contains no recipients. Aborting.");
//			return;
//		}
//		
//		// Everything OK. Retrieves the information from the mailing object.
//		String subject = mailing.getSubject();
//		String body = mailing.getBody();
//		logger.log(Level.INFO, "Background email sender will start sending new mailing to {0} mailing lists with subject: {1}", new Object[] { mailingLists.size(), subject });
//
//		// Resolves the recipient emails from all the addressees of all the mailing lists included in this mailing.
//		SortedSet<String> recipients = new TreeSet<>();
//		for (MailingList list : mailingLists) {
//			// Merges the mailing list with the current persistence session to be able to initialize lazy collections.
//			list = mailingListDAO.merge(list);
//	
//			// Resolve the email addresses.
//			if (list != null) for (MailingAddressee addressee : list.getAddressees()) {
//				List<String> recipientList = mailingAddresseeDAO.retrieveEmailsFromAddressee(addressee);
//				recipients.addAll(recipientList);
//				logger.log(Level.FINE, "Retrieved {0} email recipients from a {1} in mailing list {2}. Recipients set now contains {3} emails.", new Object[] { recipientList.size(), addressee.getClass().getName(), list.getName(), recipients.size() });
//			}
//		}
//		
//		try {
//			// Creates email delivery objects for each email message, so the user can monitor the progress.
//			for (String email : recipients) {
//				EmailDelivery delivery = new EmailDelivery(mailing, email);
//				emailDeliveryDAO.save(delivery);
//			}
//			transaction.commit();
//			
//			// FIXME: MOCK SEND.
//			for (EmailDelivery delivery : mailing.getDeliveries()) {
//				String email = delivery.getRecipientEmail();
//				
//				try {
//					logger.log(Level.FINE, "Sleeping for 3 seconds...");
//					Thread.sleep(3000);
//				}
//				catch (InterruptedException e) {
//					logger.log(Level.SEVERE, "Exception while sleeping.", e);
//				}
//				
//				logger.log(Level.FINE, "Sending mailing \"{0}\" to: {1}", new Object[] { subject, email });
//				
//				transaction.begin();
//				delivery = emailDeliveryDAO.refresh(delivery);
//				if ("vitorsouza@msn.com".equals(email)) delivery.setErrorMessage("Simulando um erro...");
//				delivery.setMessageSent(true);
//				emailDeliveryDAO.merge(delivery);
//				transaction.commit();
//			}
//			
//			logger.log(Level.INFO, "Background email sender has concluded sending the mailing with subject: {0}", subject);
//			transaction.begin();
//		}
//		catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException e) {
//			logger.log(Level.WARNING, "Background email sender caught an exception while trying to commit JPA transactions: \"{0}\". Aborting.", e);
//			return;
//		}
		
		// FIXME: do proper sending, like the Spammer. Check comments below and change test code above. 
		// SecretariatInformation.SmtpConfig smtpConfig = secretariatInformation.getSmtpConfig();
		// if (smtpConfig == null)
		// throw new IllegalStateException("TODO: change to a domain exception?");
		//
		// Add the default sender (email and name) to the properties file. Should we allow to change the sender?
		// Group senders or send individual emails? The former might be faster, the latter allows for personalization.
		// Google for best practices in mailing, if the SMTP server needs configuration, etc. Contact UOL Host. Talk to the
		// guy that is hosting Sigme at Acnet.
		// SimpleEmail email = new SimpleEmail();
		// try {
		// email.setHostName(smtpConfig.getHostName());
		// email.setSmtpPort(smtpConfig.getPort());
		// email.addTo("jdoe@somewhere.org", "John Doe");
		// email.setFrom("me@apache.org", "Me");
		// email.setSubject(mailing.getSubject());
		// email.setMsg(mailing.getBody());
		// email.send();
		// System.out.println("###### Mailer sent a test email");
		// }
		// catch (EmailException e) {
		// e.printStackTrace();
		// }
	}
}
