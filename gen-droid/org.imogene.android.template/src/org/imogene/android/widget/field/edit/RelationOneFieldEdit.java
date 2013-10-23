package org.imogene.android.widget.field.edit;

import org.imogene.android.Constants.Extras;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.database.sqlite.stmt.Where;
import org.imogene.android.domain.ImogBean;
import org.imogene.android.preference.PreferenceHelper;
import org.imogene.android.template.R;
import org.imogene.android.widget.field.FieldManager.OnActivityResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class RelationOneFieldEdit extends RelationFieldEdit<Uri> implements OnActivityResultListener {

	public RelationOneFieldEdit(Context context) {
		super(context);
	}

	public RelationOneFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public String getFieldDisplay() {
		final Uri uri = getValue();
		final String result;
		if (uri != null) {
			ImogBeanCursor cursor = (ImogBeanCursor) ImogOpenHelper.getHelper().query(uri);
			cursor.moveToFirst();
			result = cursor.getMainDisplay(getContext());
			cursor.close();
		} else {
			result = getEmptyText();
		}
		return result;
	}

	@Override
	protected void dispatchClick(View v) {
		if (mType == 1 && mHasReverse && mOppositeCardinality == 1) {
			// forbidden case
			Toast.makeText(getContext(), R.string.ig_relation_unsettable, Toast.LENGTH_LONG).show();
			return;
		}
		if (isReadOnly() && getValue() != null) {
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, getValue()));
			return;
		}
		if (mOppositeCardinality == 1 && !mHasReverse) {
			final Uri uri = getValue();
			if (uri != null) {
				getContext().startActivity(new Intent(Intent.ACTION_EDIT, uri));
			} else {
				Intent intent = new Intent(Intent.ACTION_INSERT, mContentUri);
				intent.putExtra(Extras.EXTRA_ENTITY, createBundle());
				intent.addCategory(PreferenceHelper.getEditionCategory(getContext()));
				getFieldManager().getActivity().startActivityForResult(intent, mRequestCode);
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
			builder.where().ne(ImogBean.Columns._ID, getFieldManager().getId()).and().isNotNull(mFieldName).and()
					.ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
			return new Where().notIn(ImogBean.Columns._ID, builder);
		}
		return null;
	}

	@Override
	public Where onCreateConstraint(String column) {
		final Uri uri = getValue();
		if (uri != null) {
			return new Where().eq(column, uri.getLastPathSegment());
		}
		showToastUnset();
		return null;
	}

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == mRequestCode && resultCode != Activity.RESULT_CANCELED) {
			final Uri uri = data.getData();
			if (!uri.equals(getValue())) {
				setValue(uri);
			}
			return true;
		}
		return false;
	}

}
