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
	@Size (max = 100)
	private String name; // nomeEvento
	
	/** The Event's Description */
	@Basic
	@NotNull
	@Size (max = 300)
	private String description; // descricaoEvento
	
	@Basic
	@Size (max = 100)
	private String badgeName; // nomeEventoCracha
	
	@Basic
	@Size(max = 500)
	private String additionalInformation; // informacoesComplementares
	
	@Basic
	private String price; // preçoInscricao
	
	@ManyToOne (cascade = CascadeType.ALL)
	@NotNull
	private Address address; // localDoEvento
	
	/** The regional to which the institution belongs. */
	@ManyToOne
	private Regional regional;

	@Temporal(TemporalType.DATE)
	private Date registerInitialDate; // dataInicioInscricao

	@Temporal(TemporalType.DATE)
	private Date registerFinalDate; // dataFinalInscricao

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date initialDate; // dataInicial

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date finalDate; // dataFinal
	
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
		return name;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.name = eventName;
	}

	/**
	 * @return the eventDescription
	 */
	public String getEventDescription() {
		return description;
	}

	/**
	 * @param eventDescription the eventDescription to set
	 */
	public void setEventDescription(String eventDescription) {
		this.description = eventDescription;
	}

	/**
	 * @return the eventBadgeName
	 */
	public String getEventBadgeName() {
		return badgeName;
	}

	/**
	 * @param eventBadgeName the eventBadgeName to set
	 */
	public void setEventBadgeName(String eventBadgeName) {
		this.badgeName = eventBadgeName;
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
		return price;
	}

	/**
	 * @param eventPrice the eventPrice to set
	 */
	public void setEventPrice(String eventPrice) {
		this.price = eventPrice;
	}

	/**
	 * @return the eventAddress
	 */
	public Address getEventAddress() {
		return address;
	}

	/**
	 * @param eventAddress the eventAddress to set
	 */
	public void setEventAddress(Address eventAddress) {
		this.address = eventAddress;
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
		return initialDate;
	}

	/**
	 * @param eventInitialDate the eventInitialDate to set
	 */
	public void setEventInitialDate(Date eventInitialDate) {
		this.initialDate = eventInitialDate;
	}

	/**
	 * @return the eventFinalDate
	 */
	public Date getEventFinalDate() {
		return finalDate;
	}

	/**
	 * @param eventFinalDate the eventFinalDate to set
	 */
	public void setEventFinalDate(Date eventFinalDate) {
		this.finalDate = eventFinalDate;
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
