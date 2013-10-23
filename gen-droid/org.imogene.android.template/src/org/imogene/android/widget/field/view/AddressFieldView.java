package org.imogene.android.widget.field.view;

import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class AddressFieldView extends BaseFieldView<String> implements OnQuickActionClickListener {

	private QuickActionBar mBar;
	
	public AddressFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_default);
		setOnClickListener(this);
		setIconId(android.R.drawable.ic_dialog_map);
	}
	
	@Override
	protected void dispatchClick(View v) {
		if (mBar == null) {
			final Context context = getContext();
			mBar = new QuickActionBar(context) {{
				addQuickAction(new QuickAction(context, R.drawable.maps_navto, R.string.maps_navto));
				addQuickAction(new QuickAction(context, R.drawable.maps_map, R.string.maps_show_on_map));
				
				setOnQuickActionClickListener(AddressFieldView.this);
			}};
		}
		
		mBar.show(this);
	}
	
	@Override
	public void onQuickActionClicked(QuickActionWidget widget, int position) {
		switch (position) {
		case 0:
			Intent navto = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + getValue()));
			getContext().startActivity(navto);
			return;
		case 1:
			Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + getValue()));
			getContext().startActivity(map);
			return;
		default:
			break;
		}
	}
	
	@Override
	public boolean isEmpty() {
		return TextUtils.isEmpty(getValue());
	}
	
	@Override
	protected String getFieldDisplay() {
		return getValue();
	}

}
