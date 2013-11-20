package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Person_;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * Meta-model for the Spiritist domain class, which allows DAOs to perform programmatic queries using JPA2's Criteria
 * API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Spiritist
 */
@StaticMetamodel(Spiritist.class)
public class Spiritist_ extends Person_ {
	public static volatile SingularAttribute<Spiritist, String> shortName;
	public static volatile SingularAttribute<Spiritist, String> taxCode;
	public static volatile SingularAttribute<Spiritist, String> email;
	public static volatile SingularAttribute<Spiritist, String> password;
	public static volatile SingularAttribute<Spiritist, Address> address;
	public static volatile SetAttribute<Spiritist, Telephone> telephones;
	public static volatile SetAttribute<Spiritist, Attendance> attendances;
	public static volatile SingularAttribute<Spiritist, Date> lastUpdateDate;
	public static volatile SingularAttribute<Spiritist, Date> lastLoginDate;
}
