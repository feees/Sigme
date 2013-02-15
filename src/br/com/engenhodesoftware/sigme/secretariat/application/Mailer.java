package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
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
	
	/** TODO: document this field. */
	@EJB
	private SecretariatInformation secretariatInformation;

	/**
	 * TODO: document this method.
	 * 
	 * @param event
	 */
	public void sendMailing(@Observes @InBackground BackgroundEvent event) {
		// FIXME: check if correct event!
		SendMailingEvent mailingEvent = (SendMailingEvent) event;
		Mailing mailing = mailingEvent.getMailing();
		System.out.println("###### Mailer received send mailing event for mailing: " + mailing.getSubject());
		
		SecretariatInformation.SmtpConfig smtpConfig = secretariatInformation.getSmtpConfig();
		if (smtpConfig == null)
			throw new IllegalStateException("TODO: change to a domain exception?");

		// FIXME: do proper sending, like the Spammer.
		// Add the default sender (email and name) to the properties file. Should we allow to change the sender?
		// Group senders or send individual emails? The former might be faster, the latter allows for personalization.
		// Google for best practices in mailing, if the SMTP server needs configuration, etc. Contact UOL Host. Talk to the guy that is hosting Sigme at Acnet.
		SimpleEmail email = new SimpleEmail();
		try {
			email.setHostName(smtpConfig.getHostName());
			email.setSmtpPort(smtpConfig.getPort());
			email.addTo("jdoe@somewhere.org", "John Doe");
			email.setFrom("me@apache.org", "Me");
			email.setSubject(mailing.getSubject());
			email.setMsg(mailing.getBody());
			email.send();
			System.out.println("###### Mailer sent a test email");
		}
		catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
