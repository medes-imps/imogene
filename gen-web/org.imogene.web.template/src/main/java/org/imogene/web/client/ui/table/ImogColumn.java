package org.imogene.web.client.ui.table;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

public abstract class ImogColumn<T,C> extends Column<T,C> {

	public ImogColumn(Cell<C> cell) {
		super(cell);
	}

	public abstract String getPropertyName ();
	
}
