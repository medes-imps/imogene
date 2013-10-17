package org.imogene.lib.common.criteria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.DaoUtil;

/**
 * This class enables to convert the Imogene criterions to the JPA criterions and the inverse.
 * 
 * @author Medes-IMPS
 */
public class DaoUtil {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * @param criterion
	 * @param builder
	 * @param root
	 * @return
	 */
	public static <T> Predicate toPredicate(ImogCriterion criterion, CriteriaBuilder builder, Root<T> root) {
		if (criterion instanceof BasicCriteria) {
			Predicate predicate = convert((BasicCriteria) criterion, builder, root);
			if (predicate != null) {
				return predicate;
			}
		} else if (criterion instanceof ImogJunction) {
			Predicate junction = null;
			if (criterion instanceof ImogDisjunction) {
				junction = builder.disjunction();
			} else {
				junction = builder.conjunction();
			}
			for (ImogCriterion c : ((ImogJunction) criterion).getCriterions()) {
				addCriterion(junction, c, builder, root);
			}
			return junction;
		}
		return builder.conjunction();
	}

	/**
	 * @param predicate
	 * @param criterion
	 * @param builder
	 * @param root
	 */
	public static <T> void addCriterion(Predicate predicate, ImogCriterion criterion, CriteriaBuilder builder,
			Root<T> root) {
		if (criterion == null) {
			return;
		}
		if (criterion instanceof BasicCriteria) {
			Predicate p = convert((BasicCriteria) criterion, builder, root);
			if (p != null) {
				predicate.getExpressions().add(p);
			}
		} else if (criterion instanceof ImogJunction) {
			Predicate junction = builder.conjunction();
			if (criterion instanceof ImogDisjunction) {
				junction = builder.disjunction();
			}
			for (ImogCriterion c : ((ImogJunction) criterion).getCriterions()) {
				addCriterion(junction, c, builder, root);
			}
			predicate.getExpressions().add(junction);
		}
	}

	/**
	 * @param criteria
	 * @param builder
	 * @param root
	 * @return
	 */
	private static <T> Predicate convert(BasicCriteria criteria, CriteriaBuilder builder, Root<T> root) {
		String property = criteria.getField();
		String operator = criteria.getOperation();
		String value = criteria.getValue();
		if (operator.equals(CriteriaConstants.STRING_OPERATOR_CONTAINS)) {
			return builder.like(builder.lower(DaoUtil.<String> getCascadeRoot(root, property)),
					"%" + value.toLowerCase() + "%");
		} else if (operator.equals(CriteriaConstants.STRING_OPERATOR_EQUAL)) {
			return builder.equal(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.STRING_OPERATOR_DIFF)) {
			return builder.notLike(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.STRING_OPERATOR_INF)) {
			return builder.lessThanOrEqualTo(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.STRING_OPERATOR_SUP)) {
			return builder.greaterThanOrEqualTo(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.STRING_OPERATOR_STARTWITH)) {
			return builder.like(DaoUtil.<String> getCascadeRoot(root, property), value + "%");
		} else if (operator.equals(CriteriaConstants.DATE_OPERATOR_BEFORE)) {
			try {
				return builder.<Date> lessThanOrEqualTo(DaoUtil.<Date> getCascadeRoot(root, property),
						DATE_FORMAT.parse(value));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (operator.equals(CriteriaConstants.DATE_OPERATOR_AFTER)) {
			try {
				return builder.<Date> greaterThanOrEqualTo(DaoUtil.<Date> getCascadeRoot(root, property),
						DATE_FORMAT.parse(value));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (operator.equals(CriteriaConstants.DATE_OPERATOR_EQUAL)) {
			try {
				return builder.equal(DaoUtil.<Date> getCascadeRoot(root, property), DATE_FORMAT.parse(value));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (operator.equals(CriteriaConstants.INT_OPERATOR_EQUAL)) {
			return builder.equal(DaoUtil.<Integer> getCascadeRoot(root, property), Integer.valueOf(value));
		} else if (operator.equals(CriteriaConstants.INT_OPERATOR_SUP)) {
			return builder.ge(DaoUtil.<Integer> getCascadeRoot(root, property), Integer.valueOf(value));
		} else if (operator.equals(CriteriaConstants.INT_OPERATOR_INF)) {
			return builder.le(DaoUtil.<Integer> getCascadeRoot(root, property), Integer.valueOf(value));
		} else if (operator.equals(CriteriaConstants.FLOAT_OPERATOR_EQUAL)) {
			return builder.equal(DaoUtil.<Float> getCascadeRoot(root, property), Float.valueOf(value));
		} else if (operator.equals(CriteriaConstants.FLOAT_OPERATOR_SUP)) {
			return builder.ge(DaoUtil.<Float> getCascadeRoot(root, property), Float.valueOf(value));
		} else if (operator.equals(CriteriaConstants.FLOAT_OPERATOR_INF)) {
			return builder.le(DaoUtil.<Float> getCascadeRoot(root, property), Float.valueOf(value));
		} else if (operator.equals(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL)) {
			return builder.equal(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.RELATIONFIELD_OPERATOR_EQUAL_NULL)) {
			return DaoUtil.getCascadeRoot(root, property).isNull();
		} else if (operator.equals(CriteriaConstants.BOOLEAN_OPERATOR_EQUAL)) {
			return builder.equal(DaoUtil.<Boolean> getCascadeRoot(root, property), Boolean.valueOf(value));
		} else if (operator.equals(CriteriaConstants.OPERATOR_ISNULL)) {
			return DaoUtil.getCascadeRoot(root, property).isNull();
		} else if (operator.equals(CriteriaConstants.OPERATOR_ISNULL_OR_EMPTY)) {
			return builder.or(DaoUtil.getCascadeRoot(root, property).isNull(),
					builder.equal(DaoUtil.getCascadeRoot(root, property), ""));
		} else if (operator.equals(CriteriaConstants.ENUM_MULT_OPERATOR_CONTAINS_ALL)) {
			return builder.equal(DaoUtil.<String> getCascadeRoot(root, property), value);
		} else if (operator.equals(CriteriaConstants.ENUM_MULT_OPERATOR_CONTAINS_ONE_OF)) {
			String[] values = value.split(";");
			if (values.length > 0) {
				Predicate disjunction = builder.disjunction();
				final List<Expression<Boolean>> list = disjunction.getExpressions();
				for (String v : values) {
					list.add(builder.like(DaoUtil.<String> getCascadeRoot(root, property), v + ";%"));
					list.add(builder.like(DaoUtil.<String> getCascadeRoot(root, property), "%;" + v + ";%"));
					list.add(builder.like(DaoUtil.<String> getCascadeRoot(root, property), "%;" + v));
					list.add(builder.like(DaoUtil.<String> getCascadeRoot(root, property), v));
				}
				return disjunction;
			}
		} else if (operator.equals(CriteriaConstants.OPERATOR_ISNOTNULL)) {
			return DaoUtil.getCascadeRoot(root, property).isNotNull();
		}
		return null;
	}

	public static final <T> Path<T> getCascadeRoot(Path<?> root, String property) {
		if (property.contains(".")) {
			String[] split = property.split("\\.", 2);
			return DaoUtil.<T> getCascadeRoot(root.get(split[0]), split[1]);
		} else {
			return root.<T> get(property);
		}
	}
}
