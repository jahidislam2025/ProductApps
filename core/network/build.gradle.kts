plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "2.0.21"
    id ("kotlin-parcelize")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.qsoft.network"
}

dependencies {

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
   implementation(libs.kotlinx.serialization.json)
   // implementation(libs.datastore.preferences)
//    implementation(libs.gson)
    implementation (libs.converter.gson)
}