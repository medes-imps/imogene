package org.imogene.android.oaw.generator;

import java.util.ArrayList;
import java.util.List;

import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.FieldDependentVisibility;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.GeoField;
import org.imogene.model.core.Project;
import org.imogene.model.core.RelationFieldEntity;
import org.imogene.model.core.TextField;
import org.imogene.model.core.Thema;

/**
 */
public class MedooAndroidGenHelper {

	public static int increment = 0;

	public static void log(Object o) {
		System.out.println(o);
	}

	public static int getDatabaseVersion() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static boolean hasLocalizedField(CardEntity entity) {
		for (FieldGroup group : entity.getGroups()) {
			for (FieldEntity field : group.getFields()) {
				if (field instanceof TextField) {
					if (((TextField) field).isTranslatable()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean hasDependancyField(FieldEntity field) {
		for (FieldGroup group : field.getParentCard().getGroups()) {
			for (FieldEntity f : group.getFields()) {
				for (FieldDependentVisibility fdv : f.getFieldDependentVisibility()) {
					if (fdv.getDependencyField().getShortName().equals(field.getShortName()))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean hasHelp(List<Description> list) {
		for (Description des : list) {
			String help = des.getHelp();
			if (help != null && !help.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static List<RelationFieldEntity> getMoreFields(List<FieldEntity> fields) {
		ArrayList<RelationFieldEntity> result = new ArrayList<RelationFieldEntity>();
		for (FieldEntity field : fields) {
			if (!field.isHidden()) {
				if (field instanceof RelationFieldEntity)
					if (((RelationFieldEntity) field).getOppositeRelationField() != null
							&& ((RelationFieldEntity) field).getCardinality() != 1) {
						result.add((RelationFieldEntity) field);
					}
			}
		}
		return result;
	}

	public static boolean hasMiscellaneousThema(List<CardEntity> entities, List<Thema> themas) {
		for (CardEntity entity : entities) {
			if (isInMiscellaneousThema(themas, entity))
				return true;
		}
		return false;
	}

	public static boolean isInMiscellaneousThema(List<Thema> themas, CardEntity entity) {
		if (!entity.isTopLevel())
			return false;
		for (Thema thema : themas) {
			if (thema.getEntities().contains(entity))
				return false;
		}
		return true;
	}

	public static List<FieldEntity> getMainFields(CardEntity entity) {
		ArrayList<FieldEntity> main = new ArrayList<FieldEntity>();
		for (FieldEntity field : entity.getMainFields())
			if (!entity.getSecondaryFields().contains(field))
				main.add(field);
		return main;
	}

	public static List<RelationFieldEntity> getFilteredFields(CardEntity entity, FieldEntity filter) {
		ArrayList<RelationFieldEntity> result = new ArrayList<RelationFieldEntity>();
		if (filter instanceof RelationFieldEntity)
			for (FieldGroup group : entity.getGroups())
				for (FieldEntity field : group.getFields())
					if (field instanceof RelationFieldEntity) {
						RelationFieldEntity relation = (RelationFieldEntity) field;
						if (relation.getRelationHierarchicalFilter() != null
								&& relation.getRelationHierarchicalFilter().size() == 2)
							if (relation.getRelationHierarchicalFilter().get(0).equals(filter))
								result.add(relation);
					}
		return result;
	}

	public static Integer mod2(Integer i) {
		return i % 2;
	}

	public static boolean hasFilter(Project project) {
		for (CardEntity entity : project.getEntities()) {
			if (hasFilter(entity)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasFilter(CardEntity entity) {
		return entity.isClientPeriodFilterable() || entity.getClientFilterFields().size() > 0;
	}

	public static List<FieldGroup> getFilteredGroups(List<FieldEntity> filters) {
		ArrayList<FieldGroup> result = new ArrayList<FieldGroup>();
		for (FieldEntity field : filters)
			if (!result.contains(field.getParentGroup()))
				result.add(field.getParentGroup());
		return result;
	}

	public static int getGeoType(GeoField field) {
		return field.getType().getValue();
	}

	public static int next() {
		if (increment == 0) {
			increment = (int) (System.currentTimeMillis() / 1000);
		}
		return increment++;
	}

	public static List<FieldEntity> getNestedFields(CardEntity entity) {
		if (entity.getNestedFields() != null && entity.getNestedFields().size() > 0) {
			return entity.getNestedFields();
		} else {
			ArrayList<FieldEntity> fields = new ArrayList<FieldEntity>();
			for (FieldGroup group : entity.getGroups()) {
				fields.addAll(group.getFields());
			}
			return fields;
		}
	}

	public static boolean isNested(Project project, CardEntity entity) {
		for (CardEntity ce : project.getEntities()) {
			for (FieldGroup group : ce.getGroups()) {
				for (FieldEntity field : group.getFields()) {
					if (field instanceof RelationFieldEntity && ((RelationFieldEntity) field).getEntity() == entity
							&& ((RelationFieldEntity) field).isNestedForm()
							&& ((RelationFieldEntity) field).getCardinality() == 1) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
