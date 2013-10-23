package org.imogene.android.widget;

import java.lang.ref.WeakReference;

import org.imogene.android.template.R;
import org.imogene.android.util.FormatHelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class GeoPicker extends ScrollView {
	
	/* UI Components */
	private final CheckBox mFormatCheckBox;
    private final TextView mLatDegreeView;
    private final TextView mLatMinuteView;
    private final TextView mLatSecondView;
    private final TextView mLatDecimalView;
    private final TextView mLonDegreeView;
    private final TextView mLonMinuteView;
    private final TextView mLonSecondView;
    private final TextView mLonDecimalView;
    private final Button mNorthSouthButton;
    private final Button mEastWestButton;
    
    private final OnCheckedChangeListener mListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			setDecimal(isChecked);
		}
	};
	
	private final OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ig_northSouth:
				setNorth(!mNorth);
				break;
			case R.id.ig_eastWest:
				setEast(!mEast);
				break;
			}
		}
	};
	
	private boolean mIsDecimal = false;
	private boolean mNorth = true;
	private boolean mEast = true;
    
    public GeoPicker(Context context) {
    	this(context, null);
    }


    public GeoPicker(Context context, AttributeSet attrs) {
    	this(context, attrs, 0);
    }

    public GeoPicker(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	
    	inflate(context, R.layout.ig_geo_picker, this);
    	
    	mNorthSouthButton = (Button) findViewById(R.id.ig_northSouth);
        mLatDegreeView = (TextView) findViewById(R.id.ig_latDegree);
        mLatMinuteView = (TextView) findViewById(R.id.ig_latMinute);
        mLatSecondView = (TextView) findViewById(R.id.ig_latSecond);
        mLatDecimalView = (TextView) findViewById(R.id.ig_latDecimal);
        mEastWestButton = (Button) findViewById(R.id.ig_eastWest);
        mLonDegreeView = (TextView) findViewById(R.id.ig_lonDegree);
        mLonMinuteView = (TextView) findViewById(R.id.ig_lonMinute);
        mLonSecondView = (TextView) findViewById(R.id.ig_lonSecond);
        mLonDecimalView = (TextView) findViewById(R.id.ig_lonDecimal);
        mFormatCheckBox = (CheckBox) findViewById(R.id.ig_geoFormat);
        
        addTextWatcher(mLatDegreeView);
        addTextWatcher(mLatMinuteView);
        addTextWatcher(mLatSecondView);
        addTextWatcher(mLonDegreeView);
        addTextWatcher(mLonMinuteView);
        addTextWatcher(mLonSecondView);
        addTextWatcher(mLatDecimalView);
        addTextWatcher(mLonDecimalView);
        
        mNorthSouthButton.setOnClickListener(mOnClickListener);
        mEastWestButton.setOnClickListener(mOnClickListener);
        mFormatCheckBox.setOnCheckedChangeListener(mListener);
    }
    
    private void addTextWatcher(TextView v) {
    	v.addTextChangedListener(new MyTextWatcher(v));
    }
    
    private void setTextViewError(TextView v, boolean error) {
    	if (error) {
    		v.setTextColor(Color.RED);
    		v.setTypeface(null, Typeface.BOLD);
    	} else {
    		v.setTextColor(Color.BLACK);
    		v.setTypeface(null, Typeface.NORMAL);
    	}
    }
    
    public void setDecimal(boolean decimal) {
    	mIsDecimal = decimal;
    	
    	mNorthSouthButton.setEnabled(!decimal);
    	mLatDegreeView.setEnabled(!decimal);
    	mLatMinuteView.setEnabled(!decimal);
    	mLatSecondView.setEnabled(!decimal);
    	
    	mEastWestButton.setEnabled(!decimal);
    	mLonDegreeView.setEnabled(!decimal);
    	mLonMinuteView.setEnabled(!decimal);
    	mLonSecondView.setEnabled(!decimal);
    	
    	mLatDecimalView.setEnabled(decimal);
    	mLonDecimalView.setEnabled(decimal);
    }
    
    public void setNorth(boolean north) {
    	mNorth = north;
    	mNorthSouthButton.setText(north ? R.string.ig_north : R.string.ig_south);
    	if (!mIsDecimal) {
    		updateLatDecimalDisplay();
    	}
    	
    }
    
    public void setEast(boolean east) {
    	mEast = east;
    	mEastWestButton.setText(east ? R.string.ig_east : R.string.ig_west);
    	if (!mIsDecimal) {
    		updateLonDecimalDisplay();
    	}
    }
    
    public void setLatitude(double latitude) {
    	boolean isDecimal = mIsDecimal;
    	if (!isDecimal) {
    		mIsDecimal = true;
    	}
    	mLatDecimalView.setText(Double.toString(latitude));
    	mIsDecimal = isDecimal;
    }
    
    public void setLongitude(double longitude) {
    	boolean isDecimal = mIsDecimal;
    	if (!isDecimal) {
    		mIsDecimal = true;
    	}
    	mLonDecimalView.setText(Double.toString(longitude));
    	mIsDecimal = isDecimal;
    }
    
    public Double getLatitude() {
    	return FormatHelper.toDouble(mLatDecimalView.getText().toString());
    }
    
    public Double getLongitude() {
    	return FormatHelper.toDouble(mLonDecimalView.getText().toString());
    }
    
    private void onLatDegreeValueChanged() {
    	Integer d = FormatHelper.toInteger(mLatDegreeView.getText().toString());
    	Integer m = FormatHelper.toInteger(mLatMinuteView.getText().toString());
    	Double s = FormatHelper.toDouble(mLatSecondView.getText().toString());
    	
   		setTextViewError(mLatDegreeView, d != null && d > 90);
   		setTextViewError(mLatMinuteView, m != null && m > 60);
   		setTextViewError(mLatSecondView, s != null && s > 60);
   		
   		if (!mIsDecimal) {
   			updateLatDecimalDisplay();
   		}
    }
    
    private void onLatDecimalValueChanged() {
    	Double d = FormatHelper.toDouble(mLatDecimalView.getText().toString());
    	
    	setTextViewError(mLatDecimalView, d != null && Math.abs(d) > 90);
    	
    	if (mIsDecimal) {
    		updateLatDegreeDisplay();
    	}
    }
    
    private void onLonDegreeValueChanged() {
    	Integer d = FormatHelper.toInteger(mLonDegreeView.getText().toString());
    	Integer m = FormatHelper.toInteger(mLonMinuteView.getText().toString());
    	Double s = FormatHelper.toDouble(mLonSecondView.getText().toString());
    	
   		setTextViewError(mLonDegreeView, d != null && d > 180);
   		setTextViewError(mLonMinuteView, m != null && m > 60);
   		setTextViewError(mLonSecondView, s != null && s > 60);
   		
   		if (!mIsDecimal) {
   			updateLonDecimalDisplay();
   		}
    }
    
    private void onLonDecimalValueChanged() {
    	Double d = FormatHelper.toDouble(mLonDecimalView.getText().toString());
    	
    	setTextViewError(mLonDecimalView, d != null && Math.abs(d) > 180);
    	
    	if (mIsDecimal) {
    		updateLonDegreeDisplay();
    	}
    }
    
    private void updateLatDegreeDisplay() {
		Double dec = FormatHelper.toDouble(mLatDecimalView.getText().toString());
		if (dec != null) {
			double dd = dec.doubleValue();
			double add = Math.abs(dd);
			int d = (int) add;
			double f = (add - d) * 60;
			int m = (int) f;
			double s = (f - m) * 60;
			
			setNorth(dd > 0);
			mLatDegreeView.setText(Integer.toString(d));
			mLatMinuteView.setText(Integer.toString(m));
			mLatSecondView.setText(Double.toString(s));
		} else {
			mLatDegreeView.setText("");
			mLatMinuteView.setText("");
			mLatSecondView.setText("");
		}
    }
    
    private void updateLatDecimalDisplay() {
    	Integer d = FormatHelper.toInteger(mLatDegreeView.getText().toString());
    	Integer m = FormatHelper.toInteger(mLatMinuteView.getText().toString());
    	Double s = FormatHelper.toDouble(mLatSecondView.getText().toString());
    	if (d != null && m != null && s != null) {
    		double dec = d + m / 60.0 + s / 3600.0;
    		int signum = mNorth ? 1 : -1;
    		mLatDecimalView.setText(Double.toString(signum * dec));
    	} else {
    		mLatDecimalView.setText("");
    	}
    }
    
    private void updateLonDegreeDisplay() {
		Double dec = FormatHelper.toDouble(mLonDecimalView.getText().toString());
		if (dec != null) {
			double dd = dec.doubleValue();
			double add = Math.abs(dd);
			int d = (int) add;
			double f = (add - d) * 60;
			int m = (int) f;
			double s = (f - m) * 60;
			
			setEast(dd > 0);
			mLonDegreeView.setText(Integer.toString(d));
			mLonMinuteView.setText(Integer.toString(m));
			mLonSecondView.setText(Double.toString(s));
		} else {
			mLonDegreeView.setText("");
			mLonMinuteView.setText("");
			mLonSecondView.setText("");
		}
    }
    
    private void updateLonDecimalDisplay() {
		Integer d = FormatHelper.toInteger(mLonDegreeView.getText().toString());
		Integer m = FormatHelper.toInteger(mLonMinuteView.getText().toString());
		Double s = FormatHelper.toDouble(mLonSecondView.getText().toString());
		if (d != null && m != null && s != null) {
			double dec = d + m / 60.0 + s / 3600.0;
			int signum = mEast ? 1 : -1;
			mLonDecimalView.setText(Double.toString(signum * dec));
		} else {
			mLonDecimalView.setText("");
		}
    }
	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		
		final SavedState myState = new SavedState(superState);
		myState.isDecimal = mIsDecimal;
		myState.north = mNorth;
		myState.east = mEast;
		return myState;
	};
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state == null || !state.getClass().equals(SavedState.class)) {
			// Didn't save state for us in onSaveInstanceState
			super.onRestoreInstanceState(state);
			return;
		}
		
		final SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		if (!myState.isDecimal) {
			setDecimal(false);
		}
		if (!myState.north) {
			setNorth(false);
		}
		if (!myState.east) {
			setEast(false);
		}
	}
	
	private static class SavedState extends BaseSavedState {
		
		private boolean isDecimal;
		private boolean north;
		private boolean east;
		
		public SavedState(Parcel source) {
			super(source);
			isDecimal = source.readInt() == 1;
			north = source.readInt() == 1;
			east = source.readInt() == 1;
		}
		
		public SavedState(Parcelable superState) {
			super(superState);
		}
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(isDecimal ? 1 : 0);
			dest.writeInt(north ? 1 : 0);
			dest.writeInt(east ? 1 : 0);
		}
		
		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			
			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
			
			@Override
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}
		};
		
	}
	
    private final class MyTextWatcher implements TextWatcher {
    	
    	private final WeakReference<TextView> mView;
    	
    	public MyTextWatcher(TextView view) {
    		mView = new WeakReference<TextView>(view);
    	}
    	
    	@Override
    	public void afterTextChanged(Editable s) {
			TextView v = mView.get();
			if (v != null) {
				switch (v.getId()) {
				case R.id.ig_latDegree:
				case R.id.ig_latMinute:
				case R.id.ig_latSecond:
					onLatDegreeValueChanged();
					break;
				case R.id.ig_latDecimal:
					onLatDecimalValueChanged();
					break;
				case R.id.ig_lonDegree:
				case R.id.ig_lonMinute:
				case R.id.ig_lonSecond:
					onLonDegreeValueChanged();
					break;
				case R.id.ig_lonDecimal:
					onLonDecimalValueChanged();
					break;
				}
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		
    }
}
