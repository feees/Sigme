package br.com.engenhodesoftware.sigme.core.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;

/**
 * Local EJB interface for the component that implements the "Manage Institutions" use case.
 * 
 * This use case consists of a CRUD for the class Institution and uses the mini CRUD framework for EJB3.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal
 */
@Local
public interface ManageInstitutionsService extends CrudServiceLocal<Institution> {}
