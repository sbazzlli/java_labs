plugins {
    java
    application
}

group = "edu.semitotal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jsoupVersion = "1.21.2"

dependencies {
    implementation("org.jsoup:jsoup:$jsoupVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass.set("org.example.Main")
}

