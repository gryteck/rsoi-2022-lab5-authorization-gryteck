import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring")
}

group = "io.gryteck"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
extra["springCloudVersion"] = "2020.0.4"
val feignVersion = "3.1.1"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ticket-service-api"))
    implementation(project(":bonus-service-api"))
    implementation(project(":user-service-api"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // config server
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

    // reactive feign
    implementation("com.playtika.reactivefeign:feign-reactor-webclient:$feignVersion")
    implementation("com.playtika.reactivefeign:feign-reactor-cloud:$feignVersion")
    implementation("com.playtika.reactivefeign:feign-reactor-spring-configuration:$feignVersion")
    implementation("io.github.openfeign:feign-slf4j:11.2")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.register<Copy>("copyJarToDocker") {
    val bootJar = tasks.getByName("bootJar")
    dependsOn(bootJar)
    from(bootJar.outputs.files.singleFile)
    into("../docker-compose/user-service")
}
