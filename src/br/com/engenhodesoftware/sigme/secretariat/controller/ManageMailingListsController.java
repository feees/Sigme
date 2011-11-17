package br.com.engenhodesoftware.sigme.secretariat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.engenhodesoftware.sigme.core.domain.Institution;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionDAO;
import br.com.engenhodesoftware.sigme.core.persistence.SpiritistDAO;
import br.com.engenhodesoftware.sigme.secretariat.application.ManageMailingListsService;
import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.ScopedMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.SpiritistMailingAddressee;
import br.com.engenhodesoftware.util.ejb3.application.CrudService;
import br.com.engenhodesoftware.util.ejb3.application.filters.LikeFilter;
import br.com.engenhodesoftware.util.ejb3.controller.CrudController;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage MailingLists".
 * 
 * This use case is a CRUD and, thus, the controller also uses the mini CRUD framework for EJB3..
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class ManageMailingListsController extends CrudController<MailingList> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageMailingListsController.class.getCanonicalName());

	/** The "Manage MailingLists" service. */
	@EJB
	private ManageMailingListsService manageMailingListsService;

	/** The DAO for Institution objects. */
	@EJB
	private InstitutionDAO institutionDAO;

	/** The DAO for Spiritist objects. */
	@EJB
	private SpiritistDAO spiritistDAO;

	/** Output: the list of addressees. */
	private List<MailingAddressee> addressees;

	/** Input: the addressee being added or edited. */
	private MailingAddressee addressee;

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#getCrudService() */
	@Override
	protected CrudService<MailingList> getCrudService() {
		// Checks if the current user has the authorization to use this functionality.
		manageMailingListsService.authorize();

		return manageMailingListsService;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#createNewEntity() */
	@Override
	protected MailingList createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty mailing list...");

		// Create a new entity.
		MailingList newEntity = new MailingList();

		// Create an empty addressees list.
		addressees = new ArrayList<MailingAddressee>();

		return newEntity;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#checkSelectedEntity() */
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.FINER, "Checking selected mailing list ({0})...", selectedEntity);

		// Create the list of addressees with the already existing addressees. Also check for null.
		if (selectedEntity.getAddressees() == null)
			selectedEntity.setAddressees(new TreeSet<MailingAddressee>());
		addressees = new ArrayList<MailingAddressee>(selectedEntity.getAddressees());
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#initFilters() */
	@Override
	protected void initFilters() {
		logger.log(Level.FINER, "Initializing filter types...");

		// One can filter mailingLists by name, acronym, city or Regional.
		addFilter(new LikeFilter("manageMailingLists.filter.byName", "name", getI18nMessage("secretariat", "manageMailingLists.text.filter.byName")));
		addFilter(new LikeFilter("manageMailingLists.filter.byDescription", "description", getI18nMessage("secretariat", "manageMailingLists.text.filter.byDescription")));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#prepEntity() */
	@Override
	protected void prepEntity() {
		logger.log(Level.FINER, "Preparing mailing list for storage ({0})...", selectedEntity);

		// Inserts addressee list in the entity.
		selectedEntity.setAddressees(new TreeSet<MailingAddressee>(addressees));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudController#listTrash() */
	@Override
	protected String listTrash() {
		// List the names of the deleted mailing lists.
		StringBuilder names = new StringBuilder();
		for (MailingList entity : trashCan)
			names.append(entity.getName()).append(", ");

		// Removes the final comma and returns the string.
		int length = names.length();
		if (length > 0)
			names.delete(length - 2, length);

		logger.log(Level.FINE, "List of mailing lists in the trash can: {0}", names.toString());
		return names.toString();
	}

	/**
	 * Analyzes what has been written so far in the spiritist field and, if not empty, looks for spiritists that start
	 * with the given name and returns them in a list, so a dynamic pop-up list can be displayed. This method is intended
	 * to be used with AJAX.
	 * 
	 * @param param
	 *          The AJAX event.
	 *          
	 * @return The list of spiritists to be displayed in the drop-down auto-completion field.
	 */
	public List<Spiritist> suggestSpiritists(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<Spiritist> suggestions = spiritistDAO.findByName(param);
				logger.log(Level.FINE, "Suggestion for spiritist with name containing \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}
	
	/**
	 * Analyzes what has been written so far in the institution field and, if not empty, looks for institutions that start
	 * with the given name or acronym and returns them in a list, so a dynamic pop-up list can be displayed. This method
	 * is intended to be used with AJAX.
	 * 
	 * @param param
	 *          The AJAX event.
	 *          
	 * @return The list of institutions to be displayed in the drop-down auto-completion field.
	 */
	public List<Institution> suggestInstitutions(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<Institution> suggestions = institutionDAO.findByNameOrAcronym(param);
				logger.log(Level.FINE, "Suggestion for institutions with name or acronym beginning with \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}

	/**
	 * Adds a new and empty spiritist addressee to the list of addressees of the mailing list, so its fields can be
	 * filled. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void newSpiritistAddressee() {
		logger.log(Level.FINER, "Adding a new spiritist attendance to the list...");
		addressee = new SpiritistMailingAddressee();
		addressees.add(addressee);
	}

	/**
	 * Adds a new and empty institution addressee to the list of addressees of the mailing list, so its fields can be
	 * filled. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void newInstitutionAddressee() {
		logger.log(Level.FINER, "Adding a new institution attendance to the list...");
		addressee = new InstitutionMailingAddressee();
		addressees.add(addressee);
	}

	/**
	 * Adds a new and empty regional addressee to the list of addressees of the mailing list, so its fields can be filled.
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void newRegionalAddressee() {
		logger.log(Level.FINER, "Adding a new regional attendance to the list...");
		addressee = new RegionalMailingAddressee();
		addressees.add(addressee);
	}

	/**
	 * Indicates to the web page if an addressee is scoped, so it knows what form controls need to go on the page. This
	 * method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 *          
	 * @return <code>true</code> if the addressee is scoped, <code>false</code> otherwise.
	 */
	public boolean isScoped(MailingAddressee addressee) {
		return addressee instanceof ScopedMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to a spiritist, so it knows what form controls need to go on
	 * the page. 
	 * 
	 * This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 *          
	 * @return <code>true</code> if the addressee is spiritist-related, <code>false</code> otherwise.
	 */
	public boolean isSpiritistRelated(MailingAddressee addressee) {
		return addressee instanceof SpiritistMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to an institution, so it knows what form controls need to go
	 * on the page. 
	 * 
	 * This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 *          
	 * @return <code>true</code> if the addressee is institution-related, <code>false</code> otherwise.
	 */
	public boolean isInstitutionRelated(MailingAddressee addressee) {
		return addressee instanceof InstitutionMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to a regional, so it knows what form controls need to go on
	 * the page. 
	 * 
	 * This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 *          
	 * @return <code>true</code> if the addressee is regional-related, <code>false</code> otherwise.
	 */
	public boolean isRegionalRelated(MailingAddressee addressee) {
		return addressee instanceof RegionalMailingAddressee;
	}

	/**
	 * Unsets the spiritist of a given addressee so it can be changed. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void removeSpiritistFromAddressee() {
		if (isSpiritistRelated(addressee)) {
			SpiritistMailingAddressee spiritistAddressee = (SpiritistMailingAddressee) addressee;
			spiritistAddressee.setSpiritist(null);
			logger.log(Level.FINE, "Spiritist \"{0}\" removed from the addressee \"{1}\"", new Object[] { spiritistAddressee.getSpiritist(), spiritistAddressee });
		}
	}

	/**
	 * Unsets the institution of a given addressee so it can be changed. 
	 * 
	 * This method is intended to be used with AJAX.
	 */
	public void removeInstitutionFromAddressee() {
		if (isInstitutionRelated(addressee)) {
			InstitutionMailingAddressee institutionAddressee = (InstitutionMailingAddressee) addressee;
			institutionAddressee.setInstitution(null);
			logger.log(Level.FINE, "Institution \"{0}\" removed from the addressee \"{1}\"", new Object[] { institutionAddressee.getInstitution(), institutionAddressee });
		}
	}
}
