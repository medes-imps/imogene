package org.imogene.android.xml;

import java.io.IOException;
import java.lang.reflect.Field;

import org.imogene.android.domain.ImogBean;
import org.imogene.android.domain.ImogHelper;
import org.imogene.android.domain.ImogHelper.ImogBeanCallback;
import org.imogene.android.util.annotation.ReflectionUtils;
import org.imogene.android.util.annotation.ReflectionUtils.FieldCallback;
import org.imogene.android.xml.annotation.XmlAlias;
import org.imogene.android.xml.annotation.XmlConverter;
import org.imogene.android.xml.converters.Converter;
import org.imogene.android.xml.converters.ConverterLookup;
import org.imogene.android.xml.mapper.ClassMapper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.net.Uri;

public class ImogXmlConverter {

	private Context context;
	private ConverterLookup lookup;
	private ClassMapper mapper = new ClassMapper();
	
	public ImogXmlConverter(Context context) {
		this.context = context;
		lookup = new DefaultConverterLookup(context, mapper);
		ImogHelper.getInstance().doWithImogBeans(new ImogBeanCallback() {
			
			@Override
			public void doWith(Class<? extends ImogBean> clazz, Uri uri) {
				processAnnotations(clazz, uri);
			}
		});
	}
	
	public void processAnnotations(Class<?> type) {
		XmlAlias alias = type.getAnnotation(XmlAlias.class);
		if (alias != null) {
			mapper.addClassAlias(alias.value(), type);
		} else {
			mapper.addClassAlias(type.getName(), type);
		}
	}

	public void processAnnotations(Class<?> type, Uri uri) {
		XmlAlias alias = type.getAnnotation(XmlAlias.class);
		if (alias != null) {
			mapper.addClassAlias(alias.value(), type, uri);
		} else {
			mapper.addClassAlias(type.getName(), type, uri);
		}
	}

	public void serialize(final XmlSerializer serializer, Object object) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag(null, mapper.serializedClass(object.getClass()));
		Converter converter = lookup.lookupConverterForType(object.getClass());
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
			bean.setUnread(true);
			bean.setSynchronized(true);
			
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
			String fieldName = mapper.realMember(obj.getClass(), parser.getName());
			if (fieldName == null) {
				continue;
			}
			Field field = null;
			try {
				field = ReflectionUtils.getField(obj.getClass(), fieldName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (field == null) {
				continue;
			}
			if (!mapper.isImmutableValueType(field.getType())) {
				field.setAccessible(true);
				Object value = null;
				try {
					value = field.get(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (value != null) {
					parse(parser, value);
				}
				continue;				
			}

			Converter fc = null;

			XmlConverter converter = field.getAnnotation(XmlConverter.class);
			if (converter != null) {
				fc = lookup.lookupConverterOfType(converter.value());
				if (fc != null) {
					fc.setInteger(converter.integer());					
				}
			} else {
				fc = lookup.lookupConverterForType(field.getType());
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
				fc = lookup.lookupConverterOfType(converter.value());
				if (fc != null) {
					fc.setInteger(converter.integer());
				}
			} else {
				fc = lookup.lookupConverterForType(field.getType());
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
