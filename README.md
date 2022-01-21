# About

A sandbox repository to investigate using more recent versions of Jenkins
together with the [Gradle Job DSL support plugin].


# Versions used

Java: 1.8.0 and 11.0.2
Gradle: 4.9, 6.3 and 6.9
Jenkins: 2.121 (upstream), 2.263.4 (works), 2.277.1 (fails)


# Background

> [With [JEP-228]], Jenkins 2.277.1 has replaced its outdated fork of the
> XStream XML serialization library with a recent release of the official
> XStream library.


# Observations

As also reported at [gradle-jenkins-job-dsl-plugin issue #23], we are
apparently observing [JENKINS-65341] here.

While everything works fine when running the test harness on Jenkins 2.121,
it fails when running it on Jenkins >=2.277.1.

```java
java.lang.VerifyError: (class: com/thoughtworks/xstream/core/util/SerializationMembers, method: callWriteObject signature: (Ljava/lang/Class;Ljava/lang/Object;Ljava/io/ObjectOutputStream;)V) Incompatible object argument for function call
	at hudson.util.RobustReflectionConverter.<init>(RobustReflectionConverter.java:106)
	at hudson.util.XStream2.setupConverters(XStream2.java:218)
	at com.thoughtworks.xstream.XStream.<init>(XStream.java:574)
	at com.thoughtworks.xstream.XStream.<init>(XStream.java:496)
	at com.thoughtworks.xstream.XStream.<init>(XStream.java:465)
	at com.thoughtworks.xstream.XStream.<init>(XStream.java:411)
	at com.thoughtworks.xstream.XStream.<init>(XStream.java:378)
	at hudson.util.XStream2.<init>(XStream2.java:113)
	at jenkins.model.Jenkins.<clinit>(Jenkins.java:5439)
	at hudson.PluginManager.<clinit>(PluginManager.java:2227)
	at org.jvnet.hudson.test.JenkinsRule.<init>(JenkinsRule.java:333)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at com.aoe.gradle.jenkinsjobdsl.JobScriptsSpec.$spock_initializeSharedFields(JobScriptsSpec.groovy:38)
```


# Synopsis

```shell
# Acquire sources
git clone https://github.com/crate-workbench/jenkins-gradle-job-dsl-plugin-sandbox
cd jenkins-gradle-job-dsl-plugin-sandbox

# Invoke test harness with Jenkins 2.121
./gradlew --build-file=build-2020.gradle clean jobDslTest --info

# Invoke test harness with Jenkins 2.277.1
./gradlew --build-file=build-2021.gradle clean jobDslTest --info
```


[Gradle Job DSL support plugin]: https://github.com/AOEpeople/gradle-jenkins-job-dsl-plugin
[JEP-228]: https://www.jenkins.io/doc/upgrade-guide/2.277/#xstream-update-jep-228
[gradle-jenkins-job-dsl-plugin issue #23]: https://github.com/AOEpeople/gradle-jenkins-job-dsl-plugin/issues/23
[JENKINS-65341]: https://issues.jenkins.io/browse/JENKINS-65341
