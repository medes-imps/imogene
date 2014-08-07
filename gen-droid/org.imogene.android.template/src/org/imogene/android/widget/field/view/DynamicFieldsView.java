package org.imogene.android.widget.field.view;

import java.util.Date;
import java.util.List;

import org.imogene.android.common.binary.Binary;
import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.template.R;
import org.imogene.android.widget.field.BaseField;
import org.imogene.android.widget.field.FieldManager;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import fr.medes.android.util.Arrays;
import fr.medes.android.util.FormatHelper;
import fr.medes.android.util.field.EnumHelper;

public class DynamicFieldsView extends BaseFieldView<List<DynamicFieldInstance>> {

	private ViewGroup group;

	public DynamicFieldsView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_dynamic_fields);
		group = (ViewGroup) findViewById(R.id.imog__dynamic_fields_list);
	}

	@Override
	public void onAttachedToHierarchy(FieldManager manager) {
		super.onAttachedToHierarchy(manager);
		for (int i = 0; i < group.getChildCount(); i++) {
			((BaseFieldView<?>) group.getChildAt(i)).onAttachedToHierarchy(manager);
		}
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < group.getChildCount(); i++) {
			if (!((BaseFieldView<?>) group.getChildAt(i)).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected void updateView() {
		super.updateView();
		group.removeAllViews();
		List<DynamicFieldInstance> instances = getValue();
		if (instances == null || instances.isEmpty()) {
			return;
		}
		for (DynamicFieldInstance instance : instances) {
			DynamicFieldTemplate template = instance.getFieldTemplate();
			BaseFieldView<?> view = obtainBaseView(template);
			view.setTitle(template.getFieldName());
			initField(template, view, instance.getFieldValue());
			group.addView(view);
		}
	}

	private BaseFieldView<?> obtainBaseView(DynamicFieldTemplate template) {
		switch (template.getFieldType()) {
		case BCODE:
		case STRING:
		case TEXT:
			return new TextFieldView(getContext());
		case BIN:
			return new BinaryFieldView(getContext());
		case BOOL:
			return new BooleanFieldView(getContext());
		case DATE:
			return new DateFieldView(getContext());
		case ENUM_M:
			EnumMultipleFieldView enumm = new EnumMultipleFieldView(getContext());
			enumm.setItems(template.getParameters().split(EnumHelper.separator));
			return enumm;
		case ENUM_S:
			EnumSingleFieldView enums = new EnumSingleFieldView(getContext());
			enums.setItems(template.getParameters().split(EnumHelper.separator));
			return enums;
		case FLOAT:
			return new FloatFieldView(getContext());
		case GEO:
			return new GeoFieldView(getContext());
		case IMG:
			return new PhotoFieldView(getContext());
		case INT:
			return new IntegerFieldView(getContext());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static void initField(DynamicFieldTemplate template, BaseField<?> field, String value) {
		switch (template.getFieldType()) {
		case BCODE:
		case STRING:
		case TEXT:
			((BaseField<String>) field).setValue(value);
			break;
		case BIN:
		case IMG:
			if (value != null) {
				Uri uri = Uri.parse(value);
				Binary binary = ImogOpenHelper.fromUri(uri);
				((BaseField<Binary>) field).setValue(binary);
			} else {
				((BaseField<Binary>) field).setValue(null);
			}
			break;
		case BOOL:
			((BaseField<Boolean>) field).setValue(FormatHelper.toBoolean(value));
			break;
		case DATE:
			((BaseField<Date>) field).setValue(FormatHelper.toDate(value));
			break;
		case ENUM_M:
			((BaseField<boolean[]>) field).setValue(EnumHelper.parse(
					template.getParameters().split(EnumHelper.separator), value));
			break;
		case ENUM_S:
			((BaseField<Integer>) field).setValue(Arrays.find(template.getParameters().split(EnumHelper.separator),
					value));
			break;
		case FLOAT:
			((BaseField<Float>) field).setValue(FormatHelper.toFloat(value));
			break;
		case GEO:
			((BaseField<Location>) field).setValue(FormatHelper.readLocation(value));
			break;
		case INT:
			((BaseField<Integer>) field).setValue(FormatHelper.toInteger(value));
			break;
		}
	}

}
