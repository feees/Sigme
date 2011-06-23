package br.com.engenhodesoftware.sigme.secretariat.application;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;

/**
 * TODO: document this type.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Local
public interface ManageMailingListsServiceLocal extends CrudServiceLocal<MailingList> {}
