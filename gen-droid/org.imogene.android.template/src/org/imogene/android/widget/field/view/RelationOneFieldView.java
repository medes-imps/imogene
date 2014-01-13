package org.imogene.android.widget.field.view;

import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class RelationOneFieldView extends BaseFieldView<Uri> {

	public RelationOneFieldView(Context context) {
		super(context, R.layout.ig_field_relation);
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_contact_card);
	}

	public RelationOneFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_relation);
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_contact_card);
	}

	public void setDrawable(Drawable drawable) {
		final View color = findViewById(R.id.ig_color);
		if (color != null) {
			color.setBackgroundDrawable(drawable);
		}
	}

	@Override
	public String getFieldDisplay() {
		final Uri uri = getValue();
		if (uri != null) {
			final String result;
			ImogBeanCursor cursor = (ImogBeanCursor) ImogOpenHelper.getHelper().query(uri);
			cursor.moveToFirst();
			result = cursor.getMainDisplay(getContext());
			cursor.close();
			return result;
		}
		return super.getFieldDisplay();
	}

	@Override
	protected void dispatchClick(View v) {
		final Uri uri = getValue();
		if (uri != null) {
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
		}
	}

}
