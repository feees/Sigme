package br.org.feees.sigme.secretariat.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.org.feees.sigme.secretariat.domain.MailingList;
import br.org.feees.sigme.secretariat.persistence.MailingListDAO;
import br.ufes.inf.nemo.util.ejb3.controller.PersistentObjectConverterFromId;

/**
 * Application-scoped bean that centralizes controller information for the secretariat package. This bean differs from the
 * singleton EJB SecretariatInformation by containing data relative to the presentation layer (controller and view, i.e., the
 * web).
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@ApplicationScoped
public class SecretariatController implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SecretariatController.class.getCanonicalName());

	/** The DAO for MailingList objects. */
	@EJB
	private MailingListDAO mailingListDAO;

	/** JSF Converter for MailingList objects. */
	private PersistentObjectConverterFromId<MailingList> mailingListConverter;

	/** Getter for mailingListConverter. */
	public Converter getMailingListConverter() {
		// Lazily create the converter.
		if (mailingListConverter == null) {
			logger.log(Level.FINEST, "Creating a mailing list converter...");
			mailingListConverter = new PersistentObjectConverterFromId<MailingList>(mailingListDAO);
		}
		return mailingListConverter;
	}
}
