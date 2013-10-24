package br.com.engenhodesoftware.sigme.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.core.application.InstallSystemService;
import br.com.engenhodesoftware.sigme.core.application.SystemInstallFailedException;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.ejb3.controller.JSFController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Install System".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@ConversationScoped
public class InstallSystemController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/installSystem/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(InstallSystemController.class.getCanonicalName());

	/** The JSF conversation. */
	@Inject
	private Conversation conversation;

	/** The "Install System" service. */
	@EJB
	private InstallSystemService installSystemService;

	/** The session controller. We need it to invalidate the menu model once the system is installed. */
	@Inject
	private SessionController sessionController;

	/** Input: the administrator being registered during the installation. */
	private Spiritist admin = new Spiritist();

	/** Input: the repeated password for the admininstrator registration. */
	private String repeatPassword;
	
	/** Input: the institution that owns this Sigme installation. */
	private Institution owner = new Institution();

	/** Getter for admin. */
	public Spiritist getAdmin() {
		return admin;
	}

	/** Setter for admin. */
	public void setAdmin(Spiritist admin) {
		this.admin = admin;
	}

	/** Getter for repeatPassword. */
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/** Setter for repeatPassword. */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	/** Getter for owner. */
	public Institution getOwner() {
		return owner;
	}

	/** Setter for owner. */
	public void setOwner(Institution owner) {
		this.owner = owner;
	}

	/**
	 * Analyzes the name that was given to the administrator and, if the short name field is still empty, suggests a value
	 * for it based on the given name.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestShortName() {
		// If the name was filled and the short name is still empty, suggest the first name as short name.
		String name = admin.getName();
		String shortName = admin.getShortName();
		if ((name != null) && ((shortName == null) || (shortName.length() == 0))) {
			int idx = name.indexOf(" ");
			admin.setShortName((idx == -1) ? name : name.substring(0, idx).trim());
			logger.log(Level.FINE, "Suggested \"{0}\" as short name for \"{1}\"", new Object[] { admin.getShortName(), name });
		}
		else logger.log(Level.FINEST, "Short name not suggested: empty name or short name already filled (name is \"{0}\", short name is \"{1}\")", new Object[] { name, shortName });
	}
	
	/**
	 * Analyzes the name that was given for the institution and, if the acronym field is still empty, suggests a value for
	 * it based on the given name. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void suggestAcronym() {
		// If the name was filled and the acronym is still empty, generate one.
		String name = owner.getName();
		String acronym = owner.getAcronym();
		if ((name != null) && ((acronym == null) || (acronym.length() == 0))) {
			// Generate the acronym joining together all upper-case letters of the name.
			StringBuilder acronymBuilder = new StringBuilder();
			char[] chars = name.toCharArray();
			for (char ch : chars)
				if (Character.isUpperCase(ch))
					acronymBuilder.append(ch);
			owner.setAcronym(acronymBuilder.toString());

			logger.log(Level.FINE, "Suggested \"{0}\" as acronym for \"{1}\"", new Object[] { owner.getAcronym(), name });
		}
		else logger.log(Level.FINEST, "Acronym not suggested: empty name or acronym already filled (name is \"{0}\", acronym is \"{1}\"", new Object[] { name, acronym });
	}

	/**
	 * Checks if both password fields have the same value.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void ajaxCheckPasswords() {
		checkPasswords();
	}

	/**
	 * Checks if the contents of the password fields match.
	 * 
	 * @return <code>true</code> if the passwords match, <code>false</code> otherwise.
	 */
	private boolean checkPasswords() {
		if (((repeatPassword != null) && (!repeatPassword.equals(admin.getPassword()))) || ((repeatPassword == null) && (admin.getPassword() != null))) {
			logger.log(Level.INFO, "Password and repeated password are not the same");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "installSystem.error.passwordsDontMatch.summary", "installSystem.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

	/**
	 * Begins the installation process.
	 * 
	 * @return The path to the web page that shows the first step of the installation process.
	 */
	public String begin() {
		// Begins the conversation, dropping any previous conversation, if existing.
		if (!conversation.isTransient()) conversation.end();
		conversation.begin();

		// Go to the first view.
		return VIEW_PATH + "index.xhtml?faces-redirect=true";
	}

	/**
	 * Registers the administrator as one of the steps of system installation and moves to the next step.
	 * 
	 * @return The path to the web page that shows the next step in the installation process.
	 */
	public String registerAdministrator() {
		logger.log(Level.FINEST, "Received input data:\n\t- admin.name = {0}\n\t- admin.email = {1}", new Object[] { admin.getName(), admin.getEmail() });

		// Check if passwords don't match. Add an error in that case.
		if (!checkPasswords()) return null;

		// Proceeds to the next view.
		return VIEW_PATH + "owner.xhtml?faces-redirect=true";
	}
	
	/**
	 * Registers the owner institution as one of the steps of system installation and ends the installation process.
	 * 
	 * @return The path to the web page that shows the next step in the installation process.
	 */
	public String registerOwnerInstitution() {
		logger.log(Level.FINEST, "Received input data:\n\t- owner.name = {0}\n\t- owner.acronym = {1}", new Object[] { owner.getName(), owner.getAcronym() });

		// Installs the system.
		try {
			installSystemService.installSystem(admin, owner);
		}
		catch (SystemInstallFailedException e) {
			logger.log(Level.SEVERE, "System installation threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "installSystem.error.installFailed.summary", "installSystem.error.installFailed.detail");
			return null;
		}

		// Invalidates the menu model so it can be rebuilt after the system is installed.
		sessionController.reloadMenuModel();

		// Ends the conversation.
		conversation.end();
		
		// Proceeds to the final view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
}
