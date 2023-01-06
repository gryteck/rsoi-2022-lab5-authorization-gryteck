plugins {
    kotlin("jvm")
}

group = "io.gryteck"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ticket-service-api"))
    implementation(project(":bonus-service-api"))

    implementation(kotlin("stdlib"))
}