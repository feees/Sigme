package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;


/**
 * Meta-model for the Attendance domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.core.domain.Attendance
 */
@StaticMetamodel(Attendance.class)
public class Attendance_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<Attendance, Spiritist> spiritist;
	public static volatile SingularAttribute<Attendance, Institution> institution;
	public static volatile SingularAttribute<Attendance, Date> startDate;
	public static volatile SingularAttribute<Attendance, Date> endDate;
}
