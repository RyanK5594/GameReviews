plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.gamereviewapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gamereviewapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation(libs.androidx.navigation.testing)

    testImplementation(libs.junit)
    testImplementation ("org.mockito:mockito-core:5.11.0")
    androidTestImplementation ("org.mockito:mockito-android:5.11.0")
    androidTestImplementation ("com.google.android.gms:play-services-tasks:18.0.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    androidTestUtil ("androidx.test:orchestrator:1.4.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.1")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}