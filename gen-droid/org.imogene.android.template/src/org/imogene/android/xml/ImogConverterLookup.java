package org.imogene.android.xml;

import org.imogene.android.xml.converters.AssociationConverter;
import org.imogene.android.xml.converters.BinaryConverter;
import org.imogene.android.xml.converters.CollectionConverter;
import org.imogene.android.xml.converters.ContentConverter;
import org.imogene.android.xml.converters.DynamicFieldTypeConverter;
import org.imogene.android.xml.converters.LocalizedTextConverter;

import android.content.Context;
import fr.medes.android.xml.DefaultConverterLookup;
import fr.medes.android.xml.mapper.Mapper;

public class ImogConverterLookup extends DefaultConverterLookup {

	public ImogConverterLookup(Context context, Mapper mapper) {
		super(context, mapper);
		converters.add(new AssociationConverter(context, mapper));
		converters.add(new CollectionConverter(context, mapper));
		converters.add(new ContentConverter(context));
		converters.add(new DynamicFieldTypeConverter());
		converters.add(new LocalizedTextConverter());
		converters.add(new BinaryConverter(this));
	}

}
