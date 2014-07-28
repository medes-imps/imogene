package org.imogene.android.widget.field.view;

import java.text.MessageFormat;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.template.R;
import org.imogene.android.util.IntentUtils;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import fr.medes.android.database.sqlite.stmt.Where;

public class RelationManyFieldView extends BaseFieldView<List<Uri>> {

	private final int displayRes;

	private Uri contentUri;

	public RelationManyFieldView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.imog__field_relation);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationField, 0, 0);
		displayRes = a.getResourceId(R.styleable.RelationField_display, R.string.imog__numberOfEntities);
		a.recycle();
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_contact_card);
	}

	public void setContentUri(Uri uri) {
		contentUri = uri;
	}

	public void setDrawable(Drawable drawable) {
		final View color = findViewById(R.id.imog__color);
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
			String fmt = getResources().getString(displayRes);
			return MessageFormat.format(fmt, size);
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
			startActivity(new Intent(Intent.ACTION_VIEW, list.get(0)));
		} else {
			Intent intent = new Intent(Intent.ACTION_VIEW, contentUri);
			Object[] ids = new String[size];
			for (int i = 0; i < size; i++) {
				ids[i] = list.get(i).getLastPathSegment();
			}
			Where where = new Where();
			where.in(ImogBean.Columns._ID, ids);
			IntentUtils.putWhereExtras(intent, where);
			startActivity(intent);
		}
	}

}
