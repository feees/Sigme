package br.com.engenhodesoftware.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * Meta-model for the Institution domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Institution
 */
@StaticMetamodel(Institution.class)
public class Institution_ extends PersistentObjectSupport_ {
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
