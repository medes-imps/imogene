package org.imogene.android.widget.field.edit;

import java.util.Date;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.BaseField.DialogFactory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class DatesFieldEdit extends BaseFieldEdit<Date> implements DialogFactory {

	private Date min;
	private Date max;

	public DatesFieldEdit(Context context) {
		super(context, R.layout.imog__field_default);
		setDialogFactory(this);
	}

	public DatesFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
		setDialogFactory(this);
	}

	public void setMin(Date min) {
		this.min = min;
	}

	public Date getMin() {
		return min;
	}

	public void setMax(Date max) {
		this.max = max;
	}

	public Date getMax() {
		return max;
	}

	@Override
	public boolean isValid() {
		final Date value = getValue();
		if (value == null) {
			return !isRequired();
		}
		if (min != null && value.before(min)) {
			return false;
		}
		if (max != null && value.after(max)) {
			return false;
		}
		return true;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		setOnClickListener(readOnly ? null : this);
		setOnLongClickListener(readOnly ? null : this);
	}

	@Override
	protected void dispatchClick(View v) {
		showDialog(null);
	}

}
