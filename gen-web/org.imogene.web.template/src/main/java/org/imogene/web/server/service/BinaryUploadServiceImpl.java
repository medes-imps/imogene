package org.imogene.web.server.service;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.server.handler.BinaryHandler;
import org.imogene.web.server.servlet.util.MediaConverter;
import org.imogene.web.server.servlet.util.PhotoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BinaryUploadServiceImpl implements BinaryUploadService {
	
	private static final String BINARY_SHORTNAME = "BIN";
	private static final String DEFAULT_DIRECTORY = "/binaries/";
	private String binaryPath = DEFAULT_DIRECTORY;
//	private static final String PATH_PARAM = "binaryPath";
	
	@Autowired
	@Qualifier(value = "videoConverter")
	private MediaConverter videoConverter;
	
	@Autowired
	@Qualifier(value = "binaryHandler")
	private BinaryHandler binaryHandler;
	
	

	/**
	 * Copy the uploaded file in the correct folder,
	 * Create the binary entity associated.
	 */
	@Override
	public String executeAction(HttpServletRequest request,	List<FileItem> sessionFiles) throws UploadActionException {
		String entityId = "noid";
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					entityId = ImogKeyGenerator.generateKeyId(BINARY_SHORTNAME);
					Binary binary = new BinaryFile();
					binary.setId(entityId);
					binary.setContentType(item.getContentType());
					binary.setLength(item.getSize());
					binary.setFileName(item.getName());
					binaryHandler.saveOrUpdateBinary(binary);
					/* binary file creation */
					File localFile = getLocalFile(item.getName(), entityId);
					item.write(localFile);
					/* binary file conversion to flv */
					if (item.getContentType().contains("video")) {
						File flvFile = new File(binaryPath + "/flv/"
								+ localFile.getName() + ".flv");
						if (!item.getContentType().contains("x-flash-video")) {
							videoConverter.convert(localFile, new File(
									binaryPath + "flv/" + localFile.getName()
											+ ".flv"), item.getContentType());
						} else {
							copy(new FileInputStream(localFile),
									new FileOutputStream(flvFile));
						}
					}
					/* image file thumbnail */
					if (item.getContentType().contains("image")) {
						File thumbnail = new File(binaryPath + "/thumb_"
								+ localFile.getName());
						PhotoConverter.convert(localFile, thumbnail,
								item.getContentType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return  entityId;
	}
	
	  /**
	   * Get the content of an uploaded file.
	   */
	  @Override
	  public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    
		String entityId = request.getParameter(UConsts.PARAM_SHOW);
		Binary binary = binaryHandler.getBinary(entityId);
		if (binary != null) {
			File thumbFile = getLocalThumbnail(binary);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ binary.getFileName() + "\"");
			response.setContentType(binary.getContentType());
			response.setContentLength((int) thumbFile.length());
			copy(new FileInputStream(thumbFile), response.getOutputStream());
		} else {
			response.sendError(404);
		}
	  }
	  
		/**
		 * Set the binary path of the application
		 */
		public void setBinaryPath(String path) {
//			ExportedPropertiesHolder eph = (ExportedPropertiesHolder) wac.getBean("exportedProperties");
			if (path != null)
				binaryPath = path;
			if (!binaryPath.endsWith("/"))
				binaryPath = binaryPath + "/";
			/* flv directory */
			File flvDir = new File(binaryPath + "flv/");
			if (!flvDir.exists())
				flvDir.mkdir();
		}
	
	/**
	 * Get the local file where copy the FileItem
	 * @param remoteName the remote name 
	 * @return the corresponding file.
	 */
	private File getLocalFile(String remoteName, String entityId) {
		String basename = (new File(remoteName)).getName();
		return new File(binaryPath + entityId + "-" + basename);
	}
	
	/**
	 * Copy the binary file to the http response output stream
	 * @param in input stream
	 * @param out output stream
	 * @throws IOException
	 */
	private static void copy(InputStream in, OutputStream out)
			throws IOException {
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
	
	/**
	 * Get the local thumbnail
	 * @param remoteName the remote name 
	 * @return the corresponding file.
	 */
	private File getLocalThumbnail(Binary binary) {
		return new File(binaryPath + "/thumb_" + binary.getId() + "-"
				+ binary.getFileName());
	}
	
	
}
