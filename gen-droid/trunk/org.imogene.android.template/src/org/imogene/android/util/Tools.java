package org.imogene.android.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class Tools {

	private static final int CLEAR_ALPHA_MASK = 0x00FFFFFF;
	private static final int HIGH_ALPHA = 255 << 24;
	private static final int MED_ALPHA = 180 << 24;
	private static final int LOW_ALPHA = 150 << 24;

	/* The corner should be rounded on the top right and bottom right */
	private static final float[] CORNERS = new float[] { 0, 0, 5, 5, 5, 5, 0, 0 };

	public static Drawable getColorChip(int color) {

		/*
		 * We want the color chip to have a nice gradient using the color of the
		 * calendar. To do this we use a GradientDrawable. The color supplied
		 * has an alpha of FF so we first do: color & 0x00FFFFFF to clear the
		 * alpha. Then we add our alpha to it. We use 3 colors to get a step
		 * effect where it starts off very light and quickly becomes dark and
		 * then a slow transition to be even darker.
		 */
		color &= CLEAR_ALPHA_MASK;
		int startColor = color | HIGH_ALPHA;
		int middleColor = color | MED_ALPHA;
		int endColor = color | LOW_ALPHA;
		int[] colors = new int[] { startColor, middleColor, endColor };
		GradientDrawable d = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, colors);
		d.setCornerRadii(CORNERS);
		return d;
	}
	
}
