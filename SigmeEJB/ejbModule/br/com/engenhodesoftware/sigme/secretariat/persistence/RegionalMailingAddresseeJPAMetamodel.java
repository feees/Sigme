package br.com.engenhodesoftware.sigme.secretariat.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeType;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;

/**
 * Meta-model for the RegionalMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee
 */
@StaticMetamodel(RegionalMailingAddressee.class)
public class RegionalMailingAddresseeJPAMetamodel {
	public static volatile SingularAttribute<RegionalMailingAddressee, MailingList> mailingList;
	public static volatile SingularAttribute<RegionalMailingAddressee, MailingAddresseeType> type;
	public static volatile SingularAttribute<RegionalMailingAddressee, MailingAddresseeScope> scope;
	public static volatile SingularAttribute<RegionalMailingAddressee, Regional> regional;
}
