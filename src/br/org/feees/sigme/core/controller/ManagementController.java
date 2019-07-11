package br.org.feees.sigme.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.feees.sigme.core.application.ManagementService;
import br.org.feees.sigme.core.domain.Attendance;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.domain.ManagementRole;
import br.org.feees.sigme.core.domain.ManagementRoleType;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.persistence.AttendanceDAO;
import br.org.feees.sigme.core.persistence.ManagementRoleTypeDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManagementController extends CrudController<Management> {

	/** Serialization Id */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementController.class.getCanonicalName());

	/** The "Manage Events" service */
	@EJB
	private ManagementService manageManangementService;

	@EJB
	private ManagementRoleTypeDAO managementRoleTypeDao;
	
	@EJB
	private AttendanceDAO attendanceDAO;

	private List<ManagementRole> managementPositions;
	private ManagementRole managementPosition;

	/**
	 * Spiriritst that belongs to the management's institution
	 */
	private List<Attendance> attendances;
	private Spiritist spiritist;
	
	private List<ManagementRoleType> managementRoleTypeList;

	@Override
	protected CrudService<Management> getCrudService() {
		manageManangementService.authorize();
		return manageManangementService;
	}

	@Override
	protected Management createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty management...");
		Management management = new Management();

		managementPositions = new ArrayList<>();
		attendances = new ArrayList<>();
		managementRoleTypeList = new ArrayList<>();
		
		return management;
	}

	/**
	 * @see br.ufes.inf.nemo.util.ejb3.controller.CrudController#checkSelectedEntity()
	 */
	@Override
	protected void checkSelectedEntity() {
		logger.log(Level.FINER, "Checking selected management ({0})...", selectedEntity);
		
		if (selectedEntity.getManagementPositions() == null)
			selectedEntity.setManagementPositions(new ArrayList<>());
		
		managementPositions = new ArrayList<>(selectedEntity.getManagementPositions());
	}
	
	/** @see br.ufes.inf.nemo.util.ejb3.controller.CrudController#prepEntity() */
	@Override
	protected void prepEntity() {
		logger.log(Level.FINER, "Preparing management for storage ({0})...", selectedEntity);

		// Inserts telephone and attendance lists in the entity.
		managementPositions.forEach(managementPosition -> managementPosition.setName(managementPosition.getManagementRoleType().getName()));
		selectedEntity.setManagementPositions(managementPositions);
	}
	
	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub

	}

	public List<ManagementRole> getManagementPositions() {
		return managementPositions;
	}

	public void setManagementPositions(List<ManagementRole> managementPositions) {
		this.managementPositions = managementPositions;
	}

	public void newManagementPosition() {
		this.managementPosition = new ManagementRole();
		this.managementPosition.setSpiritist(new Spiritist());
		this.managementPosition.setManagementRoleType(new ManagementRoleType());

		if (selectedEntity.getInstitution() != null) {
			this.managementPosition.setManagement(this.selectedEntity);
			this.managementPosition.setInstitution(selectedEntity.getInstitution());
			setAttendances(attendanceDAO.retrieveByInstitution(selectedEntity.getInstitution().getId()));
			setManagementRoleTypeList(managementRoleTypeDao.retrieveAll());
		}

		logger.log(Level.FINEST, "Empty management position created as selected position");
	}

	public void resetManagementPosition() {
		this.managementPosition = null;
		logger.log(Level.FINEST, "Management position has been reset -- no position is selected");
	}

	public ManagementRole getManagementPosition() {
		return managementPosition;
	}

	public void setManagementPosition(ManagementRole managementPosition) {
		this.managementPosition = managementPosition;
		logger.log(Level.FINEST, "Management Position \"{0}\" has been selected", managementPosition);
	}

	public Spiritist getSpiritist() {
		return spiritist;
	}

	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public List<ManagementRoleType> getManagementRoleTypeList() {
		return managementRoleTypeList;
	}

	public void setManagementRoleTypeList(List<ManagementRoleType> managementRoleTypeList) {
		this.managementRoleTypeList = managementRoleTypeList;
	}
	
}
