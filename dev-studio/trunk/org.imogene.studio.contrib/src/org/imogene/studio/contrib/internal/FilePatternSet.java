package org.imogene.studio.contrib.internal;

import org.w3c.dom.Element;

public class FilePatternSet {
	
	private final FilePattern mExcludedPattern;
	private final FilePattern mIncludedPattern;
	
    private static final String INCLUDE_TAG = "include"; //$NON-NLS-1$
    private static final String EXCLUDE_TAG = "exclude"; //$NON-NLS-1$
	
	public FilePatternSet(Element element) {
		mIncludedPattern = new FilePattern(element.getElementsByTagName(INCLUDE_TAG));
		mExcludedPattern = new FilePattern(element.getElementsByTagName(EXCLUDE_TAG));
	}
	
	public boolean mustParse(String fileName) {
		return mIncludedPattern.matches(fileName) && !mExcludedPattern.matches(fileName);
	}

}
