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
 * Checks that the FieldEntity max has the proper formats
 * @author MEDES-IMPS
 *
 */
public class MaxValueFormatConstraint extends AbstractMedanyModelConstraint {


	private static String MAX_VALUE_INT = "The max value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Integer";
	private static String MAX_VALUE_FLOAT = "The max value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Float";
	private static String MAX_VALUE_DATE = "The max value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Date at the format dd/MM/yyyy";
	private static String MAX_VALUE_DATETIME = "The max value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid DateTime at the format dd/MM/yyyy HH:mm";
	private static String MAX_VALUE_TIME = "The max value of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" should be a valid Time at the format HH:mm";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		
		if(ctx.getTarget() instanceof FieldEntity) {
			FieldEntity target = (FieldEntity)ctx.getTarget();
				
			if(target instanceof IntegerField){						
				String maxValue = ((IntegerField)target).getMax();
				if (maxValue!=null && !maxValue.equals("") && !Tools.isInteger(maxValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MAX_VALUE_INT, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});							
			}
			else if(target instanceof FloatField){							
				String maxValue = ((FloatField)target).getMax();
				if (maxValue!=null && !maxValue.equals("") && !Tools.isFloat(maxValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MAX_VALUE_FLOAT, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});							
			}
			else if(target instanceof DateField){								
				String maxValue = ((DateField)target).getMax();
				if (maxValue!=null && !maxValue.equals("") && !Tools.isDate(maxValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MAX_VALUE_DATE, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});										
			}				
			else if(target instanceof DateTimeField){							
				String maxValue = ((DateTimeField)target).getMax();
				if (maxValue!=null && !maxValue.equals("") && !Tools.isDateTime(maxValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MAX_VALUE_DATETIME, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});										
			}				
			else if(target instanceof TimeField){								
				String maxValue = ((TimeField)target).getMax();
				if (maxValue!=null && !maxValue.equals("") && !Tools.isTime(maxValue))
					return ctx.createFailureStatus(new Object[]{this.formatMessage(MAX_VALUE_TIME, ((FieldEntity)ctx.getTarget()).getParentCard(), (FieldEntity)ctx.getTarget())});										
			}		
		}		
		return ctx.createSuccessStatus();
	}
	
	

}
