package org.imogene.web.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imogene.lib.common.binary.Binary;
import org.imogene.web.server.handler.BinaryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author Medes-IMPS
 */
public class BinaryDownloadServiceImpl implements BinaryDownloadService {
	
	private static final String DEFAULT_DIRECTORY = "/binaries/";
	private String binaryPath = DEFAULT_DIRECTORY;
	
	@Autowired
	@Qualifier(value = "binaryHandler")
	private BinaryHandler binaryHandler;
	


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String paramId = req.getParameter("binaryId");
		String flvId = req.getParameter("flvId");
		String mp3Id = req.getParameter("mp3Id");
		String thumbId = req.getParameter("thumbId");
		// String barcodeId = req.getParameter("barcodeId");

		if (paramId != null) {
			Binary binary = binaryHandler.getBinary(paramId);
			if (binary != null) {
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + "\"");
				resp.setContentType(binary.getContentType());
				resp.setContentLength((int) binary.getLength());
				copy(new FileInputStream(getLocalFile(binary)), resp.getOutputStream());
			} else {
				resp.sendError(404);
			}
		} else if (flvId != null) {
			Binary binary = binaryHandler.getBinary(flvId);
			if (binary != null) {
				File flvFile = getLocalFileAsFlv(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + ".flv" + "\"");
				resp.setContentType("x-flash-video");
				resp.setContentLength((int) flvFile.length());
				copy(new FileInputStream(flvFile), resp.getOutputStream());
			} else {
				resp.sendError(404);
			}
		} else if (mp3Id != null) {
			Binary binary = binaryHandler.getBinary(mp3Id);
			if (binary != null) {
				File mp3File = getLocalFileAsMp3(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() /*+ ".mp3"*/ + "\"");
				resp.setContentType("audio/mp3");
				resp.setContentLength((int) mp3File.length());
				copy(new FileInputStream(mp3File), resp.getOutputStream());
			} else {
				resp.sendError(404);
			}
		} else if (thumbId != null) {
			Binary binary = binaryHandler.getBinary(thumbId);
			if (binary != null) {
				File thumbFile = getLocalThumbnail(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + "\"");
				resp.setContentType(binary.getContentType());
				resp.setContentLength((int) thumbFile.length());
				copy(new FileInputStream(thumbFile), resp.getOutputStream());
			} else {
				resp.sendError(404);
			}
		}
		// else if (barcodeId != null) {
		// File barcodeFile = getBarcodeFile(barcodeId);
		// resp.setHeader("Content-Disposition", "attachment; filename=\""
		// + barcodeFile.getName() + "\"");
		// resp.setContentType("image/png");
		// resp.setContentLength((int) barcodeFile.length());
		// copy(new FileInputStream(barcodeFile), resp.getOutputStream());
		// }
		else {
			resp.sendError(404);
		}
	}

	/**
	 * @param id
	 * @return
	 */
	// private File getBarcodeFile(String id) {
	// File barCode = new File(binaryPath + id + ".png");
	// if (!barCode.exists())
	// generateBarcode(id);
	// return barCode;
	// }

	/**
	 * @param entityId
	 */
	// private void generateBarcode(String entityId) {
	// try {
	// QRCodeWriter writer = new QRCodeWriter();
	// ByteMatrix bMatrix = writer.encode(entityId, BarcodeFormat.QR_CODE,
	// 120, 120, null);
	// File outFile = new File(binaryPath + entityId + ".png");
	// MatrixToImageWriter.writeToFile(bMatrix, "png", outFile);
	// } catch (WriterException ex) {
	// ex.printStackTrace();
	// } catch (IOException ioe) {
	// ioe.printStackTrace();
	// }
	// }
	
	/**
	 * Set the binary path used by this application
	 */
	public void setBinaryPath(String path) {
		if (path != null) {
			binaryPath = path;
		}
		if (!binaryPath.endsWith("/")) {
			binaryPath = binaryPath + "/";
		}
	}

	/**
	 * Get the local file where copy the FileItem
	 * @param remoteName the remote name
	 * @return the corresponding file.
	 */
	private File getLocalFile(Binary binary) {
		return new File(binaryPath + binary.getId() + "-" + binary.getFileName());
	}

	/**
	 * Get the local file where copy the FileItem
	 * @param remoteName the remote name
	 * @return the corresponding file.
	 */
	private File getLocalFileAsFlv(Binary binary) {
		return new File(binaryPath + "flv/" + binary.getId() + "-" + binary.getFileName() + ".flv");
	}

	/**
	 * Get the local thumbnail
	 * @param remoteName the remote name
	 * @return the corresponding file.
	 */
	private File getLocalThumbnail(Binary binary) {
		return new File(binaryPath + "/thumb_" + binary.getId() + "-" + binary.getFileName());
	}

	/**
	 * Get the local mp3 copy
	 * @param binary the binary
	 * @return the mp3 file copy
	 */
	private File getLocalFileAsMp3(Binary binary) {
		return new File(binaryPath /*+ "mp3/"*/ + binary.getId() + "-" + binary.getFileName() /*+ ".mp3"*/);
	}

	/**
	 * Copy the binary file to the http response output stream
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
