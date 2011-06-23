package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(Institution.class)
public class InstitutionJPAMetamodel {
	public static volatile SingularAttribute<Institution, String> name;
	public static volatile SingularAttribute<Institution, String> acronym;
	public static volatile SingularAttribute<Institution, Address> address;
	public static volatile SingularAttribute<Institution, Regional> regional;
	public static volatile SingularAttribute<Institution, String> webPage;
	public static volatile SingularAttribute<Institution, String> email;
	public static volatile SetAttribute<Institution, Telephone> telephones;
	public static volatile SingularAttribute<Institution, InstitutionType> type;
	public static volatile SingularAttribute<Institution, Date> lastUpdateDate;
}
