package org.imogene.web.client.ui.field.error;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;

public class ImogEditorError implements EditorError {
	
	private Editor<?> editor;
	private String message;
	
	
	public ImogEditorError() {
	}

	@Override
	public String getAbsolutePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Editor<?> getEditor() {
		return editor;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getUserData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConsumed(boolean consumed) {
		// TODO Auto-generated method stub
	}

	public void setEditor(Editor<?> editor) {
		this.editor = editor;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
