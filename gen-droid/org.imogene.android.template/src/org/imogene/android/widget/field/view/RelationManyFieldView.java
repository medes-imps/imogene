package org.imogene.android.widget.field.view;

import java.util.List;

import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.template.R;
import org.imogene.android.util.content.IntentUtils;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class RelationManyFieldView extends BaseFieldView<List<Uri>> {
	
	private final int displayId;
	
	private Uri contentUri;

	public RelationManyFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_relation);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationField, 0, 0);
		displayId = a.getResourceId(R.styleable.RelationField_igDisplay, 0);
		a.recycle();
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_contact_card);
	}
	
	public void setContentUri(Uri uri) {
		contentUri = uri;
	}
	
	public void setDrawable(Drawable drawable) {
		final View color = findViewById(R.id.ig_color);
		if (color != null) {
			color.setBackgroundDrawable(drawable);
		}
	}

	@Override
	public boolean isEmpty() {
		final List<Uri> list = getValue();
		return list == null || list.size() == 0;
	}

	@Override
	public String getFieldDisplay() {
		final List<Uri> uris = getValue();
		if (uris != null && !uris.isEmpty()) {
			int size = uris.size();
			return getResources().getQuantityString(displayId, size, size);
		}
		return super.getFieldDisplay();
	}
	
	@Override
	protected void dispatchClick(View v) {
		final List<Uri> list = getValue();
		
		if (list == null || list.size() == 0) {
			return;
		}
		
		final int size = list.size();
		if (size == 1) {
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, list.get(0)));
		} else {
			Intent intent = new Intent(Intent.ACTION_VIEW, contentUri);
			Object[] ids = new String[size];
			for (int i = 0; i < size; i++) {
				ids[i] = list.get(i).getLastPathSegment();
			}
			Where where = new Where();
			where.in(ImogBean.Columns._ID, ids);
			IntentUtils.putWhereExtras(intent, where);
			getContext().startActivity(intent);
		}
	}

}
