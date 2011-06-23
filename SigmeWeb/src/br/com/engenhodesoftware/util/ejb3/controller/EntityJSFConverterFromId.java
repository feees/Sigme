package br.com.engenhodesoftware.util.ejb3.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO;
import br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public abstract class EntityJSFConverterFromId<T extends PersistentObject> implements Converter, Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(EntityJSFConverterFromId.class.getCanonicalName());

	/**
	 * Abstract method that should be implemented by the concrete converters returning the class to and from which the
	 * conversions are done. This information is needed for some conversion operations.
	 * 
	 * @return A class object that represents the class referred to by the converter.
	 */
	protected abstract Class<T> getDomainClass();

	/**
	 * Abstract method that should be implemented by the concrete converters returning the DAO that will be used by the
	 * conversion operation. The DAO is needed to retrieve the entity given the Id, thus performing the conversion.
	 * 
	 * @return The DAO of the domain class referred to by the converter.
	 */
	protected abstract BaseDAO<T> getDAO();

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *      java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		T entity = null;
		logger.log(Level.INFO, "Trying to convert to an instance of {0} from the id: {1}", new Object[] { getDomainClass().getSimpleName(), value });

		// Checks for nulls.
		if (value != null) {
			// Loads the entity given the id.
			try {
				Long id = Long.valueOf(value);
				entity = getDAO().retrieveById(id);
			}
			catch (NumberFormatException e) {
				logger.log(Level.WARNING, "Value is not a number (Long): {0}", value);
				return null;
			}
		}

		// Logs the result and returns.
		logger.log(Level.INFO, "Returning: {0}", entity);
		return entity;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *      java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// Checks that the supplied value is an entity of the class referred to by the converter.
		if ((value != null) && (value.getClass().equals(getDomainClass()))) {
			@SuppressWarnings("unchecked")
			T entity = (T) value;

			// Checks for null id and returns the id converted to string.
			if (entity.getId() != null)
				return entity.getId().toString();
		}

		// If it doesn't pass one of the previous checks, return an empty string.
		return "";
	}
}
