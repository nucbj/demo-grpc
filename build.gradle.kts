import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.2"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.22"
  kotlin("plugin.spring") version "1.9.22"
  id("com.google.protobuf") version "0.9.4"
  id("com.google.cloud.tools.jib") version "3.3.0"
}

group = "com.demo.grpc"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

val grpcVersion = "3.25.3"
val grpcKotlinVersion = "1.4.1"
val grpcProtoVersion = "1.61.1"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")

  // Netty M1
  implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

  // DEV TOOL
  developmentOnly("org.springframework.boot:spring-boot-devtools")

  // R2DBC
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation("io.asyncer:r2dbc-mysql")

  implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
  implementation("io.grpc:grpc-stub:$grpcProtoVersion")
  implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
//	implementation("io.grpc:grpc-netty-shaded:$grpcProtoVersion")
  implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")
  implementation("net.devh:grpc-client-spring-boot-starter:2.15.0.RELEASE")

  implementation("org.springframework.boot:spring-boot-starter-security")
  testImplementation("org.springframework.security:spring-security-test")

  // jwt
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

sourceSets {
  main {
    java {
      srcDir("build/generated/source/proto/main/java")
      srcDir("build/generated/source/proto/main/kotlin")
    }
  }
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:$grpcVersion"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
    }
    id("grpckt") {
      artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
    }
  }
  generateProtoTasks {
    all().forEach {
      it.plugins {
        id("grpc")
        id("grpckt")
      }
      it.builtins {
        id("kotlin")
      }
    }
  }
}

jib {
  from {
    image = "openjdk:17"
  }
  to {
    image = "gcr.io/my-project/my-app"
  }
  container {
    mainClass = "com.demo.grpc.main.MainApplicationKt"
  }
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
