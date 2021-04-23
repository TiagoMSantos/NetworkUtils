plugins {
    id("com.android.library")
    id("maven-publish")
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(apiLevel = Lib.compileSdkVersion)

    defaultConfig {
        minSdkVersion(minSdkVersion = Lib.minSdkVersion)
        targetSdkVersion(targetSdkVersion = Lib.targetSdkVersion)
        versionCode(versionCode = Lib.versionCode)
        versionName = Lib.versionName
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        renderscriptTargetApi = Lib.compileSdkVersion
        renderscriptSupportModeEnabled = true

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

val publicationName = "networkutils"

publishing {
    fun getGrpUser(): String {
        return if (project.hasProperty("gpr.user")) {
            project.properties["gpr.user"] as String
        } else {
            System.getenv("GPR_USER") ?: ""
        }
    }

    fun getGprKey(): String {
        return if (project.hasProperty("gpr.key")) {
            project.properties["gpr.key"] as String
        } else {
            System.getenv("GPR_API_KEY") ?: ""
        }
    }

    repositories {
        maven {
            name = "NetworkUtils"
            url = uri(Publish.url)
            credentials {
                username = getGrpUser()
                password = getGprKey()
            }
        }
    }
}

publishing {
    publications {
        register<MavenPublication>(publicationName) {
            groupId = "com.tiagomdosantos"
            artifactId = Publish.artifactId
            version = Publish.versionName
            artifact("$buildDir/outputs/aar/${Publish.artifactId}-release.aar")
            pom {
                withXml {
                    asNode().appendNode("dependencies").let { depNode ->
                        configurations.compile.get().allDependencies.forEach {
                            depNode.appendNode("dependency").apply {
                                appendNode("groupId", it.group)
                                appendNode("artifactId", it.name)
                                appendNode("version", it.version)
                            }
                        }
                    }
                }
            }
        }
    }
}

val ktlint by configurations.creating

tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args("--android", "src/**/*.kt")
}

tasks.named("check") {
    dependsOn(ktlint)
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args("--android", "-F", "src/**/*.kt")
}

detekt {
    // Version of Detekt that will be used. When unspecified the latest detekt
    // version found will be used. Override to stay on the same version.
    toolVersion = "1.16.0"

    // The directories where detekt looks for source files.
    // Defaults to `files("src/main/java", "src/main/kotlin")`.
    input = files("src/main/java", "src/main/kotlin")

    // Builds the AST in parallel. Rules are always executed in parallel.
    // Can lead to speedups in larger projects. `false` by default.
    parallel = false

    // Define the detekt configuration(s) you want to use.
    // Defaults to the default detekt configuration.
    config = files("path/to/config.yml")

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = false

    // Turns on all the rules. `false` by default.
    allRules = false

    // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
    baseline = file("path/to/baseline.xml")

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false

    // Adds debug output during task execution. `false` by default.
    debug = false

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = false

    // Android: Don't create tasks for the specified build types (e.g. "release")
    ignoredBuildTypes = listOf("release")

    // Android: Don't create tasks for the specified build flavor (e.g. "production")
    ignoredFlavors = listOf("production")

    // Android: Don't create tasks for the specified build variants (e.g. "productionRelease")
    ignoredVariants = listOf("productionRelease")

    reports {
        // Enable/Disable XML report (default: true)
        xml {
            enabled = true
            destination = file("build/reports/detekt.xml")
        }
        // Enable/Disable HTML report (default: true)
        html {
            enabled = true
            destination = file("build/reports/detekt.html")
        }
        // Enable/Disable TXT report (default: true)
        txt {
            enabled = true
            destination = file("build/reports/detekt.txt")
        }
        // Enable/Disable SARIF report (default: false)
        sarif {
            enabled = true
            destination = file("build/reports/detekt.sarif")
        }
        custom {
            // The simple class name of your custom report.
            reportId = "CustomJsonReport"
            destination = file("build/reports/detekt.json")
        }
    }
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("performDetekt") {
    description = "Runs a custom detekt build."
    setSource(files("src/main/kotlin", "src/test/kotlin"))
    config.setFrom(files("$rootDir/config.yml"))
    debug = true
    reports {
        xml {
            destination = file("build/reports/detektReport.xml")
        }
        html.destination = file("build/reports/detektReport.html")
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
}

dependencies {
    /* Ktlint */
//    ktlint(dependencyNotation = "com.pinterest:ktlint:${Versions.ktlint}")

    api(dependencyNotation = "org.jetbrains.kotlin:kotlin-stdlib:${Dependencies.kotlin}")
    api(dependencyNotation = "org.jetbrains.kotlin:kotlin-reflect:${Dependencies.kotlin}")
    api(dependencyNotation = "androidx.appcompat:appcompat:${Versions.appCompat}")

    /* Multidex */
    api(dependencyNotation = "androidx.multidex:multidex:${Versions.multidex}")

//    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$versions.detekt")
//    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$versions.detekt")

    /* Koin */
    api(dependencyNotation = "org.koin:koin-androidx-fragment:${Versions.koin}")
    api(dependencyNotation = "org.koin:koin-androidx-viewmodel:${Versions.koin}")
    api(dependencyNotation = "org.koin:koin-androidx-scope:${Versions.koin}")
    api(dependencyNotation = "org.koin:koin-core:${Versions.koin}")

    /* Coroutines */
    api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    /* RX */
    api(dependencyNotation = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}")
    api(dependencyNotation = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}")
    api(dependencyNotation = "io.reactivex.rxjava3:rxkotlin:${Versions.rxKotlin}")

    /* Firebase */
    api(dependencyNotation = "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}") {
        exclude(group = "com.android.support")
    }
    api(dependencyNotation = "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}") {
        exclude(group = "com.android.support")
    }
    api(dependencyNotation = "com.google.firebase:firebase-config-ktx:${Versions.firebaseRemoteConfig}") {
        exclude(group = "com.android.support")
    }

    /* Request */
    api(dependencyNotation = "com.google.code.gson:gson:${Versions.gson}")
    api(dependencyNotation = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxAdapter}")
    api(dependencyNotation = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}")
    api(dependencyNotation = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    api(dependencyNotation = "com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    api(dependencyNotation = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}")
    api(dependencyNotation = "com.squareup.okhttp3:okhttp:${Versions.okhttp}")

    /* ROOM */
    api(dependencyNotation = "androidx.room:room-runtime:${Versions.room}")
    api(dependencyNotation = "androidx.room:room-ktx:${Versions.room}")
    kapt(dependencyNotation = "androidx.room:room-compiler:${Versions.room}")

    testImplementation(dependencyNotation = "junit:junit:4.+")
    androidTestImplementation(dependencyNotation = "androidx.test.ext:junit:1.1.2")
    androidTestImplementation(dependencyNotation = "androidx.test.espresso:espresso-core:3.3.0")
}