package org.imogene.android.util;

import java.util.ArrayList;

import org.imogene.android.database.GeoreferencedCursor;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;

public class OverlayLoader extends AsyncTask<QueryBuilder, Void, ArrayList<OverlayItem>> {

	public interface OnOverlaysLoadedListener {
		public void onOverlaysLoaded(ArrayList<OverlayItem> items);
	}

	private final ArrayList<OverlayItem> mItems = new ArrayList<OverlayItem>();
	private final Context mContext;

	private OnOverlaysLoadedListener mListener;

	public OverlayLoader(Context context) {
		mContext = context;
	}

	public void setOnOverlaysLoadedListener(OnOverlaysLoadedListener listener) {
		mListener = listener;
		if (listener != null && getStatus() == Status.FINISHED) {
			listener.onOverlaysLoaded(mItems);
		}
	}

	@Override
	protected ArrayList<OverlayItem> doInBackground(QueryBuilder... params) {
		GeoreferencedCursor<?> c = (GeoreferencedCursor<?>) params[0].query();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Location l = c.getGeoreference();
			if (l != null) {
				String id = c.getId();
				String title = c.getMainDisplay(mContext);
				String description = c.getSecondaryDisplay(mContext);
				GeoPoint point = new GeoPoint(l);
				mItems.add(new OverlayItem(id, title, description, point));
			}
		}
		c.close();
		return mItems;
	}

	@Override
	protected void onPostExecute(ArrayList<OverlayItem> result) {
		if (mListener != null) {
			mListener.onOverlaysLoaded(mItems);
		}
	}

}
