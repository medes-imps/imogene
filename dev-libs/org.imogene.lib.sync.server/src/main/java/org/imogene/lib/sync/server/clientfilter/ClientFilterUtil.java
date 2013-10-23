package org.imogene.lib.sync.server.clientfilter;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogCriterion;
import org.imogene.lib.common.criteria.ImogDisjunction;
import org.imogene.lib.common.filter.ClientFilter;

public class ClientFilterUtil {
	
	
	/**
	 * Defines a ImogCriterion for a client filter
	 * @param filter the client filter
	 * @return a ImogCriterion as a BasicCriteria
	 */
	public static ImogCriterion getCriterion(ClientFilter filter) {
		BasicCriteria criteria = new BasicCriteria();
		criteria.setOperation(filter.getOperator());
		criteria.setField(filter.getEntityField());
		criteria.setValue(filter.getFieldValue());
		return criteria;
	}
	
	
	/**
	 * Defines a ImogCriterion for a client filter applied to a DateField 
	 * @param filter the client filter
	 * @return a ImogCriterion as a BasicCriteria or a ImogConjunction
	 * (for dates, for several conditions inside this field type, it is an 'AND')
	 */
	public static ImogCriterion getDateCriterion(ClientFilter filter) {
		
		String operator = filter.getOperator();
		
		if(operator.equals(CriteriaConstants.DATE_OPERATOR_BETWEEN)) {
			
			String fieldValue = filter.getFieldValue();		
			String fieldType = filter.getEntityField();
			
			String[] conditions = fieldValue.split(CriteriaConstants.FIELDVALUE_SEPARATOR);
			if(conditions.length==2) {
				/* for DateFields, when several conditions inside this given field type, it is an 'AND' */
				ImogConjunction filterInsideFieldType = new ImogConjunction();
				//after condition
				BasicCriteria afterCriteria = new BasicCriteria();
				afterCriteria.setOperation(CriteriaConstants.DATE_OPERATOR_AFTER);
				afterCriteria.setField(fieldType);
				afterCriteria.setValue(conditions[0]);
				filterInsideFieldType.add(afterCriteria);
				//before confition
				BasicCriteria beforeCriteria = new BasicCriteria();
				beforeCriteria.setOperation(CriteriaConstants.DATE_OPERATOR_BEFORE);
				beforeCriteria.setField(fieldType);
				beforeCriteria.setValue(conditions[1]);
				filterInsideFieldType.add(beforeCriteria);
				return filterInsideFieldType;
			}
			else
				return null;
		}
		else
			return getCriterion(filter);
	}
	
		
	/**
	 * Defines a ImogCriterion for a client filter applied to a RelationField 
	 * @param filter the client filter
	 * @return a ImogCriterion as a ImogDisjunction 
	 * (for lists, for several conditions inside this field type, it is an 'OR')
	 */
	public static ImogCriterion getRelationCriterion(ClientFilter filter) {
		
		/* for lists (RelationFields, EnumFields), when several conditions inside this given field type, it is an 'OR' */
		ImogDisjunction filterInsideFieldType = new ImogDisjunction();
		
		String[] conditions = filter.getFieldValue().split(CriteriaConstants.FIELDVALUE_SEPARATOR);
		for(String cond:conditions) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(filter.getOperator());
			criteria.setField(filter.getEntityField() + ".id");
			criteria.setValue(cond);
			filterInsideFieldType.add(criteria);
		}
		return filterInsideFieldType;	
	}
	
	
	/**
	 * Defines a ImogCriterion for a client filter applied to an EnumerationField 
	 * @param filter the client filter
	 * @return a ImogCriterion as a ImogDisjunction 
	 * (for lists, for several conditions inside this field type, it is an 'OR')
	 */
	public static ImogCriterion getEnumerationCriterion(ClientFilter filter) {
		
		/* for lists (RelationFields, EnumFields), when several conditions inside this given field type, it is an 'OR' */
		ImogDisjunction filterInsideFieldType = new ImogDisjunction();
		
		String[] conditions = filter.getFieldValue().split(CriteriaConstants.FIELDVALUE_SEPARATOR);
		for(String cond:conditions) {
			BasicCriteria criteria = new BasicCriteria();
			criteria.setOperation(filter.getOperator());
			criteria.setField(filter.getEntityField());
			criteria.setValue(cond);
			filterInsideFieldType.add(criteria);
		}
		return filterInsideFieldType;	
	}
	
	
	/**
	 * Defines a ImogCriterion for a client filter applied to a IntegerField 
	 * @param filter the client filter
	 * @return a ImogCriterion as a ImogConjunction
	 * (for int, for several conditions inside this field type, it is an 'AND')
	 */
	public static ImogCriterion getIntegerCriterion(ClientFilter filter) {
		
		String operator = filter.getOperator();
		
		if(operator.equals(CriteriaConstants.INT_OPERATOR_BETWEEN)) {
			
			String fieldValue = filter.getFieldValue();		
			String fieldType = filter.getEntityField();
			
			String[] conditions = fieldValue.split(CriteriaConstants.FIELDVALUE_SEPARATOR);
			if(conditions.length==2) {
				/* for IntegerFields, when several conditions inside this given field type, it is an 'AND' */
				ImogConjunction filterInsideFieldType = new ImogConjunction();
				//after condition
				BasicCriteria afterCriteria = new BasicCriteria();
				afterCriteria.setOperation(CriteriaConstants.INT_OPERATOR_SUP);
				afterCriteria.setField(fieldType);
				afterCriteria.setValue(conditions[0]);
				filterInsideFieldType.add(afterCriteria);
				//before confition
				BasicCriteria beforeCriteria = new BasicCriteria();
				beforeCriteria.setOperation(CriteriaConstants.INT_OPERATOR_INF);
				beforeCriteria.setField(fieldType);
				beforeCriteria.setValue(conditions[1]);
				filterInsideFieldType.add(beforeCriteria);
				return filterInsideFieldType;
			}
			else
				return null;
		}
		else
			return getCriterion(filter);
	}
	
	
	/**
	 * Defines a ImogCriterion for a client filter applied to a FloatField 
	 * @param filter the client filter
	 * @return a ImogCriterion as a ImogConjunction
	 * (for dates, for several conditions inside this field type, it is an 'AND')
	 */
	public static ImogCriterion getFloatCriterion(ClientFilter filter) {
		
		String operator = filter.getOperator();
		
		if(operator.equals(CriteriaConstants.FLOAT_OPERATOR_BETWEEN)) {
			
			String fieldValue = filter.getFieldValue();		
			String fieldType = filter.getEntityField();
			
			String[] conditions = fieldValue.split(CriteriaConstants.FIELDVALUE_SEPARATOR);
			if(conditions.length==2) {
				/* for FloatFields, when several conditions inside this given field type, it is an 'AND' */
				ImogConjunction filterInsideFieldType = new ImogConjunction();
				//after condition
				BasicCriteria afterCriteria = new BasicCriteria();
				afterCriteria.setOperation(CriteriaConstants.FLOAT_OPERATOR_SUP);
				afterCriteria.setField(fieldType);
				afterCriteria.setValue(conditions[0]);
				filterInsideFieldType.add(afterCriteria);
				//before confition
				BasicCriteria beforeCriteria = new BasicCriteria();
				beforeCriteria.setOperation(CriteriaConstants.FLOAT_OPERATOR_INF);
				beforeCriteria.setField(fieldType);
				beforeCriteria.setValue(conditions[1]);
				filterInsideFieldType.add(beforeCriteria);
				return filterInsideFieldType;
			}
			else
				return null;
		}
		else
			return getCriterion(filter);
	}

}
