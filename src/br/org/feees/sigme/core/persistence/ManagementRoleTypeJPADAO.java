package br.org.feees.sigme.core.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.org.feees.sigme.core.domain.ManagementRoleType;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseJPADAO;

@Stateless
public class ManagementRoleTypeJPADAO extends BaseJPADAO<ManagementRoleType> implements ManagementRoleTypeDAO {
	private static final long serialVersionUID = 2010815847464784265L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Class<ManagementRoleType> getDomainClass() {
		return ManagementRoleType.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
