package org.imogene.web.client.ui.field.relation.single.listdataprovider;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.widget.ImogWidget;
import org.imogene.web.client.util.ImogBeanRenderer;
import org.imogene.web.shared.proxy.ImogBeanProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImogSingleRelationForList<T extends ImogBeanProxy> extends ImogWidget<T> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	@SuppressWarnings("rawtypes")
	interface Binder extends UiBinder<Widget, ImogSingleRelationForList> {
	}

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/* status - behavior */
	private boolean edited = false;
	private boolean isLocked = false;
	private boolean hideButtons = false;
	private boolean hideOpenButton = false;
	private boolean hideClearButton = false;
	private int boxwidth = -1;
	private List<T> values = null;

	/* listbox selected value */
	private T value;
	private ImogBeanRenderer beanRenderer;

	/* widgets */
	@UiField
	HorizontalPanel contentPanel;
	@UiField
	HorizontalPanel listBoxPanel;
	@UiField
	TextBox textBox;
	@UiField
	HorizontalPanel buttonPanel;
	@UiField
	Image openIcon;
	@UiField
	Image viewImage;
	@UiField
	Image clearImage;

	public ImogSingleRelationForList(ImogBeanRenderer beanRenderer, boolean hideButtons) {
		this(false, beanRenderer);
		this.hideButtons = hideButtons;
	}

	public ImogSingleRelationForList(ImogBeanRenderer beanRenderer) {
		this(true, beanRenderer);
	}

	public ImogSingleRelationForList(boolean canCreateEntity, ImogBeanRenderer beanRenderer) {
		initWidget(uiBinder.createAndBindUi(this));
		openIcon.setUrl(GWT.getModuleBaseURL() + "images/ico_downward.png");
		viewImage.setTitle(BaseNLS.constants().button_view());
		viewImage.setUrl(GWT.getModuleBaseURL() + "images/relation_view.png");
		clearImage.setTitle(BaseNLS.constants().button_remove());
		clearImage.setUrl(GWT.getModuleBaseURL() + "images/relation_remove.png");
		this.beanRenderer = beanRenderer;
	}

	@Override
	public boolean isEdited() {
		return edited;
	}

	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	private void showPopUpListBox() {
		ImogSingleRelationBoxPopUpForList<T> popupListBox = new ImogSingleRelationBoxPopUpForList<T>(this, beanRenderer);
		popupListBox.addValuesToList(values);
		popupListBox.popup.setPopupPosition(textBox.getAbsoluteLeft() - 1, textBox.getAbsoluteTop() + textBox.getOffsetHeight());
		popupListBox.show();
	}
	
	public void addValuesToList(List<T> values) {		
		this.values = values;
	}

	@UiHandler("textBox")
	void onClickTextBox(ClickEvent e) {
		if (edited && !isLocked && !hideOpenButton)
			showPopUpListBox();

	}

	@UiHandler("openIcon")
	void onOpenListBox(ClickEvent e) {
		if (edited)
			showPopUpListBox();
	}

	public void setViewClickHandler(ClickHandler handler) {
		registrations.add(viewImage.addClickHandler(handler));
	}

	@UiHandler("clearImage")
	void onRemoveEntity(ClickEvent e) {
		clear();
	}

	/**
	 * Get the value selected
	 * @return the value selected
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Get the display text of the selected value
	 * @return the display text of the selected value
	 */
	public String getText() {
		return textBox.getText();
	}

	/**
	 * Sets the listbox selected value
	 * @param value the selected value
	 */
	public void setValue(T value) {
		this.value = value;
		if (value != null)
			textBox.setValue(beanRenderer.getDisplayValue(value));
		else
			textBox.setValue("");
		setButtonVisibility();
	}

	/**
	 * Sets the listbox selected value
	 * @param value the selected value
	 * @param notifyChange if true notify changes
	 */
	public void setValue(T value, boolean notifyChange) {
		setValue(value);
		if (notifyChange)
			ValueChangeEvent.fire(textBox, textBox.getValue());
	}

	/**
	 * 
	 */
	public void setEnabled(boolean enabled) {

		if (!enabled || isLocked) {
			textBox.addStyleDependentName("disabled");
			edited = false;
		} else {
			textBox.removeStyleDependentName("disabled");
			edited = true;
		}
		setButtonVisibility();
		
		if(boxwidth>-1) {
			if(enabled)
				updateBoxWith(boxwidth);
			else
				updateBoxWith(boxwidth+12);
		}
	}

	/**
	 * 
	 */
	private void setButtonVisibility() {

		if (edited) {

			if (!isLocked) {

				if (!hideOpenButton)
					openIcon.setVisible(true);

				if (value == null || hideClearButton)
					clearImage.setVisible(false);
				else
					clearImage.setVisible(true);
			} else {
				openIcon.setVisible(false);
				clearImage.setVisible(false);
			}
		} else {
			openIcon.setVisible(false);
			clearImage.setVisible(false);
		}

		if (value == null || hideButtons)
			viewImage.setVisible(false);
		else
			viewImage.setVisible(true);

	}

	public void setButtonPanelWidth(String width) {
		buttonPanel.getElement().getStyle().setProperty("width", width);
	}

	/**
	 * CLear the content of the MedanyListBox TextBox
	 */
	public void clear() {
		setValue(null, true);
	}

	public void hideButtons(boolean hideButtons) {
		this.hideButtons = hideButtons;
		setButtonVisibility();
	}
	
	/**
	 * Sets the box width in pixel
	 * @param width
	 */
	public void setBoxWidth(int width) {
		this.boxwidth = width;
	}
	
	private void updateBoxWith(int width) {
		textBox.getElement().getStyle().setProperty("width", width + "px");
		listBoxPanel.getElement().getStyle().setProperty("width", width + "px");
		contentPanel.getElement().getStyle().setProperty("width", width + "px");
	}

	/**
	 * Sets if the open button shall be hidden in edit mode default is false
	 * @param hideOpenButton
	 */
	public void setHideOpenButton(boolean hideOpenButton) {
		this.hideOpenButton = hideOpenButton;
	}

	public void setHideClearButton(boolean hideClearButton) {
		this.hideClearButton = hideClearButton;
	}

	public void hideButtonPanel(boolean hide) {
		buttonPanel.setVisible(!hide);
		contentPanel.getElement().getStyle().setProperty("width", "201px");
	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	public void addValueChangeHandler(ValueChangeHandler<String> handler) {
		textBox.addValueChangeHandler(handler);
	}

}
