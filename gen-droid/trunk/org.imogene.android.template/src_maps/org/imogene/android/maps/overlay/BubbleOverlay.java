package org.imogene.android.maps.overlay;

import org.imogene.android.template.R;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BubbleOverlay extends Overlay {

	private final Drawable mBubble;
	private final Drawable mShadow;
	
	private final Rect rect = new Rect();
	private final Point reuse = new Point();
	
	private View mParent;

	private IGeoPoint mGeoPoint;
	
	public BubbleOverlay(Context context, GeoPoint point) {
		super(context);
		mGeoPoint = point;

		mBubble = context.getResources().getDrawable(R.drawable.maps_bubble);
		mShadow = context.getResources().getDrawable(R.drawable.maps_shadow);
	}
	
	public IGeoPoint getGeoPoint() {
		return mGeoPoint;
	}
	
	public void setGeoPoint(IGeoPoint point) {
		mGeoPoint = point;
		refreshOverlay();
	}
	
	public void refreshOverlay() {
		if (mParent != null) {
			mParent.postInvalidate();
		}
	}
	
	void assignParent(View parent) {
		if (mParent != parent) {
			mParent = parent;
		}
	}

	@Override
	protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
		assignParent(mapView);
   		drawMapLocations(canvas, mapView);
	}

    private void drawMapLocations(Canvas canvas, MapView mapView) {
		mapView.getProjection().toMapPixels(mGeoPoint, reuse);
		
		bound(mBubble, false);
		bound(mShadow, true);
		
		Overlay.drawAt(canvas, mBubble, reuse.x, reuse.y, true);
		Overlay.drawAt(canvas, mShadow, reuse.x, reuse.y, true);
    }
    
	private Drawable bound(Drawable d, boolean shadow) {
		final int markerWidth = (int) (d.getIntrinsicWidth() * mScale);
		final int markerHeight = (int) (d.getIntrinsicHeight() * mScale);

		rect.set(0, 0, 0 + markerWidth, 0 + markerHeight);

		if (shadow) {
			rect.offset(0, -markerHeight);			
		} else {
			rect.offset(-markerWidth / 2, -markerHeight);
		}

		d.setBounds(rect);
		return d;
	}
	
	protected final boolean overlayIsSelected(int x, int y) {
		// Track which MapLocation was hit...if any
		// Create a 'hit' testing Rectangle w/size and coordinates of our icon
		// Set the 'hit' testing Rectangle with the size and coordinates of our on screen icon
		final Rect hit = mBubble.copyBounds();
		hit.offset(reuse.x, reuse.y);

		// Finally test for a match between our 'hit' Rectangle and the
		// location clicked by the user
		return hit.contains(x, y);
	}
	
	protected Point getMarkerPoint() {
		return reuse;
	}
	
}
