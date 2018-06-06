/**
 * 
 */
package com.telappoint.adminresv.utils;

import java.util.List;

/**
 * @author Rajin
 * 
 */
public class StringUtils {

	public static final String COMMA = ",";

	/**
	 * return concatinated string of from List
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <T> String concatWithSeparator(List<T> list, String separator) {
		StringBuilder idStr = new StringBuilder();
		for (T t : list) {
			if (idStr.length() > 0) {
				idStr.append(separator);
				idStr.append(t);
			} else {
				idStr.append(t);
			}
		}
		return idStr.toString();
	}

	/**
	 * Returns true if string is empty or null
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.trim().length() == 0;
	}

	/**
	 * Returns true if string is not empty and not null
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNotEmpty(String input) {
		return !isEmpty(input);
	}
}
