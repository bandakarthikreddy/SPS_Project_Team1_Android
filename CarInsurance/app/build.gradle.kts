plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.singlepointsol.carinsurance"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.singlepointsol.carinsurance"
        minSdk = 26
        targetSdk = 35
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
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Splash Screen Dependency
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Navigation Dependency
    val nav_version = "2.8.5"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    val retrofit_version = "2.11.0"

    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.google.code.gson:gson:$retrofit_version")

    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    // Live Data Dependency
    val lifecycle_version = "2.8.7"

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Material 3 Dependency
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.4.0-alpha05")

    // Material Dependency
    implementation("androidx.compose.material:material:1.7.6")
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")
    implementation ("androidx.compose.material3:material3:1.2.0")

    // Animation Dependency
    implementation("androidx.compose.animation:animation:1.7.6")


    implementation ("androidx.compose.runtime:runtime:1.7.6")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.7.6")
    implementation ("androidx.compose.ui:ui:1.7.6")


    implementation ("androidx.activity:activity-compose:1.9.3")

    implementation ("com.google.code.gson:gson:2.10.1")


}