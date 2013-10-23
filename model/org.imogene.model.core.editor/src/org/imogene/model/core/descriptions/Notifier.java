package org.imogene.model.core.descriptions;

import java.util.HashMap;

import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.imogene.model.core.CardEntity;

public class Notifier {
	
	private static Notifier sInstance;
	
	public static Notifier getInstance() {
		if (sInstance == null) {
			sInstance = new Notifier();
		}
		return sInstance;
	}
	
	private HashMap<CardEntity, ScrolledForm> map = new HashMap<CardEntity, ScrolledForm>();
	
	public void addForm(CardEntity entity, ScrolledForm form) {
		map.put(entity, form);
	}
	
	public void clear() {
		map.clear();
	}
	
	public void layout() {
		for (ScrolledForm form : map.values()) {
			form.layout(true, true);
		}
	}
	
	public void layout(CardEntity entity) {
		if (map.containsKey(entity)) {
			map.get(entity).reflow(true);
		}
	}

}
