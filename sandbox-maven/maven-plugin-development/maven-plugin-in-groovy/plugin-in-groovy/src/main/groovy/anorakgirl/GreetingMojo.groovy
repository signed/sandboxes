package anorakgirl

import org.codehaus.gmaven.mojo.GroovyMojo

/**
 * Says "Hi" to the user
 *
 * @goal hello
 */
public class GreetingMojo extends GroovyMojo {
    void execute() {
        log.info('Hello Groovy World!')
    }
}
