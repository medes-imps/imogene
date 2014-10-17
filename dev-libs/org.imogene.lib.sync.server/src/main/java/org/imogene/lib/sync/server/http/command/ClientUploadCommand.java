package org.imogene.lib.sync.server.http.command;

import org.springframework.web.multipart.MultipartFile;

public class ClientUploadCommand extends SessionCommand {	
	
	private MultipartFile data;
	
	public MultipartFile getData(){
		return data;
	}
	
	public void setData(MultipartFile data){
		this.data = data;
	}
	
}