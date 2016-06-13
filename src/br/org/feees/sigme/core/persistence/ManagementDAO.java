package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.core.domain.Management;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.util.ejb3.persistence.exceptions.PersistentObjectNotFoundException;

@Local
public interface ManagementDAO extends BaseDAO<Management> {

	Management retrieveByInstitutionId(Long id) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;

}
