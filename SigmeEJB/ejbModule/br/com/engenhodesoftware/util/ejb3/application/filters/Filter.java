package br.com.engenhodesoftware.util.ejb3.application.filters;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Represents a type of filter that can be used in a CRUD listing.
 * 
 * In any kind of filter, the label property contains the label that will be put in a list box which can be used by the
 * user to select what kind of filter he/she wants to apply. The other properties of the class are set depending on the
 * type of filter: simple, multiple-choice, reverse multiple-choice or many-to-many.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public interface Filter<T> extends Serializable {
	/** Getter for the type. */
	FilterType getType();

	/** Getter for the key. */
	String getKey();

	/** Getter for the label. */
	String getLabel();

	/** Getter for the field name. */
	String getFieldName();

	/** Getter for the options. */
	List<T> getOptions();

	/** Getter for the option labels. */
	Map<String, String> getOptionsLabels();

	/** Getter for the reversed option labels. */
	Map<String, String> getReversedOptionsLabels();

	/** Getter for a specific option label. */
	String getOptionLabel(String key);

	/** Getter for the sub-field names. */
	String getSubFieldNames();

	/** Getter for the criteria list. */
	List<Criterion> getCriteria();

	/**
	 * Returns the enum from the class associated with the filter and represented by the value parameter.
	 * 
	 * @param value
	 *          The string representation of the enum value.
	 *          
	 * @return The enum value, or null if the filter is not associated with an enumeration.
	 * @see br.com.engenhodesoftware.util.ejb3.application.filters.FilterType
	 */
	Enum<?> getEnum(String value);
}
