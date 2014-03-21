package org.imogene.android.preference.filter;

import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.imogene.android.common.filter.RelationFilter;
import org.imogene.android.preference.Preferences;
import org.imogene.android.template.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.medes.android.util.ssl.SSLHttpClient;

public class RelationFilterPreference extends FilterPreference<RelationFilter> {

	private static final String TAG_FOOTER = "footer";
	private static final String DESC_DOWNLOADING = "downloading";
	private static final String DESC_ERROR = "error";
	private static final Item FOOTER_DOWNLOADING = new Item(TAG_FOOTER, DESC_DOWNLOADING);
	private static final Item FOOTER_ERROR = new Item(TAG_FOOTER, DESC_ERROR);

	private final Controller mController = Controller.getInstance();
	private final ControllerResults mCallback = new ControllerResults();
	private final MyAdapter mAdapter;
	private final ListDownloader mRunnable;

	private final String mEntityWs;
	private final String mHierarchicalField;

	private boolean stateSaved = false;

	public RelationFilterPreference(Context context, AttributeSet attrs) {
		super(context, attrs, RelationFilter.FILTER_CREATOR);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelationFilterPreference, 0, 0);
		mEntityWs = a.getString(R.styleable.RelationFilterPreference_filterEntityws);
		mHierarchicalField = a.getString(R.styleable.RelationFilterPreference_filterHierarchicalField);
		a.recycle();
		mAdapter = new MyAdapter(context, android.R.layout.select_dialog_multichoice);

