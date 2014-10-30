package org.imogene.web.server.service;

import gwtupload.server.exceptions.UploadActionException;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public interface BinaryUploadService {

	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException;

	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
