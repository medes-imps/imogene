package org.imogene.web.client.ui.field.widget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ImogTimePicker extends ImogWidget<Date> {

	private static TimePickerUiBinder uiBinder = GWT
			.create(TimePickerUiBinder.class);

	interface TimePickerUiBinder extends UiBinder<Widget, ImogTimePicker> {
	}

	private Date value;
	private boolean edited = true;
	private int minuteStep = 1;

	@UiField
	ListBox hours;

	@UiField
	ListBox minutes;

	public ImogTimePicker() {
		initWidget(uiBinder.createAndBindUi(this));
		populateWithHours();
		populateWithMinutes();
	}

	private void populateWithHours() {
		hours.addItem("", "");
		for (int i = 0; i < 24; i++) {
			hours.addItem(String.valueOf(i), String.valueOf(i));
		}
		hours.setSelectedIndex(0);
	}

	private void populateWithMinutes() {
//		minutes.addItem("", "");
		for (int i = 0; i < 60; i = i + minuteStep) {
			minutes.addItem(formatMinutes(i), String.valueOf(i));
		}
		minutes.setSelectedIndex(0);
	}

	@SuppressWarnings("deprecation")
	public void setValue(Date value) {

		this.value = value;
		if (value != null) {
			int valueHours = value.getHours();
			hours.setSelectedIndex(getIndexForValue(hours,	String.valueOf(valueHours)));
			int valueMinutes = value.getMinutes();
			minutes.setSelectedIndex(getIndexForValue(minutes, String.valueOf(valueMinutes)));
		} else {
			hours.setSelectedIndex(0);
			minutes.setSelectedIndex(0);
		}
	}

	@SuppressWarnings("deprecation")
	public Date getValue() {
		
		if(hours.getSelectedIndex() > 0) {
			Date result = null;
			if(value!=null)
				result = new Date(value.getTime());
			else
				result = new Date();
			result.setHours(Integer.parseInt(hours.getValue(hours.getSelectedIndex())));
			result.setMinutes(Integer.parseInt(minutes.getValue(minutes.getSelectedIndex())));
			return result;		
		}
		else
			return null;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEnabled(boolean enabled) {
		this.edited = enabled;
		hours.setEnabled(enabled);
		minutes.setEnabled(enabled);
		if (!enabled) {
			hours.addStyleDependentName("disabled");
			minutes.addStyleDependentName("disabled");
		} else {
			hours.removeStyleDependentName("disabled");
			minutes.removeStyleDependentName("disabled");
		}
	}

	private static int getIndexForValue(ListBox box, String value) {
		if (value != null) {
			for (int i = 0; i < box.getItemCount(); i++) {
				if (value.equals(box.getValue(i)))
					return i;
			}
		}
		return -1;
	}

	private String formatMinutes(int minutes) {
		NumberFormat nf = NumberFormat.getFormat("00");
		return nf.format(minutes);
	}

	public void addChangeHandler(ChangeHandler changeHandler) {
		hours.addChangeHandler(changeHandler);
		minutes.addChangeHandler(changeHandler);
	}

	/**
	 * Enables to define the different minutes that will be available in the widget
	 * @param minuteStep the step between 2 minute values (default 1)
	 */
	public void setMinuteStep(int minuteStep) {
		this.minuteStep = minuteStep;
	}

}
