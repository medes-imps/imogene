package org.imogene.android.widget.field.edit;

import java.util.ArrayList;

import org.imogene.android.Constants.Extras;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.template.R;
import org.imogene.android.util.IntentUtils;
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
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.util.Arrays;

public abstract class RelationFieldEdit<T> extends BaseFieldEdit<T> implements OnActivityResultListener {

	/**
	 * Interface to define a container able to manipulate relation fields of an entity.
	 */
	public static interface RelationManager<U extends ImogBean> {

		/**
		 * Retrieve the parent {@link ImogBean} to set in the child opposite relation field.
		 * 
		 * @return The parent {@link ImogBean} if any, {@code null} otherwise
		 */
		public U getParentBean();

	}

	/**
	 * Interface to allow passing values when creating a new form from a related one. The extras contains the name of
	 * the target field and the value that must be given to the field.
	 */
	public static interface ExtraBuilder {

		/**
		 * Method called when a new form is about to be created and the values to pass as extras argument must be set.
		 * 
		 * @param bundle The bundle in which we can add extras values.
		 */
		public void onCreateExtra(Bundle bundle);
	}

	/**
	 * Interface to allow modifying the intent to be called when selecting one or more entities. This allows for
	 * instance to add extras to the intent to be used later.
	 */
	public static interface IntentBuilder {

		/**
		 * Method called when then intent is being created just before being send.
		 * 
		 * @param intent The intent to build.
		 */
		public void onCreateIntent(Intent intent);
	}

	/**
	 * Interface used to manage hierarchical fields. This will allow filtering a list given the previously set value.
	 */
	public interface ConstraintBuilder {

		/**
		 * Create a where clause to filter the list.
		 * 
		 * @return The where clause to use.
		 */
		public Where onCreateConstraint();

	}

	private ArrayList<ConstraintBuilder> mConstraintsBuilders;
	private ArrayList<CommonFieldEntry> mCommonFields;
	private ArrayList<ExtraBuilder> mExtraBuilders;
	private ArrayList<IntentBuilder> mIntentBuilders;

	protected RelationManager<?> mRelationManager;
	protected boolean mHasReverse = false;
	protected int mDisplayRes = R.string.imog__numberOfEntities;
	protected int mOppositeCardinality = -1;
	protected int mType; // 0 for main relation field; 1 for reverse relation field
	protected String mOppositeRelationField;
	protected String mFieldName;
	protected String mTableName;
	protected Drawable mDrawable;

	protected int mRequestCode;
	protected Uri mContentUri;

	public RelationFieldEdit(Context context) {
		super(context, R.layout.imog__field_relation);
	}

	public RelationFieldEdit(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationField, 0, 0);
		mHasReverse = a.getBoolean(R.styleable.RelationField_hasReverse, false);
		mDisplayRes = a.getResourceId(R.styleable.RelationField_display, R.string.imog__numberOfEntities);
		mOppositeCardinality = a.getInt(R.styleable.RelationField_oppositeCardinality, 0);
		mType = a.getInt(R.styleable.RelationField_relationType, 0);
		a.recycle();
		setOnClickListener(this);
	}

	public RelationFieldEdit(Context context, AttributeSet attrs) {
		this(context, attrs, R.layout.imog__field_relation);
	}

	public void setDisplay(int display) {
		mDisplayRes = display;
	}

	public void setRelationManager(RelationManager<?> relationManager) {
		mRelationManager = relationManager;
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

	/**
	 * Set the content URI of the related entity.
	 * 
	 * @param contentUri The content URI of the related entity.
	 */
	public void setContentUri(Uri contentUri) {
		mContentUri = contentUri;
	}

	/**
	 * Set the drawable color chip representing the related entity.
	 * 
	 * @param drawable The drawable color chip.
	 */
	public void setDrawable(Drawable drawable) {
		mDrawable = drawable;
		final View color = findViewById(R.id.imog__color);
		if (color != null) {
			color.setBackgroundDrawable(mDrawable);
		}
	}

	/**
	 * Convenient method to add a common field. The value of the first field will be given to the field of the related
	 * entity which column name is given as second argument.
	 * 
	 * @param commonField The field which value will be passed to the new form.
	 * @param commonName The column name of the related entity to be set.
	 */
	public void registerCommonField(RelationFieldEdit<?> commonField, String commonName) {
		if (mCommonFields == null) {
			mCommonFields = new ArrayList<CommonFieldEntry>();
		}

		mCommonFields.add(new CommonFieldEntry(commonField, commonName));
	}

	/**
	 * Convenient method to add an {@link ExtraBuilder}. The extras values will be passed to the related entity when
	 * created.
	 * 
	 * @param builder The extra builder to add.
	 */
	public void registerExtraBuilder(ExtraBuilder builder) {
		if (mExtraBuilders == null) {
			mExtraBuilders = new ArrayList<ExtraBuilder>();
		}

		mExtraBuilders.add(builder);
	}

	/**
	 * Convenient method to add an {@link IntentBuilder}.
	 * 
	 * @param builder The intent builder to add.
	 */
	public void registerIntentBuilder(IntentBuilder builder) {
		if (mIntentBuilders == null) {
			mIntentBuilders = new ArrayList<IntentBuilder>();
		}

		mIntentBuilders.add(builder);
	}

	/**
	 * Convenient method to add a hierarchical filter. The constraint builder will build a where clause to filter data.
	 * 
	 * @param builder The constraint builder to build the where clause.
	 */
	public void registerConstraintBuilder(ConstraintBuilder builder) {
		if (mConstraintsBuilders == null) {
			mConstraintsBuilders = new ArrayList<ConstraintBuilder>();
		}

		mConstraintsBuilders.add(builder);
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
		if (mConstraintsBuilders != null) {
			for (ConstraintBuilder builder : mConstraintsBuilders) {
				Where constraintWhere = builder.onCreateConstraint();
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
		onPrepareIntent(intent);
		startActivityForResult(intent, mRequestCode);
	}

	protected Bundle createBundle() {
		Bundle bundle = new Bundle();
		if (mHasReverse && mOppositeCardinality == 1) {
			bundle.putParcelable(mOppositeRelationField, mRelationManager.getParentBean());
		}
		if (mCommonFields != null && !mCommonFields.isEmpty()) {
			for (CommonFieldEntry entry : mCommonFields) {
				if (entry.first instanceof RelationOneFieldEdit) {
					bundle.putParcelable(entry.second, ((RelationOneFieldEdit<?>) entry.first).getValue());
				} else if (entry.first instanceof RelationManyFieldEdit) {
					bundle.putParcelableArrayList(entry.second,
							Arrays.asArrayList(((RelationManyFieldEdit<?>) entry.first).getValue()));
				}
			}
		}
		if (mExtraBuilders != null) {
			for (ExtraBuilder builder : mExtraBuilders) {
				builder.onCreateExtra(bundle);
			}
		}
		return bundle;
	}

	protected void onPrepareIntent(Intent intent) {
		if (mIntentBuilders != null) {
			for (IntentBuilder builder : mIntentBuilders) {
				builder.onCreateIntent(intent);
			}
		}
	}

	protected Where onPrepareWhere() {
		return null;
	}

	/**
	 * Container to ease passing around a tuple of the field which value will be passed to the related form and the name
	 * of the field that will receive the value.
	 */
	private static class CommonFieldEntry extends Pair<RelationFieldEdit<?>, String> {

		public CommonFieldEntry(RelationFieldEdit<?> first, String second) {
			super(first, second);
		}

	}
}
