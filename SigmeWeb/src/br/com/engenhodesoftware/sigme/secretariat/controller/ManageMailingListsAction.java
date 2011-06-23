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
import br.com.engenhodesoftware.sigme.secretariat.application.ManageMailingListsServiceLocal;
import br.com.engenhodesoftware.sigme.secretariat.domain.InstitutionMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.MailingList;
import br.com.engenhodesoftware.sigme.secretariat.domain.RegionalMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.ScopedMailingAddressee;
import br.com.engenhodesoftware.sigme.secretariat.domain.SpiritistMailingAddressee;
import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;
import br.com.engenhodesoftware.util.ejb3.application.filters.LikeFilter;
import br.com.engenhodesoftware.util.ejb3.controller.CrudAction;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Manage MailingLists". This use case is a CRUD.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class ManageMailingListsAction extends CrudAction<MailingList> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageMailingListsAction.class.getCanonicalName());

	/** The "Manage MailingLists" service. */
	@EJB
	private ManageMailingListsServiceLocal manageMailingListsService;

	/** The DAO for Institution objects. */
	@EJB
	private InstitutionDAO institutionDAO;

	/** The DAO for Spiritist objects. */
	@EJB
	private SpiritistDAO spiritistDAO;

	/** Output: the list of addressees. */
	private List<MailingAddressee> addressees;

	/** Input: the addressee being edited. */
	private MailingAddressee selectedAddressee;

	/** Getter for selectedAddressee. */
	public MailingAddressee getSelectedAddressee() {
		return selectedAddressee;
	}

	/** Setter for selectedAddressee. */
	public void setSelectedAddressee(MailingAddressee selectedAddressee) {
		this.selectedAddressee = selectedAddressee;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#getCrudService() */
	@Override
	protected CrudServiceLocal<MailingList> getCrudService() {
		return manageMailingListsService;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#createNewEntity() */
	@Override
	protected MailingList createNewEntity() {
		logger.log(Level.INFO, "Initializing an empty mailing list");

		// Create a new entity.
		MailingList newEntity = new MailingList();

		// Initialize the addressees.
		addressees = new ArrayList<MailingAddressee>();

		return newEntity;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#checkSelectedEntity() */
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.INFO, "Checking selected mailing list: {0}", selectedEntity);

		// Create the list of addressees with the already existing addressees. Also check for null.
		if (selectedEntity.getAddressees() == null)
			selectedEntity.setAddressees(new TreeSet<MailingAddressee>());
		addressees = new ArrayList<MailingAddressee>(selectedEntity.getAddressees());
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#initFilters() */
	@Override
	protected void initFilters() {
		logger.log(Level.INFO, "Initializing filter types");

		// One can filter mailingLists by name, acronym, city or Regional.
		addFilter(new LikeFilter("manageMailingLists.filter.byName", "name", getI18nMessage("secretariat", "manageMailingLists.text.filter.byName")));
		addFilter(new LikeFilter("manageMailingLists.filter.byDescription", "description", getI18nMessage("secretariat", "manageMailingLists.text.filter.byDescription")));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#prepEntity() */
	@Override
	protected void prepEntity() {
		logger.log(Level.INFO, "Preparing mailing list for storage: {0}", selectedEntity);

		// Inserts addressee list in the entity.
		selectedEntity.setAddressees(new TreeSet<MailingAddressee>(addressees));
	}

	/** @see br.com.engenhodesoftware.util.ejb3.controller.CrudAction#listTrash() */
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

		logger.log(Level.INFO, "Listing the mailing lists in the trash can: {0}", names.toString());
		return names.toString();
	}

	/**
	 * Adds a new and empty spiritist addressee to the list of addressees of the mailing list, so its fields can be
	 * filled. This method is intended to be used with AJAX.
	 */
	public void newSpiritistAddressee() {
		logger.log(Level.INFO, "Adding a new spiritist attendance to the list");
		selectedAddressee = new SpiritistMailingAddressee();
		addressees.add(selectedAddressee);
	}

	/**
	 * Adds a new and empty institution addressee to the list of addressees of the mailing list, so its fields can be
	 * filled. This method is intended to be used with AJAX.
	 */
	public void newInstitutionAddressee() {
		logger.log(Level.INFO, "Adding a new institution attendance to the list");
		selectedAddressee = new InstitutionMailingAddressee();
		addressees.add(selectedAddressee);
	}

	/**
	 * Adds a new and empty regional addressee to the list of addressees of the mailing list, so its fields can be filled.
	 * This method is intended to be used with AJAX.
	 */
	public void newRegionalAddressee() {
		logger.log(Level.INFO, "Adding a new regional attendance to the list");
		selectedAddressee = new RegionalMailingAddressee();
		addressees.add(selectedAddressee);
	}

	/**
	 * Indicates to the web page if an addressee is scoped, so it knows what form controls need to go on the page. This
	 * method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 * @return <code>true</code> if the addressee is scoped, <code>false</code> otherwise.
	 */
	public boolean isScoped(MailingAddressee addressee) {
		return addressee instanceof ScopedMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to a spiritist, so it knows what form controls need to go on
	 * the page. This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 * @return <code>true</code> if the addressee is spiritist-related, <code>false</code> otherwise.
	 */
	public boolean isSpiritistRelated(MailingAddressee addressee) {
		return addressee instanceof SpiritistMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to an institution, so it knows what form controls need to go
	 * on the page. This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 * @return <code>true</code> if the addressee is institution-related, <code>false</code> otherwise.
	 */
	public boolean isInstitutionRelated(MailingAddressee addressee) {
		return addressee instanceof InstitutionMailingAddressee;
	}

	/**
	 * Indicates to the web page if an addressee is related to a regional, so it knows what form controls need to go on
	 * the page. This method is intended to be used with AJAX.
	 * 
	 * @param addressee
	 *          The addressee to test.
	 * @return <code>true</code> if the addressee is regional-related, <code>false</code> otherwise.
	 */
	public boolean isRegionalRelated(MailingAddressee addressee) {
		return addressee instanceof RegionalMailingAddressee;
	}

	/**
	 * Removes one of the addressee of the list of addressees of the mailing list. This method is intended to be used with
	 * AJAX.
	 */
	public void removeAddressee() {
		logger.log(Level.INFO, "Removing an addressee from the list: {0}", selectedAddressee);
		addressees.remove(selectedAddressee);
	}

	/**
	 * Analyzes what has been written so far in the spiritist field and, if not empty, looks for spiritists that start
	 * with the given name and returns them in a list, so a dynamic pop-up list can be displayed. This method is intended
	 * to be used with AJAX.
	 * 
	 * @param param
	 *          The AJAX event.
	 * @return The list of spiritists to be displayed in the drop-down auto-completion field.
	 */
	public List<Spiritist> suggestSpiritists(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<Spiritist> suggestions = spiritistDAO.findByName(param);
				logger.log(Level.INFO, "Searching for spiritist with name containing \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}

	/**
	 * Unsets the spiritist of a given addressee so it can be changed. This method is intended to be used with AJAX.
	 */
	public void removeSpiritistFromAddressee() {
		if (isSpiritistRelated(selectedAddressee)) {
			SpiritistMailingAddressee spiritistAddressee = (SpiritistMailingAddressee) selectedAddressee;
			logger.log(Level.INFO, "Unsetting the spiritist {0} from the addressee {1}", new Object[] { spiritistAddressee.getSpiritist(), spiritistAddressee });
			spiritistAddressee.setSpiritist(null);
		}
	}

	/**
	 * Analyzes what has been written so far in the institution field and, if not empty, looks for institutions that start
	 * with the given name or acronym and returns them in a list, so a dynamic pop-up list can be displayed. This method
	 * is intended to be used with AJAX.
	 * 
	 * @param param
	 *          The AJAX event.
	 * @return The list of institutions to be displayed in the drop-down auto-completion field.
	 */
	public List<Institution> suggestInstitutions(Object event) {
		if (event != null) {
			String param = event.toString();
			if (param.length() > 0) {
				List<Institution> suggestions = institutionDAO.findByNameOrAcronym(param);
				logger.log(Level.INFO, "Searching for institutions with name or acronym beginning with \"{0}\" returned {1} results", new Object[] { param, suggestions.size() });
				return suggestions;
			}
		}
		return null;
	}

	/**
	 * Unsets the institution of a given addressee so it can be changed. This method is intended to be used with AJAX.
	 */
	public void removeInstitutionFromAddressee() {
		if (isInstitutionRelated(selectedAddressee)) {
			InstitutionMailingAddressee institutionAddressee = (InstitutionMailingAddressee) selectedAddressee;
			logger.log(Level.INFO, "Unsetting the institution {0} from the addressee {1}", new Object[] { institutionAddressee.getInstitution(), institutionAddressee });
			institutionAddressee.setInstitution(null);
		}
	}
}
