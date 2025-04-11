import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}


android {
    namespace = "com.bitinovus.bos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bitinovus.bos"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiBaseURL = gradleLocalProperties(rootDir, providers)
            .getProperty("API_BASE_URL", "")
        val token = gradleLocalProperties(rootDir, providers)
            .getProperty("TOKEN", "")
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseURL\"")
        buildConfigField("String", "TOKEN", "\"$token\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // CameraX core library using the camera2 implementation
    val camerax_version = "1.5.0-alpha06"

    val retrofit_version = "2.9.0"
    val gson_version = "2.10.1"

    val lifecycle_version = "2.8.7"

    val nav_version = "2.8.9"



    // CameraX
    implementation("androidx.camera:camera-camera2:${camerax_version}")

    // CameraX Lifecycle library
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")

    // CameraX ML Kit Vision Integration
    implementation("androidx.camera:camera-mlkit-vision:${camerax_version}")

    // CameraX View class
    implementation("androidx.camera:camera-view:${camerax_version}")


    // Barcode
    implementation("com.google.mlkit:barcode-scanning:17.3.0")



    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.google.code.gson:gson:$gson_version")

    // Logging for testing
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // COIL
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Jetpack Compose Navigation integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}