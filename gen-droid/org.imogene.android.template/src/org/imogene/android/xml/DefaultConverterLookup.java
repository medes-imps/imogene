package org.imogene.android.xml;

import java.util.HashSet;
import java.util.Set;

import org.imogene.android.xml.converters.AssociationConverter;
import org.imogene.android.xml.converters.BinaryConverter;
import org.imogene.android.xml.converters.BooleanConverter;
import org.imogene.android.xml.converters.ByteArrayConverter;
import org.imogene.android.xml.converters.CollectionConverter;
import org.imogene.android.xml.converters.ContentConverter;
import org.imogene.android.xml.converters.Converter;
import org.imogene.android.xml.converters.ConverterLookup;
import org.imogene.android.xml.converters.DateConverter;
import org.imogene.android.xml.converters.DynamicFieldTypeConverter;
import org.imogene.android.xml.converters.EnumConverter;
import org.imogene.android.xml.converters.EnumMultiConverter;
import org.imogene.android.xml.converters.FloatConverter;
import org.imogene.android.xml.converters.GpsConverter;
import org.imogene.android.xml.converters.IntegerConverter;
import org.imogene.android.xml.converters.LocalizedTextConverter;
import org.imogene.android.xml.converters.LongConverter;
import org.imogene.android.xml.converters.RolesConverter;
import org.imogene.android.xml.converters.StringConverter;
import org.imogene.android.xml.mapper.Mapper;

import android.content.Context;

public class DefaultConverterLookup implements ConverterLookup {
	
	private Set<Converter> converters = new HashSet<Converter>();
	
	public DefaultConverterLookup(Context context, Mapper mapper) {
		converters.add(new AssociationConverter(context, mapper));
		converters.add(new BooleanConverter());
		converters.add(new ByteArrayConverter());
		converters.add(new CollectionConverter(context, mapper));
		converters.add(new ContentConverter(context));
		converters.add(new DateConverter());
		converters.add(new DynamicFieldTypeConverter());
		converters.add(new EnumConverter());
		converters.add(new EnumMultiConverter(context));
		converters.add(new FloatConverter());
		converters.add(new GpsConverter());
		converters.add(new IntegerConverter());
		converters.add(new LocalizedTextConverter());
		converters.add(new LongConverter());
		converters.add(new RolesConverter());
		converters.add(new StringConverter());
		converters.add(new BinaryConverter(this));
	}

	@Override
	public Converter lookupConverterOfType(Class<? extends Converter> clazz) {
		for (Converter converter : converters) {
			if (converter.getClass().equals(clazz)) {
				return converter;
			}
		}
		return null;
	}
	
	@Override
	public Converter lookupConverterForType(Class<?> clazz) {	
		for (Converter converter : converters) {
			if (converter.canConvert(clazz)) {
				return converter;
			}
		}
		return null;
	}

}
