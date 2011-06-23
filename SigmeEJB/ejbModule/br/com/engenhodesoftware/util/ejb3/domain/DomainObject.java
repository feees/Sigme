package br.com.engenhodesoftware.util.ejb3.domain;

import java.io.Serializable;

/**
 * Optional interface for domain objects that implement equals() and hashCode() using an Universal Unique Identifier
 * (UUID).
 * 
 * It is very common to have domain objects in collections such as HashSet, which assume that the methods equals() and
 * hashCode() have been implemented in a way that all instances that represent the same object return the same value as
 * hashCode() and are equals according to equals().
 * 
 * Many implementations of equals() and hashCode() have already been suggested: using properties of the domain object
 * that univocally identifies an object; using the persistence id (i.e. the primary key); etc. Each has its own
 * advantages and disadvantages.
 * 
 * The solution proposed by this class is the utilization of an Universal Unique Identifier (UUID). This interface
 * provides the methods to obtain the UUID. There is a standard implementation for this interface: DomainObjectSupport.
 * It's based on Jason Carreira's idea, explained in <a
 * href="http://jroller.com/jcarreira/date/20040504#hibernate_null_unsaved_value_and">here</a> and <a
 * href="http://www.jroller.com/jcarreira/entry/overcoming_the_hashcode_object_identity>here</a>.
 * 
 * This is just the interface. The implementation is provided in the abstract class DomainObjectSupport.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @see br.com.engenhodesoftware.util.ejb3.domain.DomainObjectSupport
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public interface DomainObject extends Serializable {
	/**
	 * Obtains the Universal Unique Identifier.
	 * 
	 * @return The UUID.
	 */
	String getUuid();
}
