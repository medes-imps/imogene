package org.imogene.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

import android.os.Environment;
import android.os.Process;

public class Logger implements Runnable {

	private static final File LOG_FILE = new File(Environment.getExternalStorageDirectory(), "log.txt");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

	public static void e(String tag, String message, Exception e) {
		getIntance().log(Type.ERROR, new Date(), tag, message, e);
	}

	public static void i(String tag, String message) {
		getIntance().log(Type.INFO, new Date(), tag, message, null);
	}

	private static Logger sInstance = null;

	private static Logger getIntance() {
		if (sInstance == null) {
			sInstance = new Logger();
		}
		return sInstance;
	}

	private final Thread mThread;
	private final LinkedBlockingQueue<Log> mLogs = new LinkedBlockingQueue<Log>();

	private Logger() {
		mThread = new Thread(this);
		mThread.start();
	}

	private void log(Type type, Date date, String tag, String message, Exception e) {
		Log log = new Log();
		log.type = type;
		log.date = date;
		log.tag = tag;
		log.message = message;
		log.exception = e;
		mLogs.offer(log);
	}

	@Override
	public void run() {
		while (true) {
			Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
			Log log;
			try {
				log = mLogs.take();
			} catch (InterruptedException e) {
				continue;
			}
			write(log);
		}
	}

	private void write(Log log) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(LOG_FILE, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		PrintStream ps = new PrintStream(fos);
		ps.append(log.type.code);
		ps.append("/time: ");
		ps.append(DATE_FORMAT.format(log.date));
		ps.append(": ");
		ps.append(log.tag);
		ps.append(": ");
		ps.append(log.message);
		if (log.exception != null) {
			log.exception.printStackTrace(ps);
		}
		ps.append('\n');
		ps.close();
	}

	private static class Log {
		Type type;
		Date date;
		String tag;
		String message;
		Exception exception;
	}

	private enum Type {

		ERROR("E"), INFO("I");

		private final String code;

		private Type(String code) {
			this.code = code;
		}
	}

}
