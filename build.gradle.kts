plugins {
    kotlin("jvm") version "1.8.20"
    id("org.openjfx.javafxplugin") version "0.0.13"
    application
}

group = "com.facturo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")
    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
    implementation("com.itextpdf:itextpdf:5.5.13.3")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    testImplementation(kotlin("test"))
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.graphics")
}

application {
    mainClass.set("com.facturo.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

// Set Java compatibility to match Kotlin
tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}