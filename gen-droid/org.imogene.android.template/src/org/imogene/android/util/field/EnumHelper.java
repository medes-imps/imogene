package org.imogene.android.util.field;

import org.imogene.android.util.Arrays;
import org.imogene.android.util.FormatHelper;

import android.content.Context;
import android.content.res.Resources;

/**
 * Contains static methods to manipulate enumeration field values.
 * 
 * @author MEDES-IMPS
 * 
 */
public class EnumHelper {

	/**
	 * The separator character for enumerate fields.
	 */
	public static final char separatorChar = ';';

	/**
	 * The separator for enumerate fields.
	 * 
	 * @see {@link #separatorChar}
	 */
	public static final String separator = String.valueOf(separatorChar);

	/**
	 * Give a string representation of a boolean array representing checked
	 * integers values in an integer array. The respective selected integer
	 * values are concatenated into a string.
	 * 
	 * @param items The possible integer values.
	 * @param values Specifies which items are checked. It should be null in
	 *            which case no items are checked. If non null it must be
	 *            exactly the same length as the array of integers.
	 * @return A concatenation of selected integer values or {@code "-1"} in
	 *         case no items were checked.
	 */
	public static String convert(int[] items, boolean[] values) {
		if (values == null) {
			return "-1";
		}
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < values.length; i++) {
			if (values[i]) {
				if (first) {
					first = false;
				} else {
					builder.append(separatorChar);
				}
				builder.append(String.valueOf(items[i]));
			}
		}
		if (first) {
			return "-1";
		}
		return builder.toString();
	}

	/**
	 * Give a string representation of a boolean array representing checked
	 * integers values in the integer array associated with a particular
	 * resource ID. The respective selected integer values are concatenated into
	 * a string.
	 * 
	 * @param context The current context of the application.
	 * @param itemsId The resource identifier of the integer array. The value 0
	 *            is an invalid identifier
	 * @param values Specifies which items are checked.
	 * @return A concatenation of selected integer values or {@code "-1"} in
	 *         case no items were checked.
	 * @see {@link #convert(int[], boolean[])}
	 */
	public static String convert(Context context, int itemsId, boolean[] values) {
		return convert(context.getResources().getIntArray(itemsId), values);
	}

	/**
	 * Give a string representation of a boolean array representing checked
	 * string values in an integer array. The respective selected string values
	 * are concatenated into a string.
	 * 
	 * @param items The possible string values.
	 * @param values Specifies which items are checked. It should be null in
	 *            which case no items are checked. If non null it must be
	 *            exactly the same length as the array of items.
	 * @return A concatenation of selected string values or {@code null} in case
	 *         no items were checked.
	 */
	public static String convert(String[] items, boolean[] values) {
		if (values == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < values.length; i++) {
			if (values[i]) {
				if (first) {
					first = false;
				} else {
					builder.append(separatorChar);
				}
				builder.append(items[i]);
			}
		}
		if (first) {
			return null;
		} else {
			return builder.toString();
		}
	}

	/**
	 * Convert a string representing a list of selected integers from an integer
	 * array to an array of checked items.
	 * 
	 * @param items The possible integer values.
	 * @param str The selected integers string representation.
	 * @return The checked items.
	 */
	public static boolean[] parse(int[] items, String str) {
		boolean[] b = new boolean[items.length];
		if (str != null) {
			for (String substr : str.split(separator)) {
				Integer i = FormatHelper.toInteger(substr);
				if (i != null) {
					int position = Arrays.find(items, i);
					if (position != -1) {
						b[position] = true;
					}
				}
			}
		}
		return b;
	}

	/**
	 * Convert a string representing a list of selected integers from an integer
	 * array to an array of checked items.
	 * 
	 * @param context The current context of the application.
	 * @param itemsId The resource identifier of the integer array. The value 0
	 *            is an invalid identifier
	 * @param str The selected integers string representation.
	 * @return The checked items.
	 */
	public static boolean[] parse(Context context, int itemsId, String str) {
		return parse(context.getResources().getIntArray(itemsId), str);
	}

	/**
	 * Convert a string representing a list of selected strings from an string
	 * array to an array of checked items.
	 * 
	 * @param items The possible string values.
	 * @param str The selected string values representation.
	 * @return The checked items.
	 */
	public static boolean[] parse(String[] items, String str) {
		boolean[] b = new boolean[items.length];
		if (str != null) {
			for (String substr : str.split(separator)) {
				int position = Arrays.find(items, substr);
				if (position != -1) {
					b[position] = true;
				}
			}
		}
		return b;
	}

	/**
	 * Gives the string representation of selected element in a single selection
	 * enumeration.
	 * 
	 * @param items The string representation of the elements.
	 * @param itemsValues The mapping array between position and stored values.
	 * @param value The selected value.
	 * @return The string representation of the selected value.
	 */
	public static String displayEnumSingle(String[] items, int[] itemsValues, int value) {
		return items[Arrays.find(itemsValues, value)].toString();
	}

	/**
	 * Gives the string representation of selected element in a single selection
	 * enumeration.
	 * 
	 * @param context The current context of the application.
	 * @param itemsId The resource identifier of the elements string
	 *            representation array.
	 * @param itemsValuesId The resource identifier of the mapping array.
	 * @param value The selected value.
	 * @return The string representation of the selected value.
	 * @see {@link #displayEnumSingle(String[], int[], int)}
	 */
	public static String displayEnumSingle(Context context, int itemsId, int itemsValuesId, int value) {
		final Resources r = context.getResources();
		final String[] items = r.getStringArray(itemsId);
		final int[] itemsValues = r.getIntArray(itemsValuesId);
		return displayEnumSingle(items, itemsValues, value);
	}

	/**
	 * Gives the string representation of the checked elements in a multiple
	 * selection enumeration.
	 * 
	 * @param items The string representation of the elements.
	 * @param value The checked items.
	 * @return The string representation of the checked items.
	 */
	public static String displayEnumMulti(String[] items, boolean[] value) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < items.length; i++) {
			if (value[i]) {
				if (first) {
					first = false;
				} else {
					builder.append(" ; ");
				}
				builder.append(items[i]);
			}
		}
		return builder.toString();
	}

	/**
	 * Gives the string representation of the checked elements in a multiple
	 * selection enumeration.
	 * 
	 * @param context The current context of the application.
	 * @param itemsId The resource identifier of the elements string
	 *            representation array.
	 * @param value The checked items.
	 * @return The string representation of the checked items.
	 * @see {@link #displayEnumMulti(String[], boolean[])}
	 */
	public static String displayEnumMulti(Context context, int itemsId, boolean[] value) {
		return displayEnumMulti(context.getResources().getStringArray(itemsId), value);
	}

}
