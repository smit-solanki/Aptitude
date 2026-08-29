plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android") version "2.3.20" apply false
}

android {
    namespace = "com.smit.frenzyaptitude"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.smit.frenzyaptitude"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.kotlin_module"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Core Android & UI
    implementation(libs.appcompat)           // Managed by libs.versions.toml
    implementation(libs.material)            // Latest Material Design components
    implementation(libs.activity)            // For ComponentActivity support
    implementation(libs.constraintlayout)    // For responsive layouts

    // Additional UI Components
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Google Play Services (Ads)
    // Always use the latest version (23.0.0) and remove the older (22.6.0)
    implementation("com.google.android.gms:play-services-ads:23.0.0")

    // Unit Testing
    testImplementation(libs.junit)

    // Instrumented Testing
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}