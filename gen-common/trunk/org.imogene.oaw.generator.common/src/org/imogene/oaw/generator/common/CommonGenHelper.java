package org.imogene.oaw.generator.common;

import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.Language;
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.Project;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;
import org.imogene.model.core.Thema;
import org.imogene.model.core.ValidationRule;

/**
 * This class contains some java methods used by OAW extension defined in this
 * projet : CommonFieldUtilExt.ext
 * @author MEDES-IMPS
 */
public class CommonGenHelper {
	
	/**
	 * 
	 */
	public static String getBeanNameFormat(String toFormat) {
		String result = "ERROR FORMATING (getBeanPropertyFormat)";
		if (toFormat != null)
			result = java.beans.Introspector.decapitalize(toFormat);
		return result;
	}

	/**
	 * 
	 */
	public static String getBeanPropertyFormat(String toFormat) {
		String result = "ERROR FORMATING (getBeanPropertyFormat)";
		if (toFormat != null)
			result = java.beans.Introspector.decapitalize(toFormat);
		return result;
	}

	/**
	 * 
	 */
	public static String getMethodFormat(String toFormat) {
		String result = "ERROR FORMATING (getMethodFormat)";

		if (toFormat != null) {
			String property = getBeanPropertyFormat(toFormat);
			result = Character.toUpperCase(property.charAt(0)) + property.substring(1);
		}
		return result;
	}

	/**
	 * Return getter function name for a specified property
	 */
	public static String getterName(String fieldname) {
		return "get" + getMethodFormat(fieldname);
	}

	/**
	 * Return setter function name for a specified property
	 */
	public static String setterName(String fieldname) {
		return "set" + getMethodFormat(fieldname);
	}
	
	/*
	 * When property has a lowercase first char and an upper case second char,
	 * some bean introspectors need getter/setter with type getXxxx
	 * and some other need getter/setter with type getxxx
	 */
	
	/**
	 * 
	 * @param toFormat
	 * @return
	 */
	public static boolean isPropertyWithLowerUpperCaseFirstTwoChar(String toFormat) {
		boolean result = false;
		if (toFormat != null) {
			String property = getBeanPropertyFormat(toFormat);
			return Character.isLowerCase(property.charAt(0)) && Character.isUpperCase(property.charAt(1));
		}
		return result;
	}
	
	/**
	 * 
	 */
	public static String getMethodFormatForPropertyWithLowerUpperCaseFirstTwoChar(String toFormat) {
		String result = "ERROR FORMATING (getMethodFormat)";

		if (toFormat != null) {
			String property = getBeanPropertyFormat(toFormat);
			result = property;
		}
		return result;
	}
	
	/**
	 * Return getter function name for a specified property
	 */
	public static String getterNameForPropertyWithLowerUpperCaseFirstTwoChar(String fieldname) {
		return "get" + getMethodFormatForPropertyWithLowerUpperCaseFirstTwoChar(fieldname);
	}

	/**
	 * Return setter function name for a specified property
	 */
	public static String setterNameForPropertyWithLowerUpperCaseFirstTwoChar(String fieldname) {
		return "set" + getMethodFormatForPropertyWithLowerUpperCaseFirstTwoChar(fieldname);
	}

	/**
	 * Extract and return the primitive type of a collection from the fieldName.
	 */
	public static String getCollectionPrimitiveType(String fieldname) {
		return fieldname.substring(0, fieldname.length());
	}		

	/**
	 * Return the string with the first char to upper case
	 */	
	private static String toFirstUpper(String from) {
		String to = new String();
		to = from.substring(0, 1).toUpperCase();
		to = to + from.substring(1);
		return to;
	}

	/**
	 * Return the string with the first char to lower case
	 */
	@SuppressWarnings("unused")
	private static String toFirstLower(String from) {
		String to = new String();
		to = from.substring(0, 1).toLowerCase();
		to = to + from.substring(1);
		return to;
	}

	
	/**
	 * Return the opposite cardinality for the 
	 * relation defined by this RelationField
	 * @param relationField
	 * @return the opposite cardinality
	 */
	public static int getOppositeCardinality(RelationFieldEntity relationField){
		int oppositeCardinality;
		if(relationField.getOppositeRelationField() != null){
			oppositeCardinality = relationField.getOppositeRelationField().getCardinality();
		}else {
			oppositeCardinality = ((MainRelationFieldEntity)relationField).getInverseCardinality();
		}
		return oppositeCardinality;
	}
	
	/**
	 * Return the opposite type for the 
	 * relation defined by this RelationField
	 * If opposite relation is not present, return RelationType.ASSOCIATION
	 * @param relationField
	 * @return the opposite relation type as int value
	 */
	public static int getOppositeRelationTypeValue(RelationFieldEntity relationField){
		int oppositeType;
		if(relationField.getOppositeRelationField() != null){
			oppositeType = relationField.getOppositeRelationField().getType().getValue();
		}else {
			oppositeType = RelationType.ASSOCIATION;
		}
		return oppositeType;
	}
	
	/**
	 * Return the opposite type for the 
	 * relation defined by this RelationField
	 * If opposite relation is not present, return RelationType.ASSOCIATION
	 * @param relationField
	 * @return the opposite relation type
	 */
	public static String getOppositeRelationType(RelationFieldEntity relationField){
		
		return RelationType.get(getOppositeRelationTypeValue(relationField)).toString();
	}
	
	/**
	 * Get the display name for a Thema
	 * @param isoCode the local ISO code
	 * @param entity the entity
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, Thema tema){				
		for(Description description : tema.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}		
		return toFirstUpper(tema.getName());
	}
	
	/**
	 * Get the display name for a FieldEntity
	 * @param isoCode the local ISO code
	 * @param entity the entity
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, FieldEntity entity){				
		for(Description description : entity.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}		
		return toFirstUpper(entity.getName());
	}
	
	/**
	 * Get the display name for a FieldGroup
	 * @param isoCode the local ISO code
	 * @param group the group
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, FieldGroup group){
		for(Description description : group.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}
		return toFirstUpper(group.getName());
	}
	
	/**
	 * Get the display name for a EnumValue
	 * @param isoCode the local ISO code
	 * @param enumValue the enumValue
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, EnumValue enumValue){
		for(Description description : enumValue.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}
		return toFirstUpper(enumValue.getName());
	}
	
	/**
	 * Get the display name for a CardEntity
	 * @param isoCode the local ISO code
	 * @param card the card
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, CardEntity card){		
		for(Description description : card.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}		
		return toFirstUpper(card.getName());
	}
	
	/**
	 * Get the display name for a ValidationRule
	 * @param isoCode the local ISO code
	 * @param rule the card
	 * @return the display name
	 */
	public static String getDisplayFromDescription(String isoCode, ValidationRule rule){
		for(Description description : rule.getDescriptions()){
			if(description.getLocale()!=null && description.getLocale().toLowerCase().equals(isoCode.toLowerCase())){
				return description.getDisplay();
			}
		}
		return rule.getValidationRegularExpression();
	}
	
	/**
	 * Get the display name for a Project
	 * @return the display name
	 */
	public static String getDisplayFromDescription(Project project){
		
		if(project.getDescription()!=null && project.getDescription().size()>0)
			return project.getDescription().get(0).getDisplay();
		else
			return project.getName();
	}
	
	/**
	 * Get the ISO code defined for the specified language.
	 * @param lng the language
	 * @return the ISO code or a blank string if the language is null or the ISO code not defined.
	 */
	public static String getIsoCode(Language lng){
		if(lng!=null && lng.getIsoCode()!=null)
			return lng.getIsoCode();
		return new String();
	}
}
