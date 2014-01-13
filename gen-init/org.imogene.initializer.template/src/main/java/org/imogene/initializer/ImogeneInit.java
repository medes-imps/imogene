package org.imogene.initializer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class that enables to insert rows in the Imogene database tables:
 * - the default user and its association with the default profile
 * - the default profile
 * - the entities that can be synchronized
 */
public class ImogeneInit {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		Initializer initializer = (Initializer) ctx.getBean("initializer");
		initializer.initialize();
	}

}
