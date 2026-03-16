plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.qsoft.feed_presentation"
}

dependencies {
    implementation(project(Modules.FEED_DOMAIN))

    implementation(project(Modules.DATASTORE))
    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.COMMON))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.UI))
}