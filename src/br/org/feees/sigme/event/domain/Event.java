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
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventBadgeName() {
		return eventBadgeName;
	}

	public void setEventBadgeName(String eventBadgeName) {
		this.eventBadgeName = eventBadgeName;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getEventPrice() {
		return eventPrice;
	}

	public void setEventPrice(String eventPrice) {
		this.eventPrice = eventPrice;
	}

	public Address getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(Address eventAddress) {
		this.eventAddress = eventAddress;
	}

	public Date getRegisterInitialDate() {
		return registerInitialDate;
	}

	public void setRegisterInitialDate(Date registerInitialDate) {
		this.registerInitialDate = registerInitialDate;
	}

	public Date getRegisterFinalDate() {
		return registerFinalDate;
	}

	public void setRegisterFinalDate(Date registerFinalDate) {
		this.registerFinalDate = registerFinalDate;
	}

	public Date getEventInitialDate() {
		return eventInitialDate;
	}

	public void setEventInitialDate(Date eventInitialDate) {
		this.eventInitialDate = eventInitialDate;
	}

	public Date getEventFinalDate() {
		return eventFinalDate;
	}

	public void setEventFinalDate(Date eventFinalDate) {
		this.eventFinalDate = eventFinalDate;
	}

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Spiritist getSpiritistOwner() {
		return spiritistOwner;
	}

	public void setSpiritistOwner(Spiritist spiritistOwner) {
		this.spiritistOwner = spiritistOwner;
	}
}
