package br.org.feees.sigme.core.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.application.CrudService;
import br.org.feees.sigme.core.domain.Institution;

/**
 * Local EJB interface for the component that implements the "Manage Institutions" use case.
 * 
 * This use case consists of a CRUD for the class Institution and uses the mini CRUD framework for EJB3.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.ejb3.application.CrudService
 */
@Local
public interface ManageInstitutionsService extends CrudService<Institution> {}
