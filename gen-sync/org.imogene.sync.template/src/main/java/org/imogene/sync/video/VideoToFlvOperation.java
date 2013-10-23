package org.imogene.sync.video;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.imogene.lib.common.binary.file.BinaryFileManager;
import org.imogene.lib.sync.server.binary.BinaryOperation;
import org.imogene.sync.MediaConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class VideoToFlvOperation implements BinaryOperation {

	private static final String FLV_DIR = "flv";

	@Autowired
	@Qualifier(value = "videoConverter")
	private MediaConverter videoConverter;

	public void operate(File incoming) {
		throw new RuntimeException("Not impplemented use operate(File, Properties).");
	}

	public void setVideoConverter(MediaConverter converter) {
		videoConverter = converter;
	}

	@Override
	public void operate(File incoming, Properties params) {
		String mimetype = params.getProperty("mimetype");
		String binaryPath = BinaryFileManager.getInstance().getBinaryPath();
		testFlvDir(binaryPath);
		if (mimetype.contains("video")) {
			File output = new File(binaryPath + FLV_DIR + "/" + incoming.getName() + ".flv");
			if (!mimetype.contains("x-flash-video")) {
				videoConverter.convert(incoming, output, mimetype);
			} else {
				try {
					copy(new FileInputStream(incoming), new FileOutputStream(output));
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	private void testFlvDir(String binaryPath) {
		File flvDir = new File(binaryPath + FLV_DIR);
		if (!flvDir.exists())
			flvDir.mkdir();
	}

	/**
	 * Copy the binary file to the http response output stream
	 * 
	 * @param in input stream
	 * @param out output stream
	 * @throws IOException
	 */
	private static void copy(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int nrOfBytes = -1;
			while ((nrOfBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
