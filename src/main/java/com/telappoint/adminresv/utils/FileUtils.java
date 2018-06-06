package com.telappoint.adminresv.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

/**
 * @author Murali
 * 
 */
public class FileUtils {
	
	//private static Logger logger = Logger.getLogger(FileUtils.class);
	
	public static byte[] readFileToByteArray(File deploymentFile) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(deploymentFile);
			return read(in);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {/* */
				}
		}
	}

	public static byte[] read(InputStream source) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		copy(source, byteOut, 4096);
		return byteOut.toByteArray();
	}

	/**
	 * Copy data from the source stream to the destination stream
	 * 
	 * @param source
	 * @param dest
	 * @return the amount of copied bytes
	 * @throws IOException
	 */
	public static int copy(InputStream source, OutputStream dest, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];

		int c, total = 0;
		while ((c = source.read(buffer)) > 0) {
			dest.write(buffer, 0, c);
			dest.flush();
			total += c;
		}
		dest.flush();

		return total;
	}

	/* Getting file size in MB. Parameter as in bytes */
	public static int getFileSizeInMB(long bytesize) {
		return (int) (bytesize) / (1024 * 1024);
	}

	public static String getTimeStamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime()).toString();
	}
	
	
	public static int copyFile(InputStream source, OutputStream dest, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];

		int c, total = 0;
		while ((c = source.read(buffer)) > 0) {
			dest.write(buffer, 0, c);
			dest.flush();
			total += c;
		}
		dest.flush();
		return total;
	}
}
