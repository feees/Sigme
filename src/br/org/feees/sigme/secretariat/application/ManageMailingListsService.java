package br.org.feees.sigme.secretariat.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.util.ejb3.application.CrudService;
import br.org.feees.sigme.secretariat.domain.MailingList;

/**
 * Local EJB interface for the component that implements the "Manage Mailing Lists" use case.
 * 
 * This use case consists of a CRUD for the class MailingList and uses the mini CRUD framework for EJB3.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.util.ejb3.application.CrudService
 */
@Local
public interface ManageMailingListsService extends CrudService<MailingList> {}
