package org.imogene.lib.sync.serializer.xml;

import java.util.HashSet;
import java.util.Set;

import org.imogene.lib.common.role.ImogRole;
import org.imogene.lib.common.role.RoleActorDao;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * XStream converter for the User roles property.
 * 
 * @author MEDES-IMPS
 */
public class RolesConverter implements Converter {
	
	RoleActorDao roleActorDao;

	@Override
	@SuppressWarnings("unchecked")
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		for (ImogRole role : (Set<ImogRole>) value) {
			writer.startNode("role");
			writer.setValue(role.getId());
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Set<ImogRole> roles = new HashSet<ImogRole>();
		while(reader.hasMoreChildren()) {
			reader.moveDown();
			String roleId = reader.getValue();
			reader.moveUp();
			
			ImogRole role = roleActorDao.getRole(roleId);
			if (role != null) {
				roles.add(role);
			}
		}
		return roles;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class toConvert) {
		return true;
	}
	
	public void setRoleActorDao(RoleActorDao roleActorDao) {
		this.roleActorDao = roleActorDao;
	}

}
