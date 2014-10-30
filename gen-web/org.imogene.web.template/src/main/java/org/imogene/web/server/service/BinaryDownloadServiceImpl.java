package org.imogene.web.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFileManager;
import org.imogene.web.server.handler.BinaryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author Medes-IMPS
 */
public class BinaryDownloadServiceImpl implements BinaryDownloadService {

	@Autowired
	@Qualifier(value = "binaryHandler")
	private BinaryHandler binaryHandler;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String paramId = req.getParameter("binaryId");
		String flvId = req.getParameter("flvId");
		String mp3Id = req.getParameter("mp3Id");
		String thumbId = req.getParameter("thumbId");

		if (paramId != null) {
			Binary binary = binaryHandler.getBinary(paramId);
			if (binary != null) {
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + "\"");
				resp.setContentType(binary.getContentType());
				resp.setContentLength((int) binary.getLength());
				FileInputStream fis = new FileInputStream(BinaryFileManager.getInstance().buildFilePath(binary));
				IOUtils.copy(fis, resp.getOutputStream());
				fis.close();
			} else {
				resp.sendError(404);
			}
		} else if (flvId != null) {
			Binary binary = binaryHandler.getBinary(flvId);
			if (binary != null) {
				File flvFile = BinaryFileManager.getInstance().buildFlvFilePath(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + ".flv" + "\"");
				resp.setContentType("x-flash-video");
				resp.setContentLength((int) flvFile.length());
				FileInputStream fis = new FileInputStream(flvFile);
				IOUtils.copy(fis, resp.getOutputStream());
				fis.close();
			} else {
				resp.sendError(404);
			}
		} else if (mp3Id != null) {
			Binary binary = binaryHandler.getBinary(mp3Id);
			if (binary != null) {
				File mp3File = BinaryFileManager.getInstance().buildMp3FilePath(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() /* + ".mp3" */
						+ "\"");
				resp.setContentType("audio/mp3");
				resp.setContentLength((int) mp3File.length());
				FileInputStream fis = new FileInputStream(mp3File);
				IOUtils.copy(fis, resp.getOutputStream());
				fis.close();
			} else {
				resp.sendError(404);
			}
		} else if (thumbId != null) {
			Binary binary = binaryHandler.getBinary(thumbId);
			if (binary != null) {
				File thumbFile = BinaryFileManager.getInstance().buildThumbFilePath(binary);
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + binary.getFileName() + "\"");
				resp.setContentType(binary.getContentType());
				resp.setContentLength((int) thumbFile.length());
				FileInputStream fis = new FileInputStream(thumbFile);
				IOUtils.copy(fis, resp.getOutputStream());
				fis.close();
			} else {
				resp.sendError(404);
			}
		} else {
			resp.sendError(404);
		}
	}

}
