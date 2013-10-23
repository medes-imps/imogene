package org.imogene.web.client.ui.field.widget;
import com.google.gwt.dom.client.Document;
import com.google.gwt.text.client.IntegerParser;
import com.google.gwt.text.client.IntegerRenderer;
import com.google.gwt.user.client.ui.ValueBox;

/**
 * A ValueBox that uses {@link IntegerParser} and {@link IntegerRenderer}.
 */
public class ImogIntBox extends ValueBox<Integer> {
  public ImogIntBox() {
    super(Document.get().createTextInputElement(), ImogIntRenderer.instance(),
    		ImogIntParser.instance());
  }
}