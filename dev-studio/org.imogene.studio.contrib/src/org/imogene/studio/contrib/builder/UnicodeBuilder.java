package org.imogene.studio.contrib.builder;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.imogene.model.core.CardEntity;
import org.imogene.model.core.Description;
import org.imogene.model.core.EnumField;
import org.imogene.model.core.EnumValue;
import org.imogene.model.core.FieldEntity;
import org.imogene.model.core.FieldGroup;
import org.imogene.model.core.Project;
import org.imogene.model.core.StringField;
import org.imogene.model.core.Thema;
import org.imogene.model.core.ValidationRule;


public class UnicodeBuilder extends IncrementalProjectBuilder {
	
	private static final String EXTENSION = "imog"; //$NON-NLS-1$

	@SuppressWarnings("unchecked")
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {

		if (shouldAudit(kind))
			ResourcesPlugin.getWorkspace().run(new UnicodeRunnable(), monitor);
		return null;
	}
	
	private boolean shouldAudit(int kind) {
		if (kind == FULL_BUILD)
			return true;
		IResourceDelta delta = getDelta(getProject());
		if (delta == null)
			return false;
		IResourceDelta[] children = delta.getAffectedChildren();
		for (IResourceDelta child : children)
			if (EXTENSION.equals(child.getProjectRelativePath().getFileExtension()))
				return true;
		return false;
	}
	
	private class UnicodeRunnable implements IWorkspaceRunnable {
		
		@Override
		public void run(IProgressMonitor monitor) throws CoreException {
			IResource[] resources = getProject().members();
			for (IResource resource : resources) {
				if (EXTENSION.equals(resource.getFileExtension())) {
					performUnicodeTranslation(resource);
					break;
				}
			}
		}
		
		private void performUnicodeTranslation(IResource resource) {
			URI resourceURI = URI.createPlatformResourceURI(resource.getFullPath().toPortableString(), true);
			Resource r = loadModel(resourceURI);

			Project model = (Project) r.getContents().get(0);
			treatDescriptions(model.getDescription());

			for (Thema thema : model.getThemas())
				treatDescriptions(thema.getDescriptions());

        	for (CardEntity card : model.getEntities()) {
        		treatDescriptions(card.getDescriptions());
	        	for (FieldGroup group : card.getGroups()) {
	        		treatDescriptions(group.getDescriptions());
	        		for (FieldEntity field : group.getFields()) {
	        			treatDescriptions(field.getDescriptions());
	        			if (field instanceof EnumField) {
	        				for (EnumValue value : ((EnumField) field).getEnumValues())
	        					treatDescriptions(value.getDescriptions());
	        			} else if (field instanceof StringField) {
	        				for (ValidationRule rule : ((StringField) field).getValidationRules())
	        					treatDescriptions(rule.getDescriptions());
	        			}
	        		}
	        	}
	        }
	        try {
	            r.save(null);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
		private Resource loadModel(final URI resUri) {
			return new ResourceSetImpl().getResource(resUri, true);
		}
		
		private void treatDescriptions(EList<Description> descriptions) {
			for (Description description : descriptions)
				treatDescription(description);
		}
		
		private void treatDescription(Description description) {
			description.setDisplay(convert(description.getDisplay()));
			description.setHelp(convert(description.getHelp()));
		}

	}
	
	private static String convert(String s) {
		if (s == null)
			return null;
		String result = new String();
		for (int i = 0 ; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0x80 || c == 0x3c || c == 0x26)
				result += "\\u" + UnicodeFormatter.charToHex(c); //$NON-NLS-1$
			else
				result += c;
		}
		return result;
	}
	
	static class UnicodeFormatter {
		
		static String byteToHex(byte b) {
			return Integer.toHexString((b >> 4) & 0x0f) + Integer.toHexString(b & 0x0f);
		}
		
		static String charToHex(char c) {
			byte hi = (byte) (c >>> 8);
			byte lo = (byte) (c & 0xff);
			return byteToHex(hi) + byteToHex(lo);
		}
		
	}
}
