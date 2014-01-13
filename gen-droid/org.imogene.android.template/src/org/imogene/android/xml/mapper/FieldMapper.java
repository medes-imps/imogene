package org.imogene.android.xml.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.net.Uri;

public class FieldMapper implements Mapper {

	private Mapper mapper;
	private Map<String, String> aliasToFieldMap = new HashMap<String, String>();
	private Map<String, String> fieldToAliasMap = new HashMap<String, String>();
	private Set<String> fieldsToOmit = new HashSet<String>();
	
	public FieldMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	
	public void addFieldAlias(String alias, String fieldName) {
		aliasToFieldMap.put(alias, fieldName);
		fieldToAliasMap.put(fieldName, alias);
	}
	
	
	public void omitField(String fieldName) {
		fieldsToOmit.add(fieldName);
	}

	@Override
	public Class<?> realClass(String elementName) {
		return mapper.realClass(elementName);
	}

	@Override
	public String serializedClass(Class<?> type) {
		return mapper.serializedClass(type);
	}

	@Override
	public String realMember(Class<?> type, String serialized) {
		return aliasToFieldMap.get(serialized);
	}

	@Override
	public String serializedMember(Class<?> type, String memberName) {
		return fieldToAliasMap.get(memberName);
	}
	
	@Override
	public Uri realUri(String elementName) {
		return mapper.realUri(elementName);
	}

	@Override
	public String serializeUri(Uri uri) {
		return mapper.serializeUri(uri);
	}
	
	@Override
	public boolean shouldSerializeMember(Class<?> definedIn, String fieldName) {
		return !fieldsToOmit.contains(fieldName);
	}

	@Override
	public Mapper lookupMapperOfType(Class<?> type) {
		return mapper.lookupMapperOfType(type);
	}

	@Override
	public boolean isImmutableValueType(Class<?> type) {
		return mapper.isImmutableValueType(type);
	}

}
