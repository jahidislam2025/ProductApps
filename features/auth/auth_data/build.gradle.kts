plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.8.10"
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.qsoft.auth_data"

}

dependencies {
    implementation(project(Modules.COMMON))
    implementation(project(Modules.AUTH_DOMAIN))
    implementation(project(Modules.DATABASE))
}