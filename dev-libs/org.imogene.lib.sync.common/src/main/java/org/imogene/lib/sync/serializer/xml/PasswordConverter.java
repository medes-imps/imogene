package org.imogene.lib.sync.serializer.xml;

import org.imogene.encryption.EncryptionManager;
import org.imogene.lib.sync.serializer.xml.base64.Base64Coder;

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
		char[] base64Result = Base64Coder.encode(encodedResult);
		String result = new String(base64Result);
		writer.setValue(result);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String encrypted = (String) reader.getValue();
		byte[] base64Result = Base64Coder.decode(encrypted);
		byte[] decodedResult = encryptionManager.decrypt(base64Result);
		return new String(decodedResult);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class toConvert) {
		return true;
	}

}
