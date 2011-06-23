package br.com.engenhodesoftware.util.ejb3.application.filters;

/**
 * For a simple filter, only the fieldName has to be set. A text field should be shown at the GUI and the user can type
 * in any value. At the persistence level, the database query will search for the search parameter in the given
 * fieldName using the operator LIKE and wildcards both before and after the search parameter. E.g.: searching a product
 * given part of its name.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class SimpleFilter extends AbstractFilter<Void> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.application.filters.AbstractFilter#AbstractFilter(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public SimpleFilter(String key, String fieldName, String label) {
		super(key, fieldName, label);
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.application.filters.AbstractFilter#AbstractFilter(java.lang.String,
	 *      java.lang.String, java.lang.String, br.com.engenhodesoftware.util.ejb3.application.filters.Criterion[])
	 */
	public SimpleFilter(String key, String fieldName, String label, Criterion ... criteria) {
		super(key, fieldName, label, criteria);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.Filter#getType() */
	@Override
	public FilterType getType() {
		return FilterType.SIMPLE;
	}
}
