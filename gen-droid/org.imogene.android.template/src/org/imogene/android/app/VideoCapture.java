package org.imogene.android.app;

import java.io.File;
import java.util.UUID;

import org.imogene.android.Constants.Paths;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class VideoCapture extends Activity implements OnScanCompletedListener {

	private static final int ACTIVITY_VIDEO_CAPTURE = 1;

	private File mPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imog__media_content);

		if (savedInstanceState != null) {
			return;
		}

		Paths.PATH_MEDIA.mkdirs();
		mPath = new File(Paths.PATH_MEDIA, UUID.randomUUID().toString() + ".3gp");

		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPath));
		startActivityForResult(intent, ACTIVITY_VIDEO_CAPTURE);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("VideoCapture_path", mPath.getAbsolutePath());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mPath = new File(savedInstanceState.getString("VideoCapture_path"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_VIDEO_CAPTURE && resultCode == RESULT_OK) {
			MediaScannerConnection.scanFile(this, new String[] { mPath.getAbsolutePath() }, null, this);
		} else {
			cancelOrFailed();
		}
	}

	private void cancelOrFailed() {
		mPath.delete();
		setResult(RESULT_CANCELED);
		finish();

	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		if (uri != null) {
			setResult(RESULT_OK, new Intent().setData(uri));
			finish();
		} else {
			cancelOrFailed();
		}
	}
	//
	// @Override
	// public void onScanComplete(Uri uri) {
	// if (uri != null) {
	// setResult(RESULT_OK, new Intent().setData(uri));
	// finish();
	// } else {
	// setResult(RESULT_CANCELED);
	// finish();
	// }
	// }
}
