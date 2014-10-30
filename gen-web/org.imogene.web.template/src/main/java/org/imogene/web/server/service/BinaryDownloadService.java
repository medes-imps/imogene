package org.imogene.web.server.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BinaryDownloadService {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
