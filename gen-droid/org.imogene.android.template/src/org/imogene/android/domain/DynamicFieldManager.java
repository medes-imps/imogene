package org.imogene.android.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.imogene.android.database.sqlite.DynamicFieldTemplateCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;
import org.imogene.android.util.Arrays;
import org.imogene.android.util.FormatHelper;
import org.imogene.android.util.content.ContentUrisUtils;
import org.imogene.android.util.field.EnumHelper;
import org.imogene.android.widget.field.BaseField;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.edit.BarcodeFieldEdit;
import org.imogene.android.widget.field.edit.BaseFieldEdit;
import org.imogene.android.widget.field.edit.BaseFieldEdit.OnValueChangeListener;
import org.imogene.android.widget.field.edit.BinaryFieldEdit;
import org.imogene.android.widget.field.edit.BooleanFieldEdit;
import org.imogene.android.widget.field.edit.DateFieldEdit;
import org.imogene.android.widget.field.edit.EnumMultipleFieldEdit;
import org.imogene.android.widget.field.edit.EnumSingleFieldEdit;
import org.imogene.android.widget.field.edit.FloatFieldEdit;
import org.imogene.android.widget.field.edit.GeoFieldEdit;
import org.imogene.android.widget.field.edit.IntegerFieldEdit;
import org.imogene.android.widget.field.edit.PhotoFieldEdit;
import org.imogene.android.widget.field.edit.TextFieldEdit;
import org.imogene.android.widget.field.view.BaseFieldView;
import org.imogene.android.widget.field.view.BinaryFieldView;
import org.imogene.android.widget.field.view.BooleanFieldView;
import org.imogene.android.widget.field.view.DateFieldView;
import org.imogene.android.widget.field.view.EnumMultipleFieldView;
import org.imogene.android.widget.field.view.EnumSingleFieldView;
import org.imogene.android.widget.field.view.FloatFieldView;
import org.imogene.android.widget.field.view.GeoFieldView;
import org.imogene.android.widget.field.view.IntegerFieldView;
import org.imogene.android.widget.field.view.PhotoFieldView;
import org.imogene.android.widget.field.view.TextFieldView;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.view.ViewGroup;

public class DynamicFieldManager implements OnValueChangeListener {

	private Context context;

	private Map<String, DynamicFieldTemplate> templatesMap = new HashMap<String, DynamicFieldTemplate>();
	private Map<String, DynamicFieldInstance> instancesMap = new HashMap<String, DynamicFieldInstance>();
	private Map<String, String> templatesToInstancesMap = new HashMap<String, String>();

	public DynamicFieldManager(Context context) {
		this.context = context;
	}

