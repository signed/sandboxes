package com.github.signed.sandboxes.maven.surefire;

import org.codehaus.plexus.util.xml.Xpp3Dom;

public interface ConfigurationSink {
    public void consume(Xpp3Dom dom);
}
