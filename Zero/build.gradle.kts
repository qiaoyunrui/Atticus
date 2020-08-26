plugins {
    groovy
    java
    kotlin("jvm") version "1.4.0"
}

group = "me.juhezi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.codehaus.groovy:groovy-all:2.3.11")
    implementation(kotlin("stdlib"))
    testImplementation("junit", "junit", "4.12")
    implementation("com.google.guava:guava:29.0-jre")
}
