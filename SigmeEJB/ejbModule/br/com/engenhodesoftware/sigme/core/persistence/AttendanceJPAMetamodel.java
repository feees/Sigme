package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * Meta-model for the Attendance domain class, which allows DAOs to perform programmatic queries involving this class
 * using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.Attendance
 */
@StaticMetamodel(Attendance.class)
public class AttendanceJPAMetamodel {
	public static volatile SingularAttribute<Attendance, Spiritist> spiritist;
	public static volatile SingularAttribute<Attendance, Institution> institution;
	public static volatile SingularAttribute<Attendance, Date> startDate;
	public static volatile SingularAttribute<Attendance, Date> endDate;
}
