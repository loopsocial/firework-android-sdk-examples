plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "tv.fw.example"

        minSdk = 21
        targetSdk = 32

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        defaultConfig {
            buildConfigField("String",
                "FW_CLIENT_ID",
                "\"f6d6ec1275217f178cdff91363825cb390e038c1168f64f6efa23cb95ec6b325\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.10"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")

    // Firework Android SDK v2
    val fireworkSdkVersion = "1.1.0-rc.8"
    implementation("com.github.loopsocial.firework_sdk_v2:fireworkSdk:$fireworkSdkVersion")

    // Carousel feature
    implementation("com.github.loopsocial.firework_sdk_v2:carouselFeature:$fireworkSdkVersion")

    // Glide (optional)
    implementation("com.github.loopsocial.firework_sdk_v2:glideImageLoader:$fireworkSdkVersion")

    // Picasso (optional)
    implementation("com.github.loopsocial.firework_sdk_v2:picassoImageLoader:$fireworkSdkVersion")
}