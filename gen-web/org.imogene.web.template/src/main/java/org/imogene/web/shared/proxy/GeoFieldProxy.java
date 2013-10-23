package org.imogene.web.shared.proxy;

import org.imogene.lib.common.entity.GeoField;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(value=GeoField.class)
public interface GeoFieldProxy extends ValueProxy  {


	public Double getLatitude();

	public void setLatitude(Double latitude);

	public Double getLongitude();

	public void setLongitude(Double longitude);

}
