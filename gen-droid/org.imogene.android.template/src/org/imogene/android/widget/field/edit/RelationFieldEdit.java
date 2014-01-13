package org.imogene.android.widget.field.edit;

import java.util.ArrayList;

import org.imogene.android.Constants.Extras;
import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.template.R;
import org.imogene.android.util.Arrays;
import org.imogene.android.util.content.IntentUtils;
import org.imogene.android.widget.field.ConstraintBuilder;
import org.imogene.android.widget.field.FieldManager;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

public abstract class RelationFieldEdit<T> extends BaseFieldEdit<T> implements OnActivityResultListener {

	public static interface ExtraBuilder {
		public void onCreateExtra(Bundle bundle);
	}

	private ArrayList<ConstraintEntry> mConstraintsBuilders;
	private ArrayList<CommonFieldEntry> mCommonFields;
	private ArrayList<ExtraBuilder> mBuilders;

	protected boolean mHasReverse = false;
	protected int mDisplayId = android.R.string.unknownName;
	protected int mOppositeCardinality = -1;
	protected int mType; // 0 for main relation field; 1 for reverse relation
							// field
	protected String mOppositeRelationField;
	protected String mFieldName;
	protected String mTableName;

	protected int mRequestCode;
	protected Uri mContentUri;

	public RelationFieldEdit(Context context) {
		super(context, R.layout.ig_field_relation);
	}

	public RelationFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.ig_field_relation);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationField, 0, 0);
		mHasReverse = a.getBoolean(R.styleable.RelationField_igHasReverse, false);
		mDisplayId = a.getResourceId(R.styleable.RelationField_igDisplay, 0);
		mOppositeCardinality = a.getInt(R.styleable.RelationField_igOppositeCardinality, 0);
		mType = a.getInt(R.styleable.RelationField_igRelationType, 0);
		a.recycle();
		setOnClickListener(this);
	}

	public void setDisplayId(int displayId) {
		mDisplayId = displayId;
	}

	public void setOppositeRelationField(String oppositerelationField) {
		mOppositeRelationField = oppositerelationField;
	}

	public void setFieldName(String fieldName) {
		mFieldName = fieldName;
	}

	public void setTableName(String tableName) {
		mTableName = tableName;
	}

	@Override
	public void onAttachedToHierarchy(FieldManager manager) {
		super.onAttachedToHierarchy(manager);
		manager.registerOnActivityResultListener(this);
		mRequestCode = manager.getNextId();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		setOnClickListener(readOnly ? null : this);
		setOnLongClickListener(readOnly ? null : this);
	}

	public void setContentUri(Uri contentUri) {
		mContentUri = contentUri;
	}

	public void setDrawable(Drawable drawable) {
		final View color = findViewById(R.id.ig_color);
		if (color != null) {
			color.setBackgroundDrawable(drawable);
		}
	}

	public void registerCommonField(RelationFieldEdit<?> commonField, String commonName) {
		if (mCommonFields == null) {
			mCommonFields = new ArrayList<CommonFieldEntry>();
		}

		mCommonFields.add(new CommonFieldEntry(commonField, commonName));
	}

	public void registerExtraBuilder(ExtraBuilder builder) {
		if (mBuilders == null) {
			mBuilders = new ArrayList<ExtraBuilder>();
		}

		mBuilders.add(builder);
	}

	public void registerConstraintBuilder(ConstraintBuilder builder, String column) {
		if (mConstraintsBuilders == null) {
			mConstraintsBuilders = new ArrayList<ConstraintEntry>();
		}

		mConstraintsBuilders.add(new ConstraintEntry(builder, column));

		builder.registerConstraintDependent(this);
	}

	@Override
	protected void dispatchClick(View v) {
		final Intent intent = new Intent(Intent.ACTION_PICK, mContentUri);
		Where where = new Where();
		boolean and = false;
		Where preparedWhere = onPrepareWhere();
		if (preparedWhere != null) {
			where.clause(preparedWhere);
			and = true;
		}
		onPrepareIntent(intent);
		if (mConstraintsBuilders != null) {
			for (ConstraintEntry entry : mConstraintsBuilders) {
				Where constraintWhere = entry.first.onCreateConstraint(entry.second);
				if (constraintWhere != null) {
					if (and) {
						where.and();
					} else {
						and = true;
					}
					where.clause(constraintWhere);
				}
			}
		}
		if (and) {
			IntentUtils.putWhereExtras(intent, where);
		}
		intent.putExtra(Extras.EXTRA_ENTITY, createBundle());
		getFieldManager().getActivity().startActivityForResult(intent, mRequestCode);
	}

	protected Bundle createBundle() {
		Bundle bundle = new Bundle();
		if (mHasReverse && mOppositeCardinality == 1) {
			bundle.putParcelable(mOppositeRelationField, getFieldManager().getUri());
		}
		if (mCommonFields != null && !mCommonFields.isEmpty()) {
			for (CommonFieldEntry entry : mCommonFields) {
				if (entry.first instanceof RelationOneFieldEdit) {
					bundle.putParcelable(entry.second, ((RelationOneFieldEdit) entry.first).getValue());
				} else if (entry.first instanceof RelationManyFieldEdit) {
					bundle.putParcelableArrayList(entry.second,
							Arrays.asArrayList(((RelationManyFieldEdit) entry.first).getValue()));
				}
			}
		}
		if (mBuilders != null) {
			for (ExtraBuilder builder : mBuilders) {
				builder.onCreateExtra(bundle);
			}
		}
		return bundle;
	}

	protected void onPrepareIntent(Intent intent) {

	}

	protected Where onPrepareWhere() {
		return null;
	}

	private static final class ConstraintEntry extends Pair<ConstraintBuilder, String> {

		public ConstraintEntry(ConstraintBuilder first, String second) {
			super(first, second);
		}

	};

	private static class CommonFieldEntry extends Pair<RelationFieldEdit<?>, String> {

		public CommonFieldEntry(RelationFieldEdit<?> first, String second) {
			super(first, second);
		}

	}
}
