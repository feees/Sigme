package br.com.engenhodesoftware.sigme.secretariat.persistence;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(MailingList.class)
public class MailingListJPAMetamodel {
	public static volatile SingularAttribute<MailingList, String> name;
	public static volatile SingularAttribute<MailingList, String> description;
	public static volatile SetAttribute<MailingList, MailingAddressee> addressees;
}
