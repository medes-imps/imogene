package org.imogene.notifier.server.services;

import org.apache.log4j.Logger;
import org.imogene.notifier.server.NotificationInstance;

/**
 * Base implementation for services.
 * 
 * @author Medes-IMPS
 */
public class NotImplementedService implements NotificationService {

	Logger logger = Logger.getLogger(" org.imogene.notifier.server.services");

	@Override
	public void sendNotification(NotificationInstance notification) {
		logger.warn("This service is not implemented");
	}

}
