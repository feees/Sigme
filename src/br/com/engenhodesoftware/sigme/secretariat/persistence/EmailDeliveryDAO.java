package br.com.engenhodesoftware.sigme.secretariat.persistence;

import java.util.List;

import javax.ejb.Local;

import br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery;
import br.com.engenhodesoftware.sigme.secretariat.domain.Mailing;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;

/**
 * Interface for a DAO for objects of the EmailDelivery domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.secretariat.domain.EmailDelivery
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 */
@Local
public interface EmailDeliveryDAO extends BaseDAO<EmailDelivery> {
	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	List<EmailDelivery> findDeliveriesFromMailing(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	List<EmailDelivery> findPendingDeliveriesFromMailing(Mailing mailing);
}
