https://github.com/michel-kraemer/gradle-download-task



http://www.gradle.org/docs/current/userguide/working_with_files.html
http://www.gradle.org/docs/current/userguide/tutorial_this_and_that.html


ow to debug gradle plugins

http://forums.gradle.org/gradle/topics/how_do_you_attach_a_debugger_to_gradle_so_that_i_can_debug_it_running_a_task

export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5006"
./gradlew cargoDeployRemote --no-daemon -Dorg.gradle.debug=true
