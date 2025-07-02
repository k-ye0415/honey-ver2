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
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.splash.screen)
    implementation(libs.naver.map)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.base)
}
