package org.imogene.android.common.dynamicfields;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.DynamicFieldTemplateCursor;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.dynamicfields.DynamicFieldTemplate")
public class DynamicFieldTemplate extends ImogBeanImpl {

	public static enum Type {
		STRING("string"), TEXT("text"), INT("int"), FLOAT("float"), DATE("date"), BOOL("bool"), ENUM_S("enum_s"), ENUM_M(
				"enum_m"), BIN("bin"), IMG("img"), GEO("geo"), BCODE("bcode");

		public final String value;

		private Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Type parseType(String value) {
			for (Type type : Type.values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type value: " + value);
		}

	}

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "dynamicfieldtemplate";
		public static final String BEAN_TYPE = "DFT";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String FIELDNAME = "fieldName";
		public static final String FIELDTYPE = "fieldType";
		public static final String PARAMETERS = "parameters";
		public static final String FORMTYPE = "formType";
		public static final String TEMPLATECREATOR = "templateCreator";
		public static final String DESCRIPTION = "description";
		public static final String REASON = "reason";
		public static final String ISDEFAULT = "isDefault";
		public static final String REQUIREDVALUE = "requiredValue";
		public static final String FIELDPOSITION = "fieldPosition";
		public static final String MINIMUMVALUE = "minimumValue";
		public static final String MAXIMUMVALUE = "maximumValue";
		public static final String DEFAULTVALUE = "defaultValue";
		public static final String ALLUSERS = "allUsers";
		public static final String ISACTIVATED = "isActivated";

	}

	@XmlAlias("fieldName")
	private String fieldName;
	@XmlAlias("fieldType")
	private Type fieldType;
	@XmlAlias("parameters")
	private String parameters;
	@XmlAlias("formType")
	private String formType;
	@XmlAlias("templateCreator")
	private ImogBean templateCreator;
	@XmlAlias("description")
	private String description;
	@XmlAlias("reason")
	private String reason;
	@XmlAlias("isDefault")
	private Boolean isDefault = true;
	@XmlAlias("requiredValue")
	private Boolean requiredValue = false;
	@XmlAlias("fieldPosition")
	private Integer fieldPosition;
	@XmlAlias("minimumValue")
	private String minimumValue;
	@XmlAlias("maximumValue")
	private String maximumValue;
	@XmlAlias("defaultValue")
	private String defaultValue;
	@XmlAlias("allUsers")
	private Boolean allUsers = false;
	@XmlAlias("isActivated")
	private Boolean isActivated = true;

	public DynamicFieldTemplate() {
	}

	public DynamicFieldTemplate(Bundle bundle) {
		super(bundle);
		if (bundle.containsKey(Columns.TEMPLATECREATOR)) {
			templateCreator = bundle.getParcelable(Columns.TEMPLATECREATOR);
		}
	}

	public DynamicFieldTemplate(DynamicFieldTemplateCursor cursor) {
		super(cursor);
		fieldName = cursor.getFieldName();
		fieldType = cursor.getFieldType();
		parameters = cursor.getParameters();
		formType = cursor.getFormType();
		templateCreator = cursor.getTemplateCreator();
		description = cursor.getDescription();
		reason = cursor.getReason();
		isDefault = cursor.getIsDefault();
		requiredValue = cursor.getRequiredValue();
		fieldPosition = cursor.getFieldPosition();
		minimumValue = cursor.getMinimumValue();
		maximumValue = cursor.getMaximumValue();
		defaultValue = cursor.getDefaultValue();
		allUsers = cursor.getAllUsers();
		isActivated = cursor.getIsActivated();
	}

	public final String getFieldName() {
		return fieldName;
	}

	public final Type getFieldType() {
		return fieldType;
	}

	public final String getParameters() {
		return parameters;
	}

	public final String getFormType() {
		return formType;
	}

	public final ImogBean getTemplateCreator() {
		return templateCreator;
	}

	public final String getDescription() {
		return description;
	}

	public final String getReason() {
		return reason;
	}

	public final Boolean getIsDefault() {
		return isDefault;
	}

	public final Boolean getRequiredValue() {
		return requiredValue;
	}

	public final Integer getFieldPosition() {
		return fieldPosition;
	}

	public final String getMinimumValue() {
		return minimumValue;
	}

	public final String getMaximumValue() {
		return maximumValue;
	}

	public final String getDefaultValue() {
		return defaultValue;
	}

	public final Boolean getAllUsers() {
		return allUsers;
	}

	public final Boolean getIsActivated() {
		return isActivated;
	}

	public final void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public final void setFieldType(Type fieldType) {
		this.fieldType = fieldType;
	}

	public final void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public final void setFormType(String formType) {
		this.formType = formType;
	}

	public final void setTemplateCreator(ImogBean templateCreator) {
		this.templateCreator = templateCreator;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final void setReason(String reason) {
		this.reason = reason;
	}

	public final void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public final void setRequiredValue(Boolean requiredValue) {
		this.requiredValue = requiredValue;
	}

	public final void setFieldPosition(Integer fieldPosition) {
		this.fieldPosition = fieldPosition;
	}

	public final void setMinimumValue(String minimumValue) {
		this.minimumValue = minimumValue;
	}

	public final void setMaximumValue(String maximumValue) {
		this.maximumValue = maximumValue;
	}

	public final void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public final void setAllUsers(Boolean allUsers) {
		this.allUsers = allUsers;
	}

	public final void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.FIELDNAME, fieldName);
		values.put(Columns.FIELDTYPE, fieldType.getValue());
		values.put(Columns.PARAMETERS, parameters);
		values.put(Columns.FORMTYPE, formType);
		if (templateCreator != null) {
			values.put(Columns.TEMPLATECREATOR, templateCreator.getId());
		} else {
			values.putNull(Columns.TEMPLATECREATOR);
		}

		values.put(Columns.DESCRIPTION, description);
		values.put(Columns.REASON, reason);
		if (isDefault != null)
			values.put(Columns.ISDEFAULT, isDefault.toString());
		else
			values.putNull(Columns.ISDEFAULT);
		if (requiredValue != null)
			values.put(Columns.REQUIREDVALUE, requiredValue.toString());
		else
			values.putNull(Columns.REQUIREDVALUE);
		values.put(Columns.FIELDPOSITION, fieldPosition);
		values.put(Columns.MINIMUMVALUE, minimumValue);
		values.put(Columns.MAXIMUMVALUE, maximumValue);
		values.put(Columns.DEFAULTVALUE, defaultValue);
		if (allUsers != null)
			values.put(Columns.ALLUSERS, allUsers.toString());
		else
			values.putNull(Columns.ALLUSERS);
		if (isActivated != null)
			values.put(Columns.ISACTIVATED, isActivated.toString());
		else
			values.putNull(Columns.ISACTIVATED);
	}

	@Override
	public final void reset() {
		fieldName = null;
		fieldType = null;
		parameters = null;
		formType = null;
		templateCreator = null;
		description = null;
		reason = null;
		isDefault = null;
		requiredValue = null;
		fieldPosition = null;
		minimumValue = null;
		maximumValue = null;
		defaultValue = null;
		allUsers = null;
		isActivated = null;
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
