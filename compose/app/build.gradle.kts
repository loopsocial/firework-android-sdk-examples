plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "tv.fw.example.compose"

    compileSdk = 33

    defaultConfig {
        applicationId = "tv.fw.example.compose"

        minSdk = 21
        targetSdk = 33

        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        defaultConfig {
            buildConfigField("String", "FW_CLIENT_ID", "\"f6d6ec1275217f178cdff91363825cb390e038c1168f64f6efa23cb95ec6b325\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lint {
        abortOnError = true
        ignoreWarnings = false
        warningsAsErrors = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

detekt {
    allRules = true
    config = files("$rootDir/config/detekt/detekt-config.yml")
    baseline = file("detekt-baseline.xml")
    buildUponDefaultConfig = true
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.material:material:1.3.1")

    // compose
    val composeUiVersion = "1.3.1"
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    val fireworkSdkVersion = "6.0.0-beta.1"
    implementation("com.github.loopsocial.firework_sdk_v2:fireworkSdk:$fireworkSdkVersion")

    // Glide Impl (Optional)
    implementation("com.github.loopsocial.firework_sdk_v2:glideImageLoader:$fireworkSdkVersion")
}
