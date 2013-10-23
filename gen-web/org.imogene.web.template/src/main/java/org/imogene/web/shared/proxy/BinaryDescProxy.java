package org.imogene.web.shared.proxy;

import org.imogene.web.server.util.BinaryDesc;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(value=BinaryDesc.class)
public interface BinaryDescProxy extends ValueProxy {
	
	public String getId();
	public void setId(String id);

	public String getName();
	public void setName(String name);

	public long getSize();
	public void setSize(long size);

}
