package org.imogene.model.validation.constraints;

import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.Project;
import org.imogene.model.core.StringField;
import org.imogene.model.core.ValidationRule;


/**
 * Check that the iso code is present for each description
 * 
 * @author MEDES-IMPS
 */
public class CheckIsoCode extends AbstractMedanyModelConstraint {

	private static String PROJECT_EMPTY_LOCALE = "The Description locale of the project \"%PROJECT_NAME%\" is empty";
	private static String PROJECT_DUPLICATE_LOCALE = "The Description locale of the card \"%PROJECT_NAME%\" is duplicated";
	private static String FIELD_EMPTY_LOCALE = "The Description locale of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is empty";
	private static String FIELD_DUPLICATE_LOCALE = "The Description locale of the field \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is duplicated";
	private static String GROUP_EMPTY_LOCALE = "The Description locale of the group \"%GROUP_NAME%\" of the card \"%ENTITY_NAME%\" is empty";
	private static String GROUP_DUPLICATE_LOCALE = "The Description locale of the field \"%GROUP_NAME%\" of the card \"%ENTITY_NAME%\" is duplicated";
	private static String CARD_EMPTY_LOCALE = "The Description locale of the card \"%ENTITY_NAME%\" is empty";
	private static String CARD_DUPLICATE_LOCALE = "The Description locale of the card \"%ENTITY_NAME%\" is duplicated";
	private static String EVAL_EMPTY_LOCALE = "The Description locale of the values of the EnumField \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is empty";
	private static String EVAL_DUPLICATE_LOCALE = "The Description locale of the values of the EnumField \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is duplicated";
	private static String VALID_EMPTY_LOCALE = "The Description locale of the validation rules of the StringField \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is empty";
	private static String VALID_DUPLICATE_LOCALE = "The Description locale of the validation rules of the StringField \"%FIELD_NAME%\" of the card \"%ENTITY_NAME%\" is duplicated";
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof Project) {
			Project project = (Project) ctx.getTarget();
			switch (validateDescriptions(project.getDescription())) {
			case 1:
				return ctx.createFailureStatus(new Object[] {formatMessage(PROJECT_EMPTY_LOCALE, project)});
			case 2:
				return ctx.createFailureStatus(new Object[] {formatMessage(PROJECT_DUPLICATE_LOCALE, project)});
			}
		} else if (ctx.getTarget() instanceof CardEntity) {
			CardEntity card = (CardEntity) ctx.getTarget();
			switch (validateDescriptions(card.getDescriptions())) {
			case 1:
				return ctx.createFailureStatus(new Object[] {formatMessage(CARD_EMPTY_LOCALE, card)});
			case 2:
				return ctx.createFailureStatus(new Object[] {formatMessage(CARD_DUPLICATE_LOCALE, card)});
			}
		} else if (ctx.getTarget() instanceof FieldEntity) {
			FieldEntity field = (FieldEntity) ctx.getTarget();
			switch (validateDescriptions(field.getDescriptions())) {
			case 1:
				return ctx.createFailureStatus(new Object[] {formatMessage(FIELD_EMPTY_LOCALE, field.getParentCard(), field)});
			case 2:
				return ctx.createFailureStatus(new Object[] {formatMessage(FIELD_DUPLICATE_LOCALE, field.getParentCard(), field)});
			}
			if (field instanceof EnumField) {
				for (EnumValue eVal : ((EnumField) field).getEnumValues()) {
					switch (validateDescriptions(eVal.getDescriptions())) {
					case 1:
						return ctx.createFailureStatus(new Object[] {formatMessage(EVAL_EMPTY_LOCALE, field.getParentCard(), field)});
					case 2:
						return ctx.createFailureStatus(new Object[] {formatMessage(EVAL_DUPLICATE_LOCALE, field.getParentCard(), field)});
					}
				}
			} else if (field instanceof StringField) {
				for (ValidationRule valid : ((StringField) field).getValidationRules()) {
					switch (validateDescriptions(valid.getDescriptions())) {
					case 1:
						return ctx.createFailureStatus(new Object[] {formatMessage(VALID_EMPTY_LOCALE, field.getParentCard(), field)});
					case 2:
						return ctx.createFailureStatus(new Object[] {formatMessage(VALID_DUPLICATE_LOCALE, field.getParentCard(), field)});
					}
				}
			}
		} else if (ctx.getTarget() instanceof FieldGroup) {
			FieldGroup group = (FieldGroup) ctx.getTarget();
			switch (validateDescriptions(group.getDescriptions())) {
			case 1:
				return ctx.createFailureStatus(new Object[] {formatMessage(GROUP_EMPTY_LOCALE, group.getParentCard(), group)});
			case 2:
				return ctx.createFailureStatus(new Object[] {formatMessage(GROUP_DUPLICATE_LOCALE, group.getParentCard(), group)});
			}
		}
		return ctx.createSuccessStatus();
	}
	
	/**
	 * 
	 * @param descriptions a list of descriptions
	 * @return 0 if valid, 1 if an empty locale was found, 2 if a locale was duplicated
	 */
	private int validateDescriptions(EList<Description> descriptions) {
		if (descriptions != null && descriptions.size() > 0) {
			Vector<String> values = new Vector<String>();
			for (Description des : descriptions) {
				if (des.getLocale() != null && !des.getLocale().isEmpty()) {
					if (values.contains(des.getLocale())) {
						return 2;
					} else {
						values.add(des.getLocale());
					}
				} else {
					return 1;
				}
			}
		}
		return 0;
	}
}
