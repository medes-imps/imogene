package org.imogene.lib.common.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GeoField implements Serializable {

	private static final long serialVersionUID = -5297846034125052939L;

	private Double latitude;
	private Double longitude;

	public GeoField() {
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
