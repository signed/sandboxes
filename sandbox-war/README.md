# JBoss #


## logging ##

http://martijnverburg.blogspot.de/2010/12/jboss-separating-application-logs.html
Intresting approach to add some directory to the classpath using the manifest of the war

## classloader ##

https://community.jboss.org/wiki/classloadingconfiguration

## 6.1.0 final ##

### Analysis result ###

Files that are put into server/<instance>/conf can be accesses by each web application that runs within <instance> (eg. default instance).
This also works with files in arbitrary subdirectories of conf.

### links ###
* https://community.jboss.org/message/571408




## 7.1 ##

### classloader ###

* http://www.javacodegeeks.com/2012/09/jboss-as-7-classloading-explained.html

### links ###
* https://docs.jboss.org/author/display/AS71/Class+Loading+in+AS7
* http://stackoverflow.com/questions/15361175/adding-of-external-jar-file-into-classpath-for-jboss-7
* https://community.jboss.org/wiki/HowToPutAnExternalFileInTheClasspath
* http://stackoverflow.com/questions/15365036/jboss-as-7-application-specific-properties-file

