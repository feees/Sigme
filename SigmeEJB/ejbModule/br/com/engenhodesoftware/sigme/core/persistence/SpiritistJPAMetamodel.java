package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(Spiritist.class)
public class SpiritistJPAMetamodel {
	public static volatile SingularAttribute<Spiritist, String> name;
	public static volatile SingularAttribute<Spiritist, Date> birthDate;
	public static volatile SingularAttribute<Spiritist, Character> gender;
	public static volatile SingularAttribute<Spiritist, String> shortName;
	public static volatile SingularAttribute<Spiritist, String> email;
	public static volatile SingularAttribute<Spiritist, String> password;
	public static volatile SingularAttribute<Spiritist, Address> address;
	public static volatile SetAttribute<Spiritist, Telephone> telephones;
	public static volatile SetAttribute<Spiritist, Attendance> attendances;
	public static volatile SingularAttribute<Spiritist, Date> lastUpdateDate;
	public static volatile SingularAttribute<Spiritist, Date> lastLoginDate;
}
