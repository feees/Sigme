package br.com.engenhodesoftware.sigme.secretariat.application;

import java.io.Serializable;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Singleton;

import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeType;

/**
 * Singleton bean that stores in memory information that is useful for the entire application, i.e., read-only
 * information shared by all users. This bean stores information for the secretariat package.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Singleton
public class SecretariatInformation implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(SecretariatInformation.class.getCanonicalName());

	/** Output: the list of mailing list addressee types. */
	private SortedSet<MailingAddresseeType> addresseeTypes;

	/** Output: the list of mailing list addressee scopes. */
	private SortedSet<MailingAddresseeScope> addresseeScopes;

	/** Getter for addresseeTypes. */
	public SortedSet<MailingAddresseeType> getAddresseeTypes() {
		// If the addressee types haven't yet been loaded, load them.
		if (addresseeTypes == null) {
			addresseeTypes = new TreeSet<MailingAddresseeType>();
			addresseeTypes.addAll(Arrays.asList(MailingAddresseeType.values()));
			logger.log(Level.INFO, "Loaded {0} mailing list addressee types.", addresseeTypes.size());
		}
		return addresseeTypes;
	}

	/** Getter for addresseeScopes. */
	public SortedSet<MailingAddresseeScope> getAddresseeScopes() {
		// If the addressee scopes haven't yet been loaded, load them.
		if (addresseeScopes == null) {
			addresseeScopes = new TreeSet<MailingAddresseeScope>();
			addresseeScopes.addAll(Arrays.asList(MailingAddresseeScope.values()));
			logger.log(Level.INFO, "Loaded {0} mailing list addressee types.", addresseeScopes.size());
		}
		return addresseeScopes;
	}
}
