package org.imogene.web.client.dynamicfields.ui.field;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imogene.web.client.dynamicfields.i18n.DynFieldsNLS;
import org.imogene.web.client.event.SelectMenuItemEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogBooleanBox;
import org.imogene.web.client.ui.field.ImogDateBox;
import org.imogene.web.client.ui.field.ImogDoubleBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogGeoBoxAsString;
import org.imogene.web.client.ui.field.ImogIntegerBox;
import org.imogene.web.client.ui.field.ImogMultiEnumBox;
import org.imogene.web.client.ui.field.ImogSingleEnumBox;
import org.imogene.web.client.ui.field.ImogTextAreaBox;
import org.imogene.web.client.ui.field.ImogTextBox;
import org.imogene.web.client.ui.field.binary.ImogBinaryBox;
import org.imogene.web.client.ui.field.binary.ImogPhotoBox;
import org.imogene.web.client.ui.field.binary.ImogQRCodeBox;
import org.imogene.web.client.ui.widget.PopupButton;
import org.imogene.web.client.ui.widget.SimpleMenuItem;
import org.imogene.web.client.util.DateUtil;
import org.imogene.web.client.util.NumericUtil;
import org.imogene.web.shared.ImogRequestFactory;
import org.imogene.web.shared.proxy.BinaryProxy;
import org.imogene.web.shared.proxy.DynamicFieldInstanceProxy;
import org.imogene.web.shared.proxy.DynamicFieldTemplateProxy;
import org.imogene.web.shared.request.BinaryRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.HasEditorDelegate;
import com.google.gwt.editor.client.HasEditorErrors;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Editor that provides the UI components that allow a DynamicField_InstanceProxy to be viewed and edited
 * in an editor list
 * @author MEDES-IMPS
 */
