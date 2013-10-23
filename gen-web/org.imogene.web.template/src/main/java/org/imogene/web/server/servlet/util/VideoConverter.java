package org.imogene.web.server.servlet.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class VideoConverter implements MediaConverter {
	
	public static final String GGGP_TAG = "3gp";
	
	public static final String AVI_TAG = "mpeg";
	
	@SuppressWarnings("unused")
	private static final String DEFAULT_COMMAND="ffmpeg -i %IN% -f flv %OUT%";
	
	private String commandLine = null;
	
	private Logger logger = Logger.getLogger("org.imogene.web.server.util.VideoConverter");

	public String convert(File input, File output, String mimeType){
		runCommand(input.getAbsolutePath(), output.getAbsolutePath());
		return null;
	}
	
	/**
	 * Set the system command line to user
	 * @param pCommandLine the command line to user
	 */
	public void setCommandLine(String pCommandLine){
		commandLine = pCommandLine;
	}
	
	private void runCommand(String inFullPath, String outFullPath){
		try{
			if(commandLine==null || commandLine.equals(""))
				logger.warn("No command set for the video converter, take a look to the 'application.properties' file");
			else{	
				String cmd = commandLine.replace("%IN%", inFullPath).replace("%OUT%", outFullPath);
				logger.debug("Video converter : "+cmd);
				Runtime.getRuntime().exec(cmd);
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	
}