		mRunnable = new ListDownloader(context, mEntityWs, mHierarchicalField, mCardEntity);
	}

	@Override
	public void onDependencyChanged(Preference dependency, boolean disableDependent) {
		super.onDependencyChanged(dependency, disableDependent);
		if (dependency instanceof FilterPreference<?> && ((FilterPreference<?>) dependency).persisted()) {
			getFilter().setFieldValue(null);
			getFilter().setDisplay(null);
			persistFilter();
		}
	}

	@Override
	public CharSequence getSummary() {
		return getFilter().getDisplay();
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		builder.setAdapter(mAdapter, null);
	}

	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);
		mController.setResultListener(mCallback);
		AlertDialog dialog = (AlertDialog) getDialog();
		dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		Thread thread = new Thread(mRunnable);
		thread.start();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		super.onClick(dialog, which);
		if (which == DialogInterface.BUTTON_POSITIVE) {
			final ListView list = ((AlertDialog) dialog).getListView();
			final SparseBooleanArray sparse = list.getCheckedItemPositions();
			final StringBuilder value = new StringBuilder();
			final StringBuilder display = new StringBuilder();
			if (sparse.size() != 0) {
				final int count = list.getCount();
				boolean first = true;
				for (int i = 0; i < count; i++) {
					if (sparse.get(i)) {
						Item item = (Item) list.getItemAtPosition(i);
						if (!TAG_FOOTER.equals(item.id)) {
							if (first) {
								first = false;
							} else {
								value.append(';');
								display.append(" , ");
							}
							value.append(item.id);
							display.append(item.description);
						}
					}
				}
			}
			getFilter().setFieldValue(value.toString());
			getFilter().setDisplay(display.toString());
			persistFilter();
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		stateSaved = true;
		mController.stop();
		return super.onSaveInstanceState();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if (!stateSaved)
			mController.stop();
	}

	private void onStarted() {
		stateSaved = false;
		mAdapter.clear();
		mAdapter.add(FOOTER_DOWNLOADING);
	}

	private void onFinished() {
		mAdapter.remove(FOOTER_DOWNLOADING);
	}

	private void onReceived() {
		mController.update(mCallback);
	}

	private void onError() {
		mAdapter.remove(FOOTER_DOWNLOADING);
		mAdapter.add(FOOTER_ERROR);
	}

	private static class ListDownloader implements Runnable {

		private final Context mContext;

		private final String mEntity;
		private final String mHierarchicalField;
		private final String mCardEntity;

		public ListDownloader(Context context, String entity, String hierarchicalField, String cardEntity) {
			mContext = context;
			mEntity = entity;
			mHierarchicalField = hierarchicalField;
			mCardEntity = cardEntity;
		}

		@Override
		public void run() {
			Preferences prefs = Preferences.getPreferences(mContext);
			final String webService = prefs.getWebServiceServer();
			final String login = prefs.getSyncLogin();
			final String password = prefs.getSyncPassword();
			final String terminal = prefs.getSyncTerminal();
			final Controller controller = Controller.getInstance();
			controller.startReceiving();

			/* request construction */
			if (webService == null) {
				controller.error();
				return;
			}
			StringBuilder builder = new StringBuilder(webService);
			if (!webService.endsWith("/"))
				builder.append('/');
			builder.append("filters/");
			builder.append(mEntity).append('s');

			if (mHierarchicalField != null) {
				String value = RelationFilter.FILTER_CREATOR.create(mContext, login, terminal, mCardEntity,
						mHierarchicalField).getFieldValue();
				if (value != null) {
					builder.append('/');
					builder.append(mHierarchicalField.toLowerCase());
					builder.append('/');
					String[] ids = value.split(";");
					for (String id : ids) {
						URI uri = URI.create(builder.toString() + id);
						if (!request(uri, controller, login, password))
							return;
					}
				} else {
					URI uri = URI.create(builder.toString());
					if (!request(uri, controller, login, password))
						return;
				}
			} else {
				URI uri = URI.create(builder.toString());
				if (!request(uri, controller, login, password))
					return;
			}
			controller.finishReceiving();

		}

		private boolean request(URI uri, Controller controller, String login, String password) {
			SSLHttpClient client = new SSLHttpClient();
			client.getCredentialsProvider().setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
					new UsernamePasswordCredentials(login, password));

			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept", "application/xml");

			try {
				/* request execution */
				HttpResponse response = client.execute(get);

				int code = response.getStatusLine().getStatusCode();
				if (code == HttpStatus.SC_OK) {
					final InputStream is = response.getEntity().getContent();
					final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
					factory.setNamespaceAware(true);
					final XmlPullParser parser = factory.newPullParser();
					parser.setInput(is, null);

					while (parser.next() != XmlPullParser.END_DOCUMENT && !controller.stopped()) {
						if (parser.getEventType() == XmlPullParser.START_TAG) {
							String name = parser.getName();
							if (mEntity.equals(name)) {
								String id = parser.getAttributeValue(null, "id");
								if (id != null) {
									Item item = new Item(id, parser.nextText());
									controller.receiveItem(item);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				controller.error();
				return false;
			}
			return true;
		}
	}

	private class ControllerResults implements Controller.Result {

		@Override
		public synchronized void notifyFinished() {
			mHandler.sendEmptyMessage(MSG_FINISHED);
		}

		@Override
		public synchronized void notifyReceived() {
			mHandler.sendEmptyMessage(MSG_RECEIVED);

		}

		@Override
		public synchronized void notifyStarted() {
			mHandler.sendEmptyMessage(MSG_STARTED);
		}

		@Override
		public synchronized void notifyError() {
			mHandler.sendEmptyMessage(MSG_ERROR);
		}

		@Override
		public synchronized void addReceived(Object o) {
			final Item item = (Item) o;
			final int count = mAdapter.getCount();
			if (count != 0) {
				mAdapter.insert(item, count - 1);
			} else {
				mAdapter.add(item);
			}
			final String value = getFilter().getFieldValue();
			final AlertDialog dialog = (AlertDialog) getDialog();
			if (dialog != null && value != null) {
				final int position = mAdapter.getPosition(item);
				dialog.getListView().setItemChecked(position, value.contains(item.id));
			}
		}
	}

	private static final int MSG_STARTED = 1;
	private static final int MSG_FINISHED = 2;
	private static final int MSG_RECEIVED = 3;
	private static final int MSG_ERROR = 4;

	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_STARTED:
				onStarted();
				break;
			case MSG_FINISHED:
				onFinished();
				break;
			case MSG_RECEIVED:
				onReceived();
				break;
			case MSG_ERROR:
				onError();
				break;
			}
		};
	};

	public static class Item {

		String id;
		String description;

		public Item(String id, String description) {
			this.id = id;
			this.description = description;
		}

		@Override
		public String toString() {
			return description;
		}

	}

	private static class MyAdapter extends ArrayAdapter<Item> {

		private final LayoutInflater mInflater;

		public MyAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Item item = getItem(position);
			if (TAG_FOOTER.equals(item.id)) {
				final View view;
				if (convertView != null) {
					String tag = (String) convertView.getTag();
					if (TAG_FOOTER.equals(tag)) {
						view = convertView;
					} else {
						view = getView(position, null, parent);
					}
				} else {
					view = mInflater.inflate(R.layout.imog__download_footer_view, parent, false);
					view.setTag(TAG_FOOTER);
				}
				final boolean down = DESC_DOWNLOADING.equals(item.description);
				view.findViewById(R.id.imog__footer_downloading).setVisibility(down ? View.VISIBLE : View.GONE);
				view.findViewById(R.id.imog__footer_error).setVisibility(down ? View.GONE : View.VISIBLE);
				return view;
			} else {
				if (convertView != null) {
					String tag = (String) convertView.getTag();
					if (TAG_FOOTER.equals(tag)) {
						return super.getView(position, null, parent);
					} else {
						return super.getView(position, convertView, parent);
					}
				}
				return super.getView(position, convertView, parent);
			}
		}

	}

}
