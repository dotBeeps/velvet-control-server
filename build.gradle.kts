import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "dog.beepboop"
version = "0.0.3"
description = "VelvetControlServer"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.amazonaws:aws-java-sdk-dynamodb:1.12.681")
    implementation("io.github.boostchicken:spring-data-dynamodb:5.2.5")
    implementation("org.springframework.cloud:spring-cloud-aws-core:2.2.6.RELEASE")
    implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:2.3.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.4")
    implementation("com.github.twitch4j:twitch4j:1.19.0")
    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("com.github.ben-manes.caffeine:caffeine:2.9.3")
    implementation("com.github.philippheuer.events4j:events4j-kotlin:0.12.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
