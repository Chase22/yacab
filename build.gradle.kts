plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.21"
    id("org.jetbrains.kotlin.kapt") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.21"
    id("groovy")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.3.2"
}

version = "0.1"
group = "io.github.chase22.telegram"

// Fetch auth tokens
val awsAuthTokenProcess = ProcessBuilder().command(
    "aws", "codeartifact", "get-authorization-token",
    "--domain", "chase",
    "--domain-owner", "186482393463",
    "--query", "authorizationToken",
    "--output", "text"
).start()
awsAuthTokenProcess.waitFor(1, TimeUnit.MINUTES)
val awsAuthToken = awsAuthTokenProcess.inputStream.bufferedReader().readText()


val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://chase-186482393463.d.codeartifact.eu-central-1.amazonaws.com/maven/chase-repository/")
        credentials {
            username = "aws"
            password = awsAuthToken
        }
    }
}

micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("io.github.chase22.telegram.*")
    }
}

dependencies {
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.beanvalidation:micronaut-hibernate-validator")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")

    implementation("io.micronaut.sql:micronaut-jasync-sql")
    implementation("com.github.jasync-sql:jasync-postgresql:1.1.6")

    implementation("org.telegram:telegrambots:5.0.1.1")
    implementation("org.telegram:telegrambotsextensions:5.0.1.1")
    implementation("io.github.chase22.telegram:telegrambot-library:0.1.2-SNAPSHOT")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:spock")
    testImplementation("org.testcontainers:postgresql")
}


application {
    mainClass.set("io.github.chase22.telegram.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }


}
