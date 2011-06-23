package br.com.engenhodesoftware.util.ejb3.application.filters;

/**
 * Enumeration of the available types of criteria.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public enum CriterionType {
	/** "field-name is null" criterion. */
	IS_NULL,
	/** "field-name is not null" criterion. */
	IS_NOT_NULL,
	/** "field-name = value" criterion. */
	EQUALS,
	/** "field-name like '%value%'" criterion. */
	LIKE;
}
