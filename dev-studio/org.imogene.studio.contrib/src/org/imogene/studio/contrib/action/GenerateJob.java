package org.imogene.studio.contrib.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderImpl;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.corext.codemanipulation.OrganizeImportsOperation;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.imogene.oaw.generator.common.OawGeneratorMedooCommon;
import org.imogene.studio.contrib.interfaces.GenerationManager;
import org.imogene.studio.contrib.interfaces.PostGenerationTask;
import org.imogene.studio.contrib.internal.FilePatternSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@SuppressWarnings("restriction")
public class GenerateJob extends WorkspaceJob implements GenerationManager {

	private static final char DELIMITER = '%';

	private final IProject mSelectedProject;
	private final String mProjectName;
	private final InputStream mArchive;
	private final InputStream mDefinition;
	private final HashMap<String, String> mProperties;
	private final String mWorkflowPath;
	private final boolean mUnCompressArchive;

	private IProject mGeneratedProject;
	private List<PostGenerationTask> mPostGenerationTasks = null;
	private FilePatternSet patternSet;

	public GenerateJob(IProject selectedProject, String projectName, InputStream archive, InputStream definition,
			HashMap<String, String> properties, String workflow, boolean uncompress) {
		super(projectName);
		mSelectedProject = selectedProject;
		mProjectName = projectName;
		mArchive = archive;
		mDefinition = definition;
		mProperties = properties;
		mWorkflowPath = workflow;
		mUnCompressArchive = uncompress;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		mGeneratedProject = createProject(mProjectName, monitor);
		if (mGeneratedProject == null) {
			return Status.CANCEL_STATUS;
		}

		if (!mGeneratedProject.isOpen()) {
			mGeneratedProject.open(monitor);
		}

		if (mUnCompressArchive) {
			/* unzip the template and parse the properties */
			unCompressArchive(mGeneratedProject.getLocation().toOSString(), mArchive);
			parseTemplate();
			processFile(mGeneratedProject.getLocation().toFile());
		}

		/* generation process */
		if (mWorkflowPath != null) {
			injectClassLoader();
			createConsole();
			HashMap<String, String> slotContents = new HashMap<String, String>();
			WorkflowRunner runner = new WorkflowRunner();
			runner.run(mWorkflowPath, new ImogeneProgressMonitor(monitor), mProperties, slotContents);
		}

		mGeneratedProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);

		mGeneratedProject.close(monitor);
		mGeneratedProject.open(monitor);

		/* post generation tasks */
		if (mPostGenerationTasks != null) {
			for (PostGenerationTask task : mPostGenerationTasks) {
				try {
					task.onPostGeneration(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		mGeneratedProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);

		// organizeImport(project, monitor);

		return Status.OK_STATUS;
	}

	public void addPostGenerationTask(PostGenerationTask task) {
		if (mPostGenerationTasks == null) {
			mPostGenerationTasks = new Vector<PostGenerationTask>();
		}
		mPostGenerationTasks.add(task);
	}

	public void setPostGenerationTasks(List<PostGenerationTask> tasks) {
		mPostGenerationTasks = tasks;
	}

	public IProject getGeneratedProject() {
		return mGeneratedProject;
	}

	public IProject getSelectedProject() {
		return mSelectedProject;
	}

	@Override
	public Map<String, String> getProperties() {
		return mProperties;
	}

	/** create the new project into the workspace if it doesn't exist */
	private IProject createProject(final String projectName, IProgressMonitor monitor) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (!project.exists()) {
				project.create(monitor);
			}
			// project.open(monitor);
			return project;
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}

	}

