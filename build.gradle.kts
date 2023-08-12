buildscript {

    val agp_version by extra("7.2.0")
    val kotlinVersion = "1.6.21"
    val naVersion = "2.5.0"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$naVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}