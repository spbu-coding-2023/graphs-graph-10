import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "graphs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("org.gephi", "gephi-toolkit", "0.10.1", classifier = "all")
    implementation("org.neo4j.driver", "neo4j-java-driver", "5.6.0")
    implementation("com.darkrockstudios:mpfilepicker:3.1.0")
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
    implementation("cafe.adriel.voyager:voyager-screenmodel:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "graphs"
            packageVersion = "1.0.0"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
