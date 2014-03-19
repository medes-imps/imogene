package org.imogene.android.common.dynamicfields;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.DynamicFieldInstanceCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlOmitField;

@XmlAlias("org.imogene.lib.common.dynamicfields.DynamicFieldInstance")
public class DynamicFieldInstance extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "dynamicfieldinstance";
		public static final String BEAN_TYPE = "DFI";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String FIELDTEMPLATE = "fieldTemplate";
		public static final String FIELDVALUE = "fieldValue";
		public static final String FORMINSTANCE = "formInstance";
	}

	@XmlAlias("fieldTemplate")
	private Uri fieldTemplate;
	@XmlAlias("fieldValue")
	private String fieldValue;

	/**
	 * Be careful the form instance will not be saved !!
	 */
	@XmlOmitField
	private Uri formInstance;

	public DynamicFieldInstance() {
	}

	public DynamicFieldInstance(Uri uri) {
		DynamicFieldInstanceCursor cursor = (DynamicFieldInstanceCursor) ImogOpenHelper.getHelper().query(uri);
		cursor.moveToFirst();
		init(cursor);
		cursor.close();
	}

	public DynamicFieldInstance(DynamicFieldInstanceCursor cursor) {
		init(cursor);
	}

	public DynamicFieldInstance(Bundle bundle) {
		if (bundle.containsKey(Columns.FIELDTEMPLATE)) {
			fieldTemplate = bundle.getParcelable(Columns.FIELDTEMPLATE);
		}
	}

	private void init(DynamicFieldInstanceCursor cursor) {
		super.init(cursor);
		fieldTemplate = cursor.getFieldTemplate();
		fieldValue = cursor.getFieldValue();
		formInstance = cursor.getFormInstance();
	}

	public final Uri getFieldTemplate() {
		return fieldTemplate;
	}

	public final String getFieldValue() {
		return fieldValue;
	}

	public Uri getFormInstance() {
		return formInstance;
	}

	public final void setFieldTemplate(Uri fieldTemplate) {
		this.fieldTemplate = fieldTemplate;
	}

	public final void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setFormInstance(Uri formInstance) {
		this.formInstance = formInstance;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		super.addValues(context, values);
		if (fieldTemplate != null) {
			values.put(Columns.FIELDTEMPLATE, fieldTemplate.getLastPathSegment());
		} else {
			values.putNull(Columns.FIELDTEMPLATE);
		}

		values.put(Columns.FIELDVALUE, fieldValue);
	}

	@Override
	public final void reset() {
		fieldTemplate = null;
		fieldValue = null;
		formInstance = null;
	}

	@Override
	public Uri saveOrUpdate(Context context) {
		return saveOrUpdate(context, Columns.CONTENT_URI);
	}

	@Override
	public void prepareForSave(Context context) {
		prepareForSave(context, Columns.BEAN_TYPE);
	}

}
