package org.c4biz.tools.build.plugin;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestResources;

import org.junit.Rule;

public abstract class AbstractUnitTest {

	@Rule
	public TestResources resources = new TestResources();

	@Rule
	public TestMavenRuntime mojos = new TestMavenRuntime();

}
