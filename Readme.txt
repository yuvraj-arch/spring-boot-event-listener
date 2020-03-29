
How to use Jib to containerize your application
Jib on Maven

<plugin>
  <groupId>com.google.cloud.tools</groupId>
  <artifactId>jib-maven-plugin</artifactId>
  <version>0.9.0</version>
  <configuration>
    <to>
      <image>gcr.io/my-project/image-built-with-jib</image>
    </to>
  </configuration>
</plugin>


# Builds to a container image registry.
$ mvn compile jib:build
# Builds to a Docker daemon.
$ mvn compile jib:dockerBuild


Jib on Gradle

plugins {
  id 'com.google.cloud.tools.jib' version '0.9.0'
}
jib.to.image = 'gcr.io/my-project/image-built-with-jib'

# Builds to a container image registry.
$ gradle jib
# Builds to a Docker daemon.
$ gradle jibDockerBuild
