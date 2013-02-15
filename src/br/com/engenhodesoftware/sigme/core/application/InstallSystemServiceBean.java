package br.com.engenhodesoftware.sigme.core.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.engenhodesoftware.sigme.core.domain.InstitutionType;
import br.com.engenhodesoftware.sigme.core.domain.Regional;
import br.com.engenhodesoftware.sigme.core.domain.Spiritist;
import br.com.engenhodesoftware.sigme.core.persistence.InstitutionTypeDAO;
import br.com.engenhodesoftware.sigme.core.persistence.RegionalDAO;
import br.com.engenhodesoftware.sigme.core.persistence.SpiritistDAO;
import br.com.engenhodesoftware.sigme.secretariat.application.SecretariatInformation;
import br.com.engenhodesoftware.util.ResourceUtil;
import br.com.engenhodesoftware.util.TextUtils;
import br.com.engenhodesoftware.util.people.domain.Address;
import br.com.engenhodesoftware.util.people.domain.City;
import br.com.engenhodesoftware.util.people.domain.ContactType;
import br.com.engenhodesoftware.util.people.domain.State;
import br.com.engenhodesoftware.util.people.persistence.CityDAO;
import br.com.engenhodesoftware.util.people.persistence.ContactTypeDAO;
import br.com.engenhodesoftware.util.people.persistence.StateDAO;

/**
 * Stateless session bean implementing the "Install System" use case component. See the implemented interface
 * documentation for details.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemService
 */
@Stateless
public class InstallSystemServiceBean implements InstallSystemService {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** The path (in the EJB module) for the data files that are used to initialize the database. */
	private static final String INIT_DATA_PATH = "/META-INF/init-data/";

	/** The logger. */
	private static final Logger logger = Logger.getLogger(InstallSystemServiceBean.class.getCanonicalName());

	/** The DAO for Spiritist objects. */
	@EJB
	private SpiritistDAO spiritistDAO;

	/** The DAO for State objects. */
	@EJB
	private StateDAO stateDAO;

	/** The DAO for City objects. */
	@EJB
	private CityDAO cityDAO;

	/** The DAO for Regional objects. */
	@EJB
	private RegionalDAO regionalDAO;

	/** The DAO for InstitutionType objects. */
	@EJB
	private InstitutionTypeDAO institutionTypeDAO;

	/** The DAO for ContactType objects. */
	@EJB
	private ContactTypeDAO contactTypeDAO;

	/** Global information about the application. */
	@EJB
	private CoreInformation coreInformation;
	
	/** Information bean for the Secretariat module. */
	@EJB
	private SecretariatInformation secretariatInformation;

	/** Map of states used to initialize the cities. */
	private static Map<String, State> states = new HashMap<String, State>();

	/** Map of cities used to initialize the regionals. */
	private static Map<String, City> cities = new HashMap<String, City>();

	/** @see br.com.engenhodesoftware.sigme.core.application.InstallSystemService#installSystem(br.com.engenhodesoftware.sigme.core.domain.Spiritist) */
	@Override
	public void installSystem(Spiritist admin) throws SystemInstallFailedException {
		logger.log(Level.FINER, "Installing system...");

		try {
			// Encodes the password.
			admin.setPassword(TextUtils.produceMd5Hash(admin.getPassword()));
			admin.setAddress(new Address());

			// Register the last update date / login date.
			Date now = new Date(System.currentTimeMillis());
			admin.setLastUpdateDate(now);
			admin.setLastLoginDate(now);
			logger.log(Level.FINE, "Admin's last update date and last login date have been set as: {0}", now);

			// Initializes the database.
			logger.log(Level.FINER, "Initializing the database with data stored in \"{0}\"...", INIT_DATA_PATH);
			initState();
			initCity();
			initRegional();
			initInstitutionType();
			initContactType();
			logger.log(Level.FINE, "Database initialization finished.");

			// Saves the administrator.
			logger.log(Level.FINER, "Persisting data...\n\t- Short name = {0}\n\t- Last update date = {1}\n\t- Last login date = {2}", new Object[] { admin.getShortName(), admin.getLastUpdateDate(), admin.getLastLoginDate() });
			spiritistDAO.save(admin);
			logger.log(Level.FINE, "The administrator has been saved: {0} ({1})", new Object[] { admin.getName(), admin.getEmail() });
			
			// Installs other modules.
			logger.log(Level.FINER, "Executing install procedure in other modules...");
			secretariatInformation.installModule();
			logger.log(Level.FINE, "Install procedures from Sigme modules executed successfully.");

			// Signals that the system is installed.
			logger.log(Level.FINER, "Signaling the system as being installed...");
			coreInformation.setSystemInstalled(true);
		}
		catch (NoSuchAlgorithmException e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Could not find MD5 algorithm for password encription!", e);
			throw new SystemInstallFailedException(e);
		}
		catch (Exception e) {
			// Logs and rethrows the exception for the controller to display the error to the user.
			logger.log(Level.SEVERE, "Exception during system installation!", e);
			throw new SystemInstallFailedException(e);
		}
	}

