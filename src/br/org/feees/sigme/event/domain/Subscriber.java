package br.org.feees.sigme.event.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.org.feees.sigme.core.domain.Spiritist;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class Subscriber extends PersistentObjectSupport implements Comparable<Subscriber> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@NotNull
	private Spiritist spiritist;
	
	@ManyToOne
	@NotNull
	private Event event;
	
	private String badgeName;
	
	@Temporal (TemporalType.DATE)
	private Date subscribeDate;
	
	@Override
	public int compareTo(Subscriber o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Spiritist getSpiritist() {
		return spiritist;
	}

	public void setSpiritist(Spiritist spiritist) {
		this.spiritist = spiritist;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getBadgeName() {
		return badgeName;
	}

	public void setBadgeName(String badgeName) {
		this.badgeName = badgeName;
	}

	public Date getSubscribeDate() {
		return subscribeDate;
	}

	public void setSubscribeDate(Date subscribeDate) {
		this.subscribeDate = subscribeDate;
	}

}
