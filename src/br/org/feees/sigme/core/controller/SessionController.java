package br.org.feees.sigme.core.controller;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import br.org.feees.sigme.core.application.CoreInformation;
import br.org.feees.sigme.core.application.SessionInformation;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.exceptions.LoginFailedException;
import br.ufes.inf.nemo.util.ejb3.controller.JSFController;

/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class SessionController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SessionController.class.getCanonicalName());

	/** Information on the whole application. */
	@EJB
	private CoreInformation coreInformation;

	/** Information on the current visitor. */
	@EJB
	private SessionInformation sessionInformation;

	/** The menu that is visible to the current visitor. */
	private MenuModel menuModel;

	/** Input: e-mail for authentication. */
	private String email;

	/** Input: password for authentication. */
	private String password;

	/** Getter for menuModel. */
	public MenuModel getMenuModel() {
		if (menuModel == null) initMenuModel();
		return menuModel;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Indicates if the user has already been identified.
	 * 
	 * @return <code>true</code> if the user is logged in, <code>false</code> otherwise.
	 */
	public boolean isLoggedIn() {
		return sessionInformation.getCurrentUser() != null;
	}

	/**
	 * Provides the current authenticated user.
	 * 
	 * @return The Spiritist object that represents the user that has been authenticated in this session.
	 */
	public Spiritist getCurrentUser() {
		return sessionInformation.getCurrentUser();
	}

	/**
	 * Provides the current date/time.
	 * 
	 * @return A Date object representing the date/time the method was called.
	 */
	public Date getNow() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Provides the expiration date/time for the user session. This makes it possible to warn the user when his session
	 * will expire.
	 * 
	 * @return A Date object representing the date/time of the user's session expiration.
	 */
	public Date getSessionExpirationTime() {
		Date expTime = null;

		// Attempts to retrieve this information from the external context.
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			logger.log(Level.FINEST, "Calculating session expiration time from the HTTP session...");
			long expTimeMillis = session.getLastAccessedTime() + session.getMaxInactiveInterval() * 1000;
			expTime = new Date(expTimeMillis);
		}

		// If it could not be retrieved from the external context, use default of 30 minutes.
		if (expTime == null) {
			logger.log(Level.FINEST, "HTTP Session not available. Using default expiration time: now plus 30 minutes...");
			expTime = new Date(System.currentTimeMillis() + 30 * 60000);
		}

		logger.log(Level.FINEST, "Calculated expiration time: {0}", expTime);
		return expTime;
	}

	/**
	 * Forces the menu to be reloaded. Should be called by other controllers when functionalities that change the
	 * authentication or authorization state for the current user are called. E.g.: when the user logs in, the menu should
	 * be reloaded to display the functionality that he can access.
	 */
	public void reloadMenuModel() {
		logger.log(Level.FINER, "Forcing the menu to be reloaded...");
		menuModel = null;
		initMenuModel();
	}

	/**
	 * Creates the menu model so PrimeFaces can show it in the web pages. Checks if the system is installed, if the user
	 * is logged in and what functionality the user has access to.
	 */
	private void initMenuModel() {
		DefaultSubMenu menu;
		DefaultMenuItem item;
		logger.log(Level.FINER, "Initializing the dynamic menu model for the PrimeFaces menu component...");
	
		// Create the menu model.
		menuModel = new DefaultMenuModel();

		// Checks if the system has already been installed.
		if (coreInformation.isSystemInstalled()) {
			logger.log(Level.FINE, "System is installed correctly. User menu to be built.");

			/* Sub-menu: Access Control. */
			menu = new DefaultSubMenu();
			menu.setLabel(getI18nMessage("msgs", "menu.access"));
			menuModel.addElement(menu);

			// Menu item: Login (if the user hasn't been identified yet) / Logout (if she has).
			if (isLoggedIn()) {
				logger.log(Level.FINE, "User is logged in. Logout menu item to be added.");
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgs", "menu.access.logout"));
				item.setIcon("menuAccessLogout");
				item.setUrl("/logout");
				menu.addElement(item);
			}
			else {
				logger.log(Level.FINE, "User is not logged in. Login/registration menu items to be added.");
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgs", "menu.access.login"));
				item.setIcon("menuAccessLogin");
				item.setUrl("/login.faces");
				menu.addElement(item);
				
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsCore", "core.menu.register"));
				item.setIcon("menuCoreRegister");
				item.setCommand("#{registerController.begin}");
				menu.addElement(item);
			}

			// All other menus should only be shown if the user is identified.
			if (isLoggedIn()) {
				logger.log(Level.FINE, "User is logged in. Menus and items for all functionalities to be added.");
				
				/* Sub-menu: CRUDs (Core). */
				menu = new DefaultSubMenu();
				menu.setLabel(getI18nMessage("msgsCore", "core.menu.cruds"));
				menuModel.addElement(menu);	

				// Menu item: Manage Institutions.
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsCore", "core.menu.cruds.institution"));
				item.setIcon("menuCoreCrudsInstitution");
				item.setUrl("/core/institution/list.faces");
				menu.addElement(item);

				// Menu item: Manage Spiritists.
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsCore", "core.menu.cruds.spiritist"));
				item.setIcon("menuCoreCrudsSpiritist");
				item.setUrl("/core/spiritist/list.faces");
				menu.addElement(item);
				
				// Menu Item: Management Type
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsCore", "core.menu.cruds.management.type"));
				item.setIcon("menuCoreCrudsManagementType");
				item.setUrl("/core/managementType/list.faces");
				menu.addElement(item);
				
				// Menu Item: Management
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsCore", "core.menu.cruds.management"));
				item.setIcon("menuCoreCrudsManagement");
				item.setUrl("/core/management/list.faces");
				menu.addElement(item);

				/* Sub-menu: CRUDs (Events). */
				menu = new DefaultSubMenu();
				menu.setLabel(getI18nMessage("msgsEvent", "event.menu.cruds.event"));
				menuModel.addElement(menu);	
				/*
				 * rodrigo.pimenta
				 * Inserção de item para inserir eventos
				 */
				// Menu item: Manage Events.
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsEvent", "event.menu.cruds.listEvents"));
				item.setIcon("menuCoreCrudsManageEvents");
				item.setUrl("/event/event/list.faces");
				menu.addElement(item);
				
				// Menu Item: My Events
				item = new DefaultMenuItem();
				item.setValue(getI18nMessage("msgsEvent", "event.menu.cruds.subscribes"));
				item.setIcon("menuCoreCrudsSubscribes");
				item.setUrl("/event/subscriber/list.faces");
				menu.addElement(item);

				
				
				/* Sub-menu: Mailing (Secretariat). */
//				menu = new Submenu();
//				menu.setLabel(getI18nMessage("msgsSecretariat", "secretariat.menu.mailing"));
//				menuModel.addSubmenu(menu);
//
//				// Menu item: Send Mailing.
//				item = new MenuItem();
//				item.setValue(getI18nMessage("msgsSecretariat", "secretariat.menu.mailing.sendMailing"));
//				item.setIcon("menuSecretariatMailingSendMailing");
//				item.setAjax(false);
//				item.setActionExpression(getExpressionFactory().createMethodExpression(getELContext(), "#{sendMailingController.begin}", String.class, new Class[0]));
//				menu.getChildren().add(item);
			}
		}
		
		// If the system hasn't been installed yet, show the System Installation sub-menu.
		else {
			logger.log(Level.FINE, "The system is not yet installed. System installation menu to be added.");

			/* Sub-menu: System Installation. */
			menu = new DefaultSubMenu();
			menu.setLabel(getI18nMessage("msgsCore", "core.menu.installSystem"));
			menuModel.addElement(menu);

			// Menu item: Install System.
			item = new DefaultMenuItem();
			item.setValue(getI18nMessage("msgsCore", "core.menu.installSystem.install"));
			item.setIcon("menuCoreInstallSystem");
			item.setCommand("#{installSystemController.begin}");
			menu.addElement(item);
		}
	}

	/**
	 * Accesses the Login service to authenticate the user given his email and password.
	 */
	public String login() {
		try {
			// Uses the Login service to authenticate the user.
			logger.log(Level.FINEST, "User attempting login with email \"{0}\"...", email);
			sessionInformation.login(email, password);
		}
		catch (LoginFailedException e) {
			// Checks if it's a normal login exception (wrong username or password) or not.
			switch (e.getReason()) {
			case INCORRECT_PASSWORD:
			case UNKNOWN_USERNAME:
				// Normal login exception (invalid usernaem or password). Report the error to the user.
				logger.log(Level.INFO, "Login failed for \"{0}\". Reason: \"{1}\"", new Object[] { email, e.getReason() });
				addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_ERROR, "error.login.summary", "error.login.detail");
				return null;

			default:
				// System failure exception. Report a fatal error and ask the user to contact the administrators.
				logger.log(Level.INFO, "System failure during login. Email: \"" + email + "\"; reason: \"" + e.getReason() + "\"", e);
				addGlobalI18nMessage("msgs", FacesMessage.SEVERITY_FATAL, "error.loginfatal.summary", new Object[0], "error.loginfatal.detail", new Object[] { new Date(System.currentTimeMillis()) });
				return null;
			}
		}

		// Invalidates the menu model so it can be rebuilt after the system is installed.
		reloadMenuModel();
		return "/login.xhtml?faces-redirect=true";
	}

	// Now that the system is going to be shared with others, how about identifying the 1st user and providing auto-login for her?
	public String autoLogin() {
//		email = "vitorsouza@gmail.com";
//		password = "123";
		email = "rodrigo";
		password = "123";
		return login();
	}
}
