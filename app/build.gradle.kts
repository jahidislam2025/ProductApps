plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //id("com.google.gms.google-services")
    id("kotlin-kapt")
    //id("com.google.devtools.ksp")
    id ("dagger.hilt.android.plugin")
   // alias(libs.plugins.googleServices)
}

android {
    namespace = ProjectConfig.APP_ID
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = ProjectConfig.APP_ID
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = ProjectConfig.VERSION_CODE
        versionName = ProjectConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.JVM_TARGET
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.navigation.compose)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.navigation.compose)
    //implementation(libs.firebase.bom)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(Modules.DESIGN_SYSTEM))
    implementation(project(Modules.COMMON))
    implementation(project(Modules.DATABASE))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.DATASTORE))

    implementation(project(Modules.AUTH_PRESENTATION))
    implementation(project(Modules.AUTH_DOMAIN))
    implementation(project(Modules.AUTH_DATA))
    implementation(project(Modules.FEED_DATA))
    implementation(project(Modules.FEED_DOMAIN))
    implementation(project(Modules.FEED_PRESENTATION))
    /*

    implementation(project(Modules.DATASTORE))

    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.DATA))
    implementation(project(Modules.UI))*/



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}