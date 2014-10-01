package org.imogene.android.common.filter;

import org.imogene.android.Constants;
import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogBeanImpl;
import org.imogene.android.database.sqlite.ClientFilterCursor;
import org.imogene.android.database.sqlite.ImogOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import fr.medes.android.database.sqlite.stmt.QueryBuilder;
import fr.medes.android.util.content.ContentUrisUtils;
import fr.medes.android.xml.annotation.XmlAlias;

@XmlAlias("org.imogene.lib.common.filter.ClientFilter")
public class ClientFilter extends ImogBeanImpl {

	public static interface Columns extends ImogBean.Columns {
		public static final String TABLE_NAME = "clientfilter";
		public static final String BEAN_TYPE = "CLTFIL";
		public static final Uri CONTENT_URI = ContentUrisUtils.buildUriForFragment(Constants.AUTHORITY, TABLE_NAME);

		public static final String USERID = "userId";
		public static final String TERMINALID = "terminalId";
		public static final String CARDENTITY = "cardEntity";
		public static final String ENTITYFIELD = "entityField";
		public static final String OPERATOR = "operator";
		public static final String FIELDVALUE = "fieldValue";
		public static final String DISPLAY = "display";
		public static final String ISNEW = "isNew";
	}

	public static final String MULTIENUM_OPERATOR_EQUAL = "equalMultiEnum";
	public static final String STRING_OPERATOR_CONTAINS = "contains";
	public static final String STRING_OPERATOR_STARTWITH = "startsWith";
	public static final String STRING_OPERATOR_EQUAL = "equalString";
	public static final String STRING_OPERATOR_DIFF = "diffString";
	public static final String STRING_OPERATOR_INF = "infString";
	public static final String STRING_OPERATOR_SUP = "supString";
	public static final String DATE_OPERATOR_BEFORE = "before";
	public static final String DATE_OPERATOR_AFTER = "after";
	public static final String DATE_OPERATOR_EQUAL = "equalDate";
	public static final String INT_OPERATOR_EQUAL = "equalInt";
	public static final String INT_OPERATOR_SUP = "supInt";
	public static final String INT_OPERATOR_INF = "infInt";
	public static final String FLOAT_OPERATOR_EQUAL = "equalFloat";
	public static final String FLOAT_OPERATOR_SUP = "supFloat";
	public static final String FLOAT_OPERATOR_INF = "infFloat";
	public static final String RELATIONFIELD_OPERATOR_EQUAL = "equalRelationField";
	public static final String RELATIONFIELD_OPERATOR_EQUAL_NULL = "equalNull";
	public static final String BOOLEAN_OPERATOR_EQUAL = "equalBoolean";
	public static final String GEOFILTER_OPERATOR = "geoFilter";
	public static final String OPERATOR_ISNULL = "isNull";
	public static final String OPERATOR_UNDEF = "undef";

	/**
	 * For user interface only, for db query, converted into a before and after
	 */
	protected static final String DATE_OPERATOR_BETWEEN = "between";
	/**
	 * For user interface only, for db query, converted into a before and after
	 */
	protected static final String INT_OPERATOR_BETWEEN = "betweenInt";
	/**
	 * For user interface only, for db query, converted into a before and after
	 */
	protected static final String FLOAT_OPERATOR_BETWEEN = "betweenFloat";

	public interface Creator<T extends ClientFilter> {
		public T create(Context context, String userId, String terminalId, String entity, String field);
	}

	protected static abstract class DefaultCreator<T extends ClientFilter> implements Creator<T> {

		@Override
		public final T create(Context context, String userId, String terminalId, String entity, String field) {
			ImogOpenHelper helper = ImogOpenHelper.getHelper();
			QueryBuilder builder = helper.queryBuilder(Columns.CONTENT_URI);
			builder.where().eq(Columns.USERID, userId).and().eq(Columns.TERMINALID, terminalId).and()
					.eq(Columns.CARDENTITY, entity).and().eq(Columns.ENTITYFIELD, field);

			T filter;
			ClientFilterCursor c = (ClientFilterCursor) builder.query();
			if (c.getCount() == 1) {
				c.moveToFirst();
				filter = newFilter(c);
			} else {
				filter = newFilter();
				filter.setUserId(userId);
				filter.setTerminalId(terminalId);
				filter.setCardEntity(entity);
				filter.setEntityField(field);
			}
			c.close();
			return filter;
		}

		protected abstract T newFilter();

		protected abstract T newFilter(ClientFilterCursor c);
	}

	@XmlAlias("userId")
	private String userId = null;

	@XmlAlias("terminalId")
	private String terminalId = null;

	@XmlAlias("cardEntity")
	private String cardEntity = null;

	@XmlAlias("entityField")
	private String entityField = null;

	@XmlAlias("operator")
	private String operator = null;

	@XmlAlias("fieldValue")
	private String fieldValue = null;

	@XmlAlias("display")
	private String display = null;

	@XmlAlias("isNew")
	private Boolean isNew = null;

	public ClientFilter() {
	}

	public ClientFilter(ClientFilterCursor cursor) {
		super(cursor);
		userId = cursor.getUserId();
		terminalId = cursor.getTerminalId();
		cardEntity = cursor.getCardEntity();
		entityField = cursor.getEntityField();
		operator = cursor.getOperator();
		fieldValue = cursor.getFieldValue();
		display = cursor.getDisplay();
		isNew = cursor.getIsNew();
	}

	public final String getUserId() {
		return userId;
	}

	public final String getTerminalId() {
		return terminalId;
	}

	public final String getCardEntity() {
		return cardEntity;
	}

	public final String getEntityField() {
		return entityField;
	}

	public final String getOperator() {
		return operator;
	}

	public final String getFieldValue() {
		return fieldValue;
	}

	public final String getDisplay() {
		return display;
	}

	public final Boolean getIsNew() {
		return isNew;
	}

	public final void setUserId(String userId) {
		this.userId = userId;
	}

	public final void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public final void setCardEntity(String cardEntity) {
		this.cardEntity = cardEntity;
	}

	public final void setEntityField(String entityField) {
		this.entityField = entityField;
	}

	public final void setOperator(String operator) {
		this.operator = operator;
	}

	public final void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public final void setDisplay(String display) {
		this.display = display;
	}

	public final void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	protected void preCommit(Context context) {
		preCommit();
	}

	protected void preCommit() {
		isNew = Boolean.TRUE;
	}

	@Override
	protected final void addValues(Context context, ContentValues values) {
		values.put(Columns.USERID, userId);
		values.put(Columns.TERMINALID, terminalId);
		values.put(Columns.CARDENTITY, cardEntity);
		values.put(Columns.ENTITYFIELD, entityField);
		values.put(Columns.OPERATOR, operator);
		values.put(Columns.FIELDVALUE, fieldValue);
		values.put(Columns.DISPLAY, display);
		if (isNew != null)
			values.put(Columns.ISNEW, isNew.toString());
		else
			values.putNull(Columns.ISNEW);
	}

	@Override
	public final void reset() {
		userId = null;
		terminalId = null;
		cardEntity = null;
		entityField = null;
		operator = null;
		fieldValue = null;
		display = null;
		isNew = null;
	}

	@Override
	public void prepareForSave(Context context) {
		prepareForSave(context, Columns.BEAN_TYPE);
	}

	@Override
	public Uri saveOrUpdate(Context context) {
		return saveOrUpdate(context, Columns.CONTENT_URI);
	}
}
