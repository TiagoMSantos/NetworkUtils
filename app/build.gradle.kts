plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(apiLevel = Demo.compileSdkVersion)

    defaultConfig {
        applicationId = Demo.applicationId
        minSdkVersion(minSdkVersion = Demo.minSdkVersion)
        targetSdkVersion(targetSdkVersion = Demo.targetSdkVersion)
        versionCode(versionCode = Demo.versionCode)
        versionName = Demo.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Dependencies.jvmTarget
    }
}

dependencies {
    implementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-stdlib:${Dependencies.kotlin}")
    implementation(dependencyNotation = "androidx.core:core-ktx:${Dependencies.core}")
    implementation(dependencyNotation = "androidx.appcompat:appcompat:${Dependencies.appCompat}")
    implementation(dependencyNotation = "com.google.android.material:material:${Versions.material}")
    implementation(dependencyNotation = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    implementation(dependencyNotation = project(path = ":networkutils"))
}