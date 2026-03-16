plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.qsoft.domain"

}

dependencies {

}