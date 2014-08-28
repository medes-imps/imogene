package org.imogene.xpand2.output;

import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;

public class QuoteProcessor implements PostProcessor {

	@Override
	public void beforeWriteAndClose(FileHandle impl) {
		impl.setBuffer(new StringBuffer(impl.getBuffer().toString().replace("'", "\\'")));
	}

	@Override
	public void afterClose(FileHandle impl) {
		// Do nothing here
	}

}
