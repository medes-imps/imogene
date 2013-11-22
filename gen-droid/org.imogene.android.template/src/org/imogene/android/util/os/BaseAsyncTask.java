package org.imogene.android.util.os;

import android.os.AsyncTask;

/**
 * Specific {@link AsyncTask} for use in activities and simplify configuration changes handling. This basically allows
 * to call the post executed method more than once and stores the result.
 * <p>
 * When attaching a callback to this task, if the {@link Status} is {@link Status#RUNNING} the method
 * {@link BaseAsyncTask#onPreExecute()} will be called again, if the task {@link Status} is {@link Status#FINISHED} the
 * method {@link BaseAsyncTask#onPostExecute(Object)} will be called again.
 * </p>
 * 
 * @param <Callback>
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public abstract class BaseAsyncTask<Callback, Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	protected Callback callback;
	private Result result;

	/**
	 * Register the callback for this task. Given the task status, the methods {@link BaseAsyncTask#onPreExecute()} or
	 * {@link BaseAsyncTask#onPostExecute(Object)} may be called.
	 * 
	 * @param callback The callback object.
	 */
	public final void setCallback(Callback callback) {
		this.callback = callback;
		if (callback != null) {
			switch (getStatus()) {
			case RUNNING:
				onPreExecute();
				break;
			case FINISHED:
				onPostExecute(result);
				break;
			}
		}
	}

	/**
	 * Returns whether if this task is finished or not.
	 * 
	 * @return {@code true} if the execution is finished, {@code false} otherwise.
	 */
	public final boolean isFinished() {
		return getStatus() == Status.FINISHED;
	}

	@Override
	protected void onPostExecute(Result result) {
		this.result = result;
	};

}