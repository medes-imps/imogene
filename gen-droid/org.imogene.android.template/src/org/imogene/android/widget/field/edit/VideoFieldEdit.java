package org.imogene.android.widget.field.edit;

import org.imogene.android.template.R;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

public class VideoFieldEdit extends BinaryFieldEdit {

	public VideoFieldEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int displayId() {
		return R.string.imog__bin_video;
	}

	@Override
	protected void onCreateIntent(Intent acquire) {
		acquire.setType("video/*");
		// acquire.addCategory(Categories.CATERGORY_VIDEO_CAPTURE);
	}

}
