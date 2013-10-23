package org.imogene.lib.sync.server.http.command;

import java.io.File;

public class DirectSendCommand extends SessionCommand {
	
	private File data;

	public File getData() {
		return data;
	}

	public void setData(File data) {
		this.data = data;
	}
}
