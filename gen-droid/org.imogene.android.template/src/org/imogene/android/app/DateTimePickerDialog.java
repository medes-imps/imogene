package org.imogene.android.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.imogene.android.template.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimePickerDialog extends AlertDialog implements OnClickListener, OnCheckedChangeListener, OnDateChangedListener,
		OnTimeChangedListener {

	private static final String HOUR = "hour";
	private static final String MINUTE = "minute";
	private static final String IS_24_HOUR = "is24hour";
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";

	private final CheckBox mSwitcher;
	private final DatePicker mDatePicker;
	private final TimePicker mTimePicker;
	private final OnDateTimeSetListener mCallBack;
	private final Calendar mCalendar;
	private final DateFormat mTitleDateFormat;

	/**
	 * The callback used to indicate the user is done filling in the date.
	 */
	public interface OnDateTimeSetListener {

		/**
		 * @param view The view associated with this listener.
		 * @param year The year that was set.
		 * @param monthOfYear The month that was set (0-11) for compatibility
		 *            with {@link java.util.Calendar}.
		 * @param dayOfMonth The day of the month that was set.
		 */
		void onDateTimeSet(DatePicker datePicker, TimePicker timePicker, int year, int monthOfYear, int dayOfMonth,
				int hourOfDay, int minute);
	}

	/**
	 * @param context The context the dialog is to run in.
	 * @param callBack How the parent is notified that the date is set.
	 * @param year The initial year of the dialog.
	 * @param monthOfYear The initial month of the dialog.
	 * @param dayOfMonth The initial day of the dialog.
	 */
	public DateTimePickerDialog(Context context, OnDateTimeSetListener callBack, int year, int monthOfYear, int dayOfMonth,
			int hourOfDay, int minute, boolean is24HourView) {
		this(context, R.style.Theme_Dialog_Alert, callBack, year, monthOfYear, dayOfMonth, hourOfDay, minute, is24HourView);
	}

	/**
	 * @param context The context the dialog is to run in.
	 * @param theme the theme to apply to this dialog
	 * @param callBack How the parent is notified that the date is set.
	 * @param year The initial year of the dialog.
	 * @param monthOfYear The initial month of the dialog.
	 * @param dayOfMonth The initial day of the dialog.
	 */
	public DateTimePickerDialog(Context context, int theme, OnDateTimeSetListener callBack, int year, int monthOfYear,
			int dayOfMonth, int hourOfDay, int minute, boolean is24HourView) {
		super(context, theme);

		mCallBack = callBack;

		mTitleDateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy hh:mm a", Locale.getDefault());
		mCalendar = Calendar.getInstance();
		updateTitle(year, monthOfYear, dayOfMonth, hourOfDay, minute);

		setButton(BUTTON_POSITIVE, context.getText(android.R.string.ok), this);
		setButton(BUTTON_NEGATIVE, context.getText(android.R.string.cancel), this);
		setIcon(R.drawable.ig_ic_dialog_time);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.ig_date_time_picker_dialog, null);
		setView(view);

		// initialize state
		mDatePicker = (DatePicker) view.findViewById(R.id.ig_datePicker);
		mTimePicker = (TimePicker) view.findViewById(R.id.ig_timePicker);
		mSwitcher = (CheckBox) view.findViewById(R.id.ig_switchDateTime);

		mSwitcher.setOnCheckedChangeListener(this);
		mDatePicker.init(year, monthOfYear, dayOfMonth, this);
		mTimePicker.setOnTimeChangedListener(this);
		mTimePicker.setCurrentHour(hourOfDay);
		mTimePicker.setCurrentMinute(minute);
		updatePickers(mSwitcher.isChecked());
	}

	// @Override
	// public void show() {
	// super.show();
	//
	// /* Sometimes the full month is displayed causing the title
	// * to be very long, in those cases ensure it doesn't wrap to
	// * 2 lines (as that looks jumpy) and ensure we ellipsize the end.
	// */
	// TextView title = (TextView) findViewById(android.R.id.ig_title);
	// title.setSingleLine();
	// title.setEllipsize(TruncateAt.END);
	// }

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == BUTTON_POSITIVE && mCallBack != null) {
			mDatePicker.clearFocus();
			mTimePicker.clearFocus();
			mCallBack.onDateTimeSet(mDatePicker, mTimePicker, mDatePicker.getYear(), mDatePicker.getMonth(),
					mDatePicker.getDayOfMonth(), mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		updatePickers(isChecked);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		updateTitle(year, monthOfYear, dayOfMonth, mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		updateTitle(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth(), hourOfDay, minute);
	}

	public void updateDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
		mDatePicker.updateDate(year, monthOfYear, dayOfMonth);
		mTimePicker.setCurrentHour(hourOfDay);
		mTimePicker.setCurrentMinute(minute);
	}

	private void updatePickers(boolean isChecked) {
		mDatePicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
		mTimePicker.setVisibility(isChecked ? View.GONE : View.VISIBLE);
	}

	private void updateTitle(int year, int month, int day, int hour, int minute) {
		mCalendar.set(Calendar.YEAR, year);
		mCalendar.set(Calendar.MONTH, month);
		mCalendar.set(Calendar.DAY_OF_MONTH, day);
		mCalendar.set(Calendar.HOUR_OF_DAY, hour);
		mCalendar.set(Calendar.MINUTE, minute);
		setTitle(mTitleDateFormat.format(mCalendar.getTime()));
	}

	@Override
	public Bundle onSaveInstanceState() {
		Bundle state = super.onSaveInstanceState();
		state.putInt(YEAR, mDatePicker.getYear());
		state.putInt(MONTH, mDatePicker.getMonth());
		state.putInt(DAY, mDatePicker.getDayOfMonth());
		state.putInt(HOUR, mTimePicker.getCurrentHour());
		state.putInt(MINUTE, mTimePicker.getCurrentMinute());
		state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
		return state;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int year = savedInstanceState.getInt(YEAR);
		int month = savedInstanceState.getInt(MONTH);
		int day = savedInstanceState.getInt(DAY);
		int hour = savedInstanceState.getInt(HOUR);
		int minute = savedInstanceState.getInt(MINUTE);
		mDatePicker.init(year, month, day, this);
		mTimePicker.setCurrentHour(hour);
		mTimePicker.setCurrentMinute(minute);
		mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
		updateTitle(year, month, day, hour, minute);
	}

}
