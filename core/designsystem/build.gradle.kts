plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.qsoft.designsystem"

}

dependencies {
implementation(project(Modules.COMMON))
}