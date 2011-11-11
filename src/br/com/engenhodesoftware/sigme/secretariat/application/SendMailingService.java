package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;

/**
 * Local EJB interface for the "Send Mailing" use case.
 * 
 * Waiting for definition of a possible integration of this functionality with the Email Manager tool or something similar.  
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Local
public interface SendMailingService extends Serializable {
	void sendMailing(Mailing mailing);
}
