import com.aliucord.gradle.AliucordExtension
import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliucord.com/snapshots")
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.github.Aliucord:gradle:main-SNAPSHOT") {
            exclude("com.github.js6pak", "jadb")
        }
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliucord.com/snapshots")
    }
}

fun Project.aliucord(configuration: AliucordExtension.() -> Unit) =
        extensions.getByName<AliucordExtension>("aliucord").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) =
        extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "com.aliucord.gradle")
    // Remove if using Java
    apply(plugin = "kotlin-android")

    // Fill out with your info
    aliucord {
        val baseGithubUrl = "https://raw.githubusercontent.com/techs-sus/plugins"
        author("real_person", 952728708303699978L)
        updateUrl.set("$baseGithubUrl/builds/updater.json")
        buildUrl.set("$baseGithubUrl/builds/%s.zip")
    }

    android {
        compileSdkVersion(30)

        defaultConfig {
            minSdk = 24
            targetSdk = 30
            versionCode = 1
            versionName = "1.0"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "11" // Required
                // Disables some unnecessary features
                freeCompilerArgs =
                        freeCompilerArgs +
                                "-Xno-call-assertions" +
                                "-Xno-param-assertions" +
                                "-Xno-receiver-assertions"
            }
        }
    }

    dependencies {
        "discord"("com.discord:discord:aliucord-SNAPSHOT")
        "implementation"("com.aliucord:Aliucord:main-SNAPSHOT")

        "implementation"("androidx.appcompat:appcompat:1.4.0")
        "implementation"("com.google.android.material:material:1.5.0")
    }
}

task<Delete>("clean") { delete(rootProject.buildDir) }
