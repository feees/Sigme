package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface SendMailingServiceLocal extends Serializable {
	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 */
	void sendMailing(Mailing mailing);
}