public class DynamicFieldEditorForList extends Composite implements LeafValueEditor<DynamicFieldInstanceProxy>, 
											HasEditorErrors<DynamicFieldInstanceProxy>, HasEditorDelegate<DynamicFieldInstanceProxy> {

	interface Binder extends UiBinder<Widget, DynamicFieldEditorForList> {	}

	private static final Binder BINDER = GWT.create(Binder.class);
	
	private static List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	protected final ImogRequestFactory requestFactory;
	private EditorDelegate<DynamicFieldInstanceProxy> delegate;
	private DynamicFieldInstanceProxy value;
	
	private int index = 0;	
	private boolean isNew;
	private String fieldType = "0";
	private boolean canManageTemplate = true;
	private boolean canManageInstance = true;

	@UiField(provided=true)
	PopupButton templateButton;	
	@UiField
	VerticalPanel fieldContainer;	
	@Ignore
	@SuppressWarnings("rawtypes")
	private ImogField fieldValue;

	/**
	 * Constructor
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public DynamicFieldEditorForList(ImogRequestFactory factory, boolean isNew) {

		this.requestFactory = factory;
		this.isNew = isNew;
		
		templateButton = new PopupButton(DynFieldsNLS.constants().dynamicfield_button_main());
		templateButton.addStyleName("dfTemplateButton");

		initWidget(BINDER.createAndBindUi(this));
	}


	/**
	 * Sets the edition mode
	 * @param isEdited true to enable the edition of the form
	 */
	public void setEdited(boolean isEdited) {
		templateButton.setVisible(isEdited && canManageTemplate);
		if(fieldValue!=null)
			fieldValue.setEdited(isEdited && canManageInstance);
	}

	/**
	 * 
	 * @param command
	 */
	public void setDisableTemplateAction(Command command) {		
		SimpleMenuItem item = new SimpleMenuItem(requestFactory.getEventBus(), DynFieldsNLS.constants().dynamicfield_button_disable(), command);
		templateButton.addPopupPanelContent(item);
	}
	
	/**
	 * 
	 * @param command
	 */
	public void setEnableTemplateAction(Command command) {		
		SimpleMenuItem item = new SimpleMenuItem(requestFactory.getEventBus(), DynFieldsNLS.constants().dynamicfield_button_enable(), command);
		templateButton.addPopupPanelContent(item);
	}
	
	/**
	 * 
	 * @param command
	 */
	public void setUpdateTemplateAction(Command command) {		
		SimpleMenuItem item = new SimpleMenuItem(requestFactory.getEventBus(), DynFieldsNLS.constants().dynamicfield_button_update(), command);
		templateButton.addPopupPanelContent(item);
	}
	
	/**
	 * Can manage field
	 * @param canManage
	 */
	public void canManageField(boolean canManage) {
		canManageTemplate = canManage;
		canManageInstance = canManage;
	}

	/**
	 * 
	 */
	@Override
	public void setValue(DynamicFieldInstanceProxy value) {
		this.value = value;
		if(value!=null) {
			DynamicFieldTemplateProxy template = value.getFieldTemplate();
			String inValue = value.getFieldValue();		
			createField(template, inValue);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public DynamicFieldInstanceProxy getValue() {
		
		String resultValue = getFieldValue();
		
		if(isNew) {
			
			if(resultValue!=null && !resultValue.isEmpty()) {
				value.setFieldValue(resultValue);	
				return value;
			}
			else
				return null;
			
		}
		else {
			value.setFieldValue(resultValue);	
			return value;		
		}
	}
	
	/**
	 * 
	 * @param template
	 * @param inValue
	 */
	@SuppressWarnings({"unchecked" })
	private void createField(DynamicFieldTemplateProxy template, String inValue) {
		
		if(template!=null && template.getFieldType()!=null)
			fieldType = template.getFieldType();
		
		/* construct field widget */
		
		// Text Area
		if(fieldType.equals(DFConstants.FIELD_TYPE_TEXT)) { 
			fieldValue = new ImogTextAreaBox();
			fieldValue.setValue(inValue);
		}			
		// Integer
		else if(fieldType.equals(DFConstants.FIELD_TYPE_INT)) { 
			fieldValue = new ImogIntegerBox();
			// set default value
			if(isNew) {
				if(template!=null && template.getDefaultValue()!=null) {
					try {
						Integer defaultValue = NumericUtil.parseToInteger(template.getDefaultValue());
						((ImogIntegerBox)fieldValue).setValue(defaultValue);
					} catch (NumberFormatException e) {
					}
				}
			}
			// set value
			if(inValue!=null) {
				try {
					fieldValue.setValue(Integer.valueOf(inValue));
				} catch (NumberFormatException e) {
				}
			}
		}
		// Float
		else if(fieldType.equals(DFConstants.FIELD_TYPE_FLOAT)) { 
			fieldValue = new ImogDoubleBox();
			// set default value
			if(isNew) {
				if(template!=null && template.getDefaultValue()!=null) {
					try {
						Double defaultValue = NumericUtil.parseToDouble(template.getDefaultValue());
						((ImogDoubleBox)fieldValue).setValue(defaultValue);
					} catch (NumberFormatException e) {
					}
				}
			}
			// set value
			if(inValue!=null) {
				try {
					fieldValue.setValue(Double.valueOf(inValue));
				} catch (NumberFormatException e) {
				}
			}
		}
		// Date
		else if(fieldType.equals(DFConstants.FIELD_TYPE_DATE)) { 
			fieldValue = new ImogDateBox(true);
			// set default value
			if(isNew) {
				if(template!=null && template.getDefaultValue()!=null) {
					if(template.getDefaultValue().equals(DFConstants.CURRENT_DATE))
						((ImogDateBox)fieldValue).setValue(new Date());
					else {
						try {
							Date defaultValue = DateUtil.parseDate(template.getDefaultValue());
							((ImogDateBox)fieldValue).setValue(defaultValue);
						} catch (Exception e) {
						}
					}
				}
			}
			// set value
			if(inValue!=null) {
				try {
					fieldValue.setValue(DateUtil.parseDate(inValue));
				} catch (Exception e) {
				}
			}
		}	
		// Boolean
		else if(fieldType.equals(DFConstants.FIELD_TYPE_BOOL)) { 
			fieldValue = new ImogBooleanBox();
			
			if(isNew) {
				// set default value
				if(template!=null && template.getDefaultValue()!=null) {
					Boolean defaultValue = new Boolean(template.getDefaultValue());
					((ImogBooleanBox)fieldValue).setValue(defaultValue);
				}
			}
			else {
				// set value
				if(inValue!=null)
					fieldValue.setValue(new Boolean(inValue));
				else
					fieldValue.setValue(null);
			}
		}
		// Enum Single
		else if(fieldType.equals(DFConstants.FIELD_TYPE_ENUM_S)) { 
			fieldValue = new ImogSingleEnumBox();
			ImogSingleEnumBox list = (ImogSingleEnumBox)fieldValue;
			if(template!=null && template.getParameters()!=null) {
				String strValues = template.getParameters();
				String[] enumValues = strValues.split(DFConstants.ENUM_SEPARATOR);
				for(String enumValue:enumValues) {
					if(!enumValue.trim().isEmpty())
						list.addItem(enumValue, enumValue);
				}				
				if(isNew) {
					// set default value
					if(template.getDefaultValue()!=null) {
						String defaultValue = template.getDefaultValue();
						fieldValue.setValue(defaultValue);
					}
				}
				else {
					// set value
					fieldValue.setValue(inValue);
				}
			}
		}
		// Enum Multi
		else if(fieldType.equals(DFConstants.FIELD_TYPE_ENUM_M)) { 
			fieldValue = new ImogMultiEnumBox();
			ImogMultiEnumBox list = (ImogMultiEnumBox)fieldValue;
			if(template!=null && template.getParameters()!=null) {
				String strValues = template.getParameters();
				String[] enumValues = strValues.split(DFConstants.ENUM_SEPARATOR);
				for(String enumValue:enumValues) {
					if(!enumValue.trim().isEmpty()){
						CheckBox chk = new CheckBox(enumValue);
						chk.setFormValue(enumValue);
						list.addItem(chk);
					}
				}
				fieldValue.setValue(inValue);
			}
		}
		// Binary
		else if(fieldType.equals(DFConstants.FIELD_TYPE_BIN)) { 
			fieldValue = new ImogBinaryBox(requestFactory);
			if(inValue!=null && !inValue.isEmpty()) {
				BinaryRequest request = requestFactory.binaryRequest();
				request.getBinary(inValue).fire(new Receiver<BinaryProxy>() {
					@Override
					public void onSuccess(BinaryProxy response) {
						fieldValue.setValue(response);
					}

					@Override
					public void onFailure(ServerFailure error) {
						Window.alert("Error retrieving the Binary");
						super.onFailure(error);
					}
				});
			}
		}
		// Image
		else if(fieldType.equals(DFConstants.FIELD_TYPE_IMG)) { 
			fieldValue = new ImogPhotoBox(requestFactory);
			if(inValue!=null && !inValue.isEmpty()) {
				BinaryRequest request = requestFactory.binaryRequest();
				request.getBinary(inValue).fire(new Receiver<BinaryProxy>() {
					@Override
					public void onSuccess(BinaryProxy response) {
						fieldValue.setValue(response);
					}

					@Override
					public void onFailure(ServerFailure error) {
						Window.alert("Error retrieving the Binary");
						super.onFailure(error);
					}
				});
			}
		}
		// GPS
		else if(fieldType.equals(DFConstants.FIELD_TYPE_GEO)) { 
			fieldValue = new ImogGeoBoxAsString();
			fieldValue.setValue(inValue);
		}
		// QRCOde
		else if(fieldType.equals(DFConstants.FIELD_TYPE_BCODE)) { 
			fieldValue = new ImogQRCodeBox(requestFactory);
			fieldValue.setValue(inValue);
		}
		// String
		else { 
			fieldValue = new ImogTextBox();
			fieldValue.setValue(inValue);
		}
		
		if(template!=null && template.getFieldName()!=null)
			fieldValue.setLabel(template.getFieldName());
		
		fieldContainer.add(fieldValue);
	}
	
	
	/**
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		fieldValue.setLabel(label);
	}
	
	/**
	 * 
	 * @return
	 */
	private String getFieldValue() {
		
		String result = null;
		
		// get minimum and maximum values for the field if defined
		String minValueStr = null;
		String maxValueStr = null;
		DynamicFieldTemplateProxy template = value.getFieldTemplate();
		if(template!=null) {			
			if(template.getMinimumValue()!=null && !template.getMinimumValue().isEmpty())
				minValueStr = template.getMinimumValue();
			if(template.getMaximumValue()!=null && !template.getMaximumValue().isEmpty())
				maxValueStr = template.getMaximumValue();
		}
	
		// get field value with validation
		if(fieldValue!=null) {
			
			// Text
			if(fieldType.equals(DFConstants.FIELD_TYPE_TEXT)) { 
				String rstring = (String)fieldValue.getValue();
				if(rstring!=null)
					result = rstring.toString();
			}
			// Integer
			else if(fieldType.equals(DFConstants.FIELD_TYPE_INT)) { 				
				try {					
					Integer intResult = ((ImogIntegerBox)fieldValue).getValueWithParseException();
					if(intResult!=null) {
						result = intResult.toString();
						// validate min value
						if(minValueStr!=null) {
							Integer minValue = NumericUtil.parseToInteger(minValueStr);
							if(intResult<minValue)
								delegate.recordError(BaseNLS.messages().error_min_num(minValueStr), result, null);
						}
						// validate max value
						if(maxValueStr!=null) {
							Integer maxValue = NumericUtil.parseToInteger(maxValueStr);
							if(intResult>maxValue)
								delegate.recordError(BaseNLS.messages().error_max_num(maxValueStr), result, null);
						}

					}
				} catch (Exception e) {
					delegate.recordError(BaseNLS.messages().error_format_int(), ((ImogIntegerBox)fieldValue).getText(), e);
				}	
			}
			// Float
			else if(fieldType.equals(DFConstants.FIELD_TYPE_FLOAT)) { 
				try {
					Double doubleResult = ((ImogDoubleBox)fieldValue).getValueWithParseException();
					if(doubleResult!=null) {
						result = doubleResult.toString();
						// validate min value
						if(minValueStr!=null) {
							Double minValue = NumericUtil.parseToDouble(minValueStr);
							if(doubleResult<minValue)
								delegate.recordError(BaseNLS.messages().error_min_num(minValueStr), result, null);
						}
						// validate max value
						if(maxValueStr!=null) {
							Double maxValue = NumericUtil.parseToDouble(maxValueStr);
							if(doubleResult>maxValue)
								delegate.recordError(BaseNLS.messages().error_max_num(maxValueStr), result, null);
						}
					}
				} catch (ParseException e) {
					delegate.recordError(BaseNLS.messages().error_format_float(), ((ImogDoubleBox)fieldValue).getText(), e);
				}
			}
			// Date
			else if(fieldType.equals(DFConstants.FIELD_TYPE_DATE)) { 
				try {
					Date rdate = ((ImogDateBox)fieldValue).getValueWithParseException();
					if(rdate!=null) {
						result = DateUtil.getDate(rdate);
						// validate min value
						if(minValueStr!=null) {
							Date minValue = DateUtil.parseDate(minValueStr);
							if(rdate.before(minValue))
								delegate.recordError(BaseNLS.messages().error_min_date(minValueStr), result, null);
						}
						// validate max value
						if(maxValueStr!=null) {
							Date maxValue = DateUtil.parseDate(maxValueStr);
							if(rdate.after(maxValue))
								delegate.recordError(BaseNLS.messages().error_max_date(maxValueStr), result, null);
						}
					}
				} catch (Exception e) {
					delegate.recordError(BaseNLS.messages().error_format_date(BaseNLS.constants().format_date()), ((ImogDateBox)fieldValue).getText(), e);
				}
			}	
			// Boolean
			else if(fieldType.equals(DFConstants.FIELD_TYPE_BOOL)) { 
				Boolean rboolean = (Boolean)fieldValue.getValue();
				if(rboolean!=null)
					result = rboolean.toString();
			}
			// Enum Single
			else if(fieldType.equals(DFConstants.FIELD_TYPE_ENUM_S)) { 
				String rstring = (String)fieldValue.getValue();
				if(rstring!=null) {
					result = rstring.toString();
				}
			}
			// Enum Multi
			else if(fieldType.equals(DFConstants.FIELD_TYPE_ENUM_M)) { 
				String rstring = (String)fieldValue.getValue();
				if(rstring!=null)
					result = rstring.toString();
			}
			// Binary
			else if(fieldType.equals(DFConstants.FIELD_TYPE_BIN)) { 
				BinaryProxy rbin = (BinaryProxy)fieldValue.getValue();
				if(rbin!=null) {
					result = rbin.getId();
				}
			}
			// Image
			else if(fieldType.equals(DFConstants.FIELD_TYPE_IMG)) { 
				BinaryProxy rbin = (BinaryProxy)fieldValue.getValue();
				if(rbin!=null) {
					result = rbin.getId();
				}
			}
			// GPS
			else if(fieldType.equals(DFConstants.FIELD_TYPE_GEO)) { 
				try {
					String rstring = ((ImogGeoBoxAsString)fieldValue).getValueWithParseException();
					if(rstring!=null)
						result = rstring.toString();
				} catch (ParseException e) {
					delegate.recordError(BaseNLS.messages().error_format_float(), ((ImogGeoBoxAsString)fieldValue).getValue(), e);
				}

			}
			// QRCOde
			else if(fieldType.equals(DFConstants.FIELD_TYPE_BCODE)) { 
				String rstring = (String)fieldValue.getValue();
				if(rstring!=null)
					result = rstring.toString();
			}
			// String
			else { 
				String rstring = (String)fieldValue.getValue();
				if(rstring!=null)
					result = rstring.toString();
			}
		}
			
		validateRequiredValueValue(result, template);
		
		return result;	
	}
	
	
	/**
	 * Validates that a value has been entered if the field is required
	 * @param result
	 */
	private void validateRequiredValueValue(String result, DynamicFieldTemplateProxy template) {
		
		if(template!=null) {			
			if(template.getRequiredValue()!=null && template.getRequiredValue()) {
				if(result==null || result.isEmpty())
					delegate.recordError(BaseNLS.messages().error_required(), result, null);
			}
		}
	}
	

	
	/**
	 * Checks if a binary is being uploaded by the editor
	 * @return true if the editor is uploading a binary
	 */
	public boolean isUploading() {
		boolean result = false;
		if (fieldValue!=null) {
			if(fieldValue instanceof ImogBinaryBox) {
				ImogBinaryBox field = (ImogBinaryBox)fieldValue;
				return field.isUploading();
			}
			else if (fieldValue instanceof ImogPhotoBox) {
				ImogPhotoBox field = (ImogPhotoBox)fieldValue;
				return field.isUploading();
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param newIndex
	 */
	public void setIndex(int newIndex) {
		this.index = newIndex;
	}

	/**
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isNew() {
		return isNew;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void showErrors(List<EditorError> errors) {
		fieldValue.showErrors(errors);
	}
	
	@Override
	public void setDelegate(EditorDelegate<DynamicFieldInstanceProxy> delegate) {
		this.delegate = delegate;
	}
	
	private void setButtonHandlers() {

		// Handler for templateButton		
		if (templateButton != null) {
			registrations.add(requestFactory.getEventBus().addHandler(SelectMenuItemEvent.TYPE,
					new SelectMenuItemEvent.Handler() {
						@Override
						public void selectMenuItem(SimpleMenuItem menuItem) {
							templateButton.hidePopup();
						}
					}));
		}
	}
	
	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	protected void onLoad() {
		setButtonHandlers();
		super.onLoad();
	}

}
