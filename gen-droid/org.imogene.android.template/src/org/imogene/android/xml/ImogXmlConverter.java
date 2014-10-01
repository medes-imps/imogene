package org.imogene.android.xml;

import java.io.IOException;
import java.lang.reflect.Field;

import org.imogene.android.common.entity.ImogBean;
import org.imogene.android.common.entity.ImogHelper;
import org.imogene.android.common.model.EntityInfo;
import org.imogene.android.xml.converters.AssociationConverter;
import org.imogene.android.xml.converters.BinaryConverter;
import org.imogene.android.xml.converters.CollectionConverter;
import org.imogene.android.xml.converters.ContentConverter;
import org.imogene.android.xml.converters.DynamicFieldTypeConverter;
import org.imogene.android.xml.converters.LocalizedTextConverter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import fr.medes.android.util.annotation.ReflectionUtils;
import fr.medes.android.util.annotation.ReflectionUtils.FieldCallback;
import fr.medes.android.xml.DefaultConverterLookup;
import fr.medes.android.xml.annotation.XmlAlias;
import fr.medes.android.xml.annotation.XmlConverter;
import fr.medes.android.xml.converters.BooleanConverter;
import fr.medes.android.xml.converters.ByteArrayConverter;
import fr.medes.android.xml.converters.Converter;
import fr.medes.android.xml.converters.ConverterLookup;
import fr.medes.android.xml.converters.DateConverter;
import fr.medes.android.xml.converters.EnumConverter;
import fr.medes.android.xml.converters.EnumMultiConverter;
import fr.medes.android.xml.converters.FloatConverter;
import fr.medes.android.xml.converters.GpsConverter;
import fr.medes.android.xml.converters.IntegerConverter;
import fr.medes.android.xml.converters.LongConverter;
import fr.medes.android.xml.converters.StringConverter;
import fr.medes.android.xml.mapper.ClassMapper;

public class ImogXmlConverter {

	private Context context;
	private ConverterLookup fieldLookup;
	private ConverterLookup classLookup;
	private ClassMapper mapper = new ClassMapper();

	public ImogXmlConverter(Context context) {
		this.context = context;
		fieldLookup = new DefaultConverterLookup();
		fieldLookup.addConverter(new BooleanConverter());
		fieldLookup.addConverter(new ByteArrayConverter());
		fieldLookup.addConverter(new DateConverter());
		fieldLookup.addConverter(new EnumConverter());
		fieldLookup.addConverter(new EnumMultiConverter(context));
		fieldLookup.addConverter(new FloatConverter());
		fieldLookup.addConverter(new GpsConverter());
		fieldLookup.addConverter(new IntegerConverter());
		fieldLookup.addConverter(new LongConverter());
		fieldLookup.addConverter(new StringConverter());
		fieldLookup.addConverter(new AssociationConverter(context, mapper));
		fieldLookup.addConverter(new CollectionConverter(context, mapper));
		fieldLookup.addConverter(new ContentConverter(context));
		fieldLookup.addConverter(new DynamicFieldTypeConverter());
		fieldLookup.addConverter(new LocalizedTextConverter());

		classLookup = new DefaultConverterLookup();
		classLookup.addConverter(new BinaryConverter(fieldLookup));

		for (EntityInfo info : ImogHelper.getInstance().getEntityInfos()) {
			processAnnotations(info.clazz);
		}
	}

	public void processAnnotations(Class<?> type) {
		XmlAlias alias = type.getAnnotation(XmlAlias.class);
		if (alias != null) {
			mapper.addClassAlias(alias.value(), type);
		} else {
			mapper.addClassAlias(type.getName(), type);
		}
	}

	public void serialize(final XmlSerializer serializer, Object object) throws IllegalArgumentException,
			IllegalStateException, IOException {
		serializer.startTag(null, mapper.serializedClass(object.getClass()));
		Converter converter = classLookup.lookupConverterForType(object.getClass());
		if (converter != null) {
			converter.serialize(serializer, object);
		} else {
			ReflectionUtils.doWithFields(object.getClass(), new SerializationFieldCallback(serializer, object));
		}
		serializer.endTag(null, mapper.serializedClass(object.getClass()));
	}

	public int parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		int count = 0;
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			Class<?> clazz = mapper.realClass(parser.getName());
			if (clazz == null) {
				continue;
			}
			ImogBean bean = null;
			try {
				bean = (ImogBean) clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean == null) {
				continue;
			}

			bean.reset();
			bean.setFlagRead(false);
			bean.setFlagSynchronized(true);

			parse(parser, bean);

			bean.saveOrUpdate(context);
			count++;
		}
		return count;
	}

	public void parse(XmlPullParser parser, Object obj) throws XmlPullParserException, IOException {
		String elementName = parser.getName();
		while (parser.next() != XmlPullParser.END_TAG || !elementName.equals(parser.getName())) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			// Retrieve the real field name (if using aliases)
			String fieldName = mapper.realMember(obj.getClass(), parser.getName());
			if (fieldName == null) {
				continue;
			}
			Field field = null;
			try {
				// try to retrieve the field
				field = ReflectionUtils.getField(obj.getClass(), fieldName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (field == null) {
				continue;
			}
			// TODO Check this : Now that relations are using real beans the mapper.isImmutableValueType() is returning
			// false and the parser is expecting to parse an object included in an other
			// if (!mapper.isImmutableValueType(field.getType())) {
			// field.setAccessible(true);
			// Object value = null;
			// try {
			// value = field.get(obj);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// if (value != null) {
			// parse(parser, value);
			// }
			// continue;
			// }

			Converter fc = null;

			XmlConverter converter = field.getAnnotation(XmlConverter.class);
			if (converter != null) {
				fc = fieldLookup.lookupConverterOfType(converter.value());
				if (fc != null) {
					fc.setInteger(converter.integer());
				}
			} else {
				fc = fieldLookup.lookupConverterForType(field.getType());
			}
			if (fc == null) {
				continue;
			}

			if (obj instanceof ImogBean) {
				fc.setString(((ImogBean) obj).getId());
			}

			Object value = fc.parse(parser);
			field.setAccessible(true);
			try {
				field.set(obj, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class SerializationFieldCallback implements FieldCallback {

		private XmlSerializer serializer;
		private Object object;

		public SerializationFieldCallback(XmlSerializer serializer, Object object) {
			this.serializer = serializer;
			this.object = object;
		}

		@Override
		public void doWith(Field field) {
			String fieldName = field.getName();
			if (!mapper.shouldSerializeMember(object.getClass(), field.getName())) {
				return;
			}

			Converter fc = null;
			XmlConverter converter = field.getAnnotation(XmlConverter.class);
			if (converter != null) {
				fc = fieldLookup.lookupConverterOfType(converter.value());
				if (fc != null) {
					fc.setInteger(converter.integer());
				}
			} else {
				fc = fieldLookup.lookupConverterForType(field.getType());
			}

			if (fc == null) {
				return;
			}

			if (object instanceof ImogBean) {
				fc.setString(((ImogBean) object).getId());
			}

			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value == null) {
				return;
			}

			String elementMember = mapper.serializedMember(object.getClass(), fieldName);

			try {
				serializer.startTag(null, elementMember);
				fc.serialize(serializer, value);
				serializer.endTag(null, elementMember);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
