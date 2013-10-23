package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.DateField;
import org.imogene.model.core.DateTimeField;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FloatField;
import org.imogene.model.core.IntegerField;
import org.imogene.model.core.TimeField;


/**
 * Checks that the FieldEntity min has the proper formats
 * @author MEDES-IMPS
 *
 */
public class MinValueFormatConstraint extends AbstractMedanyModelConstraint {


	private static String MIN_VALUE_INT = "The min value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Integer";
	private static String MIN_VALUE_FLOAT = "The min value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Float";
	private static String MIN_VALUE_DATE = "The min value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Date at the format dd/MM/yyyy";
	private static String MIN_VALUE_DATETIME = "The min value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid DateTime at the format dd/MM/yyyy HH:mm";
	private static String MIN_VALUE_TIME = "The min value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Time at the format HH:mm";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		
		if(ctx.getTarget() instanceof FieldEntity) {
			FieldEntity target = (FieldEntity)ctx.getTarget();
				
			if(target instanceof IntegerField){					
				String minValue = ((IntegerField)target).getMin();
				if (minValue!=null && !minValue.equals("") && !Tools.isInteger(minValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MIN_VALUE_INT, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});
			}
			else if(target instanceof FloatField){	
				String minValue = ((FloatField)target).getMin();
				if (minValue!=null && !minValue.equals("") && !Tools.isFloat(minValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MIN_VALUE_FLOAT, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});
			}
			else if(target instanceof DateField){	
				String minValue = ((DateField)target).getMin();
				if (minValue!=null && !minValue.equals("") && !Tools.isDate(minValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MIN_VALUE_DATE, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});		
			}				
			else if(target instanceof DateTimeField){
				String minValue = ((DateTimeField)target).getMin();
				if (minValue!=null && !minValue.equals("") && !Tools.isDateTime(minValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MIN_VALUE_DATETIME, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});
			}				
			else if(target instanceof TimeField){	
				String minValue = ((TimeField)target).getMin();
				if (minValue!=null && !minValue.equals("") && !Tools.isTime(minValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MIN_VALUE_TIME, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});
			}			
		}
		
		return ctx.createSuccessStatus();
	}
	
	

}
