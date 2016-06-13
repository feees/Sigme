package br.org.feees.sigme.core.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

@StaticMetamodel(ManagementPosition.class)
public class ManagementPosition_ extends PersistentObjectSupport_ {
	public static volatile SingularAttribute<ManagementPosition, Spiritist> spiritist;
	public static volatile SingularAttribute<ManagementPosition, Institution> institution;
	public static volatile SingularAttribute<ManagementPosition, Management> management;
	public static volatile SingularAttribute<ManagementPosition, String> name;
}
