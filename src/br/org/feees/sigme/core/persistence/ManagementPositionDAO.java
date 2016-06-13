package br.org.feees.sigme.core.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.core.domain.ManagementPosition;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

@Local
public interface ManagementPositionDAO extends BaseDAO<ManagementPosition> {

}
