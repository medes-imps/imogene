package org.imogene.android.widget;

import java.util.ArrayList;

import org.imogene.android.template.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter to display the errors list when trying to register a form.
 */
public class ErrorAdapter extends BaseAdapter {

	private ArrayList<ErrorEntry> mData;

	private final LayoutInflater mInflater;

	/**
	 * Constructor for the error list adapter.
	 * 
	 * @param context The context.
	 * @param data The error entries list.
	 */
	public ErrorAdapter(Context context, ArrayList<ErrorEntry> data) {
		mData = data;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		return createViewFromResource(position, convertView, parent, R.layout.imog__dialog_list_item);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
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
		final TextView titleView = (TextView) view.findViewById(R.id.imog__dialog_item_title);
		final TextView messageView = (TextView) view.findViewById(R.id.imog__dialog_item_message);

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

	/**
	 * Convenient class to define an error entry to be displayed when trying to register a form.
	 */
	public static class ErrorEntry {

		private int tag;
		private View field;
		private CharSequence title;
		private ArrayList<String> messages;

		/**
		 * Set the tab identifier containing the field view of this entry.
		 * 
		 * @param tag The tab identifier.
		 */
		public void setTag(int tag) {
			this.tag = tag;
		}

		/**
		 * Returns the tab identifier containing the field view of this entry.
		 * 
		 * @return The tab identifier.
		 */
		public int getTag() {
			return tag;
		}

		/**
		 * Set the view representing the field on which the error messages of this entry are related to.
		 * 
		 * @param field The field corresponding to the error messages.
		 */
		public void setField(View field) {
			this.field = field;
		}

		/**
		 * Returns the field related to the error messages of this entry.
		 * 
		 * @return The field related to the error messages.
		 */
		public View getField() {
			return field;
		}

		/**
		 * Set the title of the error message entry.
		 * 
		 * @param title The title of the error message entry.
		 */
		public void setTitle(CharSequence title) {
			this.title = title;
		}

		/**
		 * Returns the title of the error message entry.
		 * 
		 * @return The title of the error message entry.
		 */
		public CharSequence getTitle() {
			return title;
		}

		/**
		 * The list of messages to display for a field entry.
		 * 
		 * @return The list of error messages.
		 */
		public ArrayList<String> getMessages() {
			return messages;
		}

		/**
		 * Add a new error message to the list of errors to display for a given field.
		 * 
		 * @param message The error message.
		 */
		public void addMessage(String message) {
			if (messages == null) {
				messages = new ArrayList<String>();
			}
			messages.add(message);
		}

	}
}
