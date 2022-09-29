plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tv.fw.example.discovery"

    compileSdk = 33

    defaultConfig {
        applicationId = "tv.fw.example.discovery"

        minSdk = 21
        targetSdk = 33

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.10"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")

    // Firework Android SDK v2
    val fireworkSdkVersion = "1.2.0-SNAPSHOT.7"
    implementation("com.github.loopsocial.firework_sdk_v2:fireworkSdk:$fireworkSdkVersion")

    // Glide (optional image loader)
    implementation("com.github.loopsocial.firework_sdk_v2:glideImageLoader:$fireworkSdkVersion")

    // Picasso (optional image loader)
    // implementation("com.github.loopsocial.firework_sdk_v2:picassoImageLoader:$fireworkSdkVersion")

    // Required to avoid crash on Android 12 API 31
    // https://stackoverflow.com/a/69152986/2359762
    implementation("androidx.work:work-runtime-ktx:2.7.1")
}
