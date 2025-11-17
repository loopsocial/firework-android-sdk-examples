plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.firework.example.storyblock"

    compileSdk = 34

    defaultConfig {
        applicationId = "com.firework.example.storyblock"

        minSdk = 21
        targetSdk = 34

        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        defaultConfig {
            buildConfigField(
                "String",
                "FW_CLIENT_ID",
                "\"f6d6ec1275217f178cdff91363825cb390e038c1168f64f6efa23cb95ec6b325\"",
            )
            buildConfigField("String", "FW_CHANNEL_ID", "\"7RXwK8k\"")
            buildConfigField("String", "FW_PLAYLIST_ID", "\"g4lA0g\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    lint {
        abortOnError = true
        ignoreWarnings = false
        warningsAsErrors = true
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

detekt {
    allRules = true
    config = files("$rootDir/config/detekt/detekt-config.yml")
    baseline = file("detekt-baseline.xml")
    buildUponDefaultConfig = true
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.android.material:material:1.11.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.0"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Firework SDK
    val fireworkBomVersion = "2025.11.17"
    implementation(platform("com.firework:firework-bom:$fireworkBomVersion"))
    implementation("com.firework:sdk")
    implementation("com.firework.external.imageloading:glide") // Glide (optional image loader)
    // implementation("com.firework.external.imageloading:picasso") // Picasso (optional image loader)
}
