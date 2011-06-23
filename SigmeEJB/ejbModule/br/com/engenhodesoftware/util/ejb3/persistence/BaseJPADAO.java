package br.com.engenhodesoftware.util.ejb3.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import br.com.engenhodesoftware.util.ejb3.application.filters.Criterion;
import br.com.engenhodesoftware.util.ejb3.application.filters.CriterionType;
import br.com.engenhodesoftware.util.ejb3.application.filters.Filter;
import br.com.engenhodesoftware.util.ejb3.application.filters.ManyToManyFilter;
import br.com.engenhodesoftware.util.people.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.com.engenhodesoftware.util.people.persistence.exceptions.PersistentObjectNotFoundException;

/**
 * Base class for DAOs that use JPA 2.0 (Java EE 6) as persistence mechanism.
 * 
 * For more information on the DAO pattern, visit: <a href=
 * "http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html"
 * target="_blank">http://java.sun.com/blueprints/corej2eepatterns/Patterns/DataAccessObject.html</a>.
 * 
 * Most methods in this class were generated automatically by NetBeans 6.8 and adapted to the DAO interface.
 * 
 * <i>This class is part of the Engenho de Software CRUD framework for EJB3 (Java EE 6).</i>
 * 
 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO
 * @see br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject
 * @author Vitor Souza (vitorsouza@gmail.com)
 * @version 1.1
 */
public abstract class BaseJPADAO<T extends PersistentObject> implements BaseDAO<T> {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(BaseJPADAO.class.getCanonicalName());

	/**
	 * Abstract method that should be implemented by the concrete DAOs returning the entity manager that is used in the
	 * persistence operations. The entity manager is automatically injected in EJBs that use the @PersistenceContext
	 * annotation.
	 * 
	 * @see javax.persistence.PersistenceContext
	 * @return The entity manager (persistence context) object supplied by the Java EE container.
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Abstract method that should be implemented by the concrete DAOs returning the class that is managed by the DAO.
	 * This information is needed for some persistence operations.
	 * 
	 * @return A class object that represents the class managed by the DAO.
	 */
	protected abstract Class<T> getDomainClass();

	/**
	 * Method that can be overriden by the subclasses to determine the default ordering when using retrieveAll and
	 * retrieveSome methods. The default implementation returns null, establishing no order.
	 * 
	 * @param cb
	 *          The criteria builder object, needed to build queries.
	 * @param root
	 *          The root of the query, meta-object that represents the class of objects beind queried.
	 * 
	 * @see javax.persistence.criteria.Order
	 * @return A list of Order objects
	 */
	protected List<Order> getOrderList(CriteriaBuilder cb, Root<T> root) {
		return null;
	}

	/**
	 * Applies ordering to a query under construction, if any ordering has been provided by the concrete DAO class.
	 * 
	 * @param cb
	 *          The criteria builder object, needed to build queries.
	 * @param root
	 *          The root of the query, meta-object that represents the class of objects beind queried.
	 * @param cq
	 *          The query being constructed.
	 */
	protected void applyOrdering(CriteriaBuilder cb, Root<T> root, CriteriaQuery<T> cq) {
		// Checks if the order list has been provided and applies it.
		List<Order> orderList = getOrderList(cb, root);
		if (orderList != null)
			cq.orderBy(orderList);
	}

