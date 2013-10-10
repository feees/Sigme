package br.com.engenhodesoftware.sigme.core.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.Person;
import br.com.engenhodesoftware.util.people.domain.Telephone;

/**
 * Domain class that represents spiritists, i.e., the users of the system.
 * 
 * Any user of the system, be them spiritist in real life or not, is represented by this class. Although this might seem
 * an inconsistency, I thought it would be better than calling everyone "User", which is such an overloaded term. In
 * other words, if someone is using Sigme, they are spiritist.
 * 
 * The term "spiritist" is taken from the translation from the Portuguese "Espírita", which as a noun means a person
 * that adheres to the religion Spiritism (see, e.g., http://en.wikipedia.org/wiki/Spiritism). It is consistent with the
 * name of the "International Spiritist Council" (http://www.spirites.org/isc/portal/), which is "an organisation
 * resulting from the union, on a world-wide scale, of the Associations representative of the National Spiritist
 * Movements".
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 */
@Entity
public class Spiritist extends Person {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Short name to use when there isn't much space. */
	@Basic
	@NotNull
	@Size(max = 15)
	private String shortName;

	/** Electronic address, which also serves as username for identification. */
	@Basic
	@Size(max = 100)
	private String email;

	/** The password, which identifies the user. */
	@Basic
	@Size(max = 32)
	private String password;

	/** The person's unique tax code (in Brazil, CPF, or "Cadastro de Pessoas Físicas"). */
	@Basic
	@Size(max = 14)
	private String taxCode;

	/** The physical address where the person lives. */
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;

	/** Phone numbers. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Telephone> telephones;

	/** Institutions that the spiritist attend. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "spiritist")
	private Set<Attendance> attendances;

	/** The last time the data about the user was updated. */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdateDate;

	/** The last time the user logged in the system. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	/** Getter for shortName. */
	public String getShortName() {
		return shortName;
	}

	/** Setter for shortName. */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** Getter for email. */
	public String getEmail() {
		return email;
	}

	/** Setter for email. */
	public void setEmail(String email) {
		this.email = email;
	}

	/** Getter for password. */
	public String getPassword() {
		return password;
	}

	/** Setter for password. */
	public void setPassword(String password) {
		this.password = password;
	}

	/** Getter for taxCode. */
	public String getTaxCode() {
		return taxCode;
	}

	/** Setter for taxCode. */
	public void setTaxCode(String taxCode) throws InvalidTaxCodeException {
		this.taxCode = checkTaxCode(taxCode);
	}

	/** Getter for address. */
	public Address getAddress() {
		return address;
	}

	/** Setter for address. */
	public void setAddress(Address address) {
		this.address = address;
	}

	/** Getter for telephones. */
	public Set<Telephone> getTelephones() {
		return telephones;
	}

	/** Setter for telephones. */
	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	/** Getter for attendances. */
	public Set<Attendance> getAttendances() {
		return attendances;
	}

	/** Setter for attendances. */
	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	/** Getter for lastUpdateDate. */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/** Setter for lastUpdateDate. */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/** Getter for lastLoginDate. */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/** Setter for lastLoginDate. */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Determines if the spiritist's registration is active (meaning they can log in) or not.
	 * 
	 * @return <code>true</code> if the spiritist is active, <code>false</code> otherwise.
	 */
	public boolean isActive() {
		return password != null;
	}

	/**
	 * Getter for a virtual bean formattedTaxCode which returns the bean taxCode formatted for display.
	 * 
	 * @return An empty string, if the spiritist has no tax code, or the formatted tax code otherwise.
	 */
	public String getFormattedTaxCode() {
		return (taxCode == null) ? "" : formatTaxCode(taxCode);
	}

	/**
	 * Verifies if the tax code is valid (well-formed), throwing an exception if it isn't. This method verifies the tax
	 * code according to the Brazilian format for tax codes (i.e., CPF or "Cadastro de Pessoas Físicas"): a CPF has 9
	 * digits plus 2 check digits that are calculated using modulo 11.
	 * 
	 * This code is based on code from an article (in Portuguese) found at DevMedia's portal:
	 * http://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
	 * 
	 * @param taxCode
	 *          The tax code to verify.
	 * @throws InvalidTaxCodeException
	 *           If the supplied tax code is not valid.
	 */
	private static String checkTaxCode(String taxCode) throws InvalidTaxCodeException {
		// Stripts the taxCode of any character that is not a digit (a number).
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < taxCode.length(); i++) {
			char c = taxCode.charAt(i);
			if (c >= '0' && c <= '9') builder.append(c);
		}

		// A valid CPF has 11 digits.
		if (builder.length() != 11) throw new InvalidTaxCodeException(taxCode);

		// Calculates the first check digit and verifies that it matches the supplied one.
		int digit = calculateCheckDigit(builder, 9);
		if (digit != builder.charAt(9) - '0') throw new InvalidTaxCodeException(taxCode);

		// Calculates the second check digit.
		digit = calculateCheckDigit(builder, 10);
		if (digit != builder.charAt(10) - '0') throw new InvalidTaxCodeException(taxCode);

		// If everything is fine, returns the tax code (digits only).
		return builder.toString();
	}

	/**
	 * Calculates the check digit of a tax code, given the index of the check digit to calculate.
	 * 
	 * @param builder
	 *          A string builder containing the digits of the tax code.
	 * @param digitIndex
	 *          The index of the check digit to calculate.
	 * @return The check digit (as an integer).
	 */
	private static int calculateCheckDigit(StringBuilder builder, int digitIndex) {
		// Sums the digits multiplied by their respective weights.
		int sum = 0, weight = digitIndex + 1, digit;
		for (int i = 0; i < digitIndex; i++, weight--) {
			digit = builder.charAt(i) - '0';
			sum += digit * weight;
		}

		// Takes the modulo of 11 to determine the digit and returns.
		digit = 11 - (sum % 11);
		if (digit > 9) digit = 0;
		return digit;
	}

	/**
	 * Formats the tax code. This method formats the tax code according to the Brazilian format for tax codes (i.e., CPF
	 * or "Cadastro de Pessoas Físicas"): separate with a '.' the nine meaninful digits in groups of 3 and separate the
	 * check digits from the remainder digits with a '-'.
	 * 
	 * @param taxCode
	 *          The tax code to format. The method accepts tax codes that are somehow formatted (have characters other
	 *          than digits), but will strip them out of the code first.
	 * @return If the tax code is invalid, returns it unchanged. Otherwise, returns a string with the formatted tax code.
	 */
	private static String formatTaxCode(String taxCode) {
		try {
			// Checks the tax code and creates a string builder out of it.
			StringBuilder builder = new StringBuilder(checkTaxCode(taxCode));

			// Add the separators where appropriate (from right to left to have easier indexes).
			builder.insert(9, '-');
			builder.insert(6, '.');
			builder.insert(3, '.');

			// Returns the formatted tax code.
			return builder.toString();
		}

		// If the tax code is invalid, return it unchanged.
		catch (InvalidTaxCodeException e) {
			return taxCode;
		}
	}

	/** @see br.com.engenhodesoftware.util.people.domain.Person#toString() */
	@Override
	public String toString() {
		return name;
	}
}
