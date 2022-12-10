import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
}

group = "uk.gibby.krg"
version = "0.0.2-pre-release"

repositories {
    mavenCentral()
}

dependencies {
    api("redis.clients:jedis:4.3.1")
    api("org.neo4j.driver:neo4j-java-driver:5.3.0")
    api(kotlin("reflect"))
    testApi("com.natpryce:konfig:1.6.10.0")
    testApi(kotlin("test"))
    testApi("org.amshove.kluent:kluent:1.72")

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
            version = "0.0.2-pre-release"
            from(components["java"])
        }
    }
}