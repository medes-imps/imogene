package org.imogene.android.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.imogene.android.Constants.Paths;
import org.imogene.android.media.SingleMediaScanner;
import org.imogene.android.media.SingleMediaScanner.SingleMediaListener;
import org.imogene.android.template.R;
import org.imogene.android.util.file.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class VideoCapture extends Activity implements SingleMediaListener {
	
	public static final String EXTRA_PATH = "VideoCapture_path";
	
	private static final int ACTIVITY_VIDEO_CAPTURE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ig_media_content);
		
		if (savedInstanceState != null)
			return;
		
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(intent, ACTIVITY_VIDEO_CAPTURE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_VIDEO_CAPTURE && resultCode == RESULT_OK) {
			File path = null;
			if (getIntent().hasExtra(EXTRA_PATH)) {
				path = new File(getIntent().getStringExtra(EXTRA_PATH));
			} else {
				path = Paths.PATH_MEDIA;
			}
			path.mkdirs();
			
			String fileName = UUID.randomUUID().toString() + ".3gp";
			
			File video = new File(path, fileName);
			
			Uri uri = data.getData();
			try {
				InputStream is = getContentResolver().openInputStream(uri);
				OutputStream os = new FileOutputStream(video);
				FileUtils.writeInFile(is, os, 0);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			getContentResolver().delete(uri, null, null);
			
			Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
			intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
			sendBroadcast(intent);
			new SingleMediaScanner(this, video, this);
		} else {
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
