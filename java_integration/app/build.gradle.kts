plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.firewok.example.java_integration"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.firewok.example.java_integration"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
            buildConfigField("String", "FW_CONTENT_ID", "\"gle8W8\"")
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

//    kotlinOptions {
//        allWarningsAsErrors = true
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.android.material:material:1.11.0")


    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Firework SDK
    val fireworkBomVersion = "2025.12.15"
    implementation(platform("com.firework:firework-bom:$fireworkBomVersion"))
    implementation("com.firework:sdk")
    implementation("com.firework.external.imageloading:glide") // Glide (optional image loader)
    implementation("com.firework.external.livestream:singleHostPlayer")
}