plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.qsoft.datastore"

}

dependencies {
    api(libs.datastore.preferences)
    implementation(libs.gson)
}