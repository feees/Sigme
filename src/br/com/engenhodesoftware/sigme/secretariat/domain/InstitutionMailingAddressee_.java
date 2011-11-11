package br.com.engenhodesoftware.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.engenhodesoftware.sigme.core.domain.Institution;

/**
 * Meta-model for the InstitutionMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee
 */
@StaticMetamodel(InstitutionMailingAddressee.class)
public class InstitutionMailingAddressee_ extends ScopedMailingAddressee_ {
	public static volatile SingularAttribute<InstitutionMailingAddressee, Institution> institution;
}
