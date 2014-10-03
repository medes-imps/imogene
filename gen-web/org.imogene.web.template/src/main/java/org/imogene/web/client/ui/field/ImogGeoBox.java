package org.imogene.web.client.ui.field;

import java.util.List;

import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogErrorLabel;
import org.imogene.web.client.ui.field.widget.ImogDblBox;
import org.imogene.web.shared.proxy.GeoFieldProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Field Editor for Geo fields 
 * @author MEDES-IMPS
 */
public class ImogGeoBox extends Composite implements ImogField<GeoFieldProxy>,
		LeafValueEditor<GeoFieldProxy>, HasEditorErrors<GeoFieldProxy> {

	private static final Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ImogGeoBox> {
	}

	/* status - behavior */
	private boolean edited = false;
	private boolean clickable = true;
	private AddressFieldBehavior thisBehavior;
	private GeoFieldProxy value;

	/* widgets */
	@UiField
	@Ignore
	ImogFieldAbstract fieldBox;
	@UiField
	ImogErrorLabel errorLabel;
	@UiField
	@Ignore
	Label labelLong;
	@UiField
	@Ignore
	ImogDblBox textBoxLong;
	@UiField
	@Ignore
	Label labelLat;
	@UiField
	@Ignore
	ImogDblBox textBoxLat;
	@UiField
	@Ignore
	Image viewOnMap;

	public ImogGeoBox() {
		this(null);
	}

	public ImogGeoBox(AddressFieldBehavior behavior) {

		initWidget(uiBinder.createAndBindUi(this));
		
		labelLong.setText(BaseNLS.constants().gps_longiture());
		labelLat.setText(BaseNLS.constants().gps_latitude());
		textBoxLong.getElement().getStyle().setProperty("width", "110px");
		textBoxLat.getElement().getStyle().setProperty("width", "110px");

		if (behavior == null) {
			thisBehavior = new DefaultAddressBehavior();
		} else {
			thisBehavior = behavior;
		}
		viewOnMap.setUrl(GWT.getModuleBaseURL() + "/images/view_on_map-24.png");
	}

	public void setEdited(boolean enabled) {
		textBoxLong.setEnabled(enabled);
		textBoxLat.setEnabled(enabled);
		if (!enabled) {
			textBoxLong.addStyleDependentName("disabled");
			textBoxLat.addStyleDependentName("disabled");
			viewOnMap.addStyleDependentName("clickable");
		} else {
			textBoxLong.removeStyleDependentName("disabled");
			textBoxLat.removeStyleDependentName("disabled");
			viewOnMap.removeStyleDependentName("clickable");
		}
		edited = enabled;
		clickable = !enabled;
	}

	@Override
	public void setLabel(String label) {
		fieldBox.setLabel(label);
	}
	
	public void setLabel(String label, HorizontalAlignmentConstant alignment) {
		fieldBox.setLabel(label, alignment);
	}
	@Override
	public boolean isEdited() {
		return edited;
	}

	@UiHandler("viewOnMap")
	void handleClick(ClickEvent e) {

		if (clickable && getValue()!=null)
			thisBehavior.addressAction(textBoxLong.getText()+ ";" + textBoxLat.getText());
	}

	/**
	 */
	@Override
	public void setValue(GeoFieldProxy value) {
		this.value = value;
		if(value!=null && value.getLongitude()!=null && value.getLatitude()!=null) {
			textBoxLong.setValue(value.getLongitude());
			textBoxLat.setValue(value.getLatitude());
		}	
	}

	/**
	 */
	@Override
	public GeoFieldProxy getValue() {
		if (textBoxLong.getValue()==null || textBoxLat.getValue()==null) {
			if(value!=null) {
				value.setLongitude(null);
				value.setLatitude(null);
			}
		}
		else {
			if(value!=null) {
				value.setLongitude(textBoxLong.getValue());
				value.setLatitude(textBoxLat.getValue());	
			}
		}
		
		if(isNull())
			return null;
		else
			return value;	
	}
	
	/**
	 * Tells if the box value is null
	 * @return
	 */
	private boolean isNull() {
		if(value!=null) {
			if(value.getLongitude()==null && value.getLatitude()==null) 
				return true;
			else
				return false;
		}
		else
			return true;

	}
	
	/**
	 * Defines that the field shall notify value changes
	 * @param eventBus the event bus that will be used to fire the value change events
	 */
	public void notifyChanges(final EventBus eventBus) {
		if(eventBus!=null) {
			textBoxLong.addValueChangeHandler(new ValueChangeHandler<Double>() {			
				@Override
				public void onValueChange(ValueChangeEvent<Double> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogGeoBox.this));
				}
			});	
			textBoxLat.addValueChangeHandler(new ValueChangeHandler<Double>() {			
				@Override
				public void onValueChange(ValueChangeEvent<Double> arg0) {
					eventBus.fireEvent(new FieldValueChangeEvent(ImogGeoBox.this));
				}
			});	
		}
	}

	@Override
	public void showErrors(List<EditorError> errors) {
		errorLabel.showErrors(errors);
	}
	
	public void setLabelWidth(String width) {
		fieldBox.setLabelWidth(width);
	}
	
	/**
	 * Sets the widget's width
	 */
	public void setBoxWidth(int width) {
		textBoxLong.getElement().getStyle().setProperty("width", width + "px");
		textBoxLat.getElement().getStyle().setProperty("width", width + "px");
	}

	/* INTERNAL CLASSES/INTERFACES */
	/**
	 * Interface which describes the behavior of the widget when the user
	 * chooses to open this address.
	 */
	public interface AddressFieldBehavior {
		public void addressAction(String address);
	}

	/**
	 * Behavior that enables to display the address location on the Google maps
	 * web site.
	 * 
	 * @author Medes-IMPS
	 */

	public static class DefaultAddressBehavior implements AddressFieldBehavior {

		private final static String GMAPS_TMPL = "http://maps.google.com/maps?f=q&hl=en&geocode=&q=63116&ie=UTF8&ll=%LONGITUDE%,%LATITUDE%&spn=0.002391,0.005879&t=k&z=18"; 
		
		@Override
		public void addressAction(String point) {
			if(point!=null){
				String[] ll = point.split(";");
				if(ll.length>1){
					Window.open(GMAPS_TMPL.replace("%LONGITUDE%", ll[0]).replace("%LATITUDE%", ll[1]), "Google maps", "");
				}				
			}						
		}
	}

}
