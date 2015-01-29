package org.imogene.lib.sync.serializer.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.media.BinaryOperation;
import org.imogene.lib.sync.SyncConstants;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.serializer.ImogSerializationException;
import org.imogene.lib.sync.serializer.ImogSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Static class that helps to the bean XML serialization.
 * 
 * @author MEDES-IMPS
 */
public abstract class ImogXmlSerializer implements ImogSerializer {

	private Logger logger = Logger.getLogger("org.imogene.sync.serializer.xml");

	private XStream xstream;

	protected DataHandlerManager dataHandlerManager;
	private BinaryOperation binaryOperation;

	public ImogXmlSerializer() {
		xstream = new XStream(new DomDriver("UTF-8"));
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.autodetectAnnotations(true);
		xstream.aliasSystemAttribute(null, "class");
	}

	public ImogXmlSerializer(ClassLoader loader) {
		this();
		xstream.setClassLoader(loader);
	}

	/**
	 * Serialize a synchronizable entity.
	 * 
	 * @param entity the entity to serialize.
	 * @return the string xml representation of the entity.
	 */
	@Override
	public <T extends ImogBean> void serialize(T entity, OutputStream xml) throws ImogSerializationException {
		if (!entity.getCreatedBy().equals(SyncConstants.SYNC_ID_SYS)) {
			xstream.toXML(entity, xml);
			logger.debug(xstream.toXML(entity));
		}
	}

	/**
	 * serialize a list of objects, and write the result in the specified output stream.
	 * 
	 * @param entities List of the entities to serialize
	 * @param xml output stream where to write the result.
	 * @throws IOException If an error occured writing to the stream.
	 */
	@Override
	public <T extends ImogBean> void serialize(List<T> entities, OutputStream xml) throws ImogSerializationException {

		try {
			xml.write("<entities>".getBytes());
			for (T entity : entities) {
				if (!entity.getCreatedBy().equals(SyncConstants.SYNC_ID_SYS)) {
					xstream.toXML(entity, xml);
					// logger.debug(xstream.toXML(entity));
				}
			}
			xml.write("</entities>".getBytes());
		} catch (Exception e) {
			throw new ImogSerializationException(e);
		}
	}

	/**
	 * Unserialize an object represented by the specified xml stream.
	 * 
	 * @param xml the xml stream
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ImogBean> T deSerialize(InputStream xml) throws ImogSerializationException {
		return (T) xstream.fromXML(xml);
	}

	/**
	 * Unserialize severals entity enclosed in an xml document as string.
	 * 
	 * @param xml the string xml document
	 * @return the list of the object unserialized
	 */
	@Override
	public List<ImogBean> deSerializeMulti(InputStream xml) throws ImogSerializationException {
		List<ImogBean> list = new Vector<ImogBean>();

		try {
			/* create a document from the xml string */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = factory.newDocumentBuilder();
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			Document dom = domBuilder.parse(xml);

			/*
			 * each parent node is an entity, we export it in a temporary file
			 */
			NodeList entities = dom.getDocumentElement().getChildNodes();
			for (int i = 0; i < entities.getLength(); i++) {
				Node current = entities.item(i);
				File tempFile = File.createTempFile("receive", ".imogobject");
				StreamResult result = new StreamResult(new FileOutputStream(tempFile));
				DOMSource source = new DOMSource(current);
				trans.transform(source, result);
				list.add(deSerialize(new FileInputStream(tempFile)));
				tempFile.delete();
			}

		} catch (Exception ex) {
			throw new ImogSerializationException(ex);
		}
		return list;
	}

	@Override
	public int processMulti(InputStream xml, ImogActor user) throws ImogSerializationException {
		int j = 0;
		try {
			/* create a document from the xml string */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = factory.newDocumentBuilder();
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			Document dom = domBuilder.parse(xml);

			/*
			 * each parent node is an entity, we export it in a temporary file
			 */
			NodeList entities = dom.getDocumentElement().getChildNodes();
			for (int i = 0; i < entities.getLength(); i++) {
				Node current = entities.item(i);
				File tempFile = File.createTempFile("receive", ".imogobject");
				StreamResult result = new StreamResult(new FileOutputStream(tempFile));
				DOMSource source = new DOMSource(current);
				trans.transform(source, result);

				// unserialize entity
				ImogBean entity = deSerialize(new FileInputStream(tempFile));
				tempFile.delete();

				// save entity
				if (entity != null) {
					if (save(entity, user)) {
						j++;
						if (entity instanceof Binary) {
							try {
								binaryOperation.operate((Binary) entity);
							} catch (Exception e) {
								logger.error("Error converting binary", e);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ImogSerializationException(ex);
		}
		return j;
	}

	abstract protected <T extends ImogBean> boolean save(T entity, ImogActor user);

	/* Setters for bean injection */

	/**
	 * Set the DataHandlerManager to use
	 * 
	 * @param dataHandlerManager the DataHandlerManager
	 */
	public void setDataHandlerManager(DataHandlerManager dataHandlerManager) {
		this.dataHandlerManager = dataHandlerManager;
	}

	/**
	 * 
	 * @param binaryOperation
	 */
	public void setBinaryOperation(BinaryOperation binaryOperation) {
		this.binaryOperation = binaryOperation;
	}

	/**
	 * Setter for spring injection
	 * 
	 * @param converters
	 */
	public void setPropertyConverters(Set<PropertyConverter> converters) {
		for (PropertyConverter conv : converters) {
			try {
				xstream.registerLocalConverter(Class.forName(conv.getClassName()), conv.getPropertyName(),
						conv.getConverter());
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * Setter for spring injection
	 * 
	 * @param converter
	 */
	public void setConverters(Set<Converter> converters) {
		for (Converter conv : converters) {
			xstream.registerConverter(conv);
		}
	}

	/**
	 * Setter for spring injection
	 * 
	 * @param omit
	 */
	public void setOmittedProperties(Set<OmittedProperty> omit) {
		for (OmittedProperty o : omit) {
			try {
				xstream.omitField(Class.forName(o.getClassName()), o.getPropertyName());
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			}
		}
	}

}
