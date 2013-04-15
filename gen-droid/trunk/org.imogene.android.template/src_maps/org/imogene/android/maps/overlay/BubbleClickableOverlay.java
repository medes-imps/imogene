package org.imogene.android.maps.overlay;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class BubbleClickableOverlay extends BubbleOverlay {
	
	private static final float ACCURACY = 10f;
	
	private boolean mWasSelected;
	
	private float firstX;
	private float firstY;
	
	private int dx;
	private int dy;
	
	private final LongClickRunnable mRunnable = new LongClickRunnable();
	private final Point reuse = new Point();

	public BubbleClickableOverlay(Context context, GeoPoint point) {
		super(context, point);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		Projection pj = mapView.getProjection();
		final float x = event.getX();
		final float y = event.getY();
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = x;
			firstY = y;
			pj.fromMapPixels((int) x, (int) y, reuse);
			mWasSelected = overlayIsSelected(reuse.x, reuse.y);
			if (!mWasSelected) {
				dx = dy = 0;
				IGeoPoint point = pj.fromPixels(x, y);
				mRunnable.setGeoPoint(point);
				mapView.postDelayed(mRunnable, ViewConfiguration.getLongPressTimeout());
			} else {
				Point marker = getMarkerPoint();
				dx = reuse.x - marker.x;
				dy = reuse.y - marker.y;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mWasSelected) {
				setGeoPoint(pj.fromPixels(x - dx, y - dy));
			} else if (Math.hypot(x - firstX, y - firstY) > ACCURACY) {
				mapView.removeCallbacks(mRunnable);
			}
			break;
		default:
			mWasSelected = false;
			mapView.removeCallbacks(mRunnable);
			break;
		}
		return mWasSelected;
	}
	
	private class LongClickRunnable implements Runnable {
		
		private IGeoPoint mPoint;
		
		public void setGeoPoint(IGeoPoint point) {
			mPoint = point;
		}
		
		@Override
		public void run() {
			setGeoPoint(mPoint);
			mWasSelected = true;
		}
	}

}