package br.com.engenhodesoftware.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class with methods related to text manipulation.
 * 
 * @author Vitor Souza (vitorsouza@gmail.com)
 */
public final class TextUtils {
	/**
	 * Produces the MD5 hash for a given string. Useful for generating hashes of passwords, for example.
	 * 
	 * @param str
	 *          Any string.
	 * 
	 * @return A string containing the MD5 hash of the string given as parameter.
	 * 
	 * @throws NoSuchAlgorithmException
	 *           If the MD5 conversion algorithm can't be found in the JVM implementation.
	 */
	public static String produceMd5Hash(String str) throws NoSuchAlgorithmException {
		if (str == null)
			return null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(str.getBytes()));
		String s = hash.toString(16);
		if (s.length() % 2 != 0)
			s = "0" + s;
		return s;
	}
}
