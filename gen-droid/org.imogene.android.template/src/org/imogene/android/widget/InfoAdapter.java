package org.imogene.android.widget;

import java.util.ArrayList;
import java.util.HashMap;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.template.R;

import android.content.Context;
import android.content.res.Resources;
import android.widget.SimpleAdapter;
import fr.medes.android.util.FormatHelper;

public class InfoAdapter extends SimpleAdapter {

	private static final String INFO_DESC = "infoDesc";
	private static final String INFO_VALUE = "infoValue";

	public InfoAdapter(Context context, ImogBean entity) {
		super(context, buildInfo(context, entity), R.layout.imog__dialog_list_item,
				new String[] { INFO_DESC, INFO_VALUE }, new int[] { R.id.imog__dialog_item_title,
						R.id.imog__dialog_item_message });
	}

	private static ArrayList<HashMap<String, String>> buildInfo(final Context context, final ImogBean entity) {
		Resources res = context.getResources();
		final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> id = new HashMap<String, String>();
		id.put(INFO_DESC, res.getString(R.string.imog__entity_id));
		id.put(INFO_VALUE, entity.getId());
		result.add(id);

		HashMap<String, String> created = new HashMap<String, String>();
		created.put(INFO_DESC, res.getString(R.string.imog__created));
		created.put(INFO_VALUE, FormatHelper.displayDateTime(entity.getCreated()));
		result.add(created);

		HashMap<String, String> createdBy = new HashMap<String, String>();
		createdBy.put(INFO_DESC, res.getString(R.string.imog__created_by));
		createdBy.put(INFO_VALUE, entity.getCreatedBy());
		result.add(createdBy);

		HashMap<String, String> modified = new HashMap<String, String>();
		modified.put(INFO_DESC, res.getString(R.string.imog__modified));
		modified.put(INFO_VALUE, FormatHelper.displayDateTime(entity.getModified()));
		result.add(modified);

		HashMap<String, String> modifiedBy = new HashMap<String, String>();
		modifiedBy.put(INFO_DESC, res.getString(R.string.imog__modified_by));
		modifiedBy.put(INFO_VALUE, entity.getModifiedBy());
		result.add(modifiedBy);

		HashMap<String, String> modifiedFrom = new HashMap<String, String>();
		modifiedFrom.put(INFO_DESC, res.getString(R.string.imog__modified_from));
		modifiedFrom.put(INFO_VALUE, entity.getModifiedFrom());
		result.add(modifiedFrom);

		return result;
	}

}
