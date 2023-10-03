plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

group = "com.modakmaker.exercise"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-cache")
    implementation ("org.springframework.boot:spring-boot-starter-test")

    implementation("com.h2database:h2:2.2.224")
    implementation("javax.cache:cache-api:1.1.1")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

    implementation("com.bucket4j:bucket4j-core:8.4.0")
    implementation ("com.bucket4j:bucket4j-caffeine:8.4.0")

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // TESTS
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")

}

tasks.test {
    useJUnitPlatform()
}