	/**
	 * Initializes the states in the database, i.e., create the first State objects from the data in a file and save these
	 * objects in the database. The file <code>State.data</code> in the initialization data path should contain the
	 * information that should be registered (e.g. the states of Brazil).
	 * 
	 * @throws FileNotFoundException
	 *           If the data file is not found.
	 * @throws URISyntaxException
	 *           If the URI used to locate the data file has any kind of problems.
	 * 
	 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemServiceBean#INIT_DATA_PATH
	 */
	private void initState() throws FileNotFoundException, URISyntaxException {
		// Loads the file with the data from the classpath.
		File dataFile = ResourceUtil.getResourceAsFile(INIT_DATA_PATH + "State.data");
		logger.log(Level.FINER, "Adding states to the database from file \"{0}\"...", dataFile.getAbsolutePath());

		// Each line is a state, format is acronym | name.
		int count = 0;
		Scanner scanner = new Scanner(dataFile);
		while (scanner.hasNextLine()) {
			Scanner lineScanner = new Scanner(scanner.nextLine());
			lineScanner.useDelimiter("(\\s*\\|\\s*)");
			while (lineScanner.hasNext()) {
				State state = new State();
				state.setAcronym(lineScanner.next());
				state.setName(lineScanner.next());

				logger.log(Level.FINEST, "Storing state: {0}", state);
				stateDAO.save(state);
				count++;

				// Also save the state in a map for the cities to use.
				states.put(state.getAcronym(), state);
			}
			lineScanner.close();
		}

		logger.log(Level.FINE, "Stored {0} states in the database", count);
		scanner.close();
	}

	/**
	 * Initializes the cities in the database, i.e., create the first City objects from the data in a file and save these
	 * objects in the database. The directory <code>City</code> in the initialization data path should contain a file for
	 * each state in the state data file (using the state's acronym) containing the data on the cities of the referred
	 * state (e.g. file ES.txt contains the cities of Espírito Santo, RJ.txt contains the cities of Rio de Janeiro, and so
	 * forth).
	 * 
	 * @throws FileNotFoundException
	 *           If the data file is not found.
	 * @throws URISyntaxException
	 *           If the URI used to locate the data file has any kind of problems.
	 *           
	 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemServiceBean#INIT_DATA_PATH
	 */
	private void initCity() throws FileNotFoundException, URISyntaxException {
		File dataDir = ResourceUtil.getResourceAsFile(INIT_DATA_PATH + "City");
		logger.log(Level.FINER, "Reading city data files from directory \"{0}\"...", dataDir.getAbsolutePath());

		// Each file contains the cities of a state. The name of the file is the acronym of the state plus extension.
		int totalCount = 0;
		File[] dataFiles = dataDir.listFiles();
		for (File dataFile : dataFiles) {
			logger.log(Level.FINER, "Adding cities to the database from file \"{0}\"...", dataFile.getAbsolutePath());

			// Retrieves the state from the map built when the states were initialized using the acronyms.
			String dataFileName = dataFile.getName();
			String stateAcronym = dataFileName.substring(0, dataFileName.lastIndexOf('.'));
			State state = states.get(stateAcronym);
			logger.log(Level.FINEST, "Extracted acronym {0} from datafile {1} and retrieved the state from the map: {2}", new Object[] { stateAcronym, dataFileName, state });

			// Each line is a city name.
			int count = 0;
			Scanner scanner = new Scanner(dataFile);
			while (scanner.hasNextLine()) {
				City city = new City();
				city.setName(scanner.nextLine());
				city.setState(state);

				// Also save the city in a map for the regional to use.
				cities.put(city.getName() + ", " + state.getAcronym(), city);

				logger.log(Level.FINEST, "Storing city: {0}", city);
				cityDAO.save(city);
				count++;
				totalCount++;
			}

			logger.log(Level.FINE, "Stored {0} cities associated with state {1}", new Object[] { count, state });
			scanner.close();
		}

		logger.log(Level.FINE, "In total, stored {0} cities in the database", totalCount);
	}

