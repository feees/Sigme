package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.sigme.core.domain.Attendance;
import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.sigme.core.persistence.AttendanceJPAMetamodel;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionJPAMetamodel;
import br.com.engenhodesoftware.sigme.core.persistence.SpiritistJPAMetamodel;
import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddresseeScope;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the MailingAddressee domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee
 * @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingAddresseeDAO
 */
@Stateless
public class MailingAddresseeJPADAO extends BaseJPADAO<MailingAddressee> implements MailingAddresseeDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(MailingAddresseeJPADAO.class.getCanonicalName());

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<MailingAddressee> getDomainClass() {
		return MailingAddressee.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingAddresseeDAO#retrieveEmailsFromInstitutionMailingAddressee(br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee) */
	@Override
	public List<String> retrieveEmailsFromInstitutionMailingAddressee(InstitutionMailingAddressee addressee) {
		logger.log(Level.FINE, "Resolving email addresses from institution mailing addressee with id \"{0}\"...", addressee.getId());

		// Constructs the query over the InstitutionMailingAddressee to return strings (the e-mail addresses).
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);
		Root<Attendance> attRoot = cq.from(Attendance.class);
		Root<InstitutionMailingAddressee> imaRoot = cq.from(InstitutionMailingAddressee.class);

		// First predicate for filtering: join the classes InstitutionMailingAddressee and Attendance by the institution.
		Predicate joinPredicate = cb.equal(attRoot.get(AttendanceJPAMetamodel.institution), imaRoot.get(InstitutionMailingAddresseeJPAMetamodel.institution));

		// Second predicate for filtering: either the addressee's scope is not active or the attendance is active.
		// Rationale: if we don't want the inactive members of an institution, the attendance must be active.
		Predicate inactiveScopePredicate = cb.or(cb.notEqual(imaRoot.get(InstitutionMailingAddresseeJPAMetamodel.scope), MailingAddresseeScope.ACTIVE), cb.isNull(attRoot.get(AttendanceJPAMetamodel.endDate)));

		// Third predicate for filtering: either the addressee's scope is active or the atendance has ended.
		// Rationale: the opposite of before, if we don't want the active members, the attendance must have ended already.
		Predicate activeScopePredicate = cb.or(cb.notEqual(imaRoot.get(InstitutionMailingAddresseeJPAMetamodel.scope), MailingAddresseeScope.INACTIVE), cb.isNotNull(attRoot.get(AttendanceJPAMetamodel.endDate)));

		// Fourth predicate for filtering: finally, specify the addresee we're talking about.
		Predicate addresseePredicate = cb.equal(imaRoot, addressee);

		// Join the predicates in a conjunction (and) to form the where clause in the query.
		cq.where(joinPredicate, inactiveScopePredicate, activeScopePredicate, addresseePredicate);

		// Returns the e-mails of the spiritists that are related to the institution.
		Join<Attendance, Spiritist> spiritistJoin = attRoot.join(AttendanceJPAMetamodel.spiritist);
		cq.select(spiritistJoin.get(SpiritistJPAMetamodel.email));
		List<String> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve email addresses of institution mailing addressee \"{0}\" returned \"{1}\" addresses", new Object[] { addressee.getId(), result.size() });
		return result;
	}

	/** @see br.com.engenhodesoftware.sigme.secretariat.persistence.MailingAddresseeDAO#retrieveEmailsFromRegionalMailingAddressee(br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee) */
	@Override
	public List<String> retrieveEmailsFromRegionalMailingAddressee(RegionalMailingAddressee addressee) {
		logger.log(Level.FINE, "Resolving email addresses from regional mailing addressee with id \"{0}\"...", addressee.getId());

		// Constructs the query over the RegionalMailingAddressee to return strings (the e-mail addresses).
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);
		Root<Attendance> attRoot = cq.from(Attendance.class);
		Root<RegionalMailingAddressee> rmaRoot = cq.from(RegionalMailingAddressee.class);

		// First predicate for filtering: join the classes RegionalMailingAddressee and Attendance by the institution's
		// regional.
		Join<Attendance, Institution> institutionJoin = attRoot.join(AttendanceJPAMetamodel.institution);
		Predicate joinPredicate = cb.equal(institutionJoin.get(InstitutionJPAMetamodel.regional), rmaRoot.get(RegionalMailingAddresseeJPAMetamodel.regional));

		// Second predicate for filtering: either the addressee's scope is not active or the attendance is active.
		// Rationale: if we don't want the inactive members of an institution, the attendance must be active.
		Predicate inactiveScopePredicate = cb.or(cb.notEqual(rmaRoot.get(RegionalMailingAddresseeJPAMetamodel.scope), MailingAddresseeScope.ACTIVE), cb.isNull(attRoot.get(AttendanceJPAMetamodel.endDate)));

		// Third predicate for filtering: either the addressee's scope is active or the atendance has ended.
		// Rationale: the opposite of before, if we don't want the active members, the attendance must have ended already.
		Predicate activeScopePredicate = cb.or(cb.notEqual(rmaRoot.get(RegionalMailingAddresseeJPAMetamodel.scope), MailingAddresseeScope.INACTIVE), cb.isNotNull(attRoot.get(AttendanceJPAMetamodel.endDate)));

		// Fourth predicate for filtering: finally, specify the addresee we're talking about.
		Predicate addresseePredicate = cb.equal(rmaRoot, addressee);

		// Join the predicates in a conjunction (and) to form the where clause in the query.
		cq.where(joinPredicate, inactiveScopePredicate, activeScopePredicate, addresseePredicate);

		// Returns the e-mails of the spiritists that are related to the institution.
		Join<Attendance, Spiritist> spiritistJoin = attRoot.join(AttendanceJPAMetamodel.spiritist);
		cq.select(spiritistJoin.get(SpiritistJPAMetamodel.email));
		List<String> result = entityManager.createQuery(cq).getResultList();
		logger.log(Level.INFO, "Retrieve email addresses of regional mailing addressee \"{0}\" returned \"{1}\" addresses", new Object[] { addressee.getId(), result.size() });
		return result;
	}
}
