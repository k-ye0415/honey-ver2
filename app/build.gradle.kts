import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.jin.honey"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jin.honey"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val unsplashApiKey: String = findApiKey("UNSPLASH_API_KEY")
        val naverClientId: String = findApiKey("NAVER_MAP_CLIENT_ID")
        val naverClientSecret: String = findApiKey("NAVER_MAP_CLIENT_SECRET")
        val kakaoMapAK: String = findApiKey("KAKAO_MAP_AK")
        val openAiApiKey: String = findApiKey("OPEN_AI_KEY")
        defaultConfig {
            buildConfigField("String", "UNSPLASH_API_KEY", "\"$unsplashApiKey\"")
            buildConfigField("String", "NAVER_MAP_CLIENT_ID", "\"$naverClientId\"")
            buildConfigField("String", "NAVER_MAP_CLIENT_SECRET", "\"$naverClientSecret\"")
            buildConfigField("String", "KAKAO_MAP_AK", "\"$kakaoMapAK\"")
            buildConfigField("String", "OPEN_AI_KEY", "\"$openAiApiKey\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

private fun findApiKey(keyName: String): String {
    val localPropertiesFile = rootProject.file("local.properties")
    return if (localPropertiesFile.exists()) {
        val properties = Properties().apply {
            load(localPropertiesFile.inputStream())
        }
        properties.getProperty(keyName).orEmpty()
    } else {
        System.getenv(keyName).orEmpty()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.splash.screen)

    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.base)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.datastore)

    implementation(libs.naver.map)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.room.paging)
}
