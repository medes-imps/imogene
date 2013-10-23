package org.imogene.model.validation.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.imogene.model.core.Actor;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.RelationFieldEntity;


public class CheckActorFields extends AbstractMedanyModelConstraint {

	private static String COLUMN_FIELD_NOT_IN_ADMIN_FIELDS = "The column field \"%FIELD_NAME%\" of the actor \"%ENTITY_NAME%\" is not in the admin fields";

	private static String RELATION_IN_ADMIN_FIELDS = "The admin field \"%FIELD_NAME%\" of the actor \"%ENTITY_NAME%\" is a relation field, admin fields cannot contain relations";
	
	@Override
	public IStatus validate(IValidationContext ctx) {
		try {
			if (ctx.getTarget() instanceof Actor) {
				Actor actor = (Actor) ctx.getTarget();
				for (FieldEntity field : actor.getAdminFields())
					if (field instanceof RelationFieldEntity)
						return ctx.createFailureStatus(new Object[] {formatMessage(RELATION_IN_ADMIN_FIELDS, actor, field)});
				for (FieldEntity field : actor.getAdminFields())
					if (!actor.getColumnFields().contains(field))
						return ctx.createFailureStatus(new Object[] {formatMessage(COLUMN_FIELD_NOT_IN_ADMIN_FIELDS, actor, field)});
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ctx.createSuccessStatus();
		}
		return ctx.createSuccessStatus();
	}

}
