package org.imogene.android.widget.field.view;

import java.util.List;

import org.imogene.android.common.dynamicfields.DynamicFieldInstance;
import org.imogene.android.common.dynamicfields.DynamicFieldTemplate;
import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.edit.DynamicFieldsEdit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
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
	protected void onValueChange() {
		group.removeAllViews();
		List<DynamicFieldInstance> instances = getValue();
		if (instances == null || instances.isEmpty()) {
			return;
		}
		for (DynamicFieldInstance instance : instances) {
			DynamicFieldTemplate template = instance.getFieldTemplate();
			BaseFieldView<?> view = obtainBaseView(template);
			view.setTitle(template.getFieldName());
			DynamicFieldsEdit.initField(template, view, instance.getFieldValue());
			group.addView(view);
		}
		super.onValueChange();
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

}
