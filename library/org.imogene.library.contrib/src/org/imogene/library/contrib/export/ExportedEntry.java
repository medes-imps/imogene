package org.imogene.library.contrib.export;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExportedEntry {
	
	private String name;
	
	private URL url;
	
	public ExportedEntry(String name, URL url){
		this.name = name;
		this.url = url;
	}

	public String getFileName(){
		return name;
	}
	
	public InputStream openStream() throws IOException {
		return url.openStream();
	}
	
}
