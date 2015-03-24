package org.c4biz.tools.build.plugin;


import io.takari.incrementalbuild.BuildContext;
import io.takari.incrementalbuild.Incremental;
import io.takari.incrementalbuild.Incremental.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.c4biz.tools.build.lib.ValidationHelper;


/**
 * 
 * @author Cristiano Gavião
 *
 */
public abstract class AbstractContainerPackMojo extends
        org.apache.maven.plugin.AbstractMojo {

    /**
     * Base directory of the project.
     */
    @Parameter(required = true, property = "basedir", readonly = true)
    private File basedir;

    /**
     * 
     */
    private final BuildContext buildContext;

    @Parameter(defaultValue = "${project.build.directory}",
            property = "buildDirectory", required = true, readonly = true)
    @Incremental(configuration = Configuration.ignore)
    private File buildDirectory;

    /**
     * 
     */
    @Parameter(required = true, property = "plugin", readonly = true)
    @Incremental(configuration = Configuration.ignore)
    // for Maven 3 only
    private PluginDescriptor pluginDescriptor;

    /**
     * The Maven project.
     */
    private MavenProject project;

    /**
     * The Maven project helper.
     */
    private final MavenProjectHelper projectHelper;

    /**
     * The runtime information for Maven, used to retrieve Maven's version
     * number.
     */
    private final RuntimeInformation runtimeInformation;

    /**
     * The Maven session".
     * <p>
     * Required in order to create the jar artifact.
     */
    @Parameter(required = true, property = "session", readonly = true)
    @Incremental(configuration = Configuration.ignore)
    private MavenSession session;

    @Parameter(defaultValue = "false", property = "skip", required = true)
    @Incremental(configuration = Configuration.ignore)
    private boolean skip;

    /**
     * .
     */
    @Parameter(defaultValue = "${project.build.sourceEncoding}",
            property = "sourceEncoding", required = false)
    private String sourceEncoding;

    @Parameter()
    private List<String> supportedProjectTypes = Arrays.asList("containerPack");
    
    protected final ValidationHelper validationHelper;
    

    @Inject
    public AbstractContainerPackMojo(final MavenProject project,
            final RuntimeInformation runtimeInformation,
            final MavenProjectHelper projectHelper,
            final BuildContext buildContext, @Named("default") ValidationHelper validationHelper2) {
        this.validationHelper = validationHelper2;
//        this.validationHelper = new Validation(project,
//                buildContext, runtimeInformation, getLog());
        this.buildContext = buildContext;
        this.runtimeInformation = runtimeInformation;
        this.projectHelper = projectHelper;
    }

    @Override
    public final void execute() throws MojoExecutionException,
            MojoFailureException {
        if (skip) {
            getLog().info(
                    "Skiping " + shortDescription() + " for project "
                            + getProject().getId());
            return;
        }

        validationHelper.initializeLogging(getLog());

        executeMojo();
    }

    protected abstract void executeMojo() throws MojoExecutionException,
            MojoFailureException;

    protected BuildContext getBuildContext() {
        return buildContext;
    }

    protected File getBuildDirectory() {
        return buildDirectory;
    }

    protected PluginDescriptor getPluginDescriptor() {
        return pluginDescriptor;
    }

    protected MavenProject getProject() {
        return project;
    }

    protected MavenProjectHelper getProjectHelper() {
        return projectHelper;
    }

    public RuntimeInformation getRuntimeInformation() {
        return runtimeInformation;
    }

    protected MavenSession getSession() {
        return session;
    }

    public List<String> getSupportedProjectTypes() {
        return supportedProjectTypes;
    }

    public void setSupportedProjectTypes(List<String> supportedProjectTypes) {
        this.supportedProjectTypes = supportedProjectTypes;
    }

    protected abstract String shortDescription();

}
