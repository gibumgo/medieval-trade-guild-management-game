plugins {
    kotlin("jvm") version "2.2.0"
}

group = "io.github.gibumgo"
version = "0.1.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("com.github.woowacourse-projects:mission-utils:1.2.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
