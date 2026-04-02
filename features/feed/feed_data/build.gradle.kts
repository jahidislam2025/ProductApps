plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.qsoft.feed_data"

}

dependencies {
    implementation(project(Modules.COMMON))
    implementation(project(Modules.FEED_DOMAIN))
    implementation(project(Modules.DATABASE))
    implementation(project(Modules.NETWORK))

}