package org.c4biz.tools.build.plugin;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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

@Mojo(name = "generateContainerArchive", defaultPhase = LifecyclePhase.PACKAGE, requiresProject = true, inheritByDefault = true, aggregator = true, threadSafe = false, requiresDependencyResolution = ResolutionScope.COMPILE, requiresDependencyCollection = ResolutionScope.COMPILE)
public class MojoGenerateContainerArchive extends AbstractContainerPackMojo {

	@Inject
	public MojoGenerateContainerArchive(final MavenProject project, RuntimeInformation runtimeInformation,
			MavenProjectHelper projectHelper, BuildContext buildContext, ValidationHelper validationHelper) {
		super(project, runtimeInformation, projectHelper, buildContext, validationHelper);
	}

	/**
	 * Base directory of the project.
	 */
	@Parameter(required = true, property = "basedir", readonly = true)
	private File basedir;

	/**
	 * Location where the downloaded bundle dependencies will be stored.
	 */
	@Parameter(property = "bundlesDirectory", defaultValue = "${workingDir}/bundles", required = true)
	private File bundlesDirectory;

	/**
	 * The final name of the generated artifact file.
	 */
	@Parameter(defaultValue = "${project.build.finalName}", required = true, readonly = true)
	private String finalName;

	/**
	 * The project's remote repositories to use for the resolution of plugins
	 * and their dependencies.
	 */
	@Parameter(required = true, property = "project.remoteProjectRepositories", readonly = true)
	private List<RemoteRepository> remoteRepos;

	@Override
	protected void executeMojo() throws MojoExecutionException, MojoFailureException {

		Map<String, String> config = Maps.newHashMap();
		if (validationHelper.validateConfiguration(config)) {
			getLog().info("Container archive was sucessfully generated.");
		}
	}

	protected String shortDescription() {

		return "Container archive generation";
	}

}
