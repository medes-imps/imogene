package org.imogene.sync.client.jobs;

import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.imogene.sync.client.SyncActivator;
import org.imogene.sync.client.ui.ISyncConstants;

public class NTPOffsetJob extends PeriodicalJob {

	private String host;
	private long rate;

	public NTPOffsetJob() {
		super("NtpOffsetJob"); //$NON-NLS-1$
	}

	/**
	 * Update the NTP parameters.
	 * <p>
	 * This will try to run the NTP client process if needed.
	 * </p>
	 * 
	 * @param host URL of the NTP host
	 * @param rate frequency of the updates
	 */
	public void setParameters(String host, long rate) {
		this.host = host;
		this.rate = rate;
		schedule(false, 0L);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		NTPUDPClient client = new NTPUDPClient();
		try {
			client.setDefaultTimeout(60000);
			TimeInfo time = client.getTime(InetAddress.getByName(host));
			time.computeDetails();
			client.close();
			SyncActivator.getDefault().getPreferenceStore().setValue(ISyncConstants.NTP_OFFSET, time.getOffset());
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, SyncActivator.PLUGIN_ID, "Error retrieving the time offset", e); //$NON-NLS-1$
		} finally {
			client.close();
			schedule(false, rate);
		}
	}

}