	protected T executeSingleResultQuery(CriteriaQuery<T> cq, Object ... params) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
		// Looks for a single result. Throws a checked exception if the entity is not found or in case of multiple results.
		try {
			T result = getEntityManager().createQuery(cq).getSingleResult();
			return result;
		}
		catch (NoResultException e) {
			logger.log(Level.WARNING, "NoResultException thrown for params: " + params, e);
			throw new PersistentObjectNotFoundException(e, getDomainClass(), params);
		}
		catch (NonUniqueResultException e) {
			logger.log(Level.WARNING, "NonUniqueResultException thrown for params: " + params, e);
			throw new MultiplePersistentObjectsFoundException(e, getDomainClass(), params);
		}
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveCount() */
	@Override
	public long retrieveCount() {
		// Using the entity manager, create a criteria query to retrieve the object count.
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> rt = cq.from(getDomainClass());
		cq.select(cb.count(rt));
		Query q = em.createQuery(cq);

		// Retrieve the value and return.
		long count = ((Long) q.getSingleResult()).longValue();
		logger.log(Level.INFO, "Retrieving count for {0}: {1}", new Object[] { getDomainClass().getName(), count });
		return count;
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveFilteredCount(br.com.engenhodesoftware.util.ejb3.application.filters.Filter,
	 *      java.lang.String)
	 */
	@Override
	public long retrieveFilteredCount(Filter<?> filter, String value) {
		// Builds the filtered query.
		EntityManager em = getEntityManager();
		CriteriaQuery<Long> cq = buildFilteredCountCriteriaQuery(filter, value);
		Query q = em.createQuery(cq);

		// Retrieve the value and return.
		long count = ((Long) q.getSingleResult()).longValue();
		logger.log(Level.INFO, "Retrieving count for {0}, filtering {1} with param \"{2}\": {3}", new Object[] { getDomainClass().getName(), filter.getKey(), value, count });
		return count;
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveAll() */
	@Override
	public List<T> retrieveAll() {
		logger.log(Level.INFO, "Retrieving all: {0}", getDomainClass().getName());

		// Using the entity manager, create a criteria query to retrieve all objects of the domain class.
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
		Root<T> root = cq.from(getDomainClass());
		cq.select(root);

		// Applies ordering.
		applyOrdering(cb, root, cq);

		// Return the list of objects.
		return em.createQuery(cq).getResultList();
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveWithFilter(br.com.engenhodesoftware.util.ejb3.application.filters.Filter,
	 *      java.lang.String)
	 */
	@Override
	public List<T> retrieveWithFilter(Filter<?> filter, String value) {
		logger.log(Level.INFO, "Retrieving all, filtering {0} with param \"{1}\": {2}", new Object[] { filter.getKey(), value, getDomainClass().getName() });

		// Builds the filtered query and returns the result.
		EntityManager em = getEntityManager();
		CriteriaQuery<T> cq = buildFilteredCriteriaQuery(filter, value);
		return em.createQuery(cq).getResultList();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveSome(int[]) */
	@Override
	public List<T> retrieveSome(int[] interval) {
		logger.log(Level.INFO, "Retrieving in interval [{0}, {1}): {2}", new Object[] { interval[0], interval[1], getDomainClass().getName() });

		// Using the entity manager, create a criteria query to retrieve objects of the domain class.
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
		Root<T> root = cq.from(getDomainClass());
		cq.select(root);

		// Applies ordering.
		applyOrdering(cb, root, cq);

		// Determine the interval to retrieve.
		TypedQuery<T> q = em.createQuery(cq);
		q.setMaxResults(interval[1] - interval[0]);
		q.setFirstResult(interval[0]);

		// Return the list of objects.
		return q.getResultList();
	}

	/**
	 * @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveSomeWithFilter(br.com.engenhodesoftware.util.ejb3.application.filters.Filter,
	 *      java.lang.String, int[])
	 */
	@Override
	public List<T> retrieveSomeWithFilter(Filter<?> filter, String value, int[] interval) {
		logger.log(Level.INFO, "Retrieving in interval [{0}, {1}), filtering {2} with param \"{3}\": {4}", new Object[] { interval[0], interval[1], filter.getKey(), value, getDomainClass().getName() });

		// Builds the filtered query.
		EntityManager em = getEntityManager();
		CriteriaQuery<T> cq = buildFilteredCriteriaQuery(filter, value);

		// Determine the interval to retrieve and return the result.
		TypedQuery<T> q = em.createQuery(cq);
		q.setMaxResults(interval[1] - interval[0]);
		q.setFirstResult(interval[0]);
		return q.getResultList();
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#retrieveById(java.lang.Long) */
	@Override
	public T retrieveById(Long id) {
		logger.log(Level.INFO, "Retrieving with id {0}: {1}", new Object[] { id, getDomainClass().getName() });

		// Uses the Persistence Context to retrieve an object given its id.
		EntityManager em = getEntityManager();
		return (T) em.find(getDomainClass(), id);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#save(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void save(T object) {
		logger.log(Level.INFO, "Saving {0}: {1}", new Object[] { getDomainClass().getName(), object });

		// Uses the Persistence Context to save an object. Checks if it's a new object (INSERT) or an existing one (UPDATE).
		EntityManager em = getEntityManager();
		if (object.isPersistent())
			em.merge(object);
		else em.persist(object);
	}

	/** @see br.com.engenhodesoftware.util.ejb3.persistence.BaseDAO#delete(br.com.engenhodesoftware.util.ejb3.persistence.PersistentObject) */
	@Override
	public void delete(T object) {
		logger.log(Level.INFO, "Deleting {0}: {1}", new Object[] { getDomainClass().getName(), object });

		// Uses the Persistence Context to delete an object.
		EntityManager em = getEntityManager();
		em.remove(em.merge(object));
	}

	private CriteriaQuery<Long> buildFilteredCountCriteriaQuery(Filter<?> filter, String value) {
		// Using the entity manager, create a criteria query to retrieve an object count.
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> root = cq.from(getDomainClass());
		cq.select(cb.count(root));
		
		// Filters the criteria query and returns.
		filterCriteriaQuery(cb, cq, root, filter, value);
		return cq;
	}
	
	/**
	 * Builds a criteria query object that retrieves the domain objects according to the given filter (and its embedded
	 * criteria).
	 * 
	 * @param filter
	 *          The filter to be applied to the query.
	 * @param value
	 *          The value associated with the filter.
	 * 
	 * @return The criteria query object that can be used to produce the query.
	 */
	private CriteriaQuery<T> buildFilteredCriteriaQuery(Filter<?> filter, String value) {
		// Using the entity manager, create a criteria query to retrieve objects of the domain class.
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getDomainClass());
		Root<T> root = cq.from(getDomainClass());
		cq.select(root);
		
		// Filters the criteria query, applies ordering (if provided) and returns.
		filterCriteriaQuery(cb, cq, root, filter, value);
		applyOrdering(cb, root, cq);
		return cq;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void filterCriteriaQuery(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root, Filter<?> filter, String value) {
		// TODO: remove @SupressWarnings and add the correct generic types to all operations.
		
		// Get the model for the domain class so we can perform filtering.
		EntityType<T> model = root.getModel();

		// Add predicates to a list in order to join them together in a conjunction for the WHERE clause.
		List<Predicate> predicates = new ArrayList<Predicate>();

		// Process each criterion, adding a predicate to the list. The predicate to add depends on the type of criterion.
		for (Criterion crit : filter.getCriteria())
			predicates.add(createPredicate(cb, root, model, crit));

		// Check which type of filter is being used.
		Long id = null;
		Criterion criterion = null;
		TypeFrom pair = null;
		switch (filter.getType()) {
		case MANY_TO_MANY:
			// TODO: not yet tested...

			// Using a many-to-many filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj inner join obj.<field-name> as joinObj where <joined-criteria> and (<filters>)
			// where:
			// <joined-criteria> is a conjunction of ISNULL, NOTNULL, EQUAL or LIKE criteria on the joined object
			// <filter> is a disjunction of LIKE criteria on different properties of the joined object, like:
			// joinObj.<a-property> LIKE '%<value>%' or joinObj.<another-property> LIKE '%<value>%' ...

			// Performs the join of the two elements.
			SingularAttribute joinObjAttr = model.getSingularAttribute(filter.getFieldName());
			Join joinRoot = root.join(joinObjAttr);
			ManagedType joinObjModel = joinObjAttr.getDeclaringType();

			// Adds to the predicate list with the criteria to be applied to the joined entity (the conjunction).
			List<Criterion> joinedCriteria = ((ManyToManyFilter) filter).getJoinedCriteria();
			for (Criterion crit : joinedCriteria)
				predicates.add(createPredicate(cb, joinRoot, joinObjModel, crit));

			// Builds a predicate list with the filters to be applied to the joined-object (the disjunction).
			List<Predicate> orPredicates = new ArrayList<Predicate>();
			Scanner scanner = new Scanner(filter.getSubFieldNames());
			scanner.useDelimiter("\\s*,\\s*");
			while (scanner.hasNext())
				orPredicates.add(cb.like(joinRoot.get(joinObjModel.getSingularAttribute(scanner.next(), String.class)), "%" + value + "%"));

			// Builds the query creating an OR expression for the disjunction and joining all criteria.
			Predicate disjunction = cb.or(orPredicates.toArray(new Predicate[0]));
			predicates.add(disjunction);
			cq.where(predicates.toArray(new Predicate[0]));

			break;

		case REVERSE_MULTIPLE_CHOICE:
			// Using a reverse multiple-choice filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj where obj.<field-name> in (
			// select subDep.id from <sub-class> sub inner join sub.<sub-field-name> subDep where sub.id =
			// <value-converted-as-id>
			// )

			// Check if the ID was correctly supplied.
			try {
				id = Long.parseLong(value);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("When using reverse multiple-choice filter, a number must be supplied (the related object's ID).", e);
			}

			// Builds the subquery needed to perform a reverse multiple-choice filter.
			Class clazz = filter.getOptions().iterator().next().getClass();
			Subquery<Long> sq = cq.subquery(Long.class);
			Root subDepRoot = sq.from(clazz);
			Attribute subDepAttr = subDepRoot.getModel().getAttribute(filter.getSubFieldNames());
			ManagedType subDepModel = subDepAttr.getDeclaringType();
			Join subDepJoin = null;
			switch (((PluralAttribute) subDepAttr).getCollectionType()) {
			case COLLECTION:
				subDepJoin = sq.correlate(subDepRoot.join((CollectionAttribute) subDepAttr));
				break;
			case LIST:
				subDepJoin = sq.correlate(subDepRoot.join((ListAttribute) subDepAttr));
				break;
			case MAP:
				subDepJoin = sq.correlate(subDepRoot.join((MapAttribute) subDepAttr));
				break;
			case SET:
				subDepJoin = sq.correlate(subDepRoot.join((SetAttribute) subDepAttr));
				break;
			}
			sq.where(cb.equal(subDepRoot.get(subDepRoot.getModel().getSingularAttribute("id")), id));
			sq.select(subDepJoin.get(subDepModel.getSingularAttribute("id")));

			// Builds the query joining together the previous criteria with an IN criterion.
			pair = findManagedType(root, model, filter.getFieldName() + ".id");
			predicates.add(cb.in(pair.from.get(pair.type.getSingularAttribute("id"))).value(sq));
			cq.where(predicates.toArray(new Predicate[0]));
			break;

		case ENUM_MULTIPLE_CHOICE:
			// Using an enum multiple-choice filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj where obj.<field-name> = <enum-value>

			// Obtains the instance of the enumeration to pass as argument given the enumeration name passed as parameter.
			Enum<?> enumValue = filter.getEnum(value);

			// TODO: not tested after changed to findManagedType().
			// Add to the other criteria a EQUAL criterion between the related object's ID and the specified value.
			// predicates.add(cb.equal(root.get(model.getSingularAttribute(filter.getFieldName())), enumValue));
			// pair = findManagedType(root, model, filter.getFieldName());
			// predicates.add(cb.equal(pair.from.get(pair.type.getSingularAttribute(filter.getFieldName())), enumValue));
			criterion = new Criterion(filter.getFieldName(), CriterionType.EQUALS, enumValue);
			predicates.add(createPredicate(cb, root, model, criterion));
			cq.where(predicates.toArray(new Predicate[0]));

			break;

		case MULTIPLE_CHOICE:
			// TODO: not yet tested...

			// Using a multiple-choice filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj where obj.<field-name>.id = <value-converted-as-id>

			// Check if the ID was correctly supplied.
			try {
				id = Long.parseLong(value);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("When using multiple-choice filter, a number must be supplied (the related object's ID).", e);
			}

			// Add to the other criteria a EQUAL criterion between the related object's ID and the specified value.
			// relatedModel = model.getSingularAttribute(filter.getFieldName()).getDeclaringType();
			// predicates.add(cb.equal(root.get(relatedModel.getSingularAttribute("id")), id));
			// pair = findManagedType(root, model, filter.getFieldName() + ".id");
			// predicates.add(cb.equal(pair.from.get(pair.type.getSingularAttribute("id")), id));
			criterion = new Criterion(filter.getFieldName() + ".id", CriterionType.EQUALS, id);
			predicates.add(createPredicate(cb, root, model, criterion));
			cq.where(predicates.toArray(new Predicate[0]));

			break;

		case LIKE:
			// Using a simple filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj where obj.<field-name> LIKE '%<value>%'

			// Add to the other criteria a LIKE criterion for the field name and the specified value.
			criterion = new Criterion(filter.getFieldName(), CriterionType.LIKE, value);
			predicates.add(createPredicate(cb, root, model, criterion));
			cq.where(predicates.toArray(new Predicate[0]));
			break;

		default:
			// Using a simple filter. This query looks like this (without the extra criteria):
			// from <domain-class> obj where obj.<field-name> = <value>

			// Add to the other criteria a = (equals) criterion for the field name and the specified value.
			criterion = new Criterion(filter.getFieldName(), CriterionType.EQUALS, value);
			predicates.add(createPredicate(cb, root, model, criterion));
			cq.where(predicates.toArray(new Predicate[0]));
		}
	}

	/**
	 * Constructs a predicate using Java EE's Criteria API depending on the type of criterion.
	 * 
	 * @param cb
	 *          The criteria builder.
	 * @param path
	 *          The path object (root, join, etc.).
	 * @param model
	 *          The model object (managed type, entity type, etc.).
	 * @param criterion
	 *          The criterion used to build the predicate.
	 * 
	 * @return The predicate object that can be used to compose a CriteriaQuery.
	 * @see javax.persistence.criteria.CriteriaQuery
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Predicate createPredicate(CriteriaBuilder cb, From path, ManagedType model, Criterion criterion) {
		// TODO: remove @SupressWarnings and add the correct generic types to all operations.
		
		// Obtains the final path. This is done in case navigation is required.
		Path finalPath = findPath(path, model, criterion.getFieldName());

		// Check the criterion type.
		switch (criterion.getType()) {
		case IS_NULL:
			return cb.isNull(finalPath);

		case IS_NOT_NULL:
			return cb.isNotNull(finalPath);

		case EQUALS:
			return cb.equal(finalPath, criterion.getParam());

		case LIKE:
			return cb.like(cb.lower(finalPath), "%" + criterion.getParam().toString().toLowerCase() + "%");
		}

		// Thrown an exception in the case of an unknown criterion type.
		throw new IllegalArgumentException("Unknown criterion type: " + this);
	}

	/**
	 * Finds the final path that has to be compared in the criterion. In case of simple fields (e.g. name, representing a
	 * String), this is straightforward. In case of fields that require navigation (e.g. address.city.name), this method
	 * performs the necessary joins and returns the Path object representing the correct property (in the example, name)
	 * to be compared.
	 * 
	 * @param root
	 *          The From object representing where we should start the navigation.
	 * @param model
	 *          The metamodel for the starting entity.
	 * @param fieldName
	 *          The name of the field.
	 * @return The Path object representing the property that should be compared.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Path findPath(From root, ManagedType model, String fieldName) {
		// TODO: remove @SupressWarnings and add the correct generic types to all operations.
		
		// Finds the From and the ManagedType from the entity right before the last field.
		TypeFrom pair = findManagedType(root, model, fieldName);

		// Separates the name of the last field in the string.
		int idx = fieldName.lastIndexOf('.');
		fieldName = fieldName.substring(idx + 1);

		// Returns the path to the field.
		return pair.from.get(pair.type.getSingularAttribute(fieldName));

		/*
		 * Path finalPath = null; // Navigate through the entities using the dots. Joins have to be performed in case of
		 * navigation. int idx = fieldName.indexOf('.'); ManagedType entityType = model; From from = root; while (finalPath
		 * == null) { // No more dots, so obtain the singular attribute and the path. if (idx == -1) finalPath =
		 * from.get(entityType.getSingularAttribute(fieldName)); // There are dots. Obtains the first element and iterates
		 * to follow the chain. else { String firstField = fieldName.substring(0, idx); SingularAttribute attr =
		 * entityType.getSingularAttribute(firstField); // Element must be an entity in order to be navigable. Type type =
		 * attr.getType(); if (type.getPersistenceType() != Type.PersistenceType.ENTITY) throw new
		 * IllegalStateException("Cannot navigate the field \"" + firstField +
		 * "\" because it doesn't represent an entity."); // Sets the entity type, the join and removes the first entity
		 * from the field name for the next iteration. from = from.join(attr); entityType = (EntityType)type; fieldName =
		 * fieldName.substring(idx + 1); idx = fieldName.indexOf('.'); } } // Returns the final path once there was no more
		 * navigation required. return finalPath;
		 */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TypeFrom findManagedType(From root, ManagedType model, String fieldName) {
		// TODO: remove @SupressWarnings and add the correct generic types to all operations.
		
		TypeFrom pair = new TypeFrom();

		// Navigate through the entities using the dots. Joins have to be performed in case of navigation.
		pair.type = model;
		pair.from = root;
		int idx = fieldName.indexOf('.');
		while (idx != -1) {
			String firstField = fieldName.substring(0, idx);
			SingularAttribute attr = pair.type.getSingularAttribute(firstField);

			// Element must be an entity in order to be navigable.
			Type type = attr.getType();
			if (type.getPersistenceType() != Type.PersistenceType.ENTITY)
				throw new IllegalStateException("Cannot navigate the field \"" + firstField + "\" because it doesn't represent an entity.");

			// Sets the entity type, the join and removes the first entity from the field name for the next iteration.
			pair.from = pair.from.join(attr);
			pair.type = (EntityType) type;
			fieldName = fieldName.substring(idx + 1);
			idx = fieldName.indexOf('.');
		}

		// Returns the final path once there was no more navigation required.
		return pair;
	}

	private class TypeFrom {
		// TODO: remove @SupressWarnings and add the correct generic types to all operations.
		
		@SuppressWarnings("rawtypes")
		ManagedType type;
		@SuppressWarnings("rawtypes")
		From from;
	}
}
