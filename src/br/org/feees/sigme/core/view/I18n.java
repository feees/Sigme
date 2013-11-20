package br.org.feees.sigme.core.view;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.org.feees.sigme.core.application.CoreInformation;

/**
 * TODO: document this type.
 * 
 * @author Vitor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
public final class I18n {
	/** TODO: document this field. */
	private static final String BUNDLE_NAME = "br.org.feees.sigme.core.view.messages";

	/** TODO: document this field. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, CoreInformation.DEFAULT_LOCALE);

	/**
	 * TODO: document this method.
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * TODO: document this method.
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getFormattedString(String key, Object ... params) {
		String pattern = getString(key);
		return MessageFormat.format(pattern, params);
	}
}
