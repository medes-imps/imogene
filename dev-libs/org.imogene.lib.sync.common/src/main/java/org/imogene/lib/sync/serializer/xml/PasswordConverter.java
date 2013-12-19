package org.imogene.lib.sync.serializer.xml;

import org.apache.commons.codec.binary.Base64;
import org.imogene.encryption.EncryptionManager;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream converter for the User password property.
 * 
 * @author MEDES-IMPS
 */
public class PasswordConverter implements Converter {

	private EncryptionManager encryptionManager;

	public void setEncryptionManager(EncryptionManager manager) {
		encryptionManager = manager;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		String password = (String) value;
		byte[] encodedResult = encryptionManager.encrypt(password.getBytes());
		byte[] base64result = Base64.encodeBase64(encodedResult);
		writer.setValue(new String(base64result));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String encrypted = (String) reader.getValue();
		byte[] base64Result = Base64.decodeBase64(encrypted.getBytes());
		byte[] decodedResult = encryptionManager.decrypt(base64Result);
		return new String(decodedResult);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class toConvert) {
		return true;
	}

}
