package org.imogene.android.xml.converters;

import org.imogene.android.util.field.EnumHelper;

import android.content.Context;

public class EnumMultiConverter extends AbstractSingleValueConverter {
	
	private Context mContext;
	
	public EnumMultiConverter(Context context) {
		mContext = context;
	}
	
	private int arrayId;
	
	@Override
	public void setInteger(int integer) {
		arrayId = integer;
	}
	
	@Override
	public Object fromString(String str) {
		return EnumHelper.parse(mContext, arrayId, str);
	}
	
	@Override
	public String toString(Object obj) {
		return EnumHelper.convert(mContext, arrayId, ((boolean[]) obj));
	}
	
	@Override
	public boolean canConvert(Class<?> clazz) {
		return clazz.equals(boolean[].class);
	}
	
}
