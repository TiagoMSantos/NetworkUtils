buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(dependencyNotation = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}")
        classpath(dependencyNotation = "com.google.gms:google-services:${Dependencies.googleServices}")
        classpath(dependencyNotation = "com.android.tools.build:gradle:${Dependencies.gradle}")
        classpath(dependencyNotation = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

task("version") {
    doLast {
        logger.quiet(Lib.versionName)
    }
}