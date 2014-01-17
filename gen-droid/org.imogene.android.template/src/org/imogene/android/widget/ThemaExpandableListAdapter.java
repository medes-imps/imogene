package org.imogene.android.widget;

import java.util.ArrayList;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogHelper.EntityInfo;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.database.sqlite.stmt.QueryBuilder;
import org.imogene.android.template.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class ThemaExpandableListAdapter extends BaseExpandableListAdapter {

	private final Context mContext;
	private final ArrayList<Integer> mGroupData;
	private final ArrayList<ArrayList<EntityInfo>> mChildData;

	public ThemaExpandableListAdapter(Context context, ArrayList<Integer> groupData,
			ArrayList<ArrayList<EntityInfo>> childData) {
		super();
		mContext = context;
		mGroupData = groupData;
		mChildData = childData;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view;

		if (convertView != null) {
			view = convertView;
		} else {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
		}

		TextView thema = (TextView) view.findViewById(android.R.id.text1);
		thema.setText(getGroup(groupPosition));

		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		View view;

		if (convertView != null) {
			view = convertView;
		} else {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.ig_entity_row, parent, false);
		}

		EntityInfo entity = getChild(groupPosition, childPosition);

		TextView main = (TextView) view.findViewById(R.id.ig_list_main);
		main.setText(entity.description_pl);

		TextView secondary = (TextView) view.findViewById(R.id.ig_list_secondary);

		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(entity.table);
		builder.setCountOf(true);
		builder.where().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM);
		int count = (int) builder.queryForLong();

		String s = mContext.getResources().getQuantityString(R.plurals.ig_numberOfEntities, count, count);
		secondary.setText(s);

		ImageView icon = (ImageView) view.findViewById(R.id.ig_list_icon);
		if (entity.drawable != 0) {
			icon.setImageResource(entity.drawable);
		} else {
			icon.setImageResource(android.R.color.transparent);
		}

		View v = view.findViewById(R.id.ig_list_color);
		v.setBackgroundDrawable(entity.color);

		return view;
	}

	@Override
	public EntityInfo getChild(int groupPosition, int childPosition) {
		return mChildData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChildData.get(groupPosition).size();
	}

	@Override
	public Integer getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mGroupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
