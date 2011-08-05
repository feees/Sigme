package br.com.engenhodesoftware.sigme.secretariat.controller;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO;
import br.com.engenhodesoftware.util.ejb3.controller.JSFAction;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Send Mailing".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@ConversationScoped
public class SendMailingAction extends JSFAction {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/secretariat/sendMailing/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SendMailingAction.class.getCanonicalName());

	/** The conversation. */
	@Inject
	private Conversation conversation;

	/** The "Send Mailing" service. */
	@EJB
	private SendMailingService sendMailingService;

	/** The DAO for MailingList objects. */
	@EJB
	private MailingListDAO mailingListDAO;

	/** Indicates in which step of the process we are. */
	private int step = 1;

	/** Indicates which steps are accessible currently. */
	private int maxStep = 1;

	/** Input: the list of recipients. */
	private SortedSet<MailingList> recipients = new TreeSet<MailingList>();

	/** Input: the recipient being referred to. */
	private MailingList selectedRecipient;

	/** Input: the subject of the message. */
	private String subject;

	/** Input: the body of the message. */
	private String body;

	/** Getter for step. */
	public int getStep() {
		return step;
	}

	/** Getter for maxStep. */
	public int getMaxStep() {
		return maxStep;
	}

	/**
	 * Getter for "virtual" property selectedRecipient. Returns always null because we never show anything in the form
	 * regarding this property.
	 * 
	 * @return Nothing.
	 */
	public MailingList getSelectedRecipient() {
		return null;
	}

	/**
	 * Setter for "virtual" property selectedRecipient. Instead of setting a property, adds the recipient to the list.
	 * 
	 * This method is intended to be used with AJAX.
	 * 
	 * @param selectedRecipient
	 *          the selectedRecipient to set
	 */
	public void setSelectedRecipient(MailingList selectedRecipient) {
		recipients.add(selectedRecipient);
		logger.log(Level.INFO, "Adding new recipient (now {1}): \"{0}\".", new Object[] { selectedRecipient.getName(), recipients.size() });
	}

	/** Getter for recipients. */
	public SortedSet<MailingList> getRecipients() {
		return recipients;
	}

	/** Setter for recipients. */
	public void setRecipients(SortedSet<MailingList> recipients) {
		this.recipients = recipients;
	}

	/**
	 * Analyzes what has been written so far in the recipient field and, if not empty, looks for mailing lists that
	 * contain the given name and returns them in a list, so a dynamic pop-up list can be displayed. This method is
	 * intended to be used with AJAX.
	 * 
	 * @param param
	 *          The AJAX event.
	 * @return The list of mailing lists to be displayed in the drop-down auto-completion field.
	 */
	public List<MailingList> suggestRecipient(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<MailingList> suggestions = mailingListDAO.findByName(param);
				logger.log(Level.INFO, "Searching for mailing lists with name containing \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}

	/**
	 * Removes the selected recipient from the list of recipients. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void removeRecipient() {
		logger.log(Level.INFO, "Removing a recipient from the list: {0}", selectedRecipient);
		recipients.remove(selectedRecipient);

		if (recipients.isEmpty()) {
			step = maxStep = 1;
			logger.log(Level.INFO, "Removed all recipients, going back to step 1.");
		}
	}

	/** Getter for subject. */
	public String getSubject() {
		return subject;
	}

	/** Setter for subject. */
	public void setSubject(String subject) {
		this.subject = subject;

		if ("".equals(subject)) {
			step = maxStep = 2;
			logger.log(Level.INFO, "Deleted subject, going back to step 2.");
		}
	}

	/** Getter for body. */
	public String getBody() {
		return body;
	}

	/** Setter for body. */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Formats the body of the message in HTML.
	 * 
	 * @return A string with the HTML-formatted body.
	 */
	public String getFormattedBody() {
		// When we change to HTML WYSIWYG editing, this might have to adapt. <-- Integração com Email Manager resolve, não?
		return (body == null) ? "" : body.replace("\n", "<br />");
	}

	/**
	 * Goes to a specific step.
	 * 
	 * @param step
	 *          the step to set
	 */
	public void gotoStep(int step) {
		if (step <= maxStep)
			this.step = step;
	}

	/**
	 * Moves to the next step.
	 */
	public void nextStep() {
		// Implementar verificação de passo. Só passa ao próximo se passar a validação.
		logger.log(Level.INFO, "Moving to step {0}", step + 1);
		step++;
		if (step > maxStep)
			maxStep = step;
	}

	/**
	 * Implement the rest of the functionality...
	 * 
	 * @return The path to the web page that shows that the process has ended.
	 */
	public String end() {
		// Build a mailing object with the information from the page.
		Mailing mailing = new Mailing(recipients, subject, body);

		// Sends the mailing, i.e. puts it into the queue to be sent.
		sendMailingService.sendMailing(mailing);

		// Ends the conversation.
		if (!conversation.isTransient()) {
			logger.log(Level.INFO, "Ending send mailing conversation. Id = {0}", conversation.getId());
			conversation.end();
		}

		// Redirects to the final view, informing that the mailing has been queued and can be monitored.
		return VIEW_PATH + "done.xhtml";
	}
}
