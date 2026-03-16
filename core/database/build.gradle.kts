plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.qsoft.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.room.coroutine)
    implementation(libs.room.runtime)
}