package org.example.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

// https://docs.junit.org/6.0.2/advanced-topics/junit-platform-suite-engine.html
@Suite
@SelectPackages({""})
@IncludeTags({"one | three"})
public class TagSuite {
}