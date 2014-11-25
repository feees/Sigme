package br.org.feees.sigme.core.application;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.org.feees.sigme.core.domain.EmailConfirmation;
import br.org.feees.sigme.core.domain.SigmeConfiguration;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.exceptions.EmailAlreadyRegisteredException;
import br.org.feees.sigme.core.exceptions.SystemNotConfiguredException;
import br.org.feees.sigme.core.persistence.SigmeConfigurationDAO;
import br.org.feees.sigme.core.persistence.SpiritistDAO;
import br.org.feees.sigme.core.view.I18n;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * TODO: document this.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.application.RegisterService
 */
@Stateless
public class RegisterServiceBean implements RegisterService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(RegisterServiceBean.class.getCanonicalName());

	/** The DAO for Spiritist objects. */
	@EJB
	private SpiritistDAO spiritistDAO;
	
	/** The DAO for SigmeConfiguration objects. */
	@EJB
	private SigmeConfigurationDAO configDAO;

	/** @see br.org.feees.sigme.core.application.RegisterService#sendConfirmation(br.org.feees.sigme.core.domain.EmailConfirmation) */
	@Override
	public void sendConfirmation(EmailConfirmation emailConfirmation) throws EmailAlreadyRegisteredException, SystemNotConfiguredException {
		String emailAddress = emailConfirmation.getEmail();

		// Checks that the specified e-mail is not in use by another Spiritist.
		Spiritist spiritist = null;
		try {
			spiritist = spiritistDAO.retrieveByEmail(emailAddress);
		}
		catch (PersistentObjectNotFoundException e) {
			// This is what we are aiming for. No problem here.
			logger.log(Level.FINE, "There are no reigstered spiritists with the e-mail address: {0}", emailAddress);
		}
		catch (MultiplePersistentObjectsFoundException e) {
			// Problem for the user (email is taken) and for us. There shouldn't be two spiritists with the same email.
			logger.log(Level.WARNING, "There are multiple spiritists with the e-mail address: {0}", emailAddress);
			throw new EmailAlreadyRegisteredException(emailAddress);
		}
		if (spiritist != null) {
			// Problem for the user. There's already a spiritist with that e-mail address.
			logger.log(Level.INFO, "E-mail address {0} has already been registered. Cannot proceed with registration.", emailAddress);
			throw new EmailAlreadyRegisteredException(emailAddress);
		}

		// Generates a unique code for this confirmation and sets its timestamp. Saves it too.
		emailConfirmation.setConfirmationCode(UUID.randomUUID().toString());
		emailConfirmation.setTimestamp(new Date(System.currentTimeMillis()));
		
		// FIXME: save the confirmation.
		// Then, to wrap up:
		//	- Implement code verification
		//	- Improve the confirmation email with an URL that takes the visitor directly to the code verification.
		//	- Finish the registration process after code verification.
		
		// Retrieves the current system configuration.
		SigmeConfiguration config = null;
		try {
			config = configDAO.retrieveCurrentConfiguration();
		}
		catch (PersistentObjectNotFoundException e) {
			logger.log(Level.SEVERE, "Could not retrieve current system configuration! The system should be properly installed before using this feature!");
			throw new SystemNotConfiguredException(e);
		}

		// Sends the confirmation via e-mail.
		try {
			logger.log(Level.INFO, "At {0}, sending confirmation code {1} to e-mail {2}.", new Object[] { emailConfirmation.getTimestamp(), emailConfirmation.getConfirmationCode(), emailAddress });
			Email email = new SimpleEmail();
			email.setHostName(config.getSmtpServerAddress());
			email.setSmtpPort(config.getSmtpServerPort());
			email.setAuthenticator(new DefaultAuthenticator(config.getSmtpUsername(), config.getSmtpPassword()));
			email.setSSLOnConnect(false);
			email.setFrom(config.getSmtpUsername());
			email.setSubject(I18n.getFormattedString("email.confirmationCode.subject", config.getOwner().getAcronym()));
			email.setMsg(I18n.getFormattedString("email.confirmationCode.text", emailConfirmation.getConfirmationCode(), config.getOwner().getAcronym()));
			email.addTo(emailAddress);
			email.send();
		}
		catch (EmailException e) {
			logger.log(Level.SEVERE, "Could not send e-mail message through {0}:{1} with username {2}!", new Object[] {config.getSmtpServerAddress(), config.getSmtpServerPort(), config.getSmtpUsername() });
		}
	}
}
