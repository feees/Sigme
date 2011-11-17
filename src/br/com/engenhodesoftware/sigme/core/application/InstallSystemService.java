package br.com.engenhodesoftware.sigme.core.application;

import java.io.Serializable;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.application.exceptions.SystemInstallFailedException;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * Local EJB interface for the "Install System" use case.
 * 
 * This use case allows the first user of the system to register himself as administrator and start using Sigme. To
 * complete the installation, this service also fills in the database with the initial data on states, cities,
 * regionals, etc.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Local
public interface InstallSystemService extends Serializable {
	/**
	 * Registers the administrator of the system and saves the initial data that will enable the system to be used.
	 * 
	 * @param administrator
	 *          The information on the first spiritist to register in the system, which will be considered the
	 *          administrator.
	 * 
	 * @throws SystemInstallFailedException
	 *           If any failure occurs during system installation (e.g., missing data file).
	 */
	void installSystem(Spiritist administrator) throws SystemInstallFailedException;
}
