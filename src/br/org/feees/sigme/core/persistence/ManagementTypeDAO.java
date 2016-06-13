package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.core.domain.ManagementType;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Local
public interface ManagementTypeDAO extends BaseDAO<ManagementType> {

}
