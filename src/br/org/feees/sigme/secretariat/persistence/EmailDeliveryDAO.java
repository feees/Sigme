package br.org.feees.sigme.secretariat.persistence;

import javax.ejb.Local;

import br.org.feees.sigme.secretariat.domain.EmailDelivery;
import br.org.feees.sigme.secretariat.domain.Mailing;
import br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO;

/**
 * Interface for a DAO for objects of the EmailDelivery domain class.
 * 
 * Using a mini CRUD framework for EJB3, basic DAO operation definitions are inherited from the superclass, whereas
 * operations that are specific to the managed domain class (if any) are specified in this class.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.org.feees.sigme.secretariat.domain.EmailDelivery
 * @see br.ufes.inf.nemo.util.ejb3.persistence.BaseDAO
 */
@Local
public interface EmailDeliveryDAO extends BaseDAO<EmailDelivery> {
	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countDeliveriesFromMailing(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countPendingDeliveriesFromMailing(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countSentDeliveriesFromMailing(Mailing mailing);

	/**
	 * TODO: document this method.
	 * 
	 * @param mailing
	 * @return
	 */
	long countErrorDeliveriesFromMailing(Mailing mailing);
}
