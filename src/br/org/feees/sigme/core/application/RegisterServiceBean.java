package br.org.feees.sigme.core.application;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.org.feees.sigme.core.domain.EmailConfirmation;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.persistence.SpiritistDAO;
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

	/** @see br.org.feees.sigme.core.application.RegisterService#sendConfirmation(br.org.feees.sigme.core.domain.EmailConfirmation) */
	@Override
	public void sendConfirmation(EmailConfirmation emailConfirmation) throws EmailAlreadyRegisteredException {
		String email = emailConfirmation.getEmail();

		// Checks that the specified e-mail is not in use by another Spiritist.
		Spiritist spiritist = null;
		try {
			spiritist = spiritistDAO.retrieveByEmail(email);
		}
		catch (PersistentObjectNotFoundException e) {
			// This is what we are aiming for. No problem here.
			logger.log(Level.FINE, "There are no reigstered spiritists with the e-mail address: {0}", email);
		}
		catch (MultiplePersistentObjectsFoundException e) {
			// Problem for the user (email is taken) and for us. There shouldn't be two spiritists with the same email.
			logger.log(Level.WARNING, "There are multiple spiritists with the e-mail address: {0}", email);
			throw new EmailAlreadyRegisteredException(email);
		}
		if (spiritist != null) {
			logger.log(Level.INFO, "E-mail address {0} has already been registered. Cannot proceed with registration.", email);
			throw new EmailAlreadyRegisteredException(email);
		}

		// Generates a unique code for this confirmation and sets its timestamp.
		emailConfirmation.setConfirmationCode(UUID.randomUUID().toString());
		emailConfirmation.setTimestamp(new Date(System.currentTimeMillis()));

		// Sends the confirmation via e-mail.
		logger.log(Level.INFO, "At {0}, sending confirmation code {1} to e-mail {2}.", new Object[] { emailConfirmation.getTimestamp(), emailConfirmation.getConfirmationCode(), email });
		// FIXME: implement this.
	}
}
