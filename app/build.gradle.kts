plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.eventreminderapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.eventreminderapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.espresso.core)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.play.services.basement)
    implementation(libs.transport.api)
    implementation(libs.transport.api)
    implementation(libs.transport.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    // Optional: Room KTX for coroutine support
    implementation(libs.androidx.room.ktx)

    implementation (libs.firebase.messaging)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.core.ktx.v1120)

    implementation (libs.androidx.work.runtime.ktx.v290)

    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.androidx.material.icons.extended)



}