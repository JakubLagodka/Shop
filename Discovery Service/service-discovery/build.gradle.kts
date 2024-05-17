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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-parent:2023.0.1")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:4.1.1")

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