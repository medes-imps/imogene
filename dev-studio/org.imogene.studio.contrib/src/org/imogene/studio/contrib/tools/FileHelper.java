package org.imogene.studio.contrib.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {
	
	private static final int BUFFER_SIZE=1024;
	
	public static void copyFile(String srcPath, String destPath) throws IOException {
		 File src = new File(srcPath);
		 File dest = new File(destPath);
		 copyFile(src, dest);
	}
	
	public static void copyFile(File srcFile, File destFile) throws IOException {
	    try{
	     
	      InputStream in = new FileInputStream(srcFile);	     	
	      OutputStream out = new FileOutputStream(destFile);

	      byte[] buf = new byte[BUFFER_SIZE];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();
	    }
	    catch(FileNotFoundException ex){
	    	throw new IOException(ex);
	    }	    
	  }
}
