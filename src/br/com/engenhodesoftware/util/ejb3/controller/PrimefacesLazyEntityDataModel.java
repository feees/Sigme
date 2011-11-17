package br.com.engenhodesoftware.util.ejb3.controller;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;

import br.com.engenhodesoftware.util.ejb3.application.CrudServiceLocal;
import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * TODO: document this type.
 *
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public abstract class PrimefacesLazyEntityDataModel<T extends PersistentObject> extends LazyDataModel<T> implements SelectableDataModel<T> {
	/** Serialization id. */
	private static final long serialVersionUID = 3299810696986238264L;
	
	/** TODO: document this field. */
	private CrudServiceLocal<T> crudService;
	
	/** TODO: document this field. */
	private BaseDAO<T> entityDAO;
	
	/** Constructor. */
	public PrimefacesLazyEntityDataModel(CrudServiceLocal<T> crudService) {
		this.crudService = crudService;
	}

	/** Constructor. */
	public PrimefacesLazyEntityDataModel(BaseDAO<T> entityDAO) {
		this.entityDAO = entityDAO;
	}

	/** @see org.primefaces.model.LazyDataModel#getRowKey(java.lang.Object) */
	@Override
	public Object getRowKey(T object) {
		return object.getId();
	}

	/** @see org.primefaces.model.LazyDataModel#getRowData(java.lang.String) */
	@Override
	public T getRowData(String rowKey) {
		try {
			Long id = Long.parseLong(rowKey);
			if (crudService != null)
				return crudService.retrieve(id);
			else
				return entityDAO.retrieveById(id);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}
