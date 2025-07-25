plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.firework.example.compose"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.firework.example.compose"

        minSdk = 21
        targetSdk = 35

        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        defaultConfig {
            buildConfigField(
                "String",
                "FW_CLIENT_ID",
                "\"f6d6ec1275217f178cdff91363825cb390e038c1168f64f6efa23cb95ec6b325\"",
            )
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

        disable.apply {
            add("ObsoleteLintCustomCheck")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

detekt {
    allRules = true
    config = files("$rootDir/config/detekt/detekt-config.yml")
    baseline = file("detekt-baseline.xml")
    buildUponDefaultConfig = true
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.material:material:1.7.8")

    // compose
    implementation(platform("androidx.compose:compose-bom:2025.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Firework SDK
    val fireworkBomVersion = "2025.07.18"
    implementation(platform("com.firework:firework-bom:$fireworkBomVersion"))
    implementation("com.firework:sdk")
    implementation("com.firework.external.imageloading:glide") // Glide (optional image loader)
    // implementation("com.firework.external.imageloading:picasso") // Picasso (optional image loader)
}
