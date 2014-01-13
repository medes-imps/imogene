package org.imogene.android.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains static methods which operate on arrays.
 * 
 * @author MEDES-IMPS
 * 
 */
public class Arrays {

	/**
	 * Creates a default array of integers of the given size containing the
	 * values {@code 0..length-1}.
	 * 
	 * @param length The length of array.
	 * @return The array of integers.
	 */
	public static int[] defaultIntArray(int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; i++) {
			result[i] = i;
		}
		return result;
	}

	/**
	 * Searches the given array of integers for the specified integer and
	 * returns the index of the first occurrence.
	 * 
	 * @param array The array search.
	 * @param value The integer to search for.
	 * @return The index of the first occurrence of the integer or -1 if the
	 *         integer was not found.
	 */
	public static int find(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches the given array for the specified object and returns the index
	 * of the first occurrence.
	 * 
	 * @param array The array to search.
	 * @param object The object to search for.
	 * @return The index of the first occurrence of the object or -1 if the
	 *         object was not found.
	 */
	public static <T> int find(T[] array, T object) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(object)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Interchange the elements at the specified locations.
	 * 
	 * @param array The array to modify.
	 * @param replace The location of the first element.
	 * @param by The position of the second element.
	 */
	public static <T> void replace(T[] array, int replace, int by) {
		T tmp = array[replace];
		array[replace] = array[by];
		array[by] = tmp;
	}

	/**
	 * Convert any {@link List} into an {@link ArrayList} containing the same
	 * elements.
	 * 
	 * @param list The list to convert.
	 * @return The converted list or {@code null}.
	 */
	public static final <T> ArrayList<T> asArrayList(List<T> list) {
		if (list == null) {
			return null;
		}
		if (list instanceof ArrayList) {
			return (ArrayList<T>) list;
		}
		ArrayList<T> result = new ArrayList<T>();
		result.addAll(list);
		return result;
	}

}
