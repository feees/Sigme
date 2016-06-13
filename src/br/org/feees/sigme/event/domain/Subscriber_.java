package br.org.feees.sigme.event.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.org.feees.sigme.core.domain.Spiritist;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport_;

@StaticMetamodel(Subscriber.class)
public class Subscriber_ extends PersistentObjectSupport_ {
	
	public static volatile SingularAttribute<Subscriber, Spiritist> spiritist;
	public static volatile SingularAttribute<Subscriber, Event> event;
	
	public static volatile SingularAttribute<Subscriber, String> badgeName;
	
	public static volatile SingularAttribute<Subscriber, Date> subscribeDate;
}
