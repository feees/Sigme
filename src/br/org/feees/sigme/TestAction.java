package br.org.feees.sigme;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.engenhodesoftware.util.ejb3.controller.JSFController;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * Controller class responsible for mediating the communication between user interface and application service for the
 * use case "Install System".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Named
@SessionScoped
public class TestAction extends JSFController {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger logger = Logger.getLogger(TestAction.class.getCanonicalName());

	/** Output: the list of telephone numbers. */
	private List<Telephone> telephones = new ArrayList<Telephone>();

	/** Input: a telephone being added or edited. */
	private Telephone telephone;
	
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private int count;
	public int getCount() { return ++count; }
	
	public void addSomePhone() {
		logger.log(Level.INFO, "Adding some phone to the list!");
		Telephone phone = new Telephone();
		phone.setNumber("(99) 9999-9999");
		ContactType type = new ContactType();
		type.setType("Test");
		phone.setType(type);
		telephones.add(phone);
	}

	/** Getter for telephones. */
	public List<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for telephone. */
	public Telephone getTelephone() {
		return telephone;
	}

	/** Setter for telephone. */
	public void setTelephone(Telephone telephone) {
		logger.log(Level.FINEST, "Selected telephone: {0}", telephone);
		this.telephone = telephone;
	}
	
	/**
	 * Creates a new and empty telephone so the telephone fields can be filled. 
	 * 
	 * This method is intended to be used with AJAX, through the PrimeFaces Collector component.
	 */
	public String newTelephone() {
		logger.log(Level.INFO, "Creating an empty telephone to be added.");
		telephone = new Telephone();
		return null;
	}
	
	public String resetTelephone() {
		logger.log(Level.INFO, "Resetting the telephone field.");
		telephone = null;
		return null;
	}
	
}
