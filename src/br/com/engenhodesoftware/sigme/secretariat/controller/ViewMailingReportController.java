package br.com.engenhodesoftware.sigme.secretariat.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.secretariat.application.InvalidMailingException;
import br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService;
import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.util.ejb3.controller.JSFController;

/**
 * TODO: document this type.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@SessionScoped
public class ViewMailingReportController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ViewMailingReportController.class.getCanonicalName());

	/** TODO: document this field. */
	@EJB
	private SendMailingService sendMailingService;

	/** TODO: document this field. */
	private Mailing mailing;

	/** TODO: document this field. */
	private Boolean delivered;

	/** TODO: document this field. */
	private List<EmailDelivery> deliveries;

	/**
	 * TODO: document this method.
	 */
	public void loadMailing() {
		// Retrieves the UUID request parameter.
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String uuid = requestParameterMap.get("uuid");

		// If the UUID was specified and either the mailing hasn't been loaded or is a different one, loads the mailing.
		if ((uuid != null) && ((mailing == null) || (!uuid.equals(mailing.getUuid()))))
			try {
				mailing = sendMailingService.retrieveSentMailing(uuid);
			}
			catch (InvalidMailingException e) {
				addGlobalI18nMessage("msgsSecretariat", FacesMessage.SEVERITY_ERROR, "viewMailingReport.error.invalidUuid.summary", "viewMailingReport.error.invalidUuid.detail");
			}
	}

	/** Getter for mailing. */
	public Mailing getMailing() {
		loadMailing();
		return mailing;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public Boolean getDelivered() {
		logger.log(Level.FINE, "Checking if mailing \"{0}\" (sent on {1}) has been fully delivered...", new Object[] { mailing.getSubject(), mailing.getSentDate() });
		return sendMailingService.isMailingDelivered(mailing);
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public List<EmailDelivery> getDeliveries() {
		deliveries = sendMailingService.viewMailingDeliveries(mailing);
		logger.log(Level.FINE, "Retrieving deliveries for mailing \"{0}\" (sent on {1}) returned {2} results.", new Object[] { mailing.getSubject(), mailing.getSentDate(), deliveries.size() });
		return deliveries;
	}
}
