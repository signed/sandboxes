package anorakgirl

import com.github.signed.sandbox.maven.plugin.Logic
import org.codehaus.gmaven.mojo.GroovyMojo

/**
 * Says "Hi" to the user
 *
 * @goal hello
 */
public class GreetingMojo extends GroovyMojo {
    void execute() {
        log.info(new Logic().executeLogic())
    }
}
