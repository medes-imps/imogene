package org.imogene.lib.sync.serializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.imogene.lib.common.entity.ImogActor;
import org.imogene.lib.common.entity.ImogBean;

/**
 * This interface specify a bean serializer/unserializer
 * 
 * @author Medes-IMPS
 * 
 */
public interface ImogSerializer {

	/**
	 * Serialize an entity in the specified output stream
	 * 
	 * @param entity the entity to serialize
	 * @param data the output stream
	 */
	public <T extends ImogBean> void serialize(T entity, OutputStream data) throws ImogSerializationException;

	/**
	 * Serialize a list of entities in the specified output stream
	 * 
	 * @param entities entities to serialize
	 * @param data the output stream
	 */
	public <T extends ImogBean> void serialize(List<T> entities, OutputStream data) throws ImogSerializationException;

	/**
	 * Unserialize an entity, reading the input stream
	 * 
	 * @param data input stream
	 * @return the entity
	 */
	public <T extends ImogBean> T deSerialize(InputStream data) throws ImogSerializationException;

	/**
	 * Unserialize a list of entities, reading the input stream
	 * 
	 * @param data the input stream
	 * @return the list of object
	 */
	public <T extends ImogBean> List<T> deSerializeMulti(InputStream data) throws ImogSerializationException;

	/**
	 * Unserialize a list of entities, reading the input stream and save each
	 * entity after it has been unserialized
	 * 
	 * @param data the input stream
	 * @return the number of processed entities
	 */
	public int processMulti(InputStream data, ImogActor user) throws ImogSerializationException;
}
