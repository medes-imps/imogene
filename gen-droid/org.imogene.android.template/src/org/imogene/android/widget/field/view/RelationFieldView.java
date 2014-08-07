package org.imogene.android.widget.field.view;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

public class RelationFieldView<T> extends BaseFieldView<T> {

	protected int displayRes;
	protected Uri contentUri;
	protected Drawable drawable;

	public RelationFieldView(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
		displayRes = 0;
		setOnClickListener(this);
		setIconId(android.R.drawable.sym_contact_card);
	}

	public RelationFieldView(Context context, AttributeSet attrs) {
		this(context, attrs, R.layout.imog__field_relation);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationField, 0, 0);
		displayRes = a.getResourceId(R.styleable.RelationField_display, R.string.imog__numberOfEntities);
		a.recycle();
	}

	public void setContentUri(Uri uri) {
		contentUri = uri;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
		final View color = findViewById(R.id.imog__color);
		if (color != null) {
			color.setBackgroundDrawable(drawable);
		}
	}

}
