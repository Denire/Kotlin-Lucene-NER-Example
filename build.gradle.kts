plugins {
    kotlin("jvm") version "1.3.70"
}

group = "com.mva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val luceneVersion = "7.1.0"
fun lucene(module: String) = "org.apache.lucene:$module:$luceneVersion"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile(lucene("lucene-core"))
    compile(lucene("lucene-queryparser"))
    compile(lucene("lucene-analyzers-common"))
    compile("org.apache.commons:commons-csv:1.5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}