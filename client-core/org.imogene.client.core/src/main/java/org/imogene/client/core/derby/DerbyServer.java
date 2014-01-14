package org.imogene.client.core.derby;

import org.apache.derby.drda.NetworkServerControl;

public class DerbyServer {

	private NetworkServerControl mNetworkServerControl;

	public void start() throws Exception {
		mNetworkServerControl = new NetworkServerControl();
		mNetworkServerControl.start(null);
	}

	public void stop() throws Exception {
		mNetworkServerControl.shutdown();
	}

}
