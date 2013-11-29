package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.feees.sigme.people.domain.Address;
import org.feees.sigme.people.domain.Telephone;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the Institution domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Institution
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
