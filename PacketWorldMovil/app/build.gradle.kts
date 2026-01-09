plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Agrega el plugin de kotlin-kapt si aún no está presente
    kotlin("kapt")
}

android {
    namespace = "uv.tc.packetworldmovil"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "uv.tc.packetworldmovil"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Dependencias para Data Binding
    implementation("androidx.databinding:databinding-runtime:8.1.3")
    implementation("androidx.databinding:databinding-compiler:8.1.3")

    // Dependencias para Gson y Ion
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.koushikdutta.ion:ion:3.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}