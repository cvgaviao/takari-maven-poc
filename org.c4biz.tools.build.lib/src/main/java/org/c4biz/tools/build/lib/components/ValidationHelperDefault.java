package org.c4biz.tools.build.lib.components;

import io.takari.incrementalbuild.BuildContext;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.rtinfo.RuntimeInformation;
import org.c4biz.tools.build.lib.ValidationHelper;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

/**
 * 
 * @author cvgaviao
 *
 */
@Named("default")
public class ValidationHelperDefault implements ValidationHelper {

    RuntimeInformation runtimeInformation;
    BuildContext buildContext;
    MavenProject project;
    Log log;

    @Override
    public void validateClassRealm() throws MojoFailureException {
        ClassRealm classRealm = project.getClassRealm();
        if (classRealm != null) {
            try {
                // make sure project realm and plugin realm agree on
                // BuildContext.class
                // the condition is the same instance (i.e. ==) not equal
                // objects
                Class<?> contextClass = buildContext.getClass();
                if (contextClass != classRealm
                        .loadClass(contextClass.getName())) {
                    // NoClassDefFoundError triggers maven core to dump
                    // classrealm state
                    throw new NoClassDefFoundError(
                            "Inconsistent MavenProject class realm");
                }
            } catch (ClassNotFoundException e) {
                throw new MojoFailureException(
                        "Inconsistent MavenProject class realm", e);
            }
        }

    }

    @Override
    public void validateMavenVersion(String versionRange) {
        if (!runtimeInformation.isMavenVersion(versionRange)) {
            log.error("Lunifera requires that Maven version be in this range: "
                    + versionRange);
        }
    }

    @Override
    public void validatePackaging(List<String> supportedProjectTypes)
            throws MojoFailureException {
        if (!supportedProjectTypes.contains(project.getArtifact().getType())) {
            throw new MojoFailureException(
                    "Ignoring subsystem plugin for project '" + project.getId()
                            + "'");
        }
    }

    @Inject
    public ValidationHelperDefault(final MavenProject project,
            final BuildContext buildContext,
            final RuntimeInformation runtimeInformation) {
        this.buildContext = buildContext;
        this.project = project;
        this.runtimeInformation = runtimeInformation;
    }

    @Override
    public void initializeLogging(Log log) {
        this.log = log;
    }

	@Override
	public boolean validateConfiguration(Map<String, String> config) {
		return true;
	}

}
