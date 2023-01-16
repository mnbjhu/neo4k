import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    `maven-publish`
}

group = "uk.gibby.krg"
version = "0.0.1-alpha"

repositories {
    mavenCentral()
    uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    api(kotlin("reflect"))
    api("io.ktor:ktor-client-core:2.2.1")
    api("io.ktor:ktor-client-content-negotiation:2.2.1")
    api("io.ktor:ktor-serialization-kotlinx-json:2.2.1")
    api("io.ktor:ktor-client-cio:2.2.1")
    api("io.ktor:ktor-client-auth:2.2.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api("io.ktor:ktor-client-logging:2.2.1")
    testApi("com.natpryce:konfig:1.6.10.0")
    testApi(kotlin("test"))
    testApi("org.amshove.kluent:kluent:1.72")
    api("ch.qos.logback:logback-classic:1.4.5")

    implementation("org.neo4j.driver:neo4j-java-driver:5.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "uk.gibby.krg"
            artifactId = "KotlinRedisGraph"
            version = "0.0.1-alpha"
            from(components["java"])
        }
    }
}