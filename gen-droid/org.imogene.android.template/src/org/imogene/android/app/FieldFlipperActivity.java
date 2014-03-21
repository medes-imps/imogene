package org.imogene.android.app;

import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldFlipper;
import org.imogene.android.widget.field.ImogBeanWizardView;
import org.imogene.android.widget.field.ImogBeanWizardView.OnFinishClickListener;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class FieldFlipperActivity extends SherlockActivity implements OnFinishClickListener {

	private ImogBeanWizardView mWizard;

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		ensureWizard();

		final int displayed = savedInstanceState.getInt("displayedChild");
		mWizard.getFieldFlipper().setDisplayedChild(displayed);
	}

	private void ensureWizard() {
		if (mWizard == null) {
			setContentView(R.layout.imog__wizard_content);
			mWizard = (ImogBeanWizardView) findViewById(R.id.imog__wizard);
			mWizard.setup();
			mWizard.setOnFinishClickListener(this);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		final int displayed = mWizard.getFieldFlipper().getDisplayedChild();
		outState.putInt("displayedChild", displayed);
	}

	public ImogBeanWizardView getImogBeanWizardView() {
		ensureWizard();
		return mWizard;
	}

	public FieldFlipper getFieldFlipper() {
		ensureWizard();
		return mWizard.getFieldFlipper();
	}

	@Override
	public void onFinishClick() {

	}

}
