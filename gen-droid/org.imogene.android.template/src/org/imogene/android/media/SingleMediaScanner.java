package org.imogene.android.media;

import java.io.File;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

public class SingleMediaScanner implements MediaScannerConnectionClient {
	
	public static interface SingleMediaListener {
		public void onScanComplete(Uri uri);
	}

	private SingleMediaListener mListener;
	private MediaScannerConnection mMs;
	private File mFile;

	public SingleMediaScanner(Context context, File f, SingleMediaListener listener) {
		mFile = f;
		mListener = listener;
		mMs = new MediaScannerConnection(context, this);
		mMs.connect();
	}

	@Override
	public void onMediaScannerConnected() {
		mMs.scanFile(mFile.getAbsolutePath(), null);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		mMs.disconnect();
		if (mListener != null) {
			mListener.onScanComplete(uri);
		}
	}

}