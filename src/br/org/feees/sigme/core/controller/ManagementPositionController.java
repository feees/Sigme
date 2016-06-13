package br.org.feees.sigme.core.controller;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.feees.sigme.core.application.ManagementPositionService;
import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.Management;
import br.org.feees.sigme.core.domain.ManagementPosition;
import br.org.feees.sigme.core.domain.Spiritist;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManagementPositionController extends CrudController<ManagementPosition> {

	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManageSpiritistsController.class.getCanonicalName());

	@EJB
	private ManagementPositionService managementPositionService;
	
	@Override
	protected CrudService<ManagementPosition> getCrudService() {
		managementPositionService.authorize();
		return managementPositionService;
	}

	@Override
	protected ManagementPosition createNewEntity() {
		ManagementPosition mp = new ManagementPosition();
		mp.setInstitution(new Institution());
		mp.setSpiritist(new Spiritist());
		mp.setManagement(new Management());
		return mp;
	}

	@Override
	protected void initFilters() {
		// TODO Auto-generated method stub

	}

}
