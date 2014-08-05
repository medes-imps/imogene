package org.imogene.android.common.entity;

import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.database.ImogEntityCursor;
import org.imogene.android.xml.converters.CollectionConverter;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlConverter;

public abstract class ImogEntityImpl extends ImogBeanImpl implements ImogEntity {

	public ImogEntityImpl(ImogEntityCursor<? extends ImogEntity> cursor) {
		super(cursor);
		dynamicFieldValues = cursor.getDynamicFieldValues();
	}

	public ImogEntityImpl(Parcel in) {
		super(in);
	}

	public ImogEntityImpl(Bundle bundle) {
		super(bundle);
	}

	public ImogEntityImpl() {
	}

	@XmlAlias("dynamicFieldValues")
	@XmlConverter(CollectionConverter.class)
	private List<DynamicFieldInstance> dynamicFieldValues;

	@Override
	public void setDynamicFieldValues(List<DynamicFieldInstance> dynamicFieldValues) {
		this.dynamicFieldValues = dynamicFieldValues;
	}

	@Override
	public List<DynamicFieldInstance> getDynamicFieldValues() {
		return dynamicFieldValues;
	}

	@Override
	protected void prepareForSave(Context context, String beanType) {
		super.prepareForSave(context, beanType);
		if (dynamicFieldValues != null) {
			for (DynamicFieldInstance instance : dynamicFieldValues) {
				instance.prepareForSave(context);
			}
		}
	}

	@Override
	protected void postCommit(Context context) {
		super.postCommit(context);
		ContentValues v = new ContentValues();
		v.putNull(DynamicFieldInstance.Columns.FORMINSTANCE);
		context.getContentResolver().update(DynamicFieldInstance.Columns.CONTENT_URI, v,
				DynamicFieldInstance.Columns.FORMINSTANCE + "= ?", new String[] { getId() });
		if (dynamicFieldValues != null) {
			for (DynamicFieldInstance dynamicFieldInstance : dynamicFieldValues) {
				dynamicFieldInstance.setFormInstance(this);
				dynamicFieldInstance.saveOrUpdate(context);
			}
		}
	}

}
