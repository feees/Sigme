package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(Attendance.class)
public class AttendanceJPAMetamodel {
	public static volatile SingularAttribute<Attendance, Spiritist> spiritist;
	public static volatile SingularAttribute<Attendance, Institution> institution;
	public static volatile SingularAttribute<Attendance, Date> startDate;
	public static volatile SingularAttribute<Attendance, Date> endDate;
}
