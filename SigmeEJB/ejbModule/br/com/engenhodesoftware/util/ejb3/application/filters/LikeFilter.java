package br.com.engenhodesoftware.util.ejb3.application.filters;

/**
 * The Like filter is the same as the simple filter, but uses the LIKE operator on Strings instead of the = operator.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class LikeFilter extends SimpleFilter {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.application.filters.SimpleFilter#SimpleFilter(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public LikeFilter(String key, String fieldName, String label) {
		super(key, fieldName, label);
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.application.filters.SimpleFilter#SimpleFilter(java.lang.String,
	 *      java.lang.String, java.lang.String, br.com.engenhodesoftware.util.ejb3.application.filters.Criterion[])
	 */
	public LikeFilter(String key, String fieldName, String label, Criterion ... criteria) {
		super(key, fieldName, label, criteria);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.Filter#getType() */
	@Override
	public FilterType getType() {
		return FilterType.LIKE;
	}
}
