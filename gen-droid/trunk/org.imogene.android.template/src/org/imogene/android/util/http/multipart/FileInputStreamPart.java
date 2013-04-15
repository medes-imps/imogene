package org.imogene.android.util.http.multipart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.EncodingUtils;

import android.util.Log;

public class FileInputStreamPart extends PartBase {
	
	private static final boolean DEBUG = false;
	
    /** Log object for this class. */
	public static final String TAG = FileInputStreamPart.class.getName();
	
    /** Default content encoding of file attachments. */
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	
    /** Default transfer encoding of file attachments. */
    public static final String DEFAULT_TRANSFER_ENCODING = "binary";
    
    /** Attachment's file name */
    protected static final String FILE_NAME = "; filename=";
    
    /** Attachment's file name as a byte array */
    private static final byte[] FILE_NAME_BYTES = EncodingUtils.getAsciiBytes(FILE_NAME);
	
	private FileInputStream source = null;
	
	private String fileName;

    public FileInputStreamPart(String name, String fileName, FileInputStream fis, String contentType, String charset) {
        
        super(
            name, 
            contentType == null ? DEFAULT_CONTENT_TYPE : contentType, 
            charset == null ? "ISO-8859-1" : charset, 
            DEFAULT_TRANSFER_ENCODING
        );
        
        this.fileName = fileName;

        if (fis == null) {
            throw new IllegalArgumentException("Source may not be null");
        }
        this.source = fis;
    }
	
    /**
     * InputStramPart Constructor.
     *
     * @param name the name of the file part
     * @param fileName the name of the file
     * @param file the file to post
     *
     * @throws FileNotFoundException if the <i>file</i> is not a normal
     * file or if it is not readable.
     */
    public FileInputStreamPart(String name, String fileName, FileInputStream fis) 
    throws FileNotFoundException {
        this("data", fileName, fis, null, null);
    }
    
    /**
     * Write the disposition header to the output stream
     * @param out The output stream
     * @throws IOException If an IO problem occurs
     * @see Part#sendDispositionHeader(OutputStream)
     */
    @Override
    protected void sendDispositionHeader(OutputStream out) 
    throws IOException {
    	if (DEBUG) Log.i(TAG,"enter sendDispositionHeader(OutputStream out)");
        super.sendDispositionHeader(out);
            out.write(FILE_NAME_BYTES);
            out.write(QUOTE_BYTES);
            out.write(EncodingUtils.getAsciiBytes(fileName));
            out.write(QUOTE_BYTES);
    }

	@Override
	protected long lengthOfData() throws IOException {
		return source.available();
	}

	@Override
	protected void sendData(OutputStream out) throws IOException {
		if (DEBUG) Log.i(TAG, "enter sendData(OutputStream out)");
        if (lengthOfData() == 0) {
            
            // this file contains no data, so there is nothing to send.
            // we don't want to create a zero length buffer as this will
            // cause an infinite loop when reading.
        	if (DEBUG) Log.i(TAG, "No data to send.");
            return;
        }
        
        byte[] tmp = new byte[4096];
        InputStream instream = source;
        try {
            int len;
            while ((len = instream.read(tmp)) >= 0) {
                out.write(tmp, 0, len);
            }
        } finally {
            // we're done with the stream, close it
            instream.close();
        }		
	}
}
