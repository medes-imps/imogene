package org.imogene.android.widget.field.edit;

import org.imogene.android.Constants.Categories;
import org.imogene.android.Constants.Extras;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.database.sqlite.stmt.Where;
import fr.medes.android.util.content.ContentUrisUtils;

public class RelationOneFieldEdit<T extends ImogBean> extends RelationFieldEdit<T> implements OnActivityResultListener {

	private boolean editOnly = false;

	public RelationOneFieldEdit(Context context) {
		super(context);
	}

	public RelationOneFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RelationOneFieldEdit(Context context, AttributeSet attrs, int layoutId) {
		super(context, attrs, layoutId);
	}

	public void setEditOnly(boolean editOnly) {
		this.editOnly = editOnly;
	}

	@Override
	public String getFieldDisplay() {
		final ImogBean value = getValue();
		if (value != null) {
			return value.getMainDisplay(getContext());
		} else {
			return getEmptyText();
		}
	}

	@Override
	protected void dispatchClick(View v) {
		if (mType == 1 && mHasReverse && mOppositeCardinality == 1) {
			// forbidden case
			Toast.makeText(getContext(), R.string.imog__relation_unsettable, Toast.LENGTH_LONG).show();
			return;
		}
		ImogBean value = getValue();
		if (isReadOnly() && value != null) {
			startActivity(new Intent(Intent.ACTION_VIEW, ContentUrisUtils.withAppendedId(mContentUri, value.getId())));
			return;
		}
		if (editOnly || (mOppositeCardinality == 1 && !mHasReverse)) {
			if (value != null) {
				startActivity(new Intent(Intent.ACTION_EDIT,
						ContentUrisUtils.withAppendedId(mContentUri, value.getId())));
			} else {
				boolean wizard = Preferences.getPreferences(getContext()).isWizardEnabled();
				Intent intent = new Intent(Intent.ACTION_INSERT, mContentUri);
				intent.putExtra(Extras.EXTRA_ENTITY, createBundle());
				intent.addCategory(wizard ? Categories.CATEGORY_WIZARD : Categories.CATEGORY_CLASSIC);
				onPrepareIntent(intent);
				startActivityForResult(intent, mRequestCode);
			}
			return;
		}
		super.dispatchClick(v);
	}

	@Override
	protected Where onPrepareWhere() {
		if (mHasReverse && mOppositeCardinality == 1 && mType == 0) {
			QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(mTableName);
			builder.selectColumns(mFieldName);
			String parentId = mRelationManager.getParentBean().getId();
			if (parentId != null) {
				builder.where().ne(ImogBean.Columns._ID, parentId).and().isNotNull(mFieldName).and()
						.ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			} else {
				builder.where().isNotNull(mFieldName).and()
						.ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			}
			return new Where().notIn(ImogBean.Columns._ID, builder);
		}
		return null;
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			T value = ImogOpenHelper.fromUri(data.getData());
			setValueInternal(value, true);
			return true;
		}
		return false;
	}

}
