package org.imogene.lib.media;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class VideoConverter implements MediaConverter {

	private static final Logger logger = Logger.getLogger("org.imogene.web.server.util.VideoConverter");

	public static final String TAG_GGGP = "3gp";
	public static final String TAG_AVI = "mpeg";

	private static final String DEFAULT_COMMAND = "ffmpeg -i %IN% -f flv %OUT%";

	private String commandLine = DEFAULT_COMMAND;

	public String convert(File input, File output, String mimeType) {
		runCommand(input.getAbsolutePath(), output.getAbsolutePath());
		return null;
	}

	/**
	 * Set the system command line to user
	 * 
	 * @param pCommandLine the command line to user
	 */
	public void setCommandLine(String pCommandLine) {
		commandLine = pCommandLine;
	}

	private void runCommand(String inFullPath, String outFullPath) {
		try {
			if (commandLine == null || commandLine.equals(""))
				logger.warn("No command set for the video converter, take a look to the 'application.properties' file");
			else {
				String cmd = commandLine.replace("%IN%", inFullPath).replace("%OUT%", outFullPath);
				logger.debug("Video converter : " + cmd);
				Runtime.getRuntime().exec(cmd);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
