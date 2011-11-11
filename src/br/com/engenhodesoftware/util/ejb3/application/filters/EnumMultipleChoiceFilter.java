package br.com.engenhodesoftware.util.ejb3.application.filters;

import java.util.List;
import java.util.Map;

/**
 * Enum multiple-choice filters are like normal multiple-choice filter, with the difference that the field is not an
 * associated entity, but an enumeration instead.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class EnumMultipleChoiceFilter<T, E extends Enum<E>> extends MultipleChoiceFilter<T> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The class of the enumeration. */
	private Class<E> enumClass;

	/** Constructor from superclass, using fields. */
	public EnumMultipleChoiceFilter(String key, String fieldName, String label, List<T> options, Map<String, String> optionsLabels, Class<E> enumeration) {
		super(key, fieldName, label, options, optionsLabels);
		this.enumClass = enumeration;
	}

	/** Constructor from superclass, using fields. */
	public EnumMultipleChoiceFilter(String key, String fieldName, String label, List<T> options, Map<String, String> optionsLabels, Class<E> enumeration, Criterion ... criteria) {
		super(key, fieldName, label, options, optionsLabels, criteria);
		this.enumClass = enumeration;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.AbstractFilter#getEnum(java.lang.String) */
	public Enum<E> getEnum(String value) {
		return Enum.valueOf(enumClass, value);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.MultipleChoiceFilter#getType() */
	@Override
	public FilterType getType() {
		return FilterType.ENUM_MULTIPLE_CHOICE;
	}
}
