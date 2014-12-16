package org.imogene.dao.oaw.generator;

import java.util.Random;

public class DaoUtil {
	
	public static final String generateSerialVersionUID() {
		Random random = new Random();
		return Long.toString(random.nextLong());
	}

	public static final void log(String message) {
		System.out.println("+++INFO: " + message);
	}

}
