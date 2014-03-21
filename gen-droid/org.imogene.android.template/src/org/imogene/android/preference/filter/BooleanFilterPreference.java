package org.imogene.android.preference.filter;

import org.imogene.android.common.filter.BooleanFilter;
import org.imogene.android.template.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.AttributeSet;
import android.widget.ListView;

public class BooleanFilterPreference extends FilterPreference<BooleanFilter> {

	public BooleanFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, BooleanFilter.FILTER_CREATOR);
	}

	@Override
	public CharSequence getSummary() {
		Boolean value = getFilter().getValue();
		if (value != null) {
			String[] array = getContext().getResources().getStringArray(R.array.imog__select_yes_no);
			return value.booleanValue() ? array[0] : array[1];
		}
		return getContext().getString(android.R.string.unknownName);
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
		Boolean value = getFilter().getValue();
		builder.setSingleChoiceItems(R.array.imog__select_yes_no, value != null ? (value.booleanValue() ? 0 : 1) : -1,
				null);
		builder.setNeutralButton(android.R.string.cut, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getFilter().setValue(null);
				persistFilter();
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		super.onClick(dialog, which);
		if (which == DialogInterface.BUTTON_POSITIVE) {
			int pos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
			getFilter().setValue(pos != ListView.INVALID_POSITION ? (pos == 0 ? Boolean.TRUE : Boolean.FALSE) : null);
			persistFilter();
		}
	}

}
