package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObjectSupport_;

/**
 * Meta-model for the SigmeConfiguration domain class, which allows DAOs to perform programmatic queries involving this
 * class using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Attendance
 */
@StaticMetamodel(Attendance.class)
public class SigmeConfiguration_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<SigmeConfiguration, Date> creationDate;
	public static volatile SingularAttribute<SigmeConfiguration, Institution> owner;
}
