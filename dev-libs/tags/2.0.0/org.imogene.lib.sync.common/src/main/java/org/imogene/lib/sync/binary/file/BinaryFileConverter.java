package org.imogene.lib.sync.binary.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.binary.file.BinaryFileManager;
import org.imogene.lib.sync.serializer.xml.base64.Base64FileDecoder;
import org.imogene.lib.sync.serializer.xml.base64.Base64FileEncoder;

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
		// System.out.println("<content>");
		if (binary.getLength() > 0) {

			try {
				File tempFile = File.createTempFile(binary.getFileName(), null);

				// write content from binary to temp file
				OutputStream out = new FileOutputStream(tempFile);
				OutputStreamWriter outW = new OutputStreamWriter(out);
				BufferedWriter bw = new BufferedWriter(outW);
				Base64FileEncoder.encodeStream(binary.createInputStream(), bw);
				bw.flush();
				outW.flush();
				out.flush();
				bw.close();
				outW.close();
				out.close();
				// write content from temp file to xml
				FileReader fr = new FileReader(tempFile);
				BufferedReader bf = new BufferedReader(fr);
				while (true) {
					String s = bf.readLine();
					if (s == null)
						break;
					writer.startNode("data");
					// System.out.println("<data>");
					writer.setValue(s);
					// System.out.println(s);
					writer.endNode();
					// System.out.println("</data>");
				}
				bf.close();
				fr.close();
				tempFile.delete();
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		writer.endNode();
		// System.out.println("</content>");
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
				File resultFile = new File(BinaryFileManager.getInstance().buildFilePath(id, fileName));
				OutputStream out = new FileOutputStream(resultFile);

				// write content from xml to temp file
				while (reader.hasMoreChildren()) {
					reader.moveDown();
					String content = reader.getValue();
					StringReader strReader = new StringReader(content);
					BufferedReader br = new BufferedReader(strReader);
					try {
						Base64FileDecoder.decodeStream(br, out);
						out.flush();
						br.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
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
