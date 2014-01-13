package org.imogene.web.client.ui.table.pager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.user.cellview.client.SimplePager;


/**
 * NOT USED
 * @author MEDES-IMPS
 *
 */
public class ImogPager extends SimplePager {

	public ImogPager() {
		super(TextLocation.CENTER, getDefaultResources(), false, 1000, true);
	}


	private static ImogPagerResources IMOG_RESOURCES;

	private static ImogPagerResources getDefaultResources() {
		if (IMOG_RESOURCES == null) {
			IMOG_RESOURCES = GWT.create(ImogPagerResources.class);
		}
		return IMOG_RESOURCES;
	}

	/**
	 * A ClientBundle that provides images for this widget.
	 */
	public static interface ImogPagerResources extends Resources {

		/**
		 * The image used to go to the first page.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerFirstPage();

		/**
		 * The disabled first page image.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerFirstPageDisabled();

		/**
		 * The image used to go to the last page.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerLastPage();

		/**
		 * The disabled last page image.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerLastPageDisabled();

		/**
		 * The image used to go to the next page.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerNextPage();

		/**
		 * The disabled next page image.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerNextPageDisabled();

		/**
		 * The image used to go to the previous page.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerPreviousPage();

		/**
		 * The disabled previous page image.
		 */
		@ImageOptions(flipRtl = true)
		ImageResource simplePagerPreviousPageDisabled();
		
		

		/**
		 * The styles used in this widget.
		 */
		@Source("ImogPager.css")
		Style simplePagerStyle();
	}
	

}
