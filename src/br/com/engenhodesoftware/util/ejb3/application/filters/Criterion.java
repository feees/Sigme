package br.com.engenhodesoftware.util.ejb3.application.filters;

import java.io.Serializable;

/**
 * Represents a criterion to be applied to a filter, other than the main filtering criterion.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class Criterion implements Serializable {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The name of the field to which the criterion applies. */
	protected String fieldName;

	/** The type of criterion: is null, is not null, equals, like, etc. */
	protected CriterionType type;

	/** The value to be compared to in case of criterions of types equals, like, etc. */
	protected Object param;

	/** Constructor  using fields. */
	public Criterion(String fieldName, CriterionType type) {
		this.fieldName = fieldName;
		this.type = type;
	}

	/** Constructor  using fields. */
	public Criterion(String fieldName, CriterionType type, Object param) {
		this(fieldName, type);
		this.param = param;
	}

	/** Getter for fieldName. */
	public String getFieldName() {
		return fieldName;
	}

	/** Getter for type. */
	public CriterionType getType() {
		return type;
	}

	/** Getter for param. */
	public Object getParam() {
		return param;
	}
}
