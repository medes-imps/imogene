package org.imogene.web.server.service;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFile;
import org.imogene.lib.common.binary.file.BinaryFileManager;
import org.imogene.lib.media.BinaryOperation;
import org.imogene.web.client.util.ImogKeyGenerator;
import org.imogene.web.server.handler.BinaryHandler;
import org.imogene.web.server.handler.HandlerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BinaryUploadServiceImpl implements BinaryUploadService {

	private static final String BINARY_SHORTNAME = "BIN";

	@Autowired
	@Qualifier(value = "binaryOperation")
	private BinaryOperation binaryOperation;

	@Autowired
	@Qualifier(value = "binaryHandler")
	private BinaryHandler binaryHandler;

	@Autowired
	private HandlerHelper handlerHelper;

	/**
	 * Copy the uploaded file in the correct folder, Create the binary entity associated.
	 */
	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
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
					handlerHelper.prepare(binary);
					binary.setModified(null);
					binaryHandler.saveOrUpdateBinary(binary);
					/* binary file creation */
					File localFile = getLocalFile(item.getName(), entityId);
					item.write(localFile);
					/* binary file conversion to flv */
					binaryOperation.operate(binary);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return entityId;
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String entityId = request.getParameter(UConsts.PARAM_SHOW);
		Binary binary = binaryHandler.getBinary(entityId);
		if (binary != null) {
			File thumbFile = BinaryFileManager.getInstance().buildThumbFilePath(binary);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + "\"");
			response.setContentType(binary.getContentType());
			response.setContentLength((int) thumbFile.length());
			FileInputStream fis = new FileInputStream(thumbFile);
			IOUtils.copy(fis, response.getOutputStream());
			fis.close();
		} else {
			response.sendError(404);
		}
	}

	/**
	 * Get the local file where copy the FileItem
	 * 
	 * @param remoteName the remote name
	 * @return the corresponding file.
	 */
	private File getLocalFile(String remoteName, String entityId) {
		String basename = (new File(remoteName)).getName();
		return BinaryFileManager.getInstance().buildFilePath(entityId, basename);
	}

}
