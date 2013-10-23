package org.imogene.library;

import java.net.URL;

public class LibraryDesc {

	private String id;
	
	private URL url;
	
	private String name;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setUrl(URL url){
		this.url = url;
	}
	
	public URL getUrl(){
		return url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
