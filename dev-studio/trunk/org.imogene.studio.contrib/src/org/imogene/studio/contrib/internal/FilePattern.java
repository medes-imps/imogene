package org.imogene.studio.contrib.internal;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FilePattern {

	private ArrayList<String> patterns = new ArrayList<String>();
	
    private static final String EXTENSIONS_ATTR = "extensions"; //$NON-NLS-1$
    private static final String FILENAMES_ATTR = "filenames"; //$NON-NLS-1$
    private static final String DELIMITER = ","; //$NON-NLS-1$
	
    public FilePattern(NodeList elements) {
		for (int i = 0; i < elements.getLength(); i++) {
			NamedNodeMap nnm = elements.item(i).getAttributes();
			Node filenames = nnm.getNamedItem(FILENAMES_ATTR);
			if (filenames != null) {
				StringTokenizer st = new StringTokenizer(filenames.getNodeValue(), DELIMITER);
				while (st.hasMoreTokens()) {
					addPattern(st.nextToken());
				}
			}
			Node extensions = nnm.getNamedItem(EXTENSIONS_ATTR);
			if (extensions != null) {
				StringTokenizer st = new StringTokenizer(extensions.getNodeValue(), DELIMITER);
				while (st.hasMoreTokens()) {
					addPattern(st.nextToken());
				}
			}
		}
	}
    
    private void addPattern(String pattern) {
    	if (!patterns.contains(pattern))
    		patterns.add(pattern);
    }
    
    public boolean matches(String fileName) {
    	boolean match = patterns.isEmpty();
		for (String pattern : patterns)
			match = match || fileName.endsWith(pattern);
		return match;
    }
}
