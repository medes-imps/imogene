package org.imogene.android.maps.widget;

import org.imogene.android.template.R;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BalloonView extends FrameLayout {
	
	private final TextView title;
	private final TextView readmore;
	private final TextView snippet;

	/**
	 * Create a new BalloonOverlayView.
	 * 
	 * @param context The activity context.
	 * @param balloonBottomOffset The bottom padding (in pixels) to be applied when rendering this view.
	 * 
	 */
	public BalloonView(final Context context) {
		super(context);
		inflate(context, R.layout.maps_balloon_content, this);

		setPadding(10, 0, 10, 0);

		title = (TextView) findViewById(R.id.maps_balloon_item_title);
		snippet = (TextView) findViewById(R.id.maps_balloon_item_snippet);
		readmore = (TextView) findViewById(R.id.maps_balloon_item_readmore);

		readmore.setText("Read more..");

		ImageView close = (ImageView) findViewById(R.id.maps_close_img_button);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setVisibility(GONE);
			}
		});

	}
	
	/**
	 * Set the title and description of the view
	 * @param strTitle title
	 * @param strDesc description
	 */
	public void setData(String strTitle, String strDesc) {
		setVisibility(VISIBLE);
		if (strTitle != null) {
			title.setVisibility(VISIBLE);
			title.setText(strTitle);
		} else {
			title.setVisibility(GONE);
		}
		if (strDesc != null) {
			snippet.setVisibility(VISIBLE);
			snippet.setText(strDesc);
		} else {
			snippet.setVisibility(GONE);
		}
	}

}
