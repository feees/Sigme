package br.org.feees.sigme.secretariat.application;

import javax.ejb.Local;

import br.org.feees.sigme.secretariat.domain.MailingList;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;

/**
 * Local EJB interface for the component that implements the "Manage Mailing Lists" use case.
 * 
 * This use case consists of a CRUD for the class MailingList and uses the mini CRUD framework for EJB3.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.ufes.inf.nemo.util.ejb3.application.CrudService
 */
@Local
public interface ManageMailingListsService extends CrudService<MailingList> {}
