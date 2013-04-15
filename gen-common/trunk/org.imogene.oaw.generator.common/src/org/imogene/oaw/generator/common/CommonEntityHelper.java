package org.imogene.oaw.generator.common;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.imogene.model.core.Actor;
import org.imogene.model.core.BinaryField;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.CardEntityUIFormat;
import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.MainRelationFieldEntity;
import org.imogene.model.core.NotificationInfo;
import org.imogene.model.core.Project;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.RelationType;
import org.imogene.model.core.Role;
import org.imogene.model.core.TextField;
import org.imogene.model.core.Thema;




/**
 * Method to help with entity handle inside generation templates.
 */
public class CommonEntityHelper 
{

	
	/**
	 * Test the presence of a type of fields in a list of fields
	 * 
	 * @param fields fields to test
	 * @param type type researched
	 * @return true if at least one list element match the type
	 */
    public static boolean isTypePresent(List<FieldEntity> fields, String type)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if (f.eClass().getName().equals(type))
                return true;
        }
        return false;
    }
	
	
	/**
	 * Test if binaries fields are present in the list.
	 * 
	 * @param fields fields to test
	 * @return true if at least one binary field is present
	 */
    public static boolean isBinaryFieldPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if (f instanceof BinaryField)
                return true;
        }
        return false;
    }
    
	
	/**
	 * Test if at least one simple (cardinality=1) RelationField is present.
	 * 
	 * @param fields fields to test
	 * @return true is at least one simple RelationField is present
	 */
    public static boolean isSimpleRelationFieldPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if ((f instanceof RelationFieldEntity) && ((RelationFieldEntity)f).getCardinality() == 1)
                return true;
        }
        return false;

    }
    
	/**
	 * Test if at least one MainRelationField has commonFields.
	 * 
	 * @param fields fields to test
	 * @return true is at least one MainRelationField has commonFields
	 */
    public static boolean isCommonFieldsPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if ((f instanceof MainRelationFieldEntity) && !((MainRelationFieldEntity)f).getCommonFields().isEmpty() && (((MainRelationFieldEntity)f).getCommonFields().size()+2)%2==0)
                return true;
        }
        return false;

    }
    
	/**
	 * Test if at least one required simple (cardinality=1) RelationField is present.
	 * 
	 * @param fields fields to test
	 * @return true is at least one simple RelationField is present
	 */
    public static boolean isRequiredSimpleRelationFieldPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if ((f instanceof RelationFieldEntity) && (((RelationFieldEntity)f).getCardinality() == 1) && f.isRequired())
                    return true;
        }
        return false;

    }    
	
	/**
	 * Test if at least one complex (cardinality > 1) RelationField is present.
	 * 
	 * @param fields fields to test
	 * @return true if at least one complex RelationField is present
	 */
    public static boolean isMultiRelationFieldPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            Object obj = it.next();
            FieldEntity f = (FieldEntity) obj;
            if (f instanceof RelationFieldEntity)
            {
                int card = ((RelationFieldEntity)f).getCardinality();
                if ((card > 1) || (card == -1))
                  return true;
            }
        }
        return false;
    }
	
	/**
	 * 
	 */
	public static boolean isMultiRelationFieldWithAssociationPresent(List<FieldEntity> fields)
    {
        for (Iterator<FieldEntity> it = fields.iterator(); it.hasNext(); )
        {
            FieldEntity f = (FieldEntity) it.next();
            if (f instanceof RelationFieldEntity)
            {
                RelationFieldEntity rf = (RelationFieldEntity) f;
                int card = rf.getCardinality();
                int type = rf.getType().getValue();
                if ( ((card > 1) || (card == -1)) && (type == RelationType.ASSOCIATION)) return true;
            }
        }
        return false;
       
	}
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	public static boolean isTranslatableFieldPresent(List<FieldEntity> fields)
    {
        for (FieldEntity f:fields) {
            if (f instanceof TextField) {
            	TextField rf = (TextField) f;
            	if (rf.isTranslatable())
            		return true;
            }
        }
        return false;
	}
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	public static boolean hasTranslatableEntityInRelationFields(List<FieldEntity> fields)
    {
        for (FieldEntity f:fields) {
        	
            if (f instanceof RelationFieldEntity) {
            	RelationFieldEntity rf = (RelationFieldEntity) f;
            	for(FieldGroup group: rf.getEntity().getGroups()) {
            		if (isTranslatableFieldPresent(group.getFields()))
            			return true;
            	}
            }
        }
        return false;
	}
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	public static boolean hasNestedFormWithCardN(List<FieldEntity> fields)
    {
        for (FieldEntity f:fields) {
        	
            if (f instanceof RelationFieldEntity) {
            	RelationFieldEntity rf = (RelationFieldEntity) f;
            		if (rf.getCardinality()!=1 && rf.isNestedForm())
            			return true;
            }
        }
        return false;
	}
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	public static boolean hasNestedForm(List<FieldEntity> fields)
    {
        for (FieldEntity f:fields) {
        	
            if (f instanceof RelationFieldEntity) {
            	RelationFieldEntity rf = (RelationFieldEntity) f;
            		if (rf.isNestedForm())
            			return true;
            }
        }
        return false;
	}
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	public static boolean hasDynamicFields(List<CardEntity> entities)
    {
        for (CardEntity e:entities) {       	
            if (e.isHasDynamicFields())
            	return true;
        }
        return false;
	}
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	public static boolean hasNotifications(List<CardEntity> entities)
    {
        for (CardEntity e:entities) {   
        	
        	if(e instanceof Actor) {
        		Actor actor = (Actor)e;
        		if(actor.getNotificationInfos()!=null && actor.getNotificationInfos().size()>0)
        			return true;
        	}
        }
        return false;
	}
	

	/**
	 * Compute modulo between two number.
	 * 
	 * @param param1 first number
	 * @param param2 second number
	 * @return modulo
	 */
	public static Long modulo(java.lang.Long param1, java.lang.Long param2)
	{
		Long result = new Long(param1.intValue()%param2.intValue());
		return result;		
	}	
	

	
    
    /**@ return an integer for cardinality if field is a relationFieldENtity, else returns null */
    @SuppressWarnings("unused")
	private static int _getCardinality(FieldEntity f)
    {
        return (f instanceof RelationFieldEntity) ? ((RelationFieldEntity)f).getCardinality() : 0;
    }
	
	/**
	 * Returns true if a field is an adminField or a notifField

	 */	
	private static boolean isAdminOrNotifField(FieldEntity field, List<FieldEntity> adminFields, FieldEntity notifField){
				
		if(adminFields!=null && adminFields.size()>0)
		{
			if (notifField!=null)
			{
				if (adminFields.contains(field) || notifField.equals(field))
					return true;
				else
					return false;	
			}
			else
			{
				if (adminFields.contains(field))
					return true;
				else
					return false;	
			}
		}
		else
		{
			if (notifField!=null)
			{
				if (notifField.equals(field))
					return true;
				else
					return false;	
			}				
			else
				return false;
		}		
	}
	
	/**
	 * Returns true if a field is an adminField

	 */	
	private static boolean isAdminField(FieldEntity field, List<FieldEntity> adminFields){
				
		if(adminFields!=null && adminFields.contains(field))
			return true;
		else
			return false;		
	}
	
	/**
	 * Returns true if a field is an adminField or a notifField

	 */	
	public static boolean isAdminOrNotifField(FieldEntity field, List<FieldEntity> adminFields, List<NotificationInfo> notifInfos){
		
		boolean isAdminOrNotifField = false;
		
		if (notifInfos!=null && notifInfos.size()>0)
		{
			for (Iterator<NotificationInfo> it = notifInfos.iterator(); it.hasNext();) {
				NotificationInfo current = (NotificationInfo) it.next();
				if (isAdminOrNotifField(field, adminFields, current.getDataField()))
				{
					isAdminOrNotifField = true;
					break;
				}					
			}			
		}
		else
		{
			isAdminOrNotifField= isAdminField(field, adminFields);
		}
		return isAdminOrNotifField;
			
	}
	
	
	/**
	 * Returns the list of fields for actor administration to be 
	 * displayed in the list table for an actor
	 * Admin or Notif fields
	 */	
	public static List<FieldEntity> getAdministrationColumnFields(Actor actor){
		
		List<FieldEntity> adminFields = actor.getAdminFields();
		if(adminFields==null)
			return new Vector<FieldEntity>();
		return adminFields;
	}
	
	/**
	 * Returns the list of fields for actor administration
	 * Administration or Notification fields
	 */	
	public static List<FieldEntity> getAdministrationFields(Actor actor){
		
		List<FieldEntity> adminFields = actor.getAdminFields();
		List<NotificationInfo> notifInfos = actor.getNotificationInfos();
		List<FieldEntity> administrationFields = new Vector<FieldEntity>();		

		for (Iterator<FieldGroup> it1 = actor.getGroups().iterator(); it1.hasNext();) {
			FieldGroup currentGroup = (FieldGroup) it1.next();
			for (Iterator<FieldEntity> it2 = currentGroup.getFields().iterator(); it2.hasNext();) {
				FieldEntity currentField = (FieldEntity) it2.next();
				if (isAdminOrNotifField(currentField,adminFields,notifInfos)) {
					administrationFields.add(currentField);
				}
			}									
		}						
		return 	administrationFields;	
	}
	
	/**
	 * Returns the name of the first Actor of a CardEntity list
	 */	
	public static String getFirstActorName(List<CardEntity> entities){
				
		for (CardEntity entity:entities) {
			if (entity instanceof Actor)
				return entity.getName();
		}					
		return 	null;	
	}
	
	/**
	 * Returns the list of visible fields (isHidden==false) for a card entity
	 */		
	public static List<FieldEntity> getVisibleFields(CardEntity entity) {

		List<FieldEntity> visibleFields = new Vector<FieldEntity>();		

		for (Iterator<FieldGroup> it1 = entity.getGroups().iterator(); it1.hasNext();) {
			FieldGroup currentGroup = (FieldGroup) it1.next();
			for (Iterator<FieldEntity> it2 = currentGroup.getFields().iterator(); it2.hasNext();) {
				FieldEntity currentField = (FieldEntity) it2.next();
				if (!currentField.isHidden()) {
					visibleFields.add(currentField);
				}
			}									
		}						
		return 	visibleFields;	
	}	
	
	
	/**
	 * Compares one field with a field list to see if the field is used as a condition for the visibility of one of the fields of the list
	 * @return true if a field is used as a FieldVisibility condition (the visibility of another entity field depends
	 * on the value of the field passed as parameter)
	 */	
	public static boolean isEntityVisibilityParameter(FieldEntity field, List<FieldEntity> cardFields){
		for (Iterator<FieldEntity> it = cardFields.iterator(); it.hasNext();) {
			FieldEntity currentField = (FieldEntity) it.next();
			if (!currentField.equals(field) && currentField.getFieldDependentVisibility()!=null && currentField.getFieldDependentVisibility().size()>0){	
				if (isFieldVisibilityParameter(field,currentField.getFieldDependentVisibility()))
					return true;								
			}
		}		
		return false;
	}	
	
	/**
	 * Compares one field with a FieldDependentVisibility list to see if the field is used as a condition in one of the FieldDependentVisibility of the list
	 * @return true if a field is used as a FieldVisibility condition (the visibility of another entity field depends
	 * on the value of the field passed as parameter)
	 */
	public static boolean isFieldVisibilityParameter(FieldEntity field, List<FieldDependentVisibility> fieldConditions){				
		for (Iterator<FieldDependentVisibility> it = fieldConditions.iterator(); it.hasNext();) {
			FieldDependentVisibility condition = (FieldDependentVisibility)it.next();					
			FieldEntity conditionField = condition.getDependencyField();
			if (conditionField.equals(field)) {
				return true;
			}
		}									
		return false;
	}
	

	
	/**
	 * @param groups Entity groups
	 * @return list of roles that can access the entity which owns the groups
	 */
	public static List<String> getEntityAccessibilityRoles(List<FieldGroup> groups) {
		List<String> entityAccessibilityRoles = new Vector<String>();
		
		for (Iterator<FieldGroup> it = groups.iterator(); it.hasNext();) {
			FieldGroup currentGroup = (FieldGroup)it.next();
			List<Role> readerRoles = currentGroup.getReaders();
			for (Iterator<Role> it2 = readerRoles.iterator(); it2.hasNext();) {
				Role currentRole = (Role)it2.next();
				if (!entityAccessibilityRoles.contains(currentRole.getName())) {
					entityAccessibilityRoles.add(currentRole.getName());					
				}			
			}
			List<Role> writerRoles = currentGroup.getWriters();
			for (Iterator<Role> it3 = writerRoles.iterator(); it3.hasNext();) {
				Role currentRole = (Role)it3.next();
				if (!entityAccessibilityRoles.contains(currentRole.getName())) {
					entityAccessibilityRoles.add(currentRole.getName());					
				}			
			}				
		}
		return entityAccessibilityRoles;
	}
	
	/**
	 * Returns the User Interface description format object for a given CardEntity
	 * @param searchedCardEntity the CardEntity for which the format is searched
	 * @param project the project
	 * @return the CardEntityUIFormat object for the searched CardEntity
	 */
	public static CardEntityUIFormat getCardEntityUIFormat(CardEntity searchedCardEntity, Project project) {
		
		List<CardEntityUIFormat> cardEntityFormats = project.getEntityUIFormats();
		
		for(CardEntityUIFormat format:cardEntityFormats) {
			if(searchedCardEntity.getShortName().equals(format.getEntity().getShortName())) {
				return format;
			}
		}
		return null;	
	}
	
	/**
	 * Returns the RelationFieldEntities whose value list 
	 * depends on the value of a given RelationFieldEntity
	 * @param searchedField the parent RelationFieldEntity
	 * @param entityFields the child RelationFieldEntities 
	 * whose value lists depend on the value of
	 * the parent RelationFieldEntity
	 * @return List of RelationFieldEntities whose value list 
	 * depends on the value of a given RelationFieldEntity
	 */
	public static List<RelationFieldEntity> getHierarchicalFilterChilds (RelationFieldEntity searchedField, List<FieldEntity> entityFields) {
		
		List<RelationFieldEntity> result = new Vector<RelationFieldEntity>();
		
		//if(searchedField.getCardinality()==1) {
			for (FieldEntity field:entityFields) {
				if(field instanceof RelationFieldEntity ) {
					RelationFieldEntity relationField = (RelationFieldEntity) field;
					if (! (relationField.getCardinality()==1 && CommonGenHelper.getOppositeCardinality(relationField)==1)) {					
						if (relationField.getRelationHierarchicalFilter()!=null && relationField.getRelationHierarchicalFilter().size()==2) {
							if (relationField.getRelationHierarchicalFilter().get(0).equals(searchedField)) {
								result.add(relationField);
								//System.out.println("parent = " + searchedField.getName()  + ", child = " + relationField.getName());
							}
						}
					}
				}
			}			
		//}
		return result;
	}
	
	/**
	 * Checks that a field has at least one child (a field whose visibility depends on the value of the searchedField) in a list of fields
	 * @param searchedField
	 * @param entityFields
	 * @return
	 */
	public static boolean hasChildsWithVisibilityDependent(FieldEntity searchedField, List<FieldEntity> entityFields) {

		for (FieldEntity field : entityFields) {
			if (field.getFieldDependentVisibility() != null	&& field.getFieldDependentVisibility().size() > 0)
				for (FieldDependentVisibility fieldDependentVisibility : field.getFieldDependentVisibility()) {
					if (fieldDependentVisibility.getDependencyField() != null && fieldDependentVisibility.getDependencyField().getName().equals(searchedField.getName()))
						return true;
				}
		}
		return false;
	}
	
	/**
	 * Checks if the fields that are referenced in a list of FieldDependentVisibility are contained in a list of fields
	 * @param fieldDependentVisibilityList list of FieldDependentVisibility
	 * @param entityFields a list of fields
	 * @return
	 */
	public static boolean FieldDependentVisibilityContainedInList(List<FieldDependentVisibility> fieldDependentVisibilityList, List<FieldEntity> entityFields) {
				
		boolean containsField = true;
		for (FieldDependentVisibility fieldDependentVisibility : fieldDependentVisibilityList) {
			if(!entityFields.contains(fieldDependentVisibility.getDependencyField()))
				containsField= false;			
		}
		return containsField;
	}
	
	/**
	 * Returns the CardEntities that do not belong to a Thema
	 * @param project the model project
	 * @return List of CardEntities that do not belong to a Thema
	 */
	public static List<CardEntity> getCardEntitiesWithoutThema(Project project) {
		
		List<CardEntity> result = new Vector<CardEntity>();
		List<CardEntity> entities = project.getEntities();
		List<Thema> themas = project.getThemas();
			
		for(CardEntity entity:entities) {
			if (entity.isTopLevel()) {
				boolean isInThema = false;
				for(Thema thema: themas) {
					if (thema.getEntities().contains(entity))
						isInThema = true;
				}
				if (!isInThema)
					result.add(entity);				
			}
		}
		return result;
	}
	
	
	public static boolean logMessage(String message){
		System.err.println(message);
		return true;
	}
}
