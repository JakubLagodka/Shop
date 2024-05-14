plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "6.0.0"
}

group = "bench"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi:2.5.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.1")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.1.1")
    implementation("org.zalando:problem-spring-web-starter:0.29.1")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("io.jsonwebtoken:jjwt:0.12.3")
    compileOnly("org.projectlombok:lombok:1.18.32")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
openApiGenerate {
    generatorName = "spring"
    inputSpec = "$projectDir/src/main/resources/api.yaml"
    outputDir = "$projectDir"
    apiPackage = "bench.artshop.generatedApi"
    modelPackage = "bench.artshop.generatedModel"
    invokerPackage = "bench.artshop.generatedModel.invoker"
}

