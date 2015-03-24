package org.c4biz.tools.build.plugin;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.c4biz.tools.build.lib.ValidationHelper;
import org.eclipse.aether.repository.RemoteRepository;

import com.google.common.collect.Maps;

import io.takari.incrementalbuild.BuildContext;

@Mojo(name = "generateContainerFolder", defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, inheritByDefault = true, aggregator = true, threadSafe = false, requiresDependencyResolution = ResolutionScope.COMPILE, requiresDependencyCollection = ResolutionScope.COMPILE)
public class MojoGenerateContainerFolder extends AbstractContainerPackMojo {

	@Inject
	public MojoGenerateContainerFolder(final MavenProject project, RuntimeInformation runtimeInformation,
			MavenProjectHelper projectHelper, BuildContext buildContext, ValidationHelper validationHelper) {
		super(project, runtimeInformation, projectHelper, buildContext, validationHelper);
	}

	@Parameter(required = true, property = "session", readonly = true)
	private MavenSession mavenSession;

	@Parameter(required = true, property = "project.remoteProjectRepositories", readonly = true)
	private List<RemoteRepository> remoteRepos;

	@Parameter(defaultValue = "${project.build.sourceEncoding}", property = "sourceEncoding", required = false)
	private String sourceEncoding;

	@Override
	protected void executeMojo() throws MojoExecutionException, MojoFailureException {
		Map<String, String> config = Maps.newHashMap();
		if (validationHelper.validateConfiguration(config)) {
			getLog().info("Container folder was sucessfully generated.");
		}
	}

	protected String shortDescription() {

		return "Container folder generation";
	}

}
