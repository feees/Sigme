package br.org.feees.sigme.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.feees.sigme.core.application.EmailAlreadyRegisteredException;
import br.org.feees.sigme.core.application.RegisterService;
import br.org.feees.sigme.core.domain.EmailConfirmation;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Register".
 * 
 * Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class RegisterController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String VIEW_PATH = "/core/register/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(RegisterController.class.getCanonicalName());

	/** The "Register" service. */
	@EJB
	private RegisterService registerService;

	/** Input: the email being confirmed during the registration. */
	private EmailConfirmation emailConfirmation = new EmailConfirmation();

	/** Input: the confirmation code being verified. */
	private String confirmationCode;

	/** Getter for emailConfirmation. */
	public EmailConfirmation getEmailConfirmation() {
		return emailConfirmation;
	}

	/** Getter for confirmationCode. */
	public String getConfirmationCode() {
		return confirmationCode;
	}

	/** Setter for confirmationCode. */
	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	/**
	 * Begins the registration process.
	 * 
	 * @return The path to the web page that shows the first step of the registration process.
	 */
	public String begin() {
		logger.log(Level.FINEST, "Beginning registration...");
		return VIEW_PATH + "email.xhtml?faces-redirect=true";
	}

	/**
	 * Sends the confirmation code to the email informed by the visitor.
	 * 
	 * @return The path to the web page that shows the next step of the registration process.
	 */
	public String send() {
		logger.log(Level.FINEST, "Sending confirmation code...");

		// Verifies the e-mail address and sends the confirmation code.
		try {
			registerService.sendConfirmation(emailConfirmation);
		}
		
		// If the e-mail address is already in use, redirect the user to an error page.
		catch (EmailAlreadyRegisteredException e) {
			return VIEW_PATH + "/taken.xhtml";
		}

		// Directs the user to the next step in the registration process.
		return VIEW_PATH + "/confirm.xhtml?faces-redirect=true";
	}

	/**
	 * Verifies the given confirmation code and, if valid, proceeds with registration.
	 * 
	 * @return The path to the web page that shows the next step of the registration process.
	 */
	public String verify() {
		logger.log(Level.FINEST, "Verifying confirmation code...");

		// FIXME: implement this.

		// Directs the user to the next step in the registration process.
		return VIEW_PATH + "/register.xhtml?faces-redirect=true";
	}

	/**
	 * Cancels the registration process, going back to the home page.
	 * 
	 * @return The path to the home page.
	 */
	public String cancel() {
		logger.log(Level.FINEST, "Cancelling registration...");
		return "/index.xhtml?faces-redirect=true";
	}
}
