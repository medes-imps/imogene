package org.imogene.sync.client.jobs;

import java.net.InetAddress;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.imogene.sync.client.SyncActivator;
import org.imogene.sync.client.i18n.Messages;
import org.imogene.sync.client.ui.ISyncConstants;

public class NTPOffsetJob extends PeriodicalJob {

	private String host;
	private long rate;

	public NTPOffsetJob() {
		super(Messages.ntp_title);
	}

	/**
	 * Set the NTP host address.
	 * 
	 * @param host The NTP host address
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Set the update rate of the offset.
	 * 
	 * @param rate The update rate in milliseconds
	 */
	public void setRate(long rate) {
		this.rate = rate;
		schedule(false, rate);
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
			return new Status(IStatus.ERROR, SyncActivator.PLUGIN_ID, Messages.ntp_error, e);
		} finally {
			client.close();
			schedule(false, rate);
		}
	}

}
