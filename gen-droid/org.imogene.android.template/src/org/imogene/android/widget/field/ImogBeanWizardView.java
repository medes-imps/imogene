package org.imogene.android.widget.field;

import java.util.List;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldFlipper.Controller;
import org.imogene.android.widget.field.edit.BaseFieldEdit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImogBeanWizardView extends RelativeLayout implements OnClickListener, Controller {

	public interface OnFinishClickListener {
		public void onFinishClick();
	}

	private OnFinishClickListener mListener;

	private FieldFlipper mFlipper;

	private View mNext;
	private View mPrevious;
	private View mFinish;

	private TextView mConstraints;

	public ImogBeanWizardView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setup() {
		mFlipper = (FieldFlipper) findViewById(R.id.imog__flipper);

		mNext = findViewById(R.id.imog__next_field);
		mPrevious = findViewById(R.id.imog__previous_field);
		mFinish = findViewById(R.id.imog__finish);

		mConstraints = (TextView) findViewById(R.id.imog__constraints);

		mNext.setOnClickListener(this);
		mPrevious.setOnClickListener(this);
		mFinish.setOnClickListener(this);

		mFlipper.setController(this);
	}

	public void setOnFinishClickListener(OnFinishClickListener listener) {
		mListener = listener;
	}

	public FieldFlipper getFieldFlipper() {
		return mFlipper;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imog__next_field:
			mFlipper.showNext();
			break;
		case R.id.imog__previous_field:
			mFlipper.showPrevious();
			break;
		case R.id.imog__finish:
			if (mListener != null) {
				mListener.onFinishClick();
			}
			break;
		}
	}

	@Override
	public void onFieldChanged() {
		final BaseFieldEdit<?> displayed = mFlipper.getCurrentField();
		mPrevious.setVisibility(mFlipper.hasPrevious() ? View.VISIBLE : View.GONE);
		mNext.setEnabled(displayed.isValid());
		mFinish.setEnabled(displayed.isValid());
		final boolean hasNext = mFlipper.hasNext();
		mNext.setVisibility(hasNext ? View.VISIBLE : View.GONE);
		mFinish.setVisibility(hasNext ? View.GONE : View.VISIBLE);
		List<String> messages = displayed.getErrorEntry(0).getMessages();
		if (messages != null && !messages.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			for (String message : messages) {
				if (first) {
					first = false;
				} else {
					builder.append("\n\n");
				}
				builder.append("\u2022 ");
				builder.append(message);
				mConstraints.setText(builder.toString());
			}
		} else {
			mConstraints.setText(null);
		}
	}

}
