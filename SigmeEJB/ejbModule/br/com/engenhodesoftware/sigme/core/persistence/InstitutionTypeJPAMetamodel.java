package br.com.engenhodesoftware.sigme.core.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
@StaticMetamodel(InstitutionType.class)
public class InstitutionTypeJPAMetamodel {
	public static volatile SingularAttribute<InstitutionType, String> type;
	public static volatile SingularAttribute<InstitutionType, Boolean> partOfRegional;
}
