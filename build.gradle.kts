/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jdbc:3.0.0")
    api("org.springframework.boot:spring-boot-starter-web:3.0.0")
    api("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.0")
    api("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.1.0")
    api("io.github.wimdeblauwe:htmx-spring-boot-thymeleaf:2.0.0")
    api("org.webjars:webjars-locator-core:0.52")
    api("org.webjars:bootstrap:5.2.3")
    api("org.webjars:font-awesome:6.2.0")
    api("org.webjars:popper.js:2.9.3")
    api("org.webjars.npm:htmx.org:1.8.4")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.0")
    testImplementation("com.h2database:h2:2.1.214")
}

group = "com.bjoernkw"
version = "0.0.7-SNAPSHOT"
description = "Schematic"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
