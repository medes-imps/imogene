package org.imogene.web.client.ui.table;

import org.imogene.web.client.ui.table.ImogDynaTable.TableResources;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * A DataGrid that exposes its scroll panel
 * @author MEDES-IMPS
 */
public class ImogDataGrid<T> extends DataGrid<T> {
	
	public ImogDataGrid(int itemByPage, TableResources resources) {
		super(itemByPage, resources);
	}

	public ScrollPanel getScrollPanel() {
        HeaderPanel header = (HeaderPanel) getWidget();
        return (ScrollPanel) header.getContentWidget();
    }
	
}
