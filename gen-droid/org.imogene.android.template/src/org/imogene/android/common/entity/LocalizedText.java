package org.imogene.android.common.entity;

import java.util.Locale;
import java.util.Map;

import android.text.TextUtils;

public abstract class LocalizedText {

	public static LocalizedTextFactory sFactory;

	public static LocalizedText newInstance() {
		if (sFactory != null) {
			return sFactory.newLocalizedText();
		}
		return null;
	}

	public static void setFactory(LocalizedTextFactory factory) {
		sFactory = factory;
	}

	public abstract String getValue(String iso);

	public abstract void setValue(String iso, String value);

	public abstract boolean isEmpty();

	public abstract boolean matches(String regex);

	public abstract Map<String, String> getFieldAndValue();

	public String getLocalized() {
		String value = getValue(Locale.getDefault().getLanguage());
		if (!TextUtils.isEmpty(value)) {
			return value;
		}
		for (String v : getFieldAndValue().values()) {
			if (!TextUtils.isEmpty(v)) {
				return v;
			}
		}
		return null;
	}

	public interface LocalizedTextFactory {
		public LocalizedText newLocalizedText();
	}

}
