package br.org.feees.sigme.event.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.feees.sigme.people.domain.Address;

import br.org.feees.sigme.core.domain.Institution;
import br.org.feees.sigme.core.domain.Regional;
import br.org.feees.sigme.core.domain.Spiritist;
import br.ufes.inf.nemo.util.ejb3.persistence.PersistentObjectSupport;

@Entity
public class Event extends PersistentObjectSupport implements Comparable<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Event's Name */
	@Basic
	@NotNull
	@Size (max = 30)
	private String eventName; // nomeEvento
	
	/** The Event's Description */
	@Basic
	@NotNull
	private String eventDescription; // descricaoEvento
	
	@Basic
	@Size (max = 20)
	private String eventBadgeName; // nomeEventoCracha
	
	@Basic
	private String additionalInformation; // informacoesComplementares
	
	@Basic
	private String eventPrice; // preçoInscricao
	
	@ManyToOne (cascade = CascadeType.ALL)
	@NotNull
	private Address eventAddress; // localDoEvento
	
	/** The regional to which the institution belongs. */
	@ManyToOne
	private Regional regional;

	@Temporal(TemporalType.DATE)
	private Date registerInitialDate; // dataInicioInscricao

	@Temporal(TemporalType.DATE)
	private Date registerFinalDate; // dataFinalInscricao

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date eventInitialDate; // dataInicial

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date eventFinalDate; // dataFinal
	
	// Instituição da qual o evento pertence
	@ManyToOne
	@NotNull
	private Institution institution;
	
	// Espírita que criou o evento
	@ManyToOne
	@NotNull
	private Spiritist spiritistOwner;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy="event")
	private List<Subscriber> subscribers;

	@Override
	public int compareTo(Event o) {
		return uuid.compareTo(o.uuid);
	}

	/*
	 * Getters and Setters
	 */
	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the eventDescription
	 */
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * @param eventDescription the eventDescription to set
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/**
	 * @return the eventBadgeName
	 */
	public String getEventBadgeName() {
		return eventBadgeName;
	}

	/**
	 * @param eventBadgeName the eventBadgeName to set
	 */
	public void setEventBadgeName(String eventBadgeName) {
		this.eventBadgeName = eventBadgeName;
	}

	/**
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the eventPrice
	 */
	public String getEventPrice() {
		return eventPrice;
	}

	/**
	 * @param eventPrice the eventPrice to set
	 */
	public void setEventPrice(String eventPrice) {
		this.eventPrice = eventPrice;
	}

	/**
	 * @return the eventAddress
	 */
	public Address getEventAddress() {
		return eventAddress;
	}

	/**
	 * @param eventAddress the eventAddress to set
	 */
	public void setEventAddress(Address eventAddress) {
		this.eventAddress = eventAddress;
	}

	/**
	 * @return the regional
	 */
	public Regional getRegional() {
		return regional;
	}

	/**
	 * @param regional the regional to set
	 */
	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	/**
	 * @return the registerInitialDate
	 */
	public Date getRegisterInitialDate() {
		return registerInitialDate;
	}

	/**
	 * @param registerInitialDate the registerInitialDate to set
	 */
	public void setRegisterInitialDate(Date registerInitialDate) {
		this.registerInitialDate = registerInitialDate;
	}

	/**
	 * @return the registerFinalDate
	 */
	public Date getRegisterFinalDate() {
		return registerFinalDate;
	}

	/**
	 * @param registerFinalDate the registerFinalDate to set
	 */
	public void setRegisterFinalDate(Date registerFinalDate) {
		this.registerFinalDate = registerFinalDate;
	}

	/**
	 * @return the eventInitialDate
	 */
	public Date getEventInitialDate() {
		return eventInitialDate;
	}

	/**
	 * @param eventInitialDate the eventInitialDate to set
	 */
	public void setEventInitialDate(Date eventInitialDate) {
		this.eventInitialDate = eventInitialDate;
	}

	/**
	 * @return the eventFinalDate
	 */
	public Date getEventFinalDate() {
		return eventFinalDate;
	}

	/**
	 * @param eventFinalDate the eventFinalDate to set
	 */
	public void setEventFinalDate(Date eventFinalDate) {
		this.eventFinalDate = eventFinalDate;
	}

	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/**
	 * @return the spiritistOwner
	 */
	public Spiritist getSpiritistOwner() {
		return spiritistOwner;
	}

	/**
	 * @param spiritistOwner the spiritistOwner to set
	 */
	public void setSpiritistOwner(Spiritist spiritistOwner) {
		this.spiritistOwner = spiritistOwner;
	}

	/**
	 * @return the subscribers
	 */
	public List<Subscriber> getSubscribers() {
		return subscribers;
	}

	/**
	 * @param subscribers the subscribers to set
	 */
	public void setSubscribers(List<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}
	
}
