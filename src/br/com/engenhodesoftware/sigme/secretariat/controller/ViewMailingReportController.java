package br.com.engenhodesoftware.sigme.secretariat.controller;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.secretariat.application.InvalidMailingException;
import br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService;
import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.util.ejb3.application.ListingService;
import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.application.filters.MultipleChoiceFilter;
import br.com.engenhodesoftware.util.ejb3.controller.ListingController;

/**
 * TODO: document this type.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@Named
@SessionScoped
public class ViewMailingReportController extends ListingController<EmailDelivery> {
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
	Filter<Mailing> mailingFilter = new MultipleChoiceFilter<Mailing>("viewMailingReport.filter.byMailing", "mailing", null, null, null);

	/** @see br.com.engenhodesoftware.util.ejb3.controller.ListingController#getListingService() */
	@Override
	protected ListingService<EmailDelivery> getListingService() {
		return sendMailingService;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.ListingController#initFilters() */
	@Override
	protected void initFilters() { /* No filtering. */ }

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
				logger.log(Level.FINE, "Loading a new mailing from UUID {0}", uuid);
				mailing = sendMailingService.retrieveSentMailing(uuid);
				
				// A filter that constraints the listing to the deliveries of a single mailing is always set.
				filter = mailingFilter;
				filtering = true;
				filterParam = mailing.getId().toString();				
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
		logger.log(Level.FINER, "Checking if mailing \"{0}\" (sent on {1}) has been fully delivered...", new Object[] { mailing.getSubject(), mailing.getSentDate() });
		return sendMailingService.isMailingDelivered(mailing);
	}
}
