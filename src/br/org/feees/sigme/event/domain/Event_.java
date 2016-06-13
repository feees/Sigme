package br.org.feees.sigme.event.domain;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.feees.sigme.people.domain.Address;

import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.Regional;
import br.org.feees.sigme.core.domain.Spiritist;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

@StaticMetamodel(Event.class)
public class Event_ extends PersistentObjectSupport_ {

	public static volatile SingularAttribute<Event, String> eventName;
	public static volatile SingularAttribute<Event, String> eventDescription;
	public static volatile SingularAttribute<Event, String> eventBadgeName;
	public static volatile SingularAttribute<Event, String> additionalInformation;
	public static volatile SingularAttribute<Event, String> eventPrice;
	public static volatile SingularAttribute<Event, Address> eventAddress;
	public static volatile SingularAttribute<Event, Regional> regional;
	
	public static volatile SingularAttribute<Event, Institution> institution;
	public static volatile SingularAttribute<Event, Spiritist> spiritistOwner;
	
	public static volatile ListAttribute<Event, Subscriber> subscribers;
	
	public static volatile SingularAttribute<Event, Date> registerInitialDate;
	public static volatile SingularAttribute<Event, Date> registerFinalDate;
	public static volatile SingularAttribute<Event, Date> eventInitialDate;
	public static volatile SingularAttribute<Event, Date> eventFinalDate;

}
