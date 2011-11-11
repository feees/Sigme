package br.com.engenhodesoftware.sigme.core.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.domain.InstitutionType_;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;

/**
 * Stateless session bean implementing a DAO for objects of the InstitutionType domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.domain.InstitutionType
 * @see br.com.engenhodesoftware.sigme.core.persistence.InstitutionTypeDAO
 */
@Stateless
public class InstitutionTypeJPADAO extends BaseJPADAO<InstitutionType> implements InstitutionTypeDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<InstitutionType> getDomainClass() {
		return InstitutionType.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getOrderList(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root) */
	@Override
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<InstitutionType> root) {
		// Orders by type (the name of the type).
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(cb.asc(root.get(InstitutionType_.type)));
		return orderList;
	}
}
