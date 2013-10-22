package org.imogene.lib.sync.serializer.xml;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream converter for the entity associations.
 * 
 * @author MEDES-IMPS
 */
public class CollectionConverter implements Converter {

	private static String COLLECTION_NODE = "collection";

	private DataHandlerManager dataHandlerManager;

	@Override
	@SuppressWarnings("unchecked")
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode(COLLECTION_NODE);
		for (ImogBean rep : (Collection<ImogBean>) value) {
			String className = rep.getClass().getName();
			writer.startNode(className);
			writer.addAttribute("id", rep.getId().toString());
			writer.endNode();
		}
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		reader.moveDown();

		List<ImogBean> entities = new Vector<ImogBean>();
		while (reader.hasMoreChildren()) {

			reader.moveDown();
			String className = reader.getNodeName();
			String id = reader.getAttribute(0);
			reader.moveUp();

			entities.add(loadOrCreate(className, id));
		}
		reader.moveUp();
		return entities;
	}

	@SuppressWarnings("unchecked")
	private <T extends ImogBean> T loadOrCreate(String className, String id) {
		ImogBeanHandler<T> handler = (ImogBeanHandler<T>) dataHandlerManager.getHandler(className);
		T result = handler.loadEntity(id);
		if (result == null) {
			result = handler.createNewEntity(id);
			return handler.merge(result, true);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class toConvert) {
		return true;
	}

	/**
	 * setter for bean injection
	 * 
	 * @param dataHandlerManager
	 */
	public void setDataHandlerManager(DataHandlerManager dataHandlerManager) {
		this.dataHandlerManager = dataHandlerManager;
	}

}
