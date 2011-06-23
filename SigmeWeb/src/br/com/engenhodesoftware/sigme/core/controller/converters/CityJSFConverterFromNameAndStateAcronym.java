package br.com.engenhodesoftware.sigme.core.controller.converters;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;
import br.com.engenhodesoftware.util.people.persistence.exceptions.CityNotFoundException;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class CityJSFConverterFromNameAndStateAcronym implements Converter, Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(CityJSFConverterFromNameAndStateAcronym.class.getCanonicalName());

	/** The DAO for City objects. */
	private CityDAO cityDAO;

	/** Constructor. */
	public CityJSFConverterFromNameAndStateAcronym(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *      java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		City city = null;
		logger.log(Level.INFO, "Trying to convert to a City object from: {0}", value);

		// Checks for nulls.
		if (value != null) {
			// Separates the name of the city from the acronym of the state.
			int idx = value.lastIndexOf(',');
			if (idx != -1)
				// Retrieve the actual city object from the persistent store.
				try {
					String stateAcronym = value.substring(idx + 1).trim();
					value = value.substring(0, idx).trim();
					city = cityDAO.retrieveByNameAndStateAcronym(value, stateAcronym);
					logger.log(Level.INFO, "City found: {0}", city);
				}
				catch (CityNotFoundException e) {
					logger.log(Level.WARNING, "A city with name \"{0}\" and state acronym \"{1}\"  was not found!", new Object[] { e.getCityName(), e.getStateAcronym() });
				}
			// If a comma isn't present in the value that is to be converted, create a warning in the log.
			else {
				logger.log(Level.WARNING, "Could not separate the city name from the state acronym: {0}", value);
			}
		}

		return city;
	}

	/**
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent,
	 *      java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if ((value != null) && (value instanceof City))
			return ((City) value).toString();
		return "";
	}
}
