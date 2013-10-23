package org.imogene.admin.client.i18n;
import com.google.gwt.core.client.GWT;

public class AdminNLS {

	private static AdminTranslations constants = (AdminTranslations) GWT
			.create(AdminTranslations.class);

	public static AdminTranslations constants() {
		return constants;
	}

}
