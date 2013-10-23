package org.imogene.android.widget.field.view;

import org.imogene.android.domain.LocalizedText;
import org.imogene.android.template.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class LocalizedTextFieldView extends BaseFieldView<LocalizedText> {

	private final ViewFlipper mViewFlipper;
	private final View mLeftView;
	private final View mRightView;
	
	public LocalizedTextFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_view_localized);
		setClickable(false);
		mViewFlipper = (ViewFlipper) findViewById(R.id.ig_flipper);
		mLeftView = findViewById(R.id.ig_left);
		mRightView = findViewById(R.id.ig_right);
    	mLeftView.setOnClickListener(this);
    	mRightView.setOnClickListener(this);
	}
	
	@Override
	public void setValue(LocalizedText value) {
		super.setValue(value);
		init();
	}
	
	private void init() {
		mViewFlipper.removeAllViews();

		LocalizedText value = getValue();
		if (value == null) {
			return;
		}
		
		String[] locales = getResources().getStringArray(R.array.languages_iso);
		String[] display = getResources().getStringArray(R.array.languages_display);
		
		int size = 0;
		for (int i = 0; i < locales.length; i++) {
			String localized = value.getValue(locales[i]);
			if (!TextUtils.isEmpty(localized)) {
				View v = inflate(getContext(), R.layout.ig_localized_text_viewer, null);
				((TextView) v.findViewById(R.id.ig_locale)).setText(display[i]);
				((TextView) v.findViewById(R.id.ig_localized)).setText(localized);
				v.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				mViewFlipper.addView(v);
				size++;
			}
		}
		
        if (size > 1) {
        	mLeftView.setVisibility(View.VISIBLE);
        	mRightView.setVisibility(View.VISIBLE);
        } else {
        	mLeftView.setVisibility(View.GONE);
        	mRightView.setVisibility(View.GONE);
        }
	}
	
	@Override
	public boolean isEmpty() {
		LocalizedText value = getValue();
		return value != null ? value.isEmpty() : true;
	}
	
	@Override
	protected String getFieldDisplay() {
		return null;
	}
	
	@Override
	public boolean matchesDependencyValue(String dependencyValue) {
		LocalizedText value = getValue();
		return value != null ? value.matches(dependencyValue) : false;
	}
	
	@Override
	protected void dispatchClick(View v) {
		switch (v.getId()) {
		case R.id.ig_left:
			mViewFlipper.showNext();
			break;
		case R.id.ig_right:
			mViewFlipper.showPrevious();
			break;
		}
	}
	
}
