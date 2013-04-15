package org.imogene.web.client.ui.field.widget;
import com.google.gwt.dom.client.Document;
import com.google.gwt.text.client.IntegerParser;
import com.google.gwt.text.client.IntegerRenderer;
import com.google.gwt.user.client.ui.ValueBox;

/**
 * A ValueBox that uses {@link IntegerParser} and {@link IntegerRenderer}.
 */
public class ImogDblBox extends ValueBox<Double> {
  public ImogDblBox() {
    super(Document.get().createTextInputElement(), ImogDoubleRenderer.instance(),
    		ImogDoubleParser.instance());
  }
}