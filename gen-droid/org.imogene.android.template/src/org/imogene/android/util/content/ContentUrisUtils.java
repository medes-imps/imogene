package org.imogene.android.util.content;

import org.imogene.android.Constants;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Utility methods useful for working with content {@link android.net.Uri}s,
 * those with a "content" scheme.
 */
public class ContentUrisUtils {
	/**
	 * Converts the last path segment to a long.
	 * 
	 * <p>
	 * This supports a common convention for content URIs where an ID is stored
	 * in the last segment.
	 * 
	 * @return the the last segment or null if the path is empty
	 */
	public static String parseId(Uri contentUri) {
		if (contentUri != null) {
			return contentUri.getLastPathSegment();
		}
		return null;
	}

	/**
	 * Appends the given ID to the end of the path.
	 * 
	 * @param builder to append the ID to
	 * @param id to append
	 * 
	 * @return the given builder
	 */
	public static Uri.Builder appendId(Uri.Builder builder, String id) {
		return builder.appendEncodedPath(id);
	}

	/**
	 * Appends the given ID to the end of the path.
	 * 
	 * @param contentUri to start with
	 * @param id to append
	 * 
	 * @return a new URI with the given ID appended to the end of the path
	 */
	public static Uri withAppendedId(Uri contentUri, String id) {
		return appendId(contentUri.buildUpon(), id).build();
	}

	/**
	 * Creates an {@link Uri} whith scheme
	 * {@link ContentResolver#SCHEME_CONTENT}, authority
	 * {@link Constants#AUTHORITY} and the given path.
	 * 
	 * @param path The path to add at the end of the uri.
	 * @return The built uri, should be something like
	 *         {@code content://<authority>/<path>}.
	 */
	public static Uri buildUriForFragment(String path) {
		return new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(Constants.AUTHORITY).path(path).build();
	}
}
