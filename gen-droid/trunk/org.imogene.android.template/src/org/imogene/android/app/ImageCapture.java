package org.imogene.android.app;

import java.io.File;
import java.util.UUID;

import org.imogene.android.Constants.Paths;
import org.imogene.android.media.SingleMediaScanner;
import org.imogene.android.media.SingleMediaScanner.SingleMediaListener;
import org.imogene.android.template.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class ImageCapture extends Activity implements SingleMediaListener {

	private static final int ACTIVITY_IMAGE_CAPTURE = 1;

	private File mPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ig_media_content);

		if (savedInstanceState != null) {
			return;
		}

		Paths.PATH_MEDIA.mkdirs();
		mPath = new File(Paths.PATH_MEDIA, UUID.randomUUID().toString() + ".jpeg");

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPath));
		startActivityForResult(intent, ACTIVITY_IMAGE_CAPTURE);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("ImageCapture_path", mPath.getAbsolutePath());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mPath = new File(savedInstanceState.getString("ImageCapture_path"));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
			sendBroadcast(intent);
			new SingleMediaScanner(this, mPath, this);
		} else {
			mPath.delete();
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	@Override
	public void onScanComplete(Uri uri) {
		if (uri != null) {
			setResult(RESULT_OK, new Intent().setData(uri));
			finish();
		} else {
			setResult(RESULT_CANCELED);
			finish();
		}
	}
}
