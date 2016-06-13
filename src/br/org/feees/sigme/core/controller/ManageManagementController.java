package br.org.feees.sigme.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.feees.sigme.core.application.ManageManagementService;
import br.org.feees.sigme.core.domain.Attendance;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.domain.ManagementPosition;
import br.org.feees.sigme.core.domain.Spiritist;
import br.org.feees.sigme.core.persistence.AttendanceDAO;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManageManagementController extends CrudController<Management> {

	/** Serialization Id */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageManagementController.class.getCanonicalName());

	/** The "Manage Events" service */
	@EJB
	private ManageManagementService manageManangementService;

	@EJB
	private AttendanceDAO attendanceDAO;

	private List<ManagementPosition> managementPositions;
	private ManagementPosition managementPosition;

	/**
	 * Spiriritst that belongs to the management's institution
	 */
	private List<Attendance> attendances;
	private Spiritist spiritist;

	@Override
	protected CrudService<Management> getCrudService() {
		manageManangementService.authorize();
		return manageManangementService;
	}

	@Override
	protected Management createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty management...");
		Management management = new Management();

		managementPositions = new ArrayList<ManagementPosition>();
		attendances = new ArrayList<Attendance>();
		
		return management;
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub

	}

	public List<ManagementPosition> getManagementPositions() {
		return managementPositions;
	}

	public void setManagementPositions(List<ManagementPosition> managementPositions) {
		this.managementPositions = managementPositions;
	}

	public void newManagementPosition() {
		this.managementPosition = new ManagementPosition();

		if (selectedEntity.getInstitution() != null) {
			managementPosition.setManagement(this.selectedEntity);
			this.managementPosition.setInstitution(selectedEntity.getInstitution());
			setAttendances(attendanceDAO.retrieveByInstitution(selectedEntity.getInstitution().getId()));
		}

		logger.log(Level.FINEST, "Empty management position created as selected position");
	}

	public void resetManagementPosition() {
		this.managementPosition = null;
		logger.log(Level.FINEST, "Management position has been reset -- no position is selected");
	}

	public ManagementPosition getManagementPosition() {
		return managementPosition;
	}

	public void setManagementPosition(ManagementPosition managementPosition) {
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
	
}
