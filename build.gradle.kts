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
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(files("libs/gephi-toolkit-0.10.0-all.jar"))
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

tasks.register("downloadGephiToolkit") {
    val path = "libs/gephi-toolkit-0.10.0-all.jar"
    val sourceUrl = "https://github.com/gephi/gephi-toolkit/releases/download/v0.10.0/gephi-toolkit-0.10.0-all.jar"

    val libsDirectory = File("libs")
    val jarFile = File(path)

    if (!libsDirectory.exists())
        libsDirectory.mkdir()

    if (!jarFile.exists())
        download(sourceUrl, path)
}

tasks.build {
    dependsOn("downloadGephiToolkit")
}

fun download(url: String, path: String){
    val destinationFile = File(path)
    ant.invokeMethod("get", mapOf("src" to url, "dest" to destinationFile))
}