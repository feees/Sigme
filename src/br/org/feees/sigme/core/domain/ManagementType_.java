package br.org.feees.sigme.core.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

@StaticMetamodel(ManagementType.class)
public class ManagementType_ extends PersistentObjectSupport_ {

	public static volatile SingularAttribute<ManagementType, String> name;
}
