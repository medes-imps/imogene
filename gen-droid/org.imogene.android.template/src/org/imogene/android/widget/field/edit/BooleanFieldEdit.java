package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.AttributeSet;
import android.view.View;

public class BooleanFieldEdit extends BaseFieldEdit<Boolean> implements OnClickListener {

	public BooleanFieldEdit(Context context) {
		super(context, R.layout.imog__field_default);
	}

	public BooleanFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_default);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		setOnClickListener(readOnly ? null : this);
		setOnLongClickListener(readOnly ? null : this);
	}

	@Override
	public String getFieldDisplay() {
		final Boolean bool = getValue();
		if (bool != null) {
			String[] array = getResources().getStringArray(R.array.imog__select_yes_no);
			return bool.booleanValue() ? array[0] : array[1];
		} else {
			return getEmptyText();
		}
	}

	@Override
	public boolean matchesDependencyValue(String value) {
		final Boolean b = getValue();
		return b != null ? b.booleanValue() == Boolean.parseBoolean(value) : false;
	}

	@Override
	protected void dispatchClick(View v) {
		showDialog(null);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		final Boolean init = getValue();
		builder.setSingleChoiceItems(R.array.imog__select_yes_no, init != null ? (init ? 0 : 1) : -1, this);
		builder.setNeutralButton(android.R.string.cut, this);
		builder.setNegativeButton(android.R.string.cancel, null);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case 0:
			setValueInternal(true, true);
			dialog.dismiss();
			break;
		case 1:
			setValueInternal(false, true);
			dialog.dismiss();
			break;
		case Dialog.BUTTON_NEUTRAL:
			setValueInternal(null, true);
			break;
		}
	}

}
