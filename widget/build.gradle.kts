plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.tenkey.kotoba.widget"
    compileSdk = 36
    defaultConfig {
        applicationId = "dev.tenkey.kotoba.widget"
        minSdk = 26
        targetSdk = 36
        versionCode = 12000
        versionName = "1.2.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    sourceSets { getByName("main").assets.srcDir("../shared") }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("junit:junit:4.13.2")
}
