package br.com.engenhodesoftware.sigme.secretariat.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.secretariat.application.InvalidMailingException;
import br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.persistence.MailingListDAO;
import br.com.engenhodesoftware.util.ejb3.controller.JSFController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Send Mailing".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@ConversationScoped
public class SendMailingController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/secretariat/sendMailing/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SendMailingController.class.getCanonicalName());

	/** The service class that implements the use case. */
	@EJB
	private SendMailingService sendMailingService;

	/** The JSF conversation to which the instance belongs. */
	@Inject
	private Conversation conversation;

	/** The DAO for MailingList objects. */
	@EJB
	private MailingListDAO mailingListDAO;

	/** Param: the list of recipients in the data table of recipients. */
	private List<MailingList> recipients = new ArrayList<MailingList>();

	/** Param: the recipient selected in the data table of recipients or to be added by the new recipient dialog. */
	private MailingList recipient;

	/** Param: the message's subject. */
	private String subject;

	/** Param: the message's contents. */
	private String message;

	/** Param: the mailing's UUID. */
	private String sentMailingUuid;

	/** Getter for recipients. */
	public List<MailingList> getRecipients() {
		return recipients;
	}

	/** Getter for recipient. */
	public MailingList getRecipient() {
		return recipient;
	}

	/** Setter for recipient. */
	public void setRecipient(MailingList recipient) {
		this.recipient = recipient;
	}

	/** Getter for subject. */
	public String getSubject() {
		return subject;
	}

	/** Setter for subject. */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** Getter for message. */
	public String getMessage() {
		return message;
	}

	/** Setter for message. */
	public void setMessage(String message) {
		this.message = message;
	}

	/** Getter for sentMailingUuid. */
	public String getSentMailingUuid() {
		return sentMailingUuid;
	}

	/**
	 * Resets the selected recipient from the data table by setting the parameter to null.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void resetRecipient() {
		recipient = null;
	}

	/**
	 * Suggests existing mailing lists as possible recipients to be added to the mailing based on part of these mailing
	 * lists' names as typed by the user.
	 * 
	 * This method is intended to be used with AJAX, through the Primefaces AutoComplete component.
	 * 
	 * @param param
	 *          Part of the name of existing mailing lists to be suggested.
	 * @return List of mailing lists whose names start with the string sent by the user.
	 */
	public List<MailingList> suggestRecipient(String param) {
		if ((param != null) && (param.length() > 0)) {
			List<MailingList> suggestions = mailingListDAO.findByName(param);
			logger.log(Level.FINE, "Suggestion for mailing lists with name containing \"{0}\" returned {1} results.", new Object[] { param, suggestions.size() });
			return suggestions;
		}
		return null;
	}

	/**
	 * Begins the "Send Mailing" use case by starting a conversation scope for this controller and directing the browser
	 * to the webpage with the "Send Mailing" form.
	 * 
	 * @return The path to the webpage with the "Send Mailing" form.
	 */
	public String begin() {
		// If not already part of a long-running conversation, begin one.
		if (conversation.isTransient()) {
			conversation.begin();
			logger.log(Level.FINER, "Beginning new conversation for \"Send Mailing\" use case: {0}.", conversation.getId());
		}

		// If there is only one mailing list registered in the system, uses it by default as recipient.
		MailingList singleList = sendMailingService.retrieveSingleMailingList();
		if (singleList != null) {
			logger.log(Level.FINER, "Mailing list \"{0}\" is the only one in the system. Using it by default as recipient.", singleList.getName());
			recipients.add(singleList);
		}

		// Open the initial page for the use case.
		return VIEW_PATH + "index.faces?faces-redirect=true";
	}

	/**
	 * Processes all the input of the "Send Mailing" form and, if everything is OK, invokes the use case implementation in
	 * the application layer.
	 * 
	 * @return The path to the webpage with the success message or <code>null</code> if there are any errors in the input.
	 */
	public String send() {
		logger.log(Level.FINEST, "Received input data:\n\t- recipients list contains {0} items\n\t- message contains {1} characters", new Object[] { recipients.size(), (message == null) ? 0 : message.length() });

		// Checks that the recipients list is not empty.
		if (recipients.isEmpty()) {
			logger.log(Level.INFO, "User attempted to send a mailing with no recipients. Displaying an error message.");
			addGlobalI18nMessage("msgsSecretariat", FacesMessage.SEVERITY_WARN, "sendMailing.error.noRecipients.summary", "sendMailing.error.noRecipients.detail");
			return null;
		}

		// Checks that the message is not empty.
		if ((message == null) || message.isEmpty()) {
			logger.log(Level.INFO, "User attempted to send a mailing with no message. Displaying an error message.");
			addGlobalI18nMessage("msgsSecretariat", FacesMessage.SEVERITY_WARN, "sendMailing.error.emptyMessage.summary", "sendMailing.error.emptyMessage.detail");
			return null;
		}

		// If everything is fine, creates the mailing and executes the use case.
		try {
			Set<MailingList> recipients = new HashSet<MailingList>();
			recipients.addAll(this.recipients);
			Mailing mailing = new Mailing(recipients, subject, message);
			sendMailingService.sendMailing(mailing);
			sentMailingUuid = mailing.getUuid();
		}
		catch (InvalidMailingException e) {
			logger.log(Level.FINE, "Received invalid mailing exception while trying to send the mailing.");
			addGlobalI18nMessage("msgsSecretariat", FacesMessage.SEVERITY_WARN, "sendMailing.error.invalidMailing.summary", "sendMailing.error.invalidMailing.detail");
			return null;
		}

		// Ends the conversation, but places the controller instance in the flash scope for final access.
		conversation.end();
		getFlash().put("sendMailingController", this);

		// Proceeds to the final view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
}