	private boolean unCompressArchive(String projectLocation, InputStream archive) {
		try {
			ZipInputStream zis = new ZipInputStream(archive);
			ZipEntry ze = zis.getNextEntry();
			File file;
			FileOutputStream fos;

			while (ze != null) {
				String name = getParsedString(ze.getName());
				file = new File(projectLocation, name);
				File parentDir = file.getParentFile();
				if (parentDir != null) {
					parentDir.mkdirs();
				}

				if (ze.isDirectory()) {
					file.mkdirs();
				} else {
					fos = new FileOutputStream(file);
					byte[] buffer = new byte[8192];
					int length;
					while ((length = zis.read(buffer)) != -1) {
						fos.write(buffer, 0, length);
					}
					fos.flush();
					fos.close();
				}
				ze = zis.getNextEntry();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return true;
	}

	private String getParsedString(String name) {
		int start = -1;
		if ((start = name.indexOf(DELIMITER)) == -1)
			return name;

		int end = name.indexOf(DELIMITER, start + 1);

		if (end == -1)
			return name;

		String param = name.substring(start + 1, end);

		String value = mProperties.get(param);

		if (value != null)
			return name.substring(0, end + 1).replace(DELIMITER + param + DELIMITER, value)
					+ getParsedString(name.substring(end + 1));
		else
			return name.substring(0, end + 1) + getParsedString(name.substring(end + 1));
	}

	private void parseTemplate() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			Document dom = db.parse(mDefinition);

			NodeList nl = dom.getElementsByTagName("parseSet"); //$NON-NLS-1$
			if (nl.getLength() > 0) {
				patternSet = new FilePatternSet((Element) nl.item(0));
			}
		} catch (Exception e) {

		}
	}

	private void processFile(File file) {
		try {
			if (file.isDirectory()) {
				for (File f : file.listFiles())
					processFile(f);
				return;
			}

			if (patternSet != null && !patternSet.mustParse(file.getAbsolutePath()))
				return;

			FileInputStream fis = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fis);

			StringBuffer outBuffer = new StringBuffer();
			StringBuffer tmpBuffer = new StringBuffer();
			char[] buffer = new char[1024];
			int read = 0;

			boolean delimiterEncountered = false;
			while (read != -1) {
				read = reader.read(buffer);
				for (int i = 0; i < read; i++) {
					char c = buffer[i];
					if (c == DELIMITER) {
						if (delimiterEncountered) {
							String param = tmpBuffer.toString();
							String value = mProperties.get(param);
							if (value != null) {
								outBuffer.append(value);
							} else {
								outBuffer.append(DELIMITER);
								outBuffer.append(tmpBuffer);
								outBuffer.append(DELIMITER);
								tmpBuffer = null;
							}
							delimiterEncountered = false;
						} else {
							delimiterEncountered = true;
							tmpBuffer = null;
							tmpBuffer = new StringBuffer();
						}
					} else {
						if (delimiterEncountered) {
							tmpBuffer.append(c);
						} else {
							outBuffer.append(c);
						}
					}

				}
			}

			if (delimiterEncountered && tmpBuffer != null)
				outBuffer.append(tmpBuffer);

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(outBuffer.toString().getBytes());
			fos.flush();
			fos.close();
			fis.close();
			reader.close();
			fis.close();
		} catch (Exception e) {
		}
	}

	// inject the classloader associated with the current class
	private void injectClassLoader() {
		ResourceLoaderFactory.setCurrentThreadResourceLoader(new ResourceLoaderImpl(OawGeneratorMedooCommon
				.getDefault().getClass().getClassLoader()));
		Thread.currentThread().setContextClassLoader(GenerateJob.class.getClassLoader());
	}

	// Setup a console for the log output.
	private void createConsole() {
		MessageConsole console = null;
		IConsole[] consoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
		for (IConsole c : consoles) {
			if (c.getName().equals(Messages.GenerateJob_1)) {
				ConsolePlugin.getDefault().getConsoleManager().showConsoleView(c);
				console = (MessageConsole) c;
				console.clearConsole();
				break;
			}
		}

		if (console == null) {
			console = new MessageConsole(Messages.GenerateJob_2, null);
			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
			ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);

			final MessageConsoleStream stream = console.newMessageStream();

			// Register the logger and add a loglistener.
			Log.registerToLogFactory();
			Log.addLogListener(new LogListener() {
				@Override
				public void logEvent(LogEvent e) {
					// Print the messages in the console stream.
					stream.println(e.getMessage());

				}
			});
		}
	}

	private void organizeImport(final IProject project, final IProgressMonitor monitor) throws JavaModelException {
		IJavaProject jProject = JavaCore.create(project);
		for (IPackageFragment fragment : jProject.getPackageFragments()) {
			if (fragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				for (ICompilationUnit unit : fragment.getCompilationUnits()) {
					CompilationUnit astRoot = SharedASTProvider.getAST(unit, SharedASTProvider.WAIT_ACTIVE_ONLY,
							monitor);
					OrganizeImportsOperation op = new OrganizeImportsOperation(unit, astRoot, false, true, true, null);
					try {
						op.run(monitor);
					} catch (OperationCanceledException e) {
						e.printStackTrace();
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
