package org.c4biz.tools.build.lib;

import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

public interface ValidationHelper {

    void validateClassRealm() throws MojoFailureException;

    void validateMavenVersion(String versionRange) throws MojoFailureException;

    void validatePackaging(List<String> supportedProjectTypes)
            throws MojoFailureException;

    void initializeLogging(Log log);

	boolean validateConfiguration(Map<String, String> config);

}
