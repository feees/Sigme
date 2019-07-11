package br.org.feees.sigme.core.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.feees.sigme.core.application.ManagementTypeService;
import br.org.feees.sigme.core.domain.ManagementType;
import br.ufes.inf.nemo.util.ejb3.application.CrudService;
import br.ufes.inf.nemo.util.ejb3.application.filters.LikeFilter;
import br.ufes.inf.nemo.util.ejb3.controller.CrudController;

@Named
@SessionScoped
public class ManagementTypeController extends CrudController<ManagementType> {

	private static final long serialVersionUID = -926024275172951756L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(ManagementTypeController.class.getCanonicalName());

	@EJB
	private ManagementTypeService managementTypeService;

	@Override
	protected CrudService<ManagementType> getCrudService() {
		return this.managementTypeService;
	}

	@Override
	protected ManagementType createNewEntity() {
		logger.log(Level.FINER, "Initializing an empty managementType...");

		return new ManagementType();
	}

	@Override
	protected void initFilters() {
		logger.log(Level.FINER, "Initializing filter types...");

		addFilter(new LikeFilter("managementType.filter.byName", "name", getI18nMessage("msgsCore", "managementType.text.filter.byName")));
		addFilter(new LikeFilter("managementType.filter.byDescription", "email", getI18nMessage("msgsCore", "managementType.text.filter.byDescription")));
	}	

}
