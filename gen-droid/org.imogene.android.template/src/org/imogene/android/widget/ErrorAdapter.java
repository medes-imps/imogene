package org.imogene.android.widget;

import java.util.ArrayList;

import org.imogene.android.template.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ErrorAdapter extends BaseAdapter {

	private ArrayList<ErrorEntry> mData;

	private final LayoutInflater mInflater;

	public ErrorAdapter(Context context, ArrayList<ErrorEntry> data) {
		mData = data;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ErrorEntry getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent,
				R.layout.ig_dialog_list_item);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		View v;
		if (convertView == null) {
			v = mInflater.inflate(resource, parent, false);
		} else {
			v = convertView;
		}

		bindView(position, v);

		return v;
	}

	private void bindView(int position, View view) {
		final ErrorEntry entry = mData.get(position);
		final TextView titleView = (TextView) view
				.findViewById(R.id.ig_dialog_item_title);
		final TextView messageView = (TextView) view
				.findViewById(R.id.ig_dialog_item_message);

		titleView.setText(entry.title);

		final ArrayList<String> messages = entry.messages;
		if (messages != null) {
			if (messages.size() > 1) {
				boolean first = true;
				StringBuilder builder = new StringBuilder();
				for (String message : messages) {
					if (first) {
						first = false;
					} else {
						builder.append('\n');
					}
					builder.append(message);
				}
				messageView.setText(builder.toString());
			} else {
				messageView.setText(messages.get(0));
			}
		}
	}
	
	public static class ErrorEntry {
		
		private int tag;
		private View field;
		private CharSequence title;
		private ArrayList<String> messages;
		
		public void setTag(int tag) {
			this.tag = tag;
		}
		
		public int getTag() {
			return tag;
		}
		
		public void setField(View field) {
			this.field = field;
		}
		
		public View getField() {
			return field;
		}
		
		public void setTitle(CharSequence title) {
			this.title = title;
		}
		
		public CharSequence getTitle() {
			return title;
		}
		
		public ArrayList<String> getMessages() {
			return messages;
		}
		
		public void addMessage(String message) {
			if (messages == null) {
				messages = new ArrayList<String>();
			}
			messages.add(message);
		}
		
	}
}

