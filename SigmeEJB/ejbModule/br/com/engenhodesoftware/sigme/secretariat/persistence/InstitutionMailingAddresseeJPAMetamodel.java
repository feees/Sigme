package br.com.engenhodesoftware.sigme.secretariat.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeType;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;

/**
 * Meta-model for the InstitutionMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee
 */
@StaticMetamodel(InstitutionMailingAddressee.class)
public class InstitutionMailingAddresseeJPAMetamodel {
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingList> mailingList;
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingAddresseeType> type;
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingAddresseeScope> scope;
	public static volatile SingularAttribute<InstitutionMailingAddressee, Institution> institution;
}
