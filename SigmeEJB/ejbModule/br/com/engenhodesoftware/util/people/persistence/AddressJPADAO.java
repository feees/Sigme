package br.com.engenhodesoftware.util.people.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO;
import br.com.engenhodesoftware.util.people.domain.Address;

/**
 * Stateless session bean implementing a DAO for objects of the Address domain class using JPA2.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation implementations are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any is defined in the implementing DAO interface) have
 * to be implemented in this class.
 * 
 * <i>This class is part of the Engenho de Software "Legal Entity" mini framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.people.domain.Address
 * @see br.com.engenhodesoftware.util.people.persistence.AddressDAO
 */
@Stateless
public class AddressJPADAO extends BaseJPADAO<Address> implements AddressDAO {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The application's persistent context provided by the application server. */
	@PersistenceContext
	private EntityManager entityManager;

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#getDomainClass() */
	@Override
	public Class<Address> getDomainClass() {
		return Address.class;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseJPADAO#getEntityManager() */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
