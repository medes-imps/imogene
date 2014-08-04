package org.imogene.android;

import java.io.File;

import android.os.Environment;

public final class Constants {

	public static final boolean DEBUG = true;

	public static final String AUTHORITY = "org.imogene.android.%realProjectName%.entity";

	public static interface Paths {
		public static final File PATH_BACKUP = new File(Environment.getExternalStorageDirectory(),
				".%realProjectName%/backup");
		public static final File PATH_BINARIES = new File(Environment.getExternalStorageDirectory(),
				".%realProjectName%/binaries");
		public static final File PATH_SYNCHRO = new File(Environment.getExternalStorageDirectory(),
				".%realProjectName%/synchro");
		public static final File PATH_TEMPORARY = new File(Environment.getExternalStorageDirectory(),
				".%realProjectName%/temporary");
		public static final File PATH_MEDIA = new File(Environment.getExternalStorageDirectory(),
				"%realProjectName%/media");
	}

	public static interface Intents {
		public static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
		public static final String ACTION_SECRET_CODE = "android.provider.Telephony.SECRET_CODE";
		public static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	}

	public static interface Databases {
		public static final String DATABASE_NAME = "imogene.db";
	}

	public static interface Extras {

		/**
		 * The base or saved entity to pass to the entity editor
		 */
		public static final String EXTRA_ENTITY = "org.imogene.android.extra.ENTITY";

		/**
		 * Where clause to display an list of entities
		 */
		public static final String EXTRA_WHERE = "org.imogene.android.extra.WHERE";

		/**
		 * Where arguments to display an list of entities
		 */
		public static final String EXTRA_ARGS = "org.imogene.android.extra.ARGS";

		/**
		 * Had these extra to indicate that the list should allow multiple selection
		 */
		public static final String EXTRA_MULTIPLE = "org.imogene.android.extra.MULTIPLE";

		/**
		 * A {@link ArrayList<String>} of preselected entities identifiers to pass to a multiple selection list
		 */
		public static final String EXTRA_SELECTED = "org.imogene.android.extra.SELECTED";
		/**
		 * The key to sort the entities by
		 */
		public static final String EXTRA_SORT_KEY = "org.imogene.android.extra.SORT_KEY";

		/**
		 * The sort order true for ascending, false otherwise.
		 */
		public static final String EXTRA_ASCENDING = "org.imogene.android.extra.ASCENDING";

		/**
		 * Extra to use when using preference screen with light theme to precise the next preference file to display
		 */
		public static final String EXTRA_FILENAME = "org.imogene.android.extra.FILENAME";
	}

	public static interface Categories {

		public static final String CATEGORY_CLASSIC = "org.imogene.android.category.CLASSIC";
		public static final String CATEGORY_WIZARD = "org.imogene.android.category.WIZARD";

		public static final String CATERGORY_IMAGE_CAPTURE = "org.imogene.android.diabsat.category.IMAGE_CATPURE";
		public static final String CATERGORY_VIDEO_CAPTURE = "org.imogene.android.diabsat.category.VIDEO_CATPURE";

	}

	public static interface Packages {
		public static final String PACKAGE_ZXING = "com.google.zxing.client.android";
	}

}