	public void loadTemplates(String formType) {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(DynamicFieldTemplate.Columns.CONTENT_URI);
		Where where = builder.where();
		where.and(
				where.eq(DynamicFieldTemplate.Columns.FORMTYPE, formType),
				where.eq(DynamicFieldTemplate.Columns.ISACTIVATED, "true"),
				where.or(where.eq(DynamicFieldTemplate.Columns.ALLUSERS, "true"),
						where.eq(DynamicFieldTemplate.Columns.TEMPLATECREATOR, PreferenceHelper.getCurrentLogin(context)).and()
								.eq(DynamicFieldTemplate.Columns.ISDEFAULT, "true")));
		DynamicFieldTemplateCursor cursor = (DynamicFieldTemplateCursor) builder.query();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			DynamicFieldTemplate template = (DynamicFieldTemplate) cursor.newBean();
			if (!templatesMap.containsKey(template.getId())) {
				templatesMap.put(template.getId(), template);
			}
		}
	}

	public void loadtemplate(Uri uri) {
		String id = uri.getLastPathSegment();
		if (!templatesMap.containsKey(id)) {
			DynamicFieldTemplate template = new DynamicFieldTemplate(uri);
			templatesMap.put(id, template);
		}
	}

	public void loadInstance(DynamicFieldInstance instance) {
		loadtemplate(instance.getFieldTemplate());
		String tmpId = UUID.randomUUID().toString();
		instancesMap.put(tmpId, instance);
		templatesToInstancesMap.put(instance.getFieldTemplate().getLastPathSegment(), tmpId);
	}

	public void loadInstancesUris(List<Uri> instances) {
		if (instances == null) {
			return;
		}
		for (Uri uri : instances) {
			loadInstance(new DynamicFieldInstance(uri));
		}
	}

	public void loadInstances(Collection<DynamicFieldInstance> instances) {
		if (instances == null) {
			return;
		}
		for (DynamicFieldInstance instance : instances) {
			loadInstance(instance);
		}
	}

	public Collection<DynamicFieldInstance> getInstances() {
		return instancesMap.values();
	}

	public boolean hasTemplate() {
		return !templatesMap.isEmpty();
	}

	public void clear() {
		templatesMap.clear();
		instancesMap.clear();
		templatesToInstancesMap.clear();
	}

	public void saveDynamicFields(ImogEntity bean) {
		List<Uri> dynamicFields = null;
		for (DynamicFieldInstance instance : instancesMap.values()) {
			DynamicFieldTemplate template = templatesMap.get(instance.getFieldTemplate().getLastPathSegment());
			switch (template.getFieldType()) {
			case BIN:
			case IMG:
				if (instance.getFieldValue() != null) {
					Uri uri = Uri.parse(instance.getFieldValue());
					if (uri.getScheme() != null) {
						Uri bin = BinaryFile.toBinary(context, uri);
						instance.setFieldValue(bin != null ? bin.getLastPathSegment() : null);
					}
				}
				break;
			}
			instance.prepareForSave(context);
			Uri uri = instance.saveOrUpdate(context);
			if (uri != null) {
				if (dynamicFields == null) {
					dynamicFields = new Vector<Uri>();
				}
				dynamicFields.add(uri);
			}
		}
		bean.setDynamicFieldValues(dynamicFields);
	}

	public void attachViewsForEdition(ViewGroup group, FieldManager manager) {
		for (DynamicFieldTemplate template : templatesMap.values()) {
			String value = template.getDefaultValue();

			DynamicFieldInstance instance = instancesMap.get(templatesToInstancesMap.get(template.getId()));
			if (instance != null) {
				value = instance.getFieldValue();
			}

			BaseFieldEdit<?> view = obtainEditView(template);
			view.setTag(R.id.ig_dynamic_template, template.getId());
			view.setTitle(template.getFieldName());
			view.setEmptyText(R.string.ig_select);
			view.setRequired(template.getRequiredValue());
			view.setReadOnly(false);
			view.setOnValueChangeListener(this);
			view.onAttachedToHierarchy(manager);
			initField(template, view, value);
			group.addView(view);
		}
	}

	public BaseFieldEdit<?> obtainEditView(DynamicFieldTemplate template) {
		switch (template.getFieldType()) {
		case BCODE:
			return new BarcodeFieldEdit(context);
		case BIN:
			return new BinaryFieldEdit(context);
		case BOOL:
			return new BooleanFieldEdit(context);
		case DATE:
			DateFieldEdit dateFieldEdit = new DateFieldEdit(context);
			dateFieldEdit.setMin(FormatHelper.readDate(template.getMinimumValue()));
			dateFieldEdit.setMax(FormatHelper.readDate(template.getMaximumValue()));
			return dateFieldEdit;
		case ENUM_M:
			EnumMultipleFieldEdit multi = new EnumMultipleFieldEdit(context);
			multi.setItems(template.getParameters().split(EnumHelper.separator));
			return multi;
		case ENUM_S:
			EnumSingleFieldEdit single = new EnumSingleFieldEdit(context);
			single.setItems(template.getParameters().split(EnumHelper.separator));
			return single;
		case FLOAT:
			FloatFieldEdit floatt = new FloatFieldEdit(context);
			floatt.setMin(FormatHelper.toFloat(template.getMinimumValue()));
			floatt.setMax(FormatHelper.toFloat(template.getMaximumValue()));
			return floatt;
		case GEO:
			return new GeoFieldEdit(context);
		case IMG:
			return new PhotoFieldEdit(context);
		case INT:
			IntegerFieldEdit intt = new IntegerFieldEdit(context);
			intt.setMin(FormatHelper.toInteger(template.getMinimumValue()));
			intt.setMax(FormatHelper.toInteger(template.getMaximumValue()));
			return intt;
		case STRING:
			TextFieldEdit string = new TextFieldEdit(context);
			string.setStringType(TextFieldEdit.TYPE_TEXT);
			return string;
		case TEXT:
			TextFieldEdit text = new TextFieldEdit(context);
			text.setStringType(TextFieldEdit.TYPE_TEXT_LONG);
			return text;
		}
		return null;
	}

	public void attachViewsForView(ViewGroup group, FieldManager manager) {
		for (DynamicFieldInstance instance : instancesMap.values()) {
			DynamicFieldTemplate template = templatesMap.get(instance.getFieldTemplate().getLastPathSegment());

			BaseFieldView<?> view = obtainBaseView(template);
			view.setTitle(template.getFieldName());
			view.onAttachedToHierarchy(manager);
			initField(template, view, instance.getFieldValue());
			group.addView(view);
		}
	}

	public BaseFieldView<?> obtainBaseView(DynamicFieldTemplate template) {
		switch (template.getFieldType()) {
		case BCODE:
		case STRING:
		case TEXT:
			return new TextFieldView(context);
		case BIN:
			return new BinaryFieldView(context);
		case BOOL:
			return new BooleanFieldView(context);
		case DATE:
			return new DateFieldView(context);
		case ENUM_M:
			EnumMultipleFieldView enumm = new EnumMultipleFieldView(context);
			enumm.setItems(template.getParameters().split(EnumHelper.separator));
			return enumm;
		case ENUM_S:
			EnumSingleFieldView enums = new EnumSingleFieldView(context);
			enums.setItems(template.getParameters().split(EnumHelper.separator));
			return enums;
		case FLOAT:
			return new FloatFieldView(context);
		case GEO:
			return new GeoFieldView(context);
		case IMG:
			return new PhotoFieldView(context);
		case INT:
			return new IntegerFieldView(context);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void initField(DynamicFieldTemplate template, BaseField<?> field, String value) {
		switch (template.getFieldType()) {
		case BCODE:
		case STRING:
		case TEXT:
			((BaseField<String>) field).init(value);
			break;
		case BIN:
		case IMG:
			if (value != null) {
				Uri uri = Uri.parse(value);
				if (uri.getScheme() == null) {
					uri = ContentUrisUtils.withAppendedId(Binary.Columns.CONTENT_URI, value);
				}
				((BaseField<Uri>) field).init(uri);
			} else {
				((BaseField<Uri>) field).init(null);
			}
			break;
		case BOOL:
			((BaseField<Boolean>) field).init(FormatHelper.toBoolean(value));
			break;
		case DATE:
			((BaseField<Date>) field).init(FormatHelper.toDate(value));
			break;
		case ENUM_M:
			((BaseField<boolean[]>) field).init(EnumHelper.parse(template.getParameters().split(EnumHelper.separator), value));
			break;
		case ENUM_S:
			((BaseField<Integer>) field).init(Arrays.find(template.getParameters().split(EnumHelper.separator), value));
			break;
		case FLOAT:
			((BaseField<Float>) field).init(FormatHelper.toFloat(value));
			break;
		case GEO:
			((BaseField<Location>) field).init(FormatHelper.readLocation(value));
			break;
		case INT:
			((BaseField<Integer>) field).init(FormatHelper.toInteger(value));
			break;
		}
	}

	@Override
	public void onValueChange(BaseFieldEdit<?> field) {
		String templateId = (String) field.getTag(R.id.ig_dynamic_template);

		DynamicFieldTemplate template = templatesMap.get(templateId);
		DynamicFieldInstance instance = instancesMap.get(templatesToInstancesMap.get(templateId));
		if (instance == null) {
			instance = new DynamicFieldInstance();
			instance.setFieldTemplate(ContentUrisUtils.withAppendedId(DynamicFieldTemplate.Columns.CONTENT_URI, templateId));
			loadInstance(instance);
		}
		switch (template.getFieldType()) {
		case BCODE:
			instance.setFieldValue(((BarcodeFieldEdit) field).getValue());
			break;
		case IMG:
		case BIN:
			Uri uri = ((BinaryFieldEdit) field).getValue();
			if (uri != null) {
				instance.setFieldValue(BinaryFile.isBinary(uri) ? uri.getLastPathSegment() : uri.toString());
			} else {
				instance.setFieldValue(null);
			}
			break;
		case BOOL:
			Boolean bool = ((BooleanFieldEdit) field).getValue();
			instance.setFieldValue(bool != null ? bool.toString() : null);
			break;
		case DATE:
			Date date = ((DateFieldEdit) field).getValue();
			instance.setFieldValue(date != null ? String.valueOf(date.getTime()) : null);
			break;
		case ENUM_M:
			instance.setFieldValue(EnumHelper.convert(template.getParameters().split(EnumHelper.separator),
					((EnumMultipleFieldEdit) field).getValue()));
			break;
		case ENUM_S:
			Integer senum = ((EnumSingleFieldEdit) field).getValue();
			instance.setFieldValue(senum != null && senum != -1 ? template.getParameters().split(EnumHelper.separator)[senum]
					: null);
			break;
		case FLOAT:
			Float floatt = ((FloatFieldEdit) field).getValue();
			instance.setFieldValue(floatt != null ? floatt.toString() : null);
			break;
		case GEO:
			Location location = ((GeoFieldEdit) field).getValue();
			if (location != null) {
				instance.setFieldValue(location.getLatitude() + ";" + location.getLongitude());
			} else {
				instance.setFieldValue(null);
			}
			break;
		case INT:
			Integer intt = ((IntegerFieldEdit) field).getValue();
			instance.setFieldValue(intt != null ? intt.toString() : null);
			break;
		case STRING:
		case TEXT:
			instance.setFieldValue(((TextFieldEdit) field).getValue());
			break;
		}
	}

}
