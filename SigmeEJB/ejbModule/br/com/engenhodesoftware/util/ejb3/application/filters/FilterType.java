package br.com.engenhodesoftware.util.ejb3.application.filters;

/**
 * Enumeration of the types of filter.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public enum FilterType {
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.SimpleFilter */
	SIMPLE, 
	
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.LikeFilter */
	LIKE, 
	
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.MultipleChoiceFilter */
	MULTIPLE_CHOICE, 
	
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.ReverseMultipleChoiceFilter */
	REVERSE_MULTIPLE_CHOICE, 
	
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.EnumMultipleChoiceFilter */
	ENUM_MULTIPLE_CHOICE, 
	
	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.ManyToManyFilter */
	MANY_TO_MANY;
}
