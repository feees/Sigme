package br.com.engenhodesoftware.util.ejb3.application.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Many-to-many filters can be used when the thing we need to filter is an entity associated with the filtered entities
 * by a many-to-many association. In this case, subFieldNames must contain a comma-separated list of fields of the
 * associated entity, while joinedCriteria should contain an optional list of criteria to be applied to parameters of
 * the associated entity (much like criteria holds for the filtered entity. In the persistence layer, the database will
 * perform an inner join between the filtered entity and the associated entity and apply the criteria to both.
 * 
 * As an example, imagine a scenario where products can participate in offers (promotions). Offers can contain many
 * products and products can participate in many offers, given that offers have a starting and ending date. If an offer
 * has a name (e.g. "Christmas 2009 sale!"), a many-to-many filter can filter the products that participate in an offer
 * that have "christmas" in their name. Furthermore, with joinedCriteria you can filter out offers that haven't yet
 * started or have already ended, for example.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public class ManyToManyFilter extends AbstractFilter<Void> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Fields to filter in the associated entity, separated by a comma (","). */
	protected String subFieldNames;

	/** Criteria to be applied to the associated entity. */
	protected List<Criterion> joinedCriteria = new ArrayList<Criterion>();

	/** Constructor from superclass, using fields. */
	public ManyToManyFilter(String key, String fieldName, String label, String subFieldNames) {
		super(key, fieldName, label);
		this.subFieldNames = subFieldNames;
	}

	/** Constructor from superclass, using fields. */
	public ManyToManyFilter(String key, String fieldName, String label, String subFieldNames, boolean joinCriteria, Criterion ... criteria) {
		super(key, fieldName, label);
		this.subFieldNames = subFieldNames;

		// Adds the criteria to the entity or the joined entity, depending on the choice.
		if (joinCriteria) {
			for (Criterion criterion : criteria) {
				this.joinedCriteria.add(criterion);
			}
		}
		else {
			for (Criterion criterion : criteria) {
				this.criteria.add(criterion);
			}
		}
	}

	/** Constructor from superclass, using fields. */
	public ManyToManyFilter(String key, String fieldName, String label, String subFieldNames, Criterion[] criteria, Criterion[] joinedCriteria) {
		super(key, fieldName, label, criteria);
		this.subFieldNames = subFieldNames;
		for (Criterion criterion : joinedCriteria) {
			this.joinedCriteria.add(criterion);
		}
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.AbstractFilter#getSubFieldNames() */
	@Override
	public String getSubFieldNames() {
		return subFieldNames;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.application.filters.Filter#getType() */
	public FilterType getType() {
		return FilterType.MANY_TO_MANY;
	}

	/** Getter for joinedCriteria. */
	public List<Criterion> getJoinedCriteria() {
		return joinedCriteria;
	}
}
