plugins {
    kotlin("jvm")
    id("java-library")
}

group = "io.gryteck"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":bonus-service-api"))

    implementation(kotlin("stdlib"))
}
