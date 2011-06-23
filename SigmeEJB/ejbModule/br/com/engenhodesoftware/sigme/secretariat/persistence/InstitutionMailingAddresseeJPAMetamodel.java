package br.com.engenhodesoftware.sigme.secretariat.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeType;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(InstitutionMailingAddressee.class)
public class InstitutionMailingAddresseeJPAMetamodel {
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingList> mailingList;
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingAddresseeType> type;
	public static volatile SingularAttribute<InstitutionMailingAddressee, MailingAddresseeScope> scope;
	public static volatile SingularAttribute<InstitutionMailingAddressee, Institution> institution;
}
