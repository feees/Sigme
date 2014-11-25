package br.org.feees.sigme.secretariat.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.org.feees.sigme.secretariat.application.InvalidMailingException;
import br.org.feees.sigme.secretariat.application.SendMailingService;
import br.org.feees.sigme.secretariat.domain.EmailDelivery;
import br.org.feees.sigme.secretariat.domain.Mailing;
import br.ufes.inf.nemo.util.ejb3.application.ListingService;
import br.ufes.inf.nemo.util.ejb3.application.filters.Criterion;
import br.ufes.inf.nemo.util.ejb3.application.filters.CriterionType;
import br.ufes.inf.nemo.util.ejb3.application.filters.Filter;
import br.ufes.inf.nemo.util.ejb3.application.filters.MultipleChoiceFilter;
import br.ufes.inf.nemo.util.ejb3.controller.ListingController;

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
	Filter<Mailing> mailingFilter = new MultipleChoiceFilter<Mailing>("viewMailingReport.filter.byMailing", "mailing", "total", null, null);

	/** TODO: document this field. */
	private CartesianChartModel statusModel;

	/** TODO: document this field. */
	private long statusModelMaxValue;

	/** TODO: document this field. */
	private long pendingDeliveriesCount;

	/** TODO: document this field. */
	private long lastUpdate;

	/** @see br.ufes.inf.nemo.util.ejb3.controller.ListingController#getListingService() */
	@Override
	protected ListingService<EmailDelivery> getListingService() {
		return sendMailingService;
	}

	/** @see br.ufes.inf.nemo.util.ejb3.controller.ListingController#initFilters() */
	@Override
	protected void initFilters() {
		// Creates filters for displaying the detailed status: total, sent, error, pending.
		filters = new ArrayList<>();
		filters.add(mailingFilter);
		filters.add(new MultipleChoiceFilter<Mailing>("viewMailingReport.filter.byMailing", "mailing", "sent", null, null, new Criterion("messageSent", CriterionType.EQUALS, Boolean.TRUE), new Criterion("errorMessage", CriterionType.IS_NOT_NULL)));
		filters.add(new MultipleChoiceFilter<Mailing>("viewMailingReport.filter.byMailing", "mailing", "error", null, null, new Criterion("messageSent", CriterionType.EQUALS, Boolean.TRUE), new Criterion("errorMessage", CriterionType.IS_NULL)));
		filters.add(new MultipleChoiceFilter<Mailing>("viewMailingReport.filter.byMailing", "mailing", "pending", null, null, new Criterion("messageSent", CriterionType.EQUALS, Boolean.FALSE)));
	}

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
				pendingDeliveriesCount = sendMailingService.countPendingDeliveries(mailing);

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
		logger.log(Level.FINER, "Checking if mailing \"{0}\" (sent on {1}) has been fully delivered. Pending deliveries: {2}.", new Object[] { mailing.getSubject(), mailing.getSentDate(), pendingDeliveriesCount });
		return (pendingDeliveriesCount == 0);
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	public CartesianChartModel getStatusModel() {
		// Checks if we need to build the status model. The timestamp allows us not to build another model for the next X
		// seconds, where X is half the default refresh rate.
		if ((statusModel == null) || (System.currentTimeMillis() > (lastUpdate + DEFAULT_REFRESH_RATE * 500))) {
			statusModel = createStatusModel();
			lastUpdate = System.currentTimeMillis();
		}
		return statusModel;
	}

	/** Getter for statusModelMaxValue. */
	public long getStatusModelMaxValue() {
		return statusModelMaxValue;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @return
	 */
	private CartesianChartModel createStatusModel() {
		CartesianChartModel newModel = new CartesianChartModel();

		// Creates arrays of label keys and counts. Sets the max value as the total plus 10%. Sets the pending count also.
		String[] labelKeys = new String[] { "total", "sent", "error", "pending" };
		long[] counts = new long[] { sendMailingService.countTotalDeliveries(mailing), sendMailingService.countSentDeliveries(mailing), sendMailingService.countErrorDeliveries(mailing), sendMailingService.countPendingDeliveries(mailing) };
		statusModelMaxValue = counts[0] + Math.round(Math.ceil(0.1 * counts[0]));
		pendingDeliveriesCount = counts[3];

		// Creates a series, adds the delivery counts and adds the series to the model.
		ChartSeries series = new ChartSeries();
		for (int i = 0; i < labelKeys.length; i++)
			series.set(getI18nMessage("msgsSecretariat", "viewMailingReport.table.delivery.status." + labelKeys[i]), counts[i]);
		newModel.addSeries(series);

		return newModel;
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param event
	 */
	public void filterDetailedStatus(ItemSelectEvent event) {
		// Selects the filter to apply following the presentation order: total, sent, error, pending.
		filter = filters.get(event.getItemIndex());
		goFirst();
		
		// FIXME: apply a different title to the detailed status panel, depending on the filter.
		//String title = getI18nMessage("msgsSecretariat", "viewMailingReport.text.detailedStatus." + filter.getLabel());
	}
}
