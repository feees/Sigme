package br.com.engenhodesoftware.sigme.core.persistence;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;

/**
 * Meta-model for the InstitutionType domain class, which allows DAOs to perform programmatic queries involving this
 * class using JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.InstitutionType
 */
@StaticMetamodel(InstitutionType.class)
public class InstitutionTypeJPAMetamodel {
	public static volatile SingularAttribute<InstitutionType, String> type;
	public static volatile SingularAttribute<InstitutionType, Boolean> partOfRegional;
}
