plugins {
    kotlin("jvm") version "1.2.51"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.6")
    compile("commons-io:commons-io:2.6")
}

task<JavaExec>("manual") {
    classpath = java.sourceSets["main"].runtimeClasspath
    main = "manual.Manual"
//    jvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=n,address=127.0.0.1:5005,suspend=y")
}

task<JavaExec>("annotation") {
    classpath = java.sourceSets["main"].runtimeClasspath
    main = "annotation.Annotation"
}