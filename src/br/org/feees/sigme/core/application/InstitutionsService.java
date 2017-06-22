package br.org.feees.sigme.core.application;

import javax.ejb.Local;

import br.org.feees.sigme.core.domain.Institution;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Institutions" use case.
 * 
 * This use case consists of a CRUD for the class Institution and uses the mini CRUD framework for EJB3.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.ufes.inf.nemo.util.ejb3.application.CrudService
 */
@Local
public interface InstitutionsService extends CrudService<Institution> {}
