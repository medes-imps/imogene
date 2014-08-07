package org.imogene.android.widget.field.edit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.database.sqlite.DynamicFieldTemplateCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;
import org.imogene.android.widget.ErrorAdapter.ErrorEntry;
import org.imogene.android.widget.field.BaseField;
import org.imogene.android.widget.field.BaseField.OnValueChangeListener;
import org.imogene.android.widget.field.FieldManager;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.util.Arrays;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.EnumHelper;

public class DynamicFieldsEdit extends BaseFieldEdit<List<DynamicFieldInstance>> implements OnValueChangeListener {

	private ViewGroup group;
	private List<DynamicFieldTemplate> templates;
	private String formType;

	public DynamicFieldsEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_dynamic_fields);
		group = (ViewGroup) findViewById(R.id.imog__dynamic_fields_list);
	}

	@Override
	public void onAttachedToHierarchy(FieldManager manager) {
		super.onAttachedToHierarchy(manager);
		for (int i = 0; i < group.getChildCount(); i++) {
			((BaseFieldEdit<?>) group.getChildAt(i)).onAttachedToHierarchy(manager);
		}
	}

	@Override
	protected void updateView() {
		super.updateView();
		List<DynamicFieldInstance> value = getValue();
		if (value == null || value.isEmpty() || group.getChildCount() == 0) {
			return;
		}
		for (DynamicFieldInstance instance : value) {
			for (int i = 0; i < group.getChildCount(); i++) {
				BaseFieldEdit<?> view = (BaseFieldEdit<?>) group.getChildAt(i);
				DynamicFieldTemplate template = (DynamicFieldTemplate) view.getTag(R.id.imog__dynamic_template);
				if (instance.getFieldTemplate().getId().equals(template.getId())) {
					setValueInternal(template, view, instance.getFieldValue(), false);
				}
			}
		}
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Override
	public boolean isEmpty() {
		return templates == null || templates.isEmpty();
	}

	@Override
	public boolean isValid() {
		for (int i = 0; i < group.getChildCount(); i++) {
			if (!((BaseFieldEdit<?>) group.getChildAt(i)).isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onValueChange(BaseField<?> field) {
		DynamicFieldTemplate template = (DynamicFieldTemplate) field.getTag(R.id.imog__dynamic_template);
		DynamicFieldInstance instance = null;

		List<DynamicFieldInstance> instances = getValue();
		if (instances == null) {
			instances = new ArrayList<DynamicFieldInstance>();
		}
		for (DynamicFieldInstance i : instances) {
			if (i.getFieldTemplate().getId().equals(template.getId())) {
				instance = i;
				break;
			}
		}
		if (instance == null) {
			instance = new DynamicFieldInstance();
			instance.setFieldTemplate(template);
			instances.add(instance);
		}
		switch (template.getFieldType()) {
		case BCODE:
			instance.setFieldValue(((BarcodeFieldEdit) field).getValue());
			break;
		case IMG:
		case BIN:
			Binary binary = ((BinaryFieldEdit) field).getValue();
			if (binary != null) {
				instance.setFieldValue(binary.getId());
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
		setValueInternal(instances, true);
	}

	public void updateErrorEntries(List<ErrorEntry> errors, int tag) {
		for (int i = 0; i < group.getChildCount(); i++) {
			errors.add(((BaseFieldEdit<?>) group.getChildAt(i)).getErrorEntry(tag));
		}
	}

	public void buildViews() {
		loadTemplates();
		if (templates == null || templates.isEmpty()) {
			return;
		}
		group.removeAllViews();
		for (DynamicFieldTemplate template : templates) {
			String value = template.getDefaultValue();

			List<DynamicFieldInstance> instances = getValue();
			if (value != null) {
				for (DynamicFieldInstance instance : instances) {
					if (instance.getFieldTemplate().getId().equals(template.getId())) {
						value = instance.getFieldValue();
					}
				}
			}

			BaseFieldEdit<?> view = obtainEditView(template);
			view.setTag(R.id.imog__dynamic_template, template);
			view.setTitle(template.getFieldName());
			view.setEmptyText(R.string.imog__select);
			view.setRequired(template.getRequiredValue());
			view.setReadOnly(false);
			view.setOnValueChangedListener(this);
			setValueInternal(template, view, value, false);
			group.addView(view);
		}
	}

	private void loadTemplates() {
		ImogOpenHelper helper = ImogOpenHelper.getHelper();
		QueryBuilder builder = helper.queryBuilder(DynamicFieldTemplate.Columns.CONTENT_URI);
		Where where = builder.where();
		where.and(
				where.eq(DynamicFieldTemplate.Columns.FORMTYPE, formType),
				where.eq(DynamicFieldTemplate.Columns.ISACTIVATED, "true"),
				where.or(
						where.eq(DynamicFieldTemplate.Columns.ALLUSERS, "true"),
						where.eq(DynamicFieldTemplate.Columns.TEMPLATECREATOR,
								Preferences.getPreferences(getContext()).getCurrentLogin()).and()
								.eq(DynamicFieldTemplate.Columns.ISDEFAULT, "true")));
		DynamicFieldTemplateCursor cursor = (DynamicFieldTemplateCursor) builder.query();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			DynamicFieldTemplate template = (DynamicFieldTemplate) cursor.newBean();
			if (templates == null) {
				templates = new ArrayList<DynamicFieldTemplate>();
			}
			templates.add(template);
		}
	}

	private BaseFieldEdit<?> obtainEditView(DynamicFieldTemplate template) {
		switch (template.getFieldType()) {
		case BCODE:
			return new BarcodeFieldEdit(getContext());
		case BIN:
			return new BinaryFieldEdit(getContext());
		case BOOL:
			return new BooleanFieldEdit(getContext());
		case DATE:
			DateFieldEdit dateFieldEdit = new DateFieldEdit(getContext());
			dateFieldEdit.setMin(FormatHelper.readDate(template.getMinimumValue()));
			dateFieldEdit.setMax(FormatHelper.readDate(template.getMaximumValue()));
			return dateFieldEdit;
		case ENUM_M:
			EnumMultipleFieldEdit multi = new EnumMultipleFieldEdit(getContext());
			multi.setItems(template.getParameters().split(EnumHelper.separator));
			return multi;
		case ENUM_S:
			EnumSingleFieldEdit single = new EnumSingleFieldEdit(getContext());
			single.setItems(template.getParameters().split(EnumHelper.separator));
			return single;
		case FLOAT:
			FloatFieldEdit floatt = new FloatFieldEdit(getContext());
			floatt.setMin(FormatHelper.toFloat(template.getMinimumValue()));
			floatt.setMax(FormatHelper.toFloat(template.getMaximumValue()));
			return floatt;
		case GEO:
			return new GeoFieldEdit(getContext());
		case IMG:
			return new PhotoFieldEdit(getContext());
		case INT:
			IntegerFieldEdit intt = new IntegerFieldEdit(getContext());
			intt.setMin(FormatHelper.toInteger(template.getMinimumValue()));
			intt.setMax(FormatHelper.toInteger(template.getMaximumValue()));
			return intt;
		case STRING:
			TextFieldEdit string = new TextFieldEdit(getContext());
			string.setStringType(TextFieldEdit.TYPE_TEXT);
			return string;
		case TEXT:
			TextFieldEdit text = new TextFieldEdit(getContext());
			text.setStringType(TextFieldEdit.TYPE_TEXT_LONG);
			return text;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void setValueInternal(DynamicFieldTemplate template, BaseField<?> field, String value, boolean notifyChange) {
		switch (template.getFieldType()) {
		case BCODE:
		case STRING:
		case TEXT:
			((BaseField<String>) field).setValueInternal(value, notifyChange);
			;
			break;
		case BIN:
		case IMG:
			if (value != null) {
				Uri uri = Uri.parse(value);
				Binary binary = ImogOpenHelper.fromUri(uri);
				((BaseFieldEdit<Binary>) field).setValueInternal(binary, notifyChange);
			} else {
				((BaseFieldEdit<Binary>) field).setValueInternal(null, notifyChange);
			}
			break;
		case BOOL:
			((BaseFieldEdit<Boolean>) field).setValueInternal(FormatHelper.toBoolean(value), notifyChange);
			break;
		case DATE:
			((BaseFieldEdit<Date>) field).setValueInternal(FormatHelper.toDate(value), notifyChange);
			break;
		case ENUM_M:
			((BaseFieldEdit<boolean[]>) field).setValueInternal(
					EnumHelper.parse(template.getParameters().split(EnumHelper.separator), value), notifyChange);
			break;
		case ENUM_S:
			((BaseFieldEdit<Integer>) field).setValueInternal(
					Arrays.find(template.getParameters().split(EnumHelper.separator), value), notifyChange);
			break;
		case FLOAT:
			((BaseFieldEdit<Float>) field).setValueInternal(FormatHelper.toFloat(value), notifyChange);
			break;
		case GEO:
			((BaseFieldEdit<Location>) field).setValueInternal(FormatHelper.readLocation(value), notifyChange);
			break;
		case INT:
			((BaseFieldEdit<Integer>) field).setValueInternal(FormatHelper.toInteger(value), notifyChange);
			break;
		}
	}
}
