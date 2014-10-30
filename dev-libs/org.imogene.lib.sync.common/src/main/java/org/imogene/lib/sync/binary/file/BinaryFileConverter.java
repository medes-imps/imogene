package org.imogene.lib.sync.binary.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.binary.file.BinaryFileManager;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream converter for Binary.
 * 
 * @author MEDES-IMPS
 */
public class BinaryFileConverter implements Converter {

	private Logger logger = Logger.getLogger("org.imogene.lib.sync.binary.file");

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		BinaryFile binary = (BinaryFile) value;

		// id
		writer.startNode("id");
		writer.setValue(binary.getId());
		writer.endNode();

		// modified
		writer.startNode("modified");
		writer.setValue(String.valueOf(binary.getModified().getTime()));
		writer.endNode();

		// uploadDate
		writer.startNode("uploadDate");
		if (binary.getUploadDate() != null)
			writer.setValue(String.valueOf(binary.getUploadDate().getTime()));
		writer.endNode();

		// modifiedBy
		writer.startNode("modifiedBy");
		if (binary.getModifiedBy() != null)
			writer.setValue(binary.getModifiedBy());
		writer.endNode();

		// modifiedFrom
		writer.startNode("modifiedFrom");
		if (binary.getModifiedFrom() != null)
			writer.setValue(binary.getModifiedFrom());
		writer.endNode();

		// created
		writer.startNode("created");
		writer.setValue(String.valueOf(binary.getCreated().getTime()));
		writer.endNode();

		// createdBy
		writer.startNode("createdBy");
		writer.setValue(binary.getCreatedBy());
		writer.endNode();

		// fileName
		writer.startNode("fileName");
		if (binary.getFileName() != null)
			writer.setValue(binary.getFileName());
		writer.endNode();

		// contentType
		writer.startNode("contentType");
		if (binary.getContentType() != null)
			writer.setValue(binary.getContentType());
		writer.endNode();

		// length
		writer.startNode("length");
		if (binary.getLength() > 0)
			writer.setValue(String.valueOf(binary.getLength()));
		writer.endNode();

		// content
		writer.startNode("content");
		if (binary.getLength() > 0) {

			try {
				InputStream is = binary.createInputStream();
				byte[] bytes = new byte[1024];
				int read = -1;
				while ((read = is.read(bytes)) != -1) {
					writer.startNode("data");
					if (read != bytes.length) {
						byte[] newBytes = Arrays.copyOf(bytes, read);
						writer.setValue(new String(Base64.encodeBase64(newBytes)));
					} else {
						writer.setValue(new String(Base64.encodeBase64(bytes)));
					}
					writer.endNode();
				}
				is.close();
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		BinaryFile binary = new BinaryFile();

		// Id
		reader.moveDown();
		String id = reader.getValue();
		binary.setId(id);
		reader.moveUp();

		// modified
		reader.moveDown();
		String modified = reader.getValue();
		binary.setModified(new Date(Long.valueOf(modified)));
		reader.moveUp();

		// uploadDate
		reader.moveDown();
		reader.getValue();
		reader.moveUp();

		// modifiedBy
		reader.moveDown();
		String modifiedBy = reader.getValue();
		if (!modifiedBy.equals(""))
			binary.setModifiedBy(modifiedBy);
		reader.moveUp();

		// modifiedFrom
		reader.moveDown();
		String modifiedFrom = reader.getValue();
		if (!modifiedFrom.equals(""))
			binary.setModifiedFrom(modifiedFrom);
		reader.moveUp();

		// created
		reader.moveDown();
		String created = reader.getValue();
		binary.setCreated(new Date(Long.valueOf(created)));
		reader.moveUp();

		// createdBy
		reader.moveDown();
		String createdBy = reader.getValue();
		binary.setCreatedBy(createdBy);
		reader.moveUp();

		// fileName
		reader.moveDown();
		String fileName = reader.getValue();
		if (!fileName.equals(""))
			binary.setFileName(fileName);
		reader.moveUp();

		// contentType
		reader.moveDown();
		String contentType = reader.getValue();
		if (!contentType.equals(""))
			binary.setContentType(contentType);
		reader.moveUp();

		// length
		reader.moveDown();
		String length = reader.getValue();
		if (!length.equals(""))
			binary.setLength(Long.valueOf(length));
		reader.moveUp();

		// content
		reader.moveDown();
		if (reader.hasMoreChildren()) {
			try {
				File resultFile = BinaryFileManager.getInstance().buildFilePath(id, fileName);
				OutputStream out = new FileOutputStream(resultFile);

				// write content from xml to temp file
				while (reader.hasMoreChildren()) {
					reader.moveDown();
					String content = reader.getValue();
					StringReader strReader = new StringReader(content);
					BufferedReader br = new BufferedReader(strReader);
					while (true) {
						String line = br.readLine();
						if (line == null) {
							break;
						}
						byte[] bytes = Base64.decodeBase64(line.getBytes());
						out.write(bytes);
						out.flush();
					}
					strReader.close();
					reader.moveUp();
				}
				out.close();

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		reader.moveUp();

		return binary;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class cl) {
		for (Class implemented : cl.getInterfaces()) {
			if (implemented.equals(Binary.class))
				return true;
		}
		return false;
	}

}
