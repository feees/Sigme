package br.com.engenhodesoftware.sigme.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import br.com.engenhodesoftware.sigme.core.application.InstallSystemService;
import br.com.engenhodesoftware.sigme.core.application.exceptions.SystemInstallFailedException;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.ejb3.controller.JSFAction;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Install System".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Model
public class InstallSystemAction extends JSFAction {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/installSystem/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(InstallSystemAction.class.getCanonicalName());

	/** The "Install System" service. */
	@EJB
	private InstallSystemService installSystemService;

	/** The session controller. We need it to invalidate the menu model once the system is installed. */
	@Inject
	private SessionController sessionController;

	/** Input: the administrator being registered during the installation. */
	private Spiritist admin = new Spiritist();

	/** Input: the repeated password for the admininstrator registrattion. */
	private String repeatPassword;

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
	 * Checks if both password fields have the same value. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public boolean checkPasswords() {
		if (((repeatPassword != null) && (!repeatPassword.equals(admin.getPassword()))) || ((repeatPassword == null) && (admin.getPassword() != null))) {
			logger.log(Level.INFO, "Password and repeated password are not the same");
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_WARN, "installSystem.error.passwordsDontMatch.summary", "installSystem.error.passwordsDontMatch.detail");
			return false;
		}
		return true;
	}

	/**
	 * Registers the administrator as one of the steps of system installation and moves to the next step.
	 * 
	 * @return The path to the web page that shows the next step in the installation process.
	 */
	public String registerAdministrator() {
		logger.log(Level.FINEST, "Received input data:\n\t- admin.name = {0}\n\t- admin.email = {1}", new Object[] { admin.getName(), admin.getEmail() });

		// Check if passwords don't match. Add an error in that case.
		if (!checkPasswords())
			return null;

		// Installs the system.
		try {
			installSystemService.installSystem(admin);
		}
		catch (SystemInstallFailedException e) {
			logger.log(Level.SEVERE, "System installation threw exception", e);
			addGlobalI18nMessage("msgsCore", FacesMessage.SEVERITY_FATAL, "installSystem.error.installFailed.summary", "installSystem.error.installFailed.detail");
			return null;
		}

		// Invalidates the menu model so it can be rebuilt after the system is installed.
		sessionController.reloadMenuModel();

		// Proceeds to the final view.
		return VIEW_PATH + "done.xhtml?faces-redirect=true";
	}
}