	/**
	 * Initializes the regionals in the database, i.e., create the first Regional objects from the data in a file and save
	 * these objects in the database. The file <code>Regional.data</code> in the initialization data path should contain
	 * the information that should be registered (e.g. the regionals of the Espírito Santo state, according to the
	 * Espírito Santo Spiritist Federation).
	 * 
	 * @throws FileNotFoundException
	 *           If the data file is not found.
	 * @throws URISyntaxException
	 *           If the URI used to locate the data file has any kind of problems.
	 *           
	 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemServiceBean#INIT_DATA_PATH
	 */
	private void initRegional() throws FileNotFoundException, URISyntaxException {
		// Loads the file with the data from the classpath.
		File dataFile = ResourceUtil.getResourceAsFile(INIT_DATA_PATH + "Regional.data");
		logger.log(Level.FINER, "Adding regionals to the database from file \"{0}\"...", dataFile.getAbsolutePath());

		// Each two lines is a regional, first line has regional number and name (separated by "|"), second has cities
		// separated by "|".
		int count = 0;
		Scanner scanner = new Scanner(dataFile);
		while (scanner.hasNextLine()) {
			// First line has regional number | regional name.
			Regional regional = new Regional();
			Scanner lineScanner = new Scanner(scanner.nextLine());
			lineScanner.useDelimiter("(\\s*\\|\\s*)");
			regional.setNumber(lineScanner.nextInt());
			regional.setName(lineScanner.next());
			lineScanner.close();
			Set<City> regionalCities = new HashSet<City>();

			// Read cities that belong to the regional.
			int cityCount = 0;
			lineScanner = new Scanner(scanner.nextLine());
			lineScanner.useDelimiter("(\\s*\\|\\s*)");
			while (lineScanner.hasNext()) {
				String cityName = lineScanner.next();
				City city = cities.get(cityName);
				if (city == null) {
					logger.log(Level.WARNING, "City not found while trying to associate it to regional \"{0}\": \"{1}\"", new Object[] { regional, cityName });
				}
				else {
					regionalCities.add(city);
					logger.log(Level.FINEST, "City \"{0}\" was added to the set of cities of regional \"{1}\"", new Object[] { city, regional });
					cityCount++;
				}
			}
			lineScanner.close();

			regional.setCities(regionalCities);
			logger.log(Level.FINEST, "Storing regional: {0} ({1} associated city(ies))", new Object[] { regional, cityCount });
			regionalDAO.save(regional);
			count++;
		}

		logger.log(Level.FINE, "Stored {0} regionals in the database", count);
		scanner.close();
	}

	/**
	 * Initializes the institution types in the database, i.e., create the first InstitutionType objects from the data in
	 * a file and save these objects in the database. The file <code>InstitutionType.data</code> in the initialization
	 * data path should contain the information that should be registered (e.g. Spiritist Institution, Non-spiritist
	 * Institution, Spiritist Federation, etc.).
	 * 
	 * @throws FileNotFoundException
	 *           If the data file is not found.
	 * @throws URISyntaxException
	 *           If the URI used to locate the data file has any kind of problems.
	 *           
	 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemServiceBean#INIT_DATA_PATH
	 */
	private void initInstitutionType() throws FileNotFoundException, URISyntaxException {
		// Loads the file with the data from the classpath.
		File dataFile = ResourceUtil.getResourceAsFile(INIT_DATA_PATH + "InstitutionType.data");
		logger.log(Level.FINE, "Adding institution types to the database from file: {0}", dataFile.getAbsolutePath());

		// Each line is a institution type, if the institutions of this type are part of a regional is defined by the
		// true/false value in the same line, separated by |.
		int count = 0;
		Scanner scanner = new Scanner(dataFile);
		while (scanner.hasNextLine()) {
			Scanner lineScanner = new Scanner(scanner.nextLine());
			lineScanner.useDelimiter("(\\s*\\|\\s*)");

			InstitutionType type = new InstitutionType();
			type.setType(lineScanner.next());
			type.setPartOfRegional(Boolean.valueOf(lineScanner.next()));
			lineScanner.close();

			logger.log(Level.FINEST, "Storing institution type: {0}", type);
			institutionTypeDAO.save(type);
			count++;
		}

		logger.log(Level.FINE, "Stored {0} institution types in the database", count);
		scanner.close();
	}

	/**
	 * Initializes the contact types in the database, i.e., create the first ContactType objects from the data in a file
	 * and save these objects in the database. The file <code>ContactType.data</code> in the initialization data path
	 * should contain the information that should be registered (e.g. home phone, work phone, cell phone, etc.).
	 * 
	 * @throws FileNotFoundException
	 *           If the data file is not found.
	 * @throws URISyntaxException
	 *           If the URI used to locate the data file has any kind of problems.
	 *           
	 * @see br.com.engenhodesoftware.sigme.core.application.InstallSystemServiceBean#INIT_DATA_PATH
	 */
	private void initContactType() throws FileNotFoundException, URISyntaxException {
		// Loads the file with the data from the classpath.
		File dataFile = ResourceUtil.getResourceAsFile(INIT_DATA_PATH + "ContactType.data");
		logger.log(Level.FINE, "Adding contact types to the database from file: {0}", dataFile.getAbsolutePath());

		// Each line is a contact type.
		int count = 0;
		Scanner scanner = new Scanner(dataFile);
		while (scanner.hasNextLine()) {
			ContactType type = new ContactType();
			type.setType(scanner.nextLine());

			logger.log(Level.FINEST, "Storing contact type: {0}", type);
			contactTypeDAO.save(type);
			count++;
		}

		logger.log(Level.FINE, "Stored {0} contact types in the database", count);
		scanner.close();
	}
}
