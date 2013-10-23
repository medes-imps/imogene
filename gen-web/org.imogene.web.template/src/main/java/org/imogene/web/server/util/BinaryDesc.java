package org.imogene.web.server.util;

/**
 * Class that describes an available binary entity
 * @author Medes-IMPS
 */
public class BinaryDesc {
	
	
	private String id;
	private String name = "-";
	private long size = 0;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
		
}
