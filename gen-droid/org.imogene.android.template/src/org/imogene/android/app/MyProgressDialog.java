package org.imogene.android.app;

import java.lang.reflect.Field;
import java.text.NumberFormat;

import org.imogene.android.template.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MyProgressDialog extends ProgressDialog {
	
	private Formatter mFormatter;
	
	private TextView mProgressNumber;
    private String mProgressNumberFormat;
	private TextView mProgressPercent;
    private NumberFormat mProgressPercentFormat;
	
	public MyProgressDialog(Context context) {
		this(context, R.style.Theme_Dialog_Alert);
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int progressStyle = getField("mProgressStyle");
		if (progressStyle == STYLE_HORIZONTAL) {
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					/* Update the number and percent */
					int progress = getProgress();
					int max = getMax();
					double percent = (double) progress / (double) max;
					if (mFormatter != null) {
						mProgressNumber.setText(mFormatter.format(progress, max));
					} else {
						String format = mProgressNumberFormat;
						mProgressNumber.setText(String.format(format, progress,	max));
					}
					SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
					tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					mProgressPercent.setText(tmp);
				}
			};
			setField("mViewUpdateHandler", handler);
			
			mProgressNumber = getField("mProgressNumber");
			mProgressNumberFormat = "%d/%d";
			mProgressPercent = getField("mProgressPercent");
			mProgressPercentFormat = NumberFormat.getPercentInstance();
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getField(String name) {
		try {
			Field field = ProgressDialog.class.getDeclaredField(name);
			field.setAccessible(true);
			return (T) field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setField(String name, Object value) {
		try {
			Field field = ProgressDialog.class.getDeclaredField(name);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFormatter(Formatter formatter) {
		mFormatter = formatter;
	}

	public static interface Formatter {
		public String format(int progress, int max);
	}
}