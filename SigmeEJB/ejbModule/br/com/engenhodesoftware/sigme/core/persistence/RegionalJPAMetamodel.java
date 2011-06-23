package br.com.engenhodesoftware.sigme.core.persistence;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.util.people.domain.City;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(Regional.class)
public class RegionalJPAMetamodel {
	public static volatile SingularAttribute<Regional, Integer> number;
	public static volatile SingularAttribute<Regional, String> name;
	public static volatile SetAttribute<Regional, City> cities;
}
