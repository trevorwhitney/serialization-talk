plugins {
    kotlin("jvm") version "1.2.51"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
}

task<JavaExec>("manual") {
    classpath = java.sourceSets["main"].runtimeClasspath
    main = "manual.ManualKt"
    args = listOf("hello")
}

task<JavaExec>("annotation") {
    classpath = java.sourceSets["main"].runtimeClasspath
    main = "annotation.AnnotationKt"
}