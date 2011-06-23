package br.com.engenhodesoftware.sigme.secretariat.application;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Stateless
@RolesAllowed({ "USER" })
public class SendMailingService implements SendMailingServiceLocal {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** @see br.com.engenhodesoftware.sigme.secretariat.application.SendMailingService#sendMailing(br.com.engenhodesoftware.sigme.secretariat.domain.Mailing) */
	public void sendMailing(Mailing mailing) {}
}
