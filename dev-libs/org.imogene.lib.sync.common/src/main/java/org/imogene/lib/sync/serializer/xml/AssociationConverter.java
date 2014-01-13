package org.imogene.lib.sync.serializer.xml;

import org.imogene.lib.common.entity.ImogBean;
import org.imogene.lib.sync.handler.DataHandlerManager;
import org.imogene.lib.sync.handler.ImogBeanHandler;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream converter for the entity association with cardinality 1.
 * 
 * @author MEDES-IMPS
 */
public class AssociationConverter implements Converter {

	private DataHandlerManager dataHandlerManager;

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		String className = value.getClass().getName();
		writer.startNode(className);
		writer.addAttribute("id", ((ImogBean) value).getId().toString());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		reader.moveDown();
		String className = reader.getNodeName();
		String id = reader.getAttribute(0);
		reader.moveUp();
		return loadOrCreate(className, id);
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

	@SuppressWarnings("rawtypes" )
	@Override
	public boolean canConvert(Class toConvert) {
		return true;
	}

	/**
	 * Setter for bean injection
	 * 
	 * @param dataHandlerManager
	 */
	public void setDataHandlerManager(DataHandlerManager dataHandlerManager) {
		this.dataHandlerManager = dataHandlerManager;
	}

}
