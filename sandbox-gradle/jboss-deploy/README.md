https://github.com/michel-kraemer/gradle-download-task



http://www.gradle.org/docs/current/userguide/working_with_files.html
http://www.gradle.org/docs/current/userguide/tutorial_this_and_that.html


ow to debug gradle plugins

http://forums.gradle.org/gradle/topics/how_do_you_attach_a_debugger_to_gradle_so_that_i_can_debug_it_running_a_task

export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5006"
./gradlew cargoDeployRemote --no-daemon -Dorg.gradle.debug=true



How to search in jars
find . -name *.jar -exec bash -c "echo {} && jar tvf {} | grep ClientLoginModule " \;


# How to configure jboss #
https://community.jboss.org/thread/168789?tstart=120

http://jaitechwriteups.blogspot.de/2011/09/jboss-as-702-arc-released.html

https://issues.jboss.org/browse/AS7-1752


https://issues.jboss.org/browse/AS7-4123 add-user

# Manual steps to do #
* add a management user to jboss