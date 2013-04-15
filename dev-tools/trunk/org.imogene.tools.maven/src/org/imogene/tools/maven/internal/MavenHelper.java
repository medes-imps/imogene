package org.imogene.tools.maven.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.DependencyResolutionResult;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.project.ProjectBuildingRequest.RepositoryMerging;
import org.apache.maven.project.ProjectBuildingResult;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;

public class MavenHelper {

	private RepositorySystem repositorySystem;

	private RepositorySystemSession repositorySystemSession;

	private ProjectBuilder projectBuilder;

	private ProjectBuildingRequest request;

	private ProjectBuildingResult result;

	private File pomFile;

	private File localRepositoryBaseDir;

	public MavenHelper(File pomFile) {
		this.pomFile = pomFile;
		repositorySystem = MavenUtils.newRepositorySystem();
		repositorySystemSession = MavenUtils
				.newRepositorySystemSession(repositorySystem);

		PlexusContainer plexusContainer;
		try {
			plexusContainer = new DefaultPlexusContainer();
		} catch (PlexusContainerException e) {
			throw new IllegalStateException(e);
		}
		try {
			projectBuilder = plexusContainer.lookup(ProjectBuilder.class);
		} catch (ComponentLookupException e) {
			throw new IllegalStateException(e);
		}

		request = new DefaultProjectBuildingRequest();
		request.setRepositorySession(repositorySystemSession);
		request.setProcessPlugins(false);
		request.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_0);
		localRepositoryBaseDir = repositorySystemSession.getLocalRepository()
				.getBasedir();
	}

	public MavenProject buildMavenProject() throws ProjectBuildingException {
		buildProjectWithDependencies(true);
		return result.getProject();
	}

	public List<String> getDependenciesJarsPath() {
		List<String> jars = new ArrayList<String>();
		if (result == null) {
			try {
				buildProjectWithDependencies(true);
			} catch (ProjectBuildingException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {			
			DependencyNode node = result.getDependencyResolutionResult()
					.getDependencyGraph();			
			addJarsToList(node, jars);	
			//displayDependencies(node);
		}
		return jars;
	}

	public List<File> getDependenciesJars() {
		List<String> jars = getDependenciesJarsPath();
		List<File> files = new ArrayList<File>();
		for (String s : jars) {
			files.add(new File(s));
		}
		return files;
	}

	private void buildProjectWithDependencies(boolean resolveDependencies)
			throws ProjectBuildingException {

		request.setResolveDependencies(resolveDependencies);
		request.setRepositoryMerging(RepositoryMerging.REQUEST_DOMINANT);
		result = projectBuilder.build(pomFile, request);			
		handleProblem(result);
	}
	
	private void handleProblem(ProjectBuildingResult result){
		List<ModelProblem> problems = result.getProblems();
		for(ModelProblem mp : problems){
			LogUtils.logError(mp.getMessage(), mp.getException());
		}
		DependencyResolutionResult depRes = result.getDependencyResolutionResult();
		List<Dependency> allDeps = depRes.getDependencies();
		List<Dependency> resolvedDeps = depRes.getResolvedDependencies();
		List<Dependency> unresolvedDeps = depRes.getUnresolvedDependencies();
		
		StringBuilder builder = new StringBuilder();
		builder.append("Dependencies count: ").append(allDeps.size());
		builder.append(" -  resolved: ").append(resolvedDeps.size());
		builder.append(" -  unresolved: ").append(unresolvedDeps.size());
		LogUtils.logInfo(builder.toString());
		for(Dependency d : unresolvedDeps){
			LogUtils.logError("Unresolved dependency : "+d.getArtifact());
		}
		
		
	}
	
	@SuppressWarnings("unused")
	private void displayDependencies(DependencyNode node) {

		if (node.getDependency() != null) {
			Artifact a = node.getDependency().getArtifact();
			if (a != null){
				LogUtils.logInfo(MavenUtils.getJarPath(a,
						localRepositoryBaseDir.getAbsolutePath()));
			}
		}
		for (DependencyNode n : node.getChildren())
			displayDependencies(n);
	}

	private void addJarsToList(DependencyNode node, List<String> jars) {
		if (node.getDependency() != null) {
			Artifact a = node.getDependency().getArtifact();
			if (a != null)
				jars.add(MavenUtils.getJarPath(a,
						localRepositoryBaseDir.getAbsolutePath()));
		}
		for (DependencyNode n : node.getChildren())
			addJarsToList(n, jars);
	}

}
