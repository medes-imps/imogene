package org.imogene.web.server.security;

/**
 * Handler to manage the class to secure the beans by filtering the field values
 * depending on the user roles
 * Used for the report generation
 * @author MEDES-IMPS
 */
public class ImogBeanFilterHandler {

	private ImogBeanFilter filter;

	/* The unique instance of this object */
	private static ImogBeanFilterHandler instance = null;

	public static ImogBeanFilterHandler getInstance() {
		if (instance == null)
			instance = new ImogBeanFilterHandler();
		return instance;
	}

	public ImogBeanFilter getFilter() {
		return filter;
	}

	/**
	 * Setter for bean injection
	 * @param filter
	 */
	public void setFilter(ImogBeanFilter filter) {
		this.filter = filter;
	}

}
