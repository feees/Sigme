package br.com.engenhodesoftware.util.ejb3.application.filters;

/**
 * The Like filter is the same as the simple filter, but uses the LIKE operator on Strings instead of the = operator.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class LikeFilter extends SimpleFilter {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Constructor from superclass. */
	public LikeFilter(String key, String fieldName, String label) {
		super(key, fieldName, label);
	}

	/** Constructor from superclass. */
	public LikeFilter(String key, String fieldName, String label, Criterion ... criteria) {
		super(key, fieldName, label, criteria);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.SimpleFilter#getType() */
	@Override
	public FilterType getType() {
		return FilterType.LIKE;
	}
}
