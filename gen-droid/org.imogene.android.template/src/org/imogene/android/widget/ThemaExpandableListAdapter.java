package org.imogene.android.widget;

import java.text.MessageFormat;
import java.util.List;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.model.EntityInfo;
import org.imogene.android.database.sqlite.ImogOpenHelper;
import org.imogene.android.template.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.widget.ArrayExpandableListAdapter;

public class ThemaExpandableListAdapter extends ArrayExpandableListAdapter<Integer, EntityInfo> {

	public ThemaExpandableListAdapter(Context context, List<Integer> groupData, List<List<EntityInfo>> childData) {
		this(context, groupData, android.R.layout.simple_expandable_list_item_1, childData, R.layout.imog__entity_row);
	}

	public ThemaExpandableListAdapter(Context context, List<Integer> groupData, int groupLayout,
			List<List<EntityInfo>> childData, int childLayout) {
		super(context, groupData, groupLayout, childData, childLayout);
	}

	@Override
	public void bindChildView(View view, EntityInfo child) {
		TextView main = (TextView) view.findViewById(android.R.id.text1);
		main.setText(child.description);

		TextView secondary = (TextView) view.findViewById(android.R.id.text2);

		QueryBuilder builder = ImogOpenHelper.getHelper().queryBuilder(child.table);
		builder.setCountOf(true);
		builder.where().ne(ImogBean.Columns.MODIFIEDFROM, ImogBean.Columns.SYNC_SYSTEM).and()
		.isNull(ImogBean.Columns.DELETED);
		int count = (int) builder.queryForLong();

		String fmt = view.getContext().getString(R.string.imog__numberOfEntities);
		secondary.setText(MessageFormat.format(fmt, count));

		ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
		if (child.drawable != 0) {
			icon.setImageResource(child.drawable);
		} else {
			icon.setImageResource(android.R.color.transparent);
		}

		View v = view.findViewById(android.R.id.background);
		v.setBackgroundDrawable(child.color);
	}

}
