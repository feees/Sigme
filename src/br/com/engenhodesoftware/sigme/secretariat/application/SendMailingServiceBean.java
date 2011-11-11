package br.com.engenhodesoftware.sigme.secretariat.application;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;

/**
 * Stateless session bean implementing the "Send Mailing" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService
 */
@Stateless
@RolesAllowed({ "USER" })
public class SendMailingServiceBean implements SendMailingService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#sendMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	public void sendMailing(Mailing mailing) {}
}
