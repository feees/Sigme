package br.org.feees.sigme.secretariat.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.org.feees.sigme.core.domain.Institution;

/**
 * Meta-model for the InstitutionMailingAddressee domain class, which allows DAOs to perform programmatic queries using
 * JPA2's Criteria API.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.InstitutionMailingAddressee
 */
@StaticMetamodel(InstitutionMailingAddressee.class)
public class InstitutionMailingAddressee_ extends ScopedMailingAddressee_ {
	public static volatile SingularAttribute<InstitutionMailingAddressee, Institution> institution;
}
