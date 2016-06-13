package br.org.feees.sigme.core.domain;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

@StaticMetamodel(Management.class)
public class Management_ extends PersistentObjectSupport_ {
	
	public static volatile SingularAttribute<Management, Institution> institution;
	
	public static volatile ListAttribute<Management, Spiritist> spiritists;
	public static volatile ListAttribute<Management, ManagementPosition> managementPositions;
	
	public static volatile SingularAttribute<Management, ManagementType> type;
	public static volatile SingularAttribute<Management, String> name;
	public static volatile SingularAttribute<Management, String> description;
	public static volatile SingularAttribute<Management, Date> initialDate;
	public static volatile SingularAttribute<Management, Date> finalDate;
	
}
