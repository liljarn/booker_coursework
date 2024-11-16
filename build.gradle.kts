import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.9.4"
}

group = "ru.liljarn"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.28.2"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.68.0"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") { }
                id("grpckt") { }
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("src/main/proto")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // gRPC
    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")
    api(platform("io.grpc:grpc-bom:1.68.0"))
    api("io.grpc:grpc-api")
    api("io.grpc:grpc-core")
    api("io.grpc:grpc-protobuf")
    api("io.grpc:grpc-kotlin-stub:1.4.1")
    api("io.grpc:grpc-stub")
    api("io.grpc:grpc-netty-shaded")
    api("com.google.protobuf:protobuf-java-util:4.28.2")
    api("com.google.protobuf:protobuf-kotlin:4.28.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
