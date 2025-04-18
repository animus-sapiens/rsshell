# Remote Shell Socket

## Table of contents
- [Project Overview](#project-overview)
- [Software Requirements](#software-requirements)
- [Building and Execution](#building-and-execution)
- [License](#license)
- [Used Frameworks](#used-frameworks)


## Project Overview
Spring Boot application in Gradle three modules project.

<ul>
  <li>
    <div>rsstub</div>
    <div>Library implementing Spring Reactive WebSocket with Spring Shell.</div>
  </li>
  <li>
    <div>rsweb</div>
    <div>Spring Boot Server application using rsstub module and Xterm.js as a Node.js front-end. Custom shell command implementing OSHI system analysis.</div>
  </li>
  <li>
    <div>rscmd</div>
    <div>Client Spring Reactive WebSocket command-line application with loaded Spring Boot features.</div>
  </li>
</ul>

### Software requirements
- Java JDK 17+
- Gradle 8
- Windows, Linux and macOS specified in
  <a target="_blank" href= https://www.oracle.com/java/technologies/javase/products-doc-jdk17certconfig.html >Oracle JDK 17 Certified System Configurations</a>
  or later.

### Building and Execution
Build with test execution:
```
gradlew build
```
Only test:
```
gradlew test --rerun-tasks
```
For detailed test log:
```
gradlew test --rerun-tasks --info
```
Run web-terminal:
```
gradlew bootRun
```
Open web-terminal:
<p style="text-align: left;">
<a target="_blank" href=http://localhost:8084>http://localhost:8084</a>
</p>

Can be run command-line terminal:
```
java -jar rscmd-1.0.0.jar
```

List all in-built and custom shell commands:
```
help
```
Execute custom shell command:
```
system-info
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