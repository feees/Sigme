package br.com.engenhodesoftware.sigme.core.controller;

import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.secretariat.application.SendMailingEvent;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.util.ejb3.controller.JSFController;
import br.com.engenhodesoftware.util.jms.InForeground;

/**
 * Session-scoped managed bean that provides to web pages the login service, indication if the user is logged in, the
 * user's menu and the current date/time.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class TestController extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(TestController.class.getCanonicalName());
	
	@Inject
	@InForeground
	private Event<SendMailingEvent> mailingEvent;

	public String test() {
		Mailing mailing = new Mailing();
		mailing.setSubject("TestController Created Mailing @ " + new java.util.Date());
		mailingEvent.fire(new SendMailingEvent(mailing));
		return "/test.xhtml";
	}
}
