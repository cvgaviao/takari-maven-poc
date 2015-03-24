
package org.c4biz.tools.build.plugin.it;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({ "3.2.2" })
public class ValidPomIntegrationTest extends AbstractIntegrationTest {

	public ValidPomIntegrationTest(MavenRuntimeBuilder builder) throws Exception {
		super(builder);
	}

	@Test
	public void testGeneralLifecycle() throws Exception {
		File basedir = resources.getBasedir("project--valid-file");

		MavenExecutionResult result = verifier.forProject(basedir).execute("package");
		result.assertLogText("Container folder was sucessfully generated.");
		result.assertLogText("Container archive was sucessfully generated.");
		// TODO assert the class file(s) were actually created
	}

}