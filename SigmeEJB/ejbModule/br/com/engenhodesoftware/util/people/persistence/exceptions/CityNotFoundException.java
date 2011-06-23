package br.com.engenhodesoftware.util.people.persistence.exceptions;

/**
 * TODO: documentation pending.
 * 
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 */
public class CityNotFoundException extends Exception {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The name of the city used in the query. */
	private String cityName;

	/** The state acronym used in the query. */
	private String stateAcronym;

	/** Constructor. */
	public CityNotFoundException(String cityName, String stateAcronym) {
		this.cityName = cityName;
		this.stateAcronym = stateAcronym;
	}

	/** Getter for cityName. */
	public String getCityName() {
		return cityName;
	}

	/** Getter for stateAcronym. */
	public String getStateAcronym() {
		return stateAcronym;
	}
}
