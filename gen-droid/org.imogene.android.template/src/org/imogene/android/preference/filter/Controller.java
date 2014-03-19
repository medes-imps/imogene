package org.imogene.android.preference.filter;

import java.util.Stack;

public class Controller {

	private static Controller sInstance;

	private Stack<Object> mStack = new Stack<Object>();
	private Result mListener;
	private boolean mStop = false;

	public synchronized static Controller getInstance() {
		if (sInstance == null)
			sInstance = new Controller();
		return sInstance;
	}

	public synchronized void setResultListener(Result listener) {
		mListener = listener;
	}

	public synchronized void stop() {
		mStop = true;
	}

	public synchronized boolean stopped() {
		return mStop;
	}

	public synchronized void startReceiving() {
		mStack.clear();
		mStop = false;
		if (mListener != null)
			mListener.notifyStarted();
	}

	public synchronized void receiveItem(Object object) {
		mStack.push(object);
		if (mListener != null)
			mListener.notifyReceived();
	}

	public synchronized void finishReceiving() {
		if (mListener != null)
			mListener.notifyFinished();
	}

	public synchronized void update(Result result) {
		while (!mStack.isEmpty())
			result.addReceived(mStack.pop());
	}

	public synchronized void error() {
		if (mListener != null)
			mListener.notifyError();
	}

	public interface Result {

		public void notifyStarted();

		public void notifyReceived();

		public void notifyFinished();

		public void notifyError();

		public void addReceived(Object object);

	}

}
