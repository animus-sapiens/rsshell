# Remote Shell Socket

## Table of contents
- [Project Overview](#project-overview)
- [Software Requirements](#software-requirements)
- [Building and Execution](#building-and-execution)
- [License](#license)
- [Used Frameworks](#used-frameworks)


## Project Overview
Spring Boot application in three module Gradle project.

<ul>
  <li>
    <div>rsstub</div>
    <div>Library implementing Spring Reactive WebSocket with Spring Shell for implementing custom command.</div>
  </li>
  <li>
    <div>rsweb</div>
    <div>Server application on Spring Boot, using rsstub lib and Node.js with Xterm.js. Custom shell command implements OSHI</div>
  </li>
  <li>
    <div>rscmd</div>
    <div>Client Spring Reactive WebSocket command-line application with selectively loaded Spring Boot frameworks.</div>
  </li>
</ul>

### Software requirements
- Java JDK 17+
- Gradle 7.4+
- Windows, Linux and macOS specified in
  <a target="_blank" href= https://www.oracle.com/java/technologies/javase/products-doc-jdk17certconfig.html >Oracle JDK 17 Certified System Configurations</a>
  or later.

### Building and Execution
Build with test execution:
```
./gradlew build
```
Only test:
```
./gradlew test
```
For detailed test log:
```
./gradlew test --info
```
Run web-terminal:
```
./rsweb/gradlew bootRun
```
or
```
java -jar  rsweb.jar
```
Open web-terminal:
<p style="text-align: left;">
<a target="_blank" href=http://localhost>http://localhost</a>
</p>

List built in and shell commands:
```
help
```
Execute custom shell command:
```
system-info
```
Run shell-terminal:
```
java -jar rscmd-1.0.0.jar
```

### License

<p style="text-align: left;">
<a target="_blank" href=https://www.mozilla.org/en-US/MPL/2.0/>Mozilla Public License
Version 2.0</a>
</p>

### Used Frameworks
- https://docs.spring.io/spring-framework/reference/rsocket.html
- https://docs.spring.io/spring-framework/reference/web/webflux-websocket.html
- https://spring.io/projects/spring-shell
- https://nodejs.org/
- https://github.com/xtermjs/xterm.js
- https://www.npmjs.com/package/bootstrap
- https://rsocket.io/guides/rsocket-js/
- https://github.com/oshi/oshi
- https://gradle.org/
- https://plugins.gradle.org/plugin/com.github.node-gradle.node
- https://plugins.gradle.org/plugin/com.github.sherter.google-java-format