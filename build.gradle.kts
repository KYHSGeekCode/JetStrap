import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
    id("maven-publish")
}
val githubProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "github.properties")))
} //Set env variable GPR_USER & GPR_API_KEY if not adding a properties file
println("Property:" + githubProperties.getProperty("GPR_USER"))

publishing {
    publications {
        create<MavenPublication>("JetStrap") {
            from(components["kotlin"])
            groupId = "com.kyhsgeekcode"
            artifactId = "jetstrap"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            /** Configure path of your package repository on Github
             ** Replace GITHUB_USERID with your/organisation Github userID
             ** and REPOSITORY with the repository name on GitHub
             */
            url = uri("https://maven.pkg.github.com/KYHSGeekCode/JetStrap")
            credentials {
                /** Create github.properties in root project folder file with
                 ** gpr.usr=GITHUB_USER_ID & gpr.key=PERSONAL_ACCESS_TOKEN
                 ** Set env variable GPR_USER & GPR_API_KEY if not adding a properties file**/

                username = githubProperties.getProperty("GPR_USER", System.getenv("GPR_USER"))
                password = githubProperties.getProperty("GPR_API_KEY", System.getenv("GPR_API_KEY"))
            }
        }
    }
}

group = "com.kyhsgeekcode"
version = "1.0.7"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    compileOnly(compose.desktop.currentOs)
//    compileOnly(compose.runtime)
//    compileOnly(compose.material)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "untitled"
            packageVersion = "1.0.0"
        }
    }
